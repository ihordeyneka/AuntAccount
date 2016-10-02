CREATE TABLE `dido`.`post` 
  ( 
     `id`          BIGINT(20) NOT NULL, 
     `userid`      VARCHAR(45) NOT NULL, 
     `description` VARCHAR(255) NULL, 
     `pricemin`    FLOAT NULL, 
     `pricemax`    FLOAT NULL, 
     `latitude`    VARCHAR(45) NULL, 
     `longitude`   VARCHAR(45) NULL, 
     `photo`       VARCHAR(45) NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`tag` 
  ( 
     `id`  INT NOT NULL, 
     `tag` VARCHAR(20) NOT NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`posttag` 
  ( 
     `id`     BIGINT(10) NOT NULL, 
     `tagid`  BIGINT(10) NOT NULL, 
     `postid` BIGINT(10) NOT NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`consumer` 
  ( 
     `id`       BIGINT(10) NOT NULL, 
     `name`     VARCHAR(45) NOT NULL, 
     `password` VARCHAR(45) NOT NULL, 
     `rate`     INT NULL, 
     `photo`    BINARY(10) NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`supplier` 
  ( 
     `id`          INT NOT NULL, 
     `name`        VARCHAR(45) NOT NULL, 
     `description` VARCHAR(255) NULL, 
     `password`    VARCHAR(45) NOT NULL, 
     `rate`        INT NULL, 
     `photo`       BINARY(10) NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`suppliertag` 
  ( 
     `id`         BIGINT(10) NOT NULL, 
     `supplierid` BIGINT(10) NOT NULL, 
     `tagid`      BIGINT(10) NOT NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`offer` 
  ( 
     `id`         BIGINT(10) NOT NULL, 
     `postid`     BIGINT(10) NOT NULL, 
     `supplierid` BIGINT(10) NOT NULL, 
     PRIMARY KEY (`id`) 
  ); 

CREATE TABLE `dido`.`message` 
  ( 
     `id`          BIGINT(10) NOT NULL, 
     `senderid`    BIGINT(10) NOT NULL, 
     `offerid`     BIGINT(10) NOT NULL, 
     `description` VARCHAR(255) NULL, 
     `time`        DATETIME NULL, 
     `photo`       BINARY(10) NULL, 
     PRIMARY KEY (`id`) 
  ); 