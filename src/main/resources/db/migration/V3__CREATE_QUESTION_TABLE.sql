CREATE TABLE `question` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(50),
	`description` TEXT,
	`tag` VARCHAR(256),
	`gmt_create` BIGINT,
	`gmt_modified` BIGINT,
	`creator` INT,
	`comment_count` INT DEFAULT '0',
	`view_count` INT DEFAULT '0',
	`like_count` INT DEFAULT '0',
	PRIMARY KEY (`id`)
);