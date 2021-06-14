CREATE TABLE `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`account_id` VARCHAR(100),
	`name` VARCHAR(50),
	`token` CHAR(36),
	`gmt_create` BIGINT,
	`gmt_modified` BIGINT,
	PRIMARY KEY (`id`)
);