SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `whaves_scmu` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `whaves_scmu` ;

-- -----------------------------------------------------
-- Table `whaves_scmu`.`properties`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `whaves_scmu`.`properties` (
  `idproperties` BIGINT NOT NULL AUTO_INCREMENT,
  `lamp` TINYINT(1) NULL,
  `alarm` TINYINT(1) NULL,
  `smsNotifications` TINYINT(1) NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  `harmfulGases` FLOAT NULL,
  `luminosity` FLOAT NULL,
  PRIMARY KEY (`idproperties`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `whaves_scmu`.`account` (
  `idaccount` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `salt` VARCHAR(255) NOT NULL,
  `locked` VARCHAR(255) NOT NULL,
  `properties_idproperties` BIGINT NOT NULL,
  PRIMARY KEY (`idaccount`),
  INDEX `fk_account_properties_idx` (`properties_idproperties` ASC),
  CONSTRAINT `fk_account_properties`
    FOREIGN KEY (`properties_idproperties`)
    REFERENCES `whaves_scmu`.`properties` (`idproperties`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
