# Host: localhost  (Version 5.5.28)
# Date: 2017-10-15 22:49:47
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "clientes"
#

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE `clientes` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `nombreCliente` varchar(50) NOT NULL,
  `nombreContacto` varchar(50) NOT NULL,
  `ruc` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `estado` int(11) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

#
# Data for table "clientes"
#

INSERT INTO `clientes` VALUES (1,'CACARDIF PERU','GISELA DELA FUENTE','10417542148','gfuentes@gfuentes.pe','941383163',1),(2,'AMERICA TV','JOSE PEREZ','10417542149','jperez@americatv.pe','941383167',1),(5,'ABB','CESAR HUANES','10417542148','cesarhuanes@gmail.com','941383163',1);
