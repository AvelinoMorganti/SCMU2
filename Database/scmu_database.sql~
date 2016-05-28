SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `whaves_scmu` DEFAULT CHARACTER SET latin1 ;
USE `whaves_scmu` ;

-- -----------------------------------------------------
-- Table `whaves_scmu`.`state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `whaves_scmu`.`state` (
  `idstate` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `lamp` TINYINT(1) NULL DEFAULT NULL,
  `alarm` TINYINT(1) NULL DEFAULT NULL,
  `smsNotifications` TINYINT(1) NULL DEFAULT NULL,
  `latitude` VARCHAR(45) NULL DEFAULT NULL,
  `longitude` VARCHAR(45) NULL DEFAULT NULL,
  `harmfulGases` FLOAT NULL DEFAULT NULL,
  `luminosity` FLOAT NULL DEFAULT NULL,
  PRIMARY KEY (`idstate`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `whaves_scmu`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `whaves_scmu`.`account` (
  `idaccount` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `salt` VARCHAR(255) NOT NULL,
  `locked` VARCHAR(255) NOT NULL,
  `state_idstate` BIGINT(20) NOT NULL,
  PRIMARY KEY (`idaccount`),
  INDEX `fk_account_state_idx` (`state_idstate` ASC),
  CONSTRAINT `fk_account_state`
    FOREIGN KEY (`state_idstate`)
    REFERENCES `whaves_scmu`.`state` (`idstate`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = latin1;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
