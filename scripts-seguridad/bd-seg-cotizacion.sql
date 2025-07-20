Scripts bd de usuarios

CREATE DATABASE  IF NOT EXISTS `bd_seg_cotizacion` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci */;
USE `bd_seg_cotizacion`;
-- MySQL dump 10.13  Distrib 5.7.22, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_seg_cotizacion
-- ------------------------------------------------------
-- Server version        5.7.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `autoridad`
--

DROP TABLE IF EXISTS `autoridad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `autoridad` (
  `aut_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `aut_nombre` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `aut_nombre_usr` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  PRIMARY KEY (`aut_id`),
  UNIQUE KEY `idxAut` (`aut_nombre`,`aut_nombre_usr`),
  KEY `fk_usuario` (`aut_nombre_usr`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`aut_nombre_usr`) REFERENCES `usuario` (`usr_nombre`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `autoridad`
--

LOCK TABLES `autoridad` WRITE;
/*!40000 ALTER TABLE `autoridad` DISABLE KEYS */;
INSERT INTO `autoridad` VALUES (1,'ROLE_ADMIN','Edgar'),
							   (2,'ROLE_SISTEMAS','Edgar'),
							   (3,'ROLE_VTAS','Alex'),
							   (4,'ROLE_SISTEMAS','Lulu');
/*!40000 ALTER TABLE `autoridad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `usr_nombre` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `usr_paswd` varchar(80) COLLATE utf8_spanish2_ci NOT NULL,
  `usr_habilitado` tinyint(1) NOT NULL,
  PRIMARY KEY (`usr_nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('Edgar','{bcrypt}$2a$10$gLC1y0SA1z64M9xxG72KL.jefI8nIqAVnudfhmNv8oOUiXn9raMi6',1),
							 ('Alex','{bcrypt}$2a$10$tBzzQtR/C/DcnuNImrTuy.WUc/Maf2Jvj2nArecvfZtldErs5P7Qa',1),
							 ('Lulu','{bcrypt}$2a$10$1v1J6dEhK8BTMtIEaXPcm.izLDYSZ473Fjn19EQQ6ZrFB4BXLOkMC',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
