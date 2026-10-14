-- Ejecutar en MySQL Workbench con una cuenta administradora de laboratorio.

-- ============================================================
-- SECCION 1 · La base de datos y la tabla     --> Paso 3

CREATE DATABASE IF NOT EXISTS flota_vehicular
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
  
USE flota_vehicular;

CREATE TABLE IF NOT EXISTS vehiculo (
  placa VARCHAR(12) PRIMARY KEY,
  marca VARCHAR(80) NOT NULL,
  kilometraje INT NOT NULL,
  estado ENUM('DISPONIBLE','EN_RUTA','TALLER') NOT NULL,
  tipo ENUM('COMBUSTION','ELECTRICO','PESADO') NOT NULL,
  nivel_energia DECIMAL(5,2) NOT NULL,
  ciclos_carga INT NOT NULL DEFAULT 0,
  tonelaje_maximo DECIMAL(8,2) NULL,
  carga_actual DECIMAL(8,2) NULL,
  tipo_carga ENUM('NORMAL','PELIGROSA','REFRIGERADA') NULL,
  CONSTRAINT chk_kilometraje CHECK (kilometraje >= 0),
  CONSTRAINT chk_energia CHECK (nivel_energia >= 0 AND nivel_energia <= 100),
  CONSTRAINT chk_ciclos CHECK (ciclos_carga >= 0),
  CONSTRAINT chk_pesado CHECK ((tipo = 'PESADO' AND tonelaje_maximo > 0 AND carga_actual >= 0 AND tipo_carga IS NOT NULL)
      OR (tipo <> 'PESADO' AND tonelaje_maximo IS NULL AND carga_actual IS NULL AND tipo_carga IS NULL))
);

-- ============================================================



-- ============================================================
-- SECCION 2 · Los usuarios                    --> Paso 4
-- ============================================================
CREATE USER IF NOT EXISTS 'flota_definer'@'localhost' IDENTIFIED BY 'root';
GRANT SELECT, INSERT, UPDATE, DELETE ON flota_vehicular.vehiculo TO 'flota_definer'@'localhost';
CREATE USER IF NOT EXISTS 'flota_app'@'localhost' IDENTIFIED BY 'root';
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'flota_app'@'localhost';


SHOW GRANTS FOR 'flota_definer'@'localhost';
SHOW GRANTS FOR 'flota_app'@'localhost';


-- ============================================================
-- SECCION 3 · Los procedimientos almacenados  --> Paso 5
-- ============================================================
-- 5.1 Borrar los anteriores
DROP PROCEDURE IF EXISTS sp_listar_vehiculos;
DROP PROCEDURE IF EXISTS sp_insertar_vehiculo;
DROP PROCEDURE IF EXISTS sp_actualizar_vehiculo;
DROP PROCEDURE IF EXISTS sp_eliminar_vehiculo;

-- 5.2 Cambiar delimitador
DELIMITER $$

-- 5.4 Procedimiento 1 · Listar
CREATE DEFINER='flota_definer'@'localhost' PROCEDURE sp_listar_vehiculos()
SQL SECURITY DEFINER
BEGIN
  SELECT placa, marca, kilometraje, estado, tipo, nivel_energia, ciclos_carga,
         tonelaje_maximo, carga_actual, tipo_carga
  FROM vehiculo
  ORDER BY placa;
END$$

-- 5.5 Procedimiento 2 · Insertar
CREATE DEFINER='flota_definer'@'localhost' PROCEDURE sp_insertar_vehiculo(
  IN p_placa VARCHAR(12), IN p_marca VARCHAR(80), IN p_kilometraje INT,
  IN p_estado VARCHAR(20), IN p_tipo VARCHAR(20), IN p_energia DECIMAL(5,2),
  IN p_ciclos INT, IN p_tonelaje DECIMAL(8,2), IN p_carga DECIMAL(8,2), IN p_tipo_carga VARCHAR(20))
SQL SECURITY DEFINER
BEGIN
  INSERT INTO vehiculo(placa,marca,kilometraje,estado,tipo,nivel_energia,ciclos_carga,tonelaje_maximo,carga_actual,tipo_carga)
  VALUES (p_placa,p_marca,p_kilometraje,p_estado,p_tipo,p_energia,p_ciclos,p_tonelaje,p_carga,p_tipo_carga);
END$$

-- 5.6 Procedimiento 3 · Actualizar
CREATE DEFINER='flota_definer'@'localhost' PROCEDURE sp_actualizar_vehiculo(
  IN p_placa VARCHAR(12), IN p_marca VARCHAR(80), IN p_kilometraje INT,
  IN p_estado VARCHAR(20), IN p_tipo VARCHAR(20), IN p_energia DECIMAL(5,2),
  IN p_ciclos INT, IN p_tonelaje DECIMAL(8,2), IN p_carga DECIMAL(8,2), IN p_tipo_carga VARCHAR(20))
SQL SECURITY DEFINER
BEGIN
  UPDATE vehiculo
  SET marca=p_marca, kilometraje=p_kilometraje, estado=p_estado, tipo=p_tipo,
      nivel_energia=p_energia, ciclos_carga=p_ciclos, tonelaje_maximo=p_tonelaje,
      carga_actual=p_carga, tipo_carga=p_tipo_carga
  WHERE placa=p_placa;

  IF ROW_COUNT() = 0 AND NOT EXISTS (SELECT 1 FROM vehiculo WHERE placa=p_placa) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='No existe un vehículo con esa placa';
  END IF;
END$$

-- 5.7 Procedimiento 4 · Eliminar
CREATE DEFINER='flota_definer'@'localhost' PROCEDURE sp_eliminar_vehiculo(IN p_placa VARCHAR(12))
SQL SECURITY DEFINER
BEGIN
  DELETE FROM vehiculo WHERE placa=p_placa;
  IF ROW_COUNT() = 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='No existe un vehículo con esa placa';
  END IF;
END$$

-- 5.8 Devolver delimitador
DELIMITER ;

-- ============================================================
-- SECCION 4 · Los permisos de la aplicación   --> Paso 6
-- ============================================================

-- 6.1 Permisos del DEFINER
GRANT SELECT, INSERT, UPDATE, DELETE ON flota_vehicular.vehiculo TO 'flota_definer'@'localhost';
GRANT EXECUTE ON flota_vehicular.* TO 'flota_definer'@'localhost';

-- 6.2 Permisos de la aplicación (cuenta flota_app)
GRANT EXECUTE ON PROCEDURE flota_vehicular.sp_listar_vehiculos   TO 'flota_app'@'localhost';
GRANT EXECUTE ON PROCEDURE flota_vehicular.sp_insertar_vehiculo  TO 'flota_app'@'localhost';
GRANT EXECUTE ON PROCEDURE flota_vehicular.sp_actualizar_vehiculo TO 'flota_app'@'localhost';
GRANT EXECUTE ON PROCEDURE flota_vehicular.sp_eliminar_vehiculo  TO 'flota_app'@'localhost';

-- 6.3 Recargar permisos
FLUSH PRIVILEGES;
