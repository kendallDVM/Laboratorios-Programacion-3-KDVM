package lab.flotavehicular;

import lab.flotavehicular.model.*;
import lab.flotavehicular.repository.PersistenciaException;
import lab.flotavehicular.repository.RepositorioVehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación JDBC. La aplicación únicamente llama procedimientos almacenados.
 */
public class RepositorioVehiculoMySQL implements RepositorioVehiculo {

    // 9.2 Atributos de conexión
    private final String url;
    private final String usuario;
    private final String clave;

    // 9.3 Constructor: lee credenciales del entorno
    public RepositorioVehiculoMySQL() {
        this.url     = variable("FLOTA_DB_URL", "jdbc:mysql://localhost:3306/flota_vehicular?serverTimezone=UTC");
        this.usuario = variable("FLOTA_DB_USER", null);
        this.clave   = variable("FLOTA_DB_PASSWORD", null);
        if (usuario == null || usuario.isBlank() || clave == null) {
            throw new PersistenciaException("Configure FLOTA_DB_USER y FLOTA_DB_PASSWORD como variables de entorno.", null);
        }
    }

    // 9.4 Método auxiliar para leer variables
    private static String variable(String nombre, String predeterminado) {
        String valor = System.getenv(nombre);
        return valor == null || valor.isBlank() ? predeterminado : valor;
    }

    // 9.5 Conectar
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, clave);
    }

    // 9.6 Consultar todos los vehículos
    @Override
    public List<Vehiculo> cargarTodos() {
        List<Vehiculo> resultado = new ArrayList<>();
        try (Connection c = conectar();
             CallableStatement cs = c.prepareCall("{CALL sp_listar_vehiculos()}");
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) resultado.add(mapear(rs));
            return resultado;
        } catch (SQLException e) {
            throw fallo("consultar la flota", e);
        }
    }

    // 9.7 Insertar y actualizar
    @Override
    public void insertar(Vehiculo v) {
        ejecutarGuardar("{CALL sp_insertar_vehiculo(?,?,?,?,?,?,?,?,?,?)}", v);
    }

    @Override
    public void actualizar(Vehiculo v) {
        ejecutarGuardar("{CALL sp_actualizar_vehiculo(?,?,?,?,?,?,?,?,?,?)}", v);
    }

    // 9.8 Método compartido
    private void ejecutarGuardar(String llamada, Vehiculo v) {
        try (Connection c = conectar(); CallableStatement cs = c.prepareCall(llamada)) {
            llenarParametros(cs, v);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw fallo("guardar el vehículo", e);
        }
    }

    // 9.9 Eliminar por placa
    @Override
    public void eliminar(String placa) {
        try (Connection c = conectar(); CallableStatement cs = c.prepareCall("{CALL sp_eliminar_vehiculo(?)}")) {
            cs.setString(1, placa);
            cs.executeUpdate();
        } catch (SQLException e) {
            throw fallo("eliminar el vehículo", e);
        }
    }

    // 9.10 Llenar parámetros
    private void llenarParametros(CallableStatement cs, Vehiculo v) throws SQLException {
        cs.setString(1, v.getPlaca());
        cs.setString(2, v.getMarca());
        cs.setInt(3, v.getKilometraje());
        cs.setString(4, v.getEstado().name());
        cs.setString(5, v.getTipo().name());

        double energia = 0; int ciclos = 0; double tonelaje = 0; double carga = 0; String tipoCarga = null;
        if (v instanceof VehiculoPesado p) {
            energia = p.getNivelCombustible(); tonelaje = p.getTonelajeMaximo(); carga = p.getCargaActual(); tipoCarga = p.getTipoCarga().name();
        } else if (v instanceof VehiculoElectrico e) {
            energia = e.getPorcentajeBateria(); ciclos = e.getCiclosDeCarga();
        } else if (v instanceof VehiculoCombustion c) {
            energia = c.getNivelCombustible();
        }

        cs.setDouble(6, energia); cs.setInt(7, ciclos);
        if (v instanceof VehiculoPesado) cs.setDouble(8, tonelaje); else cs.setNull(8, Types.DECIMAL);
        if (v instanceof VehiculoPesado) cs.setDouble(9, carga);    else cs.setNull(9, Types.DECIMAL);
        if (tipoCarga == null) cs.setNull(10, Types.VARCHAR); else cs.setString(10, tipoCarga);
    }

    // 9.11 Mapear ResultSet a objeto
    private Vehiculo mapear(ResultSet rs) throws SQLException {
        String placa = rs.getString("placa"); String marca = rs.getString("marca");
        int km = rs.getInt("kilometraje"); double energia = rs.getDouble("nivel_energia");
        TipoVehiculo tipo = TipoVehiculo.valueOf(rs.getString("tipo"));
        Vehiculo v = switch (tipo) {
            case COMBUSTION -> new VehiculoCombustion(placa, marca, km, energia);
            case ELECTRICO  -> new VehiculoElectrico(placa, marca, km, energia, rs.getInt("ciclos_carga"));
            case PESADO -> {
                VehiculoPesado p = new VehiculoPesado(placa, marca, km, energia,
                        rs.getDouble("tonelaje_maximo"), TipoCarga.valueOf(rs.getString("tipo_carga")));
                double carga = rs.getDouble("carga_actual"); if (carga > 0) p.cargarMercancia(carga);
                yield p;
            }
        };
        v.setEstado(EstadoVehiculo.valueOf(rs.getString("estado")));
        return v;
    }

    // 9.12 Manejo de errores
    private PersistenciaException fallo(String operacion, SQLException e) {
        return new PersistenciaException("No fue posible " + operacion + ". Verifique conexión, permisos y datos.", e);
    }
}
