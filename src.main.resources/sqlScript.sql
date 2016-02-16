CREATE DATABASE IF NOT EXISTS `practice07_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE practice07_db;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`(
`id_role` int (20) unsigned NOT NULL AUTO_INCREMENT,
`role_name` varchar(256) NOT NULL,
PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`(
`id` int (20) unsigned NOT NULL AUTO_INCREMENT,
`login` varchar(256) NOT NULL,
`password` varchar (256) NOT NULL,
`first_name` varchar (256) NOT NULL,
`last_name` varchar (256) NOT NULL,
`age` int (20) NOT NULL,
`role_id` int (20) unsigned NOT NULL,
PRIMARY KEY(`id`),
FOREIGN KEY (`role_id`) REFERENCES `role`(`id_role`)
);

DROP TABLE IF EXISTS `music_type`;

CREATE TABLE `music_type`(
`id_music` int(20) unsigned NOT NULL AUTO_INCREMENT,
`type_name` varchar(256) NOT NULL,
PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `user_musictype`;

CREATE TABLE `user_musictype`(
`user_id` int(20) unsigned NOT NULL,
`music_type_id` int(20) unsigned NOT NULL,
FOREIGN KEY  (user_id) REFERENCES `user`(`id`),
FOREIGN KEY  (music_type_id) REFERENCES `music_type`(`id_music`)
);

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address`(
`id_address` int (20) unsigned NOT NULL AUTO_INCREMENT,
`country` varchar(256) NOT NULL,
`street` varchar(256) NOT NULL,
`zip` int(10) unsigned NOT NULL,
PRIMARY KEY(`id_address`)
);

SELECT * FROM practice07_db.role;
INSERT INTO role (role_name) VALUES ('USER'), ('MODERATOR'), ('ADMIN');
SELECT * FROM practice07_db.role;
SELECT * FROM practice07_db.music_type;
INSERT INTO music_type(type_name) VALUES ('Folk'), ('Country'), ('Classic'), ('Trance'), ('Rock and Roll');
SELECT * FROM practice07_db.music_type;
 
 


