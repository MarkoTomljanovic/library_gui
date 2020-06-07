DROP SCHEMA IF EXISTS `library_web`;

CREATE SCHEMA `library_web`;

use `library_web`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `last_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone_num` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


DROP TABLE IF exists `books`;

CREATE TABLE `books` (
	`id` int(100) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `author_last_name` varchar(255) DEFAULT NULL,
    `author_first_name` varchar(255) DEFAULT NULL,
    `publisher` varchar(255) DEFAULT NULL,
    `publishing_year` varchar(255) DEFAULT NULL,
    `id_member` int(100) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK_DETAIL_idx` (`id_member`),
    CONSTRAINT `FK_DETAIL` FOREIGN KEY (`id_member`) 
    REFERENCES `members` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


SET FOREIGN_KEY_CHECKS = 1;
