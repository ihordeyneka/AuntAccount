CREATE TABLE `dido`.`Post` (
  `Id` bigint(20) NOT NULL,
  `UserId` bigint(10) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `LocationId` bigint(10) NOT NULL,
  `PriceMin` float DEFAULT NULL,
  `PriceMax` float DEFAULT NULL,
  `Photo` binary(10) DEFAULT NULL,
  `CreationDate` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE `dido`.`Tag` (
  `Id` bigint(10) NOT NULL,
  `Tag` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE `dido`.`PostTag` (
  `TagId` bigint(10) NOT NULL,
  `PostId` bigint(10) NOT NULL,
  PRIMARY KEY (`PostId`,`TagId`)
);

CREATE TABLE `dido`.`User` (
  `Id` bigint(10) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `LocationId` bigint(10) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `Photo` binary(10) DEFAULT NULL,
  `ReviewId` bigint(10) NOT NULL,
  `Website` varchar(45) DEFAULT NULL,
  `IsSupplier` tinyint(1) NOT NULL,
  `CreationDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE `dido`.`Review` (
  `Id` BIGINT(10) NOT NULL,
  `Description` VARCHAR(45) NULL,
  `Rate` INT NULL,
  `AuthorId` BIGINT(10) NOT NULL,
  `ObjectId` BIGINT(10) NOT NULL,
  `CreationDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE `dido`.`Location` (
  `Id` BIGINT(10) NOT NULL,
  `Latitude` VARCHAR(45) NOT NULL,
  `Longitude` VARCHAR(45) NOT NULL,
  `CountryId` BIGINT(10) NULL,
  `City` VARCHAR(45) NULL,
  `Region` VARCHAR(45) NULL,
  `Street` VARCHAR(45) NULL,
  PRIMARY KEY (`Id`)
);

CREATE TABLE `dido`.`Country` (
  `Id` BIGINT(10) NOT NULL,
  `Country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`)
);


CREATE TABLE `dido`.`SupplierTag` (
     `SupplierId` BIGINT(10) NOT NULL,
     `TagId`      BIGINT(10) NOT NULL,
     PRIMARY KEY (`SupplierId`, `TagId`)
);

CREATE TABLE `dido`.`SupplierPost` (
    `supplierId` BIGINT(10) NOT NULL,
    `postId` BIGINT(10) NOT NULL,
    PRIMARY KEY (`supplierId`, `postId`)
);

CREATE TABLE `dido`.`Offer` (
     `Id`         BIGINT(10) NOT NULL,
     `PostId`     BIGINT(10) NOT NULL,
     `SupplierId` BIGINT(10) NOT NULL,
     PRIMARY KEY (`Id`)
  ); 

CREATE TABLE `dido`.`Message` (
  `Id` bigint(10) NOT NULL,
  `SenderId` bigint(10) NOT NULL,
  `OfferId` bigint(10) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Photo` binary(10) DEFAULT NULL,
  `CreationDate` datetime NOT NULL,
  PRIMARY KEY (`Id`)
);



