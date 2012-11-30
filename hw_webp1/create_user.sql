
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;

SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;

SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';



CREATE DATABASE board DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
GRANT ALL ON board.* TO 'root' IDENTIFIED BY 'pwd';

USE `board` ;



-- -----------------------------------------------------

-- Table `board`.`boardinfo`

-- -----------------------------------------------------

CREATE  TABLE IF NOT EXISTS `board`.`boardinfo` (

  `num` INT NOT NULL ,

  `title` VARCHAR(20) NOT NULL ,

  `contents` TEXT NOT NULL ,

  `date` DATETIME NOT NULL ,

  `name` VARCHAR(10) NOT NULL ,

  `count` INT NOT NULL ,

  `pwd` VARCHAR(10) NOT NULL ,

  PRIMARY KEY (`num`) ,

  UNIQUE INDEX `num_UNIQUE` (`num` ASC) )

ENGINE = InnoDB

DEFAULT CHARACTER SET = utf8;





SET SQL_MODE=@OLD_SQL_MODE;

SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;

SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

