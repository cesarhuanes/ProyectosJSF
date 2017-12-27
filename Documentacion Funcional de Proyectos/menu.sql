# Host: localhost  (Version 5.5.28)
# Date: 2017-10-15 22:50:02
# Generator: MySQL-Front 6.0  (Build 2.20)


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
