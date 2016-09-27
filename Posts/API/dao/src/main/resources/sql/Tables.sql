CREATE TABLE `dido`.`Post` (
  `Id` BIGINT(20) NOT NULL,
  `UserId` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(255) NULL,
  `PriceMin` FLOAT NULL,
  `PriceMax` FLOAT NULL,
  `Latitude` VARCHAR(45) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Photo` VARCHAR(45) NULL,
  PRIMARY KEY (`Id`));
