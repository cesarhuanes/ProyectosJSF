# Host: localhost  (Version 5.5.28)
# Date: 2017-10-15 22:48:06
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

#
# Structure for table "menu"
#

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `codigoOpcion` int(11) NOT NULL DEFAULT '0',
  `codigoPadre` int(11) DEFAULT NULL,
  `Descripcion` varchar(255) DEFAULT NULL,
  `Url` varchar(255) DEFAULT NULL,
  `Nivel` smallint(6) DEFAULT NULL,
  `Orden` smallint(6) DEFAULT NULL,
  `Habilitado` bit(1) DEFAULT NULL,
  `UsuarioCreacion` varchar(255) DEFAULT NULL,
  `FechaCreacion` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "menu"
#

INSERT INTO `menu` VALUES (1,3,'Clientes','ListadoClientes.xhtml',2,1,b'1','chuanes','2017-09-09 00:00:00'),(2,3,'Usuario','ListadoUsuarios.xhtml',2,2,b'1','chuanes','2017-09-09 00:00:00'),(3,0,'Mantenimiento ','',1,0,b'1','chuanes','2017-09-09 00:00:00'),(4,6,'Rentabilidad','ListadoReportes.xhtml',2,1,b'1','chuanes','2017-09-09 00:00:00'),(5,6,'Ingresos','ListadoIngresos.xhtml',2,2,b'1','chuanes','2017-09-09 00:00:00'),(6,0,'Reportes','',1,0,b'1','chuanes','2017-09-09 00:00:00'),(7,0,'Marcaciòn','',1,0,b'1','chuanes','2017-09-09 00:00:00');

#
# Structure for table "usuarios"
#

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `estado` bit(1) DEFAULT NULL,
  `apPaterno` varchar(255) DEFAULT NULL,
  `apMaterno` varchar(255) DEFAULT NULL,
  `nombres` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

#
# Data for table "usuarios"
#

INSERT INTO `usuarios` VALUES (1,'chuanes','123',b'1','HUANES','BAUTISTA','CESAR ALFREDO'),(2,'bpinedo','456',b'1','PINEDO','DIAZ','BELEN');
