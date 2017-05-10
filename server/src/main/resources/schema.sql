CREATE TABLE `wine` (`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(255),
`price` int,
PRIMARY KEY (`id`)
);
CREATE TABLE `params` (`id` int NOT NULL AUTO_INCREMENT,
`name` varchar(255),
`value` VARCHAR(255),
PRIMARY KEY (`id`)
);