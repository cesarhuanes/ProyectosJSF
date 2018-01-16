# Host: localhost  (Version 5.5.28)
# Date: 2018-01-12 21:40:09
# Generator: MySQL-Front 6.0  (Build 2.20)


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
