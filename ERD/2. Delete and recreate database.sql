SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `msg` ;
CREATE SCHEMA IF NOT EXISTS `msg` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `msg` ;

-- -----------------------------------------------------
-- Table `msg`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg`.`User` (
  `id` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `fullname` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg`.`Message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg`.`Message` (
  `id` INT NOT NULL,
  `subject` VARCHAR(45) NULL,
  `body` VARCHAR(300) NULL,
  `sender_User_id` INT NOT NULL,
  PRIMARY KEY (`id`, `sender_User_id`),
  INDEX `fk_Message_User1_idx` (`sender_User_id` ASC),
  CONSTRAINT `fk_Message_User1`
    FOREIGN KEY (`sender_User_id`)
    REFERENCES `msg`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg`.`Receiver`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg`.`Receiver` (
  `id` INT NOT NULL,
  `receiver_User_id` INT NOT NULL,
  `Message_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Message_id`, `receiver_User_id`),
  INDEX `fk_Receiver_Message1_idx` (`Message_id` ASC),
  INDEX `fk_Receiver_User1_idx` (`receiver_User_id` ASC),
  CONSTRAINT `fk_Receiver_Message1`
    FOREIGN KEY (`Message_id`)
    REFERENCES `msg`.`Message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Receiver_User1`
    FOREIGN KEY (`receiver_User_id`)
    REFERENCES `msg`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg`.`SenderMessageStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg`.`SenderMessageStatus` (
  `Message_id` INT NOT NULL,
  `status` INT NULL,
  INDEX `fk_MessageStatus_Message1_idx` (`Message_id` ASC),
  PRIMARY KEY (`Message_id`),
  CONSTRAINT `fk_MessageStatus_Message1`
    FOREIGN KEY (`Message_id`)
    REFERENCES `msg`.`Message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `msg`.`ReceiverMessageStatus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `msg`.`ReceiverMessageStatus` (
  `Reciever_id` INT NOT NULL,
  `status` INT NULL,
  PRIMARY KEY (`Reciever_id`),
  CONSTRAINT `fk_ReceiverMessageStatus_Receiver1`
    FOREIGN KEY (`Reciever_id`)
    REFERENCES `msg`.`Receiver` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
