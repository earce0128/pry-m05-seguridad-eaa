-- ----------------------------------------------------
-- Esquema: bd_cotizaciones
-- MySQL 8, InnoDB, utf8mb4
-- ----------------------------------------------------
drop database `bd_cotizaciones`;

CREATE DATABASE IF NOT EXISTS `bd_cotizaciones`
  DEFAULT CHARACTER SET = utf8mb4
  DEFAULT COLLATE = utf8mb4_unicode_ci;
USE `bd_cotizaciones`;

--------------------------------------------------------------------------------
-- Tabla: cotizacion
--------------------------------------------------------------------------------
CREATE TABLE `cotizacion` (
  `num_cotizacion` BIGINT NOT NULL AUTO_INCREMENT,
  `fec_cotizacion` DATETIME NOT NULL,
  PRIMARY KEY (`num_cotizacion`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: promocion
--------------------------------------------------------------------------------
CREATE TABLE `promocion` (
  `num_promocion` BIGINT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(255) NOT NULL,
  `descripcion` VARCHAR(255),
  `fec_vigencia_desde` DATE NOT NULL,
  `fec_vigencia_hasta` DATE NOT NULL,
  PRIMARY KEY (`num_promocion`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: componente
--------------------------------------------------------------------------------
CREATE TABLE `componente` (
  `id_componente` VARCHAR(40) NOT NULL,
  `descripcion` VARCHAR(255),
  `marca` VARCHAR(255),
  `modelo` VARCHAR(255),
  `costo` DECIMAL(19,2),
  `precio_base` DECIMAL(19,2),
  `tipo` VARCHAR(20),
  `capacidad_alm` VARCHAR(255),
  `memoria` VARCHAR(255),
  `num_promocion` BIGINT,
  PRIMARY KEY (`id_componente`),
  INDEX `idx_componente_promocion` (`num_promocion`),
  CONSTRAINT `fk_componente_promocion`
    FOREIGN KEY (`num_promocion`)
    REFERENCES `promocion` (`num_promocion`)
    ON UPDATE CASCADE
    ON DELETE SET NULL
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: detalle_cotizacion
--------------------------------------------------------------------------------
CREATE TABLE `detalle_cotizacion` (
  `num_cotizacion` BIGINT NOT NULL,
  `num_detalle` INT NOT NULL,
  `id_componente` VARCHAR(40) NOT NULL,
  `cantidad` INT NOT NULL,
  `precio_base` DECIMAL(19,2) NOT NULL,
  `importe_cotizado` DECIMAL(19,2) NOT NULL,
  `categoria` VARCHAR(255),
  `descripcion` VARCHAR(255),
  PRIMARY KEY (`num_cotizacion`, `num_detalle`),
  INDEX `idx_det_cotizacion_cot` (`num_cotizacion`),
  INDEX `idx_det_cotizacion_comp` (`id_componente`),
  CONSTRAINT `fk_detalle_cotizacion_cotizacion`
    FOREIGN KEY (`num_cotizacion`)
    REFERENCES `cotizacion` (`num_cotizacion`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_detalle_cotizacion_componente`
    FOREIGN KEY (`id_componente`)
    REFERENCES `componente` (`id_componente`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: detalle_promocion
--------------------------------------------------------------------------------
CREATE TABLE `detalle_promocion` (
  `num_promocion` BIGINT NOT NULL,
  `num_det_promocion` INT NOT NULL,
  `nombre` VARCHAR(255),
  `descripcion` VARCHAR(255),
  `es_base` TINYINT(1) NOT NULL,
  `tipo_prom_base` VARCHAR(20),
  `porc_dscto_plano` DECIMAL(10,2),
  `tipo_prom_acumulable` VARCHAR(20),
  `lleve_n` INT,
  `pague_m` INT,
  PRIMARY KEY (`num_promocion`, `num_det_promocion`),
  INDEX `idx_det_promocion_prom` (`num_promocion`),
  CONSTRAINT `fk_detalle_promocion_promocion`
    FOREIGN KEY (`num_promocion`)
    REFERENCES `promocion` (`num_promocion`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: detalle_promp_dscto_x_cant
--------------------------------------------------------------------------------
CREATE TABLE `detalle_promp_dscto_x_cant` (
  `num_promocion` BIGINT NOT NULL,
  `num_det_promocion` INT NOT NULL,
  `num_dscto` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `dscto` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`num_promocion`, `num_det_promocion`, `num_dscto`),
  INDEX `idx_dpdx_prom` (`num_promocion`, `num_det_promocion`),
  CONSTRAINT `fk_dpdx_detalle_promocion`
    FOREIGN KEY (`num_promocion`, `num_det_promocion`)
    REFERENCES `detalle_promocion` (`num_promocion`, `num_det_promocion`)
    ON UPDATE CASCADE
    ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

--------------------------------------------------------------------------------
-- Tabla: subcomponent_pc
--------------------------------------------------------------------------------
CREATE TABLE `subcomponent_pc` (
  `id_componente_pc` VARCHAR(40) NOT NULL,
  `id_sub_componente` VARCHAR(40) NOT NULL,
  `cantidad` INT NOT NULL,
  PRIMARY KEY (`id_componente_pc`, `id_sub_componente`),
  INDEX `idx_subcomp_pc` (`id_sub_componente`),
  CONSTRAINT `fk_subcomp_pc_componente`
    FOREIGN KEY (`id_componente_pc`)
    REFERENCES `componente` (`id_componente`)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  CONSTRAINT `fk_subcomp_pc_subcomponente`
    FOREIGN KEY (`id_sub_componente`)
    REFERENCES `componente` (`id_componente`)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;
