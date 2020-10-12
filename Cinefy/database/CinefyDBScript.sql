-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CinefyDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CinefyDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CinefyDB` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `CinefyDB` ;

-- -----------------------------------------------------
-- Table `CinefyDB`.`GeneralUser`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`GeneralUser` (
  `username` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `password` CHAR(32) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `role` ENUM('admin', 'advanced', 'beginner') NULL DEFAULT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Admin` (
  `username` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`username`),
  INDEX `fk_Admin_GeneralUser1_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_Admin_GeneralUser1`
    FOREIGN KEY (`username`)
    REFERENCES `CinefyDB`.`GeneralUser` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Advanced`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Advanced` (
  `username` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `ruolo` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `bio` VARCHAR(200) NULL DEFAULT NULL,
  `immagine` VARCHAR(45) NULL DEFAULT 'default.png',
  `voto` DOUBLE NULL DEFAULT '0',
  `numerodivoti` INT NULL DEFAULT '0',
  `tokens` INT NULL DEFAULT '0',
  PRIMARY KEY (`username`),
  INDEX `fk_Advanced_GeneralUser1_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_Advanced_GeneralUser1`
    FOREIGN KEY (`username`)
    REFERENCES `CinefyDB`.`GeneralUser` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Beginner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Beginner` (
  `username` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `bio` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `immagine` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT 'default.png',
  PRIMARY KEY (`username`),
  INDEX `fk_Beginner_GeneralUser1_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_Beginner_GeneralUser1`
    FOREIGN KEY (`username`)
    REFERENCES `CinefyDB`.`GeneralUser` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Domanda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Domanda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(300) CHARACTER SET 'utf8' NULL DEFAULT NULL,
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Domanda_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  INDEX `fk_Domanda_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  CONSTRAINT `fk_Domanda_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`),
  CONSTRAINT `fk_Domanda_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Domanda_incoda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Domanda_incoda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(300) NULL DEFAULT NULL,
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Domanda_incoda_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  INDEX `fk_Domanda_incoda_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  CONSTRAINT `fk_Domanda_incoda_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`),
  CONSTRAINT `fk_Domanda_incoda_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Film` (
  `FilmId` INT NOT NULL,
  `Titolo` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `Url` VARCHAR(200) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `Regista` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `AttorePrincipale` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `Anno` INT NULL DEFAULT NULL,
  `Nazione` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `Genere` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  PRIMARY KEY (`FilmId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Playlist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Playlist` (
  `idPlaylist` INT NOT NULL AUTO_INCREMENT,
  `Voto` DOUBLE NULL DEFAULT '0',
  `DataPubblicazione` DATE NULL DEFAULT NULL,
  `numerodivoti` INT NULL DEFAULT '0',
  `Nome` VARCHAR(45) NULL DEFAULT NULL,
  `playlistPic` VARCHAR(45) NULL DEFAULT 'default2.jpg',
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`idPlaylist`),
  INDEX `fk_Playlist_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  CONSTRAINT `fk_Playlist_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Playlist_Film`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Playlist_Film` (
  `idPlaylist` INT NOT NULL,
  `FilmId` INT NOT NULL,
  PRIMARY KEY (`FilmId`, `idPlaylist`),
  INDEX `fk_Playlist_has_Film_Film1_idx` (`FilmId` ASC) VISIBLE,
  INDEX `fk_Playlist_has_Film_Playlist1_idx` (`idPlaylist` ASC) VISIBLE,
  CONSTRAINT `fk_Playlist_has_Film_Film1`
    FOREIGN KEY (`FilmId`)
    REFERENCES `CinefyDB`.`Film` (`FilmId`),
  CONSTRAINT `fk_Playlist_has_Film_Playlist1`
    FOREIGN KEY (`idPlaylist`)
    REFERENCES `CinefyDB`.`Playlist` (`idPlaylist`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Risposta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Risposta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(300) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `idDomanda` INT NOT NULL,
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Risposta_Domanda1_idx` (`idDomanda` ASC) VISIBLE,
  INDEX `fk_Risposta_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  INDEX `fk_Risposta_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  CONSTRAINT `fk_Risposta_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`),
  CONSTRAINT `fk_Risposta_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`),
  CONSTRAINT `fk_Risposta_Domanda1`
    FOREIGN KEY (`idDomanda`)
    REFERENCES `CinefyDB`.`Domanda` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`Risposta_incoda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`Risposta_incoda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `Contenuto` VARCHAR(300) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL,
  `idDomanda` INT NOT NULL,
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Risposta_incoda_Domanda1_idx` (`idDomanda` ASC) VISIBLE,
  INDEX `fk_Risposta_incoda_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  INDEX `fk_Risposta_incoda_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  CONSTRAINT `fk_Risposta_incoda_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`),
  CONSTRAINT `fk_Risposta_incoda_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`),
  CONSTRAINT `fk_Risposta_incoda_Domanda1`
    FOREIGN KEY (`idDomanda`)
    REFERENCES `CinefyDB`.`Domanda` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`VotaAdvanced`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`VotaAdvanced` (
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `AdvancedName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  PRIMARY KEY (`BeginnerName`, `AdvancedName`),
  INDEX `fk_Beginner_has_Advanced_Advanced1_idx` (`AdvancedName` ASC) VISIBLE,
  INDEX `fk_Beginner_has_Advanced_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  CONSTRAINT `fk_Beginner_has_Advanced_Advanced1`
    FOREIGN KEY (`AdvancedName`)
    REFERENCES `CinefyDB`.`Advanced` (`username`),
  CONSTRAINT `fk_Beginner_has_Advanced_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `CinefyDB`.`VotaPlaylist`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CinefyDB`.`VotaPlaylist` (
  `BeginnerName` VARCHAR(16) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL,
  `idPlaylist` INT NOT NULL,
  PRIMARY KEY (`BeginnerName`, `idPlaylist`),
  INDEX `fk_Beginner_has_Playlist_Playlist1_idx` (`idPlaylist` ASC) VISIBLE,
  INDEX `fk_Beginner_has_Playlist_Beginner1_idx` (`BeginnerName` ASC) VISIBLE,
  CONSTRAINT `fk_Beginner_has_Playlist_Beginner1`
    FOREIGN KEY (`BeginnerName`)
    REFERENCES `CinefyDB`.`Beginner` (`username`),
  CONSTRAINT `fk_Beginner_has_Playlist_Playlist1`
    FOREIGN KEY (`idPlaylist`)
    REFERENCES `CinefyDB`.`Playlist` (`idPlaylist`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

USE `CinefyDB` ;

-- -----------------------------------------------------
-- procedure accetta_domanda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `accetta_domanda`(IN var_id int)
BEGIN
	declare var_contenuto varchar(200);
    declare var_beginner varchar(16);
    declare var_advanced varchar(16);
	select Contenuto, BeginnerName, AdvancedName from Domanda_incoda where id = var_id
    into var_contenuto, var_beginner, var_advanced;
	insert into Domanda values (var_id, var_contenuto, var_beginner, var_advanced);
    delete from Domanda_incoda where id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure accetta_risposta
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `accetta_risposta`(IN var_id int)
BEGIN
	declare var_contenuto varchar(300);
    declare var_idDomanda int;
    declare var_advanced varchar(16);
    declare var_beginner varchar(16);
    select Contenuto, idDomanda, AdvancedName, BeginnerName from Risposta_incoda where id = var_id
    into var_contenuto, var_idDomanda, var_advanced, var_beginner;
	insert into Risposta values (var_id, var_contenuto, var_idDomanda, var_beginner, var_advanced);
    delete from Risposta_incoda where id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_admin
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_admin`(IN var_user varchar(16), IN var_pass char(32))
BEGIN
	insert into GeneralUser (username, `password`, `role`) values (var_user, md5(var_pass), 'admin');
    insert into `Admin` (username) values (var_user);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_advanced
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_advanced`(IN var_user varchar(16), IN var_pass char(32), IN var_ruolo varchar(45), IN var_bio varchar(200), IN var_immagine varchar(45))
BEGIN
	insert into GeneralUser (username, `password`, `role`) values (var_user, md5(var_pass), 'advanced');
    insert into Advanced (username, ruolo, bio, immagine, voto, numerodivoti, tokens) values (var_user, var_ruolo, var_bio, var_immagine, 0.0, 0, 0);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_beginner
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_beginner`(IN var_user varchar(16), IN var_password varchar(32), IN var_bio varchar(200), IN var_profilepic varchar(45))
BEGIN
	insert into GeneralUser (username, `password`, `role`) values (var_user, md5(var_password), 'beginner');
    insert into Beginner (username, bio, immagine) values (var_user, var_bio, var_profilepic);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_film_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_film_playlist`(IN var_id int, IN var_film int)
BEGIN
	insert into Playlist_Film values (var_id, var_film);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_nuova_domanda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_nuova_domanda`(IN var_contenuto varchar(300), IN var_beginner varchar(16), IN var_advanced varchar(16))
BEGIN
    insert into Domanda_incoda (Contenuto, BeginnerName, AdvancedName) values (var_contenuto, var_beginner, var_advanced);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_nuova_risposta
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_nuova_risposta`(IN var_contenuto varchar(300), IN var_beginner varchar(16), IN var_advanced varchar(16), IN var_id_domanda int)
BEGIN
	insert into Risposta_incoda (Contenuto, idDomanda, BeginnerName, AdvancedName) values (var_contenuto, var_id_domanda, var_beginner, var_advanced);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure aggiungi_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `aggiungi_playlist`(IN var_name varchar(45), IN var_advanced varchar(16), IN var_nome_film varchar(200), IN var_date date, IN var_pic varchar(45))
BEGIN
	declare var_film int;
    declare var_playlist int;
    insert into Playlist (AdvancedName, Voto, DataPubblicazione, numerodivoti, Nome, playlistPic) values (var_advanced, 0.0,  var_date, 0, var_name, var_pic);  
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure assegna_token_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `assegna_token_playlist`(IN var_id int)
BEGIN
	declare var_vote double;
    declare var_ad varchar(16);
	select AdvancedName, Voto from Playlist where idPlaylist = var_id into var_ad, var_vote;
    if var_vote = 0 then
		update Advanced set tokens = tokens where username = var_ad;
	end if;
    if var_vote < 5 then
		update Advanced set tokens = tokens + 1 where username = var_ad;
	end if;
    if var_vote < 7 then 
		update Advanced set tokens = tokens + 2 where username = var_ad;
	end if;
    if var_vote < 10 then
		update Advanced set tokens = tokens + 3 where username = var_ad;
	end if;
    if var_voto = 10 then
		update Advanced set tokens = tokens + 5 where username = var_ad;
	end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure classifica_advanced
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `classifica_advanced`()
BEGIN
	select * from Advanced where voto >= 4 order by voto DESC;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure classifica_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `classifica_playlist`()
BEGIN
	select * from Playlist where Voto >= 8 order by Voto DESC;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_by_username
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_by_username`(IN var_role varchar(45), IN var_name varchar(16))
BEGIN
	if var_role = 'advanced' then
		select * from Advanced where username = var_name;
    end if;
    if var_role = 'beginner' then
		select * from Beginner where username = var_name;
	end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_pending
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_pending`(IN var_nome varchar(16))
BEGIN
	select * from Domanda_incoda where BeginnerName = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure get_pending_risp
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_pending_risp`(IN var_nome varchar(16))
BEGIN
	select * from Risposta_incoda where AdvancedName = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure login
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `login`(IN var_user varchar(16), IN var_password varchar(32))
BEGIN
	select username, role from GeneralUser where username = var_user and password = md5(var_password);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure rifiuta_domanda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `rifiuta_domanda`(IN var_id int)
BEGIN
	delete from Domanda_incoda where id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure rifiuta_risposta
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `rifiuta_risposta`(IN var_id int)
BEGIN
	delete from Risposta_incoda where id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_advanced_nome
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_advanced_nome`(IN var_nome varchar(16))
BEGIN
	select * from Advanced where username = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_advanced_ruolo
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_advanced_ruolo`(IN var_role varchar(45))
BEGIN
	select * from Advanced where ruolo = var_role;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_domanda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_domanda`(IN var_id int)
BEGIN
	select * from Domanda where id = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_domande
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_domande`(IN var_name varchar(16))
BEGIN
	select * from Domanda where BeginnerName = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_domande_ad
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_domande_ad`(IN var_name varchar(16))
BEGIN
	select * from Domanda where AdvancedName = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_domande_in_coda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_domande_in_coda`()
BEGIN
	select * from Domanda_incoda;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_anno
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_anno`(IN var int)
BEGIN
	select *
    from Film
    where Anno = var;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_attore
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_attore`(IN var_name varchar(45))
BEGIN
	select *
    from Film
    where AttorePrincipale = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_genere
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_genere`(IN var_name varchar(45))
BEGIN
	select *
    from Film
    where Genere = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_nazione
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_nazione`(IN var_name varchar(45))
BEGIN
	select *
    from Film
    where Nazione = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_nome
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_nome`(IN var_nome varchar(200))
BEGIN
	select *
    from Film
    where Titolo = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_playlist`(IN var_id int)
BEGIN
	select * from Film join Playlist_Film on Film.FilmId = Playlist_Film.FilmId where Playlist_Film.idPlaylist = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_film_regista
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_film_regista`(IN var_nome varchar(45))
BEGIN
	select *
    from Film
    where Regista = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_playlist_nome
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_playlist_nome`(IN var_nome varchar(45))
BEGIN
	select * from Playlist where Nome = var_nome;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_playlist_username
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_playlist_username`(IN var_name varchar(16))
BEGIN
	select * from Playlist where AdvancedName = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_risposta
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_risposta`(IN var_name varchar(16), IN var_id int)
BEGIN
	select * from Risposta where BeginnerName = var_name and idDomanda = var_id;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_risposte
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_risposte`(IN var_name varchar(16))
BEGIN
	select * from Risposta where AdvancedName = var_name;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure stampa_risposte_in_coda
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `stampa_risposte_in_coda`()
BEGIN
	select * from Risposta_incoda;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure vota_advanced
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `vota_advanced`(IN var_advanced varchar(16), IN var_beginner varchar(16), IN var_voto int)
BEGIN
	declare var_media double;
    declare var_controllo double;
    declare var_new_numero int;
    insert into VotaAdvanced (`BeginnerName`, `AdvancedName`) values (var_beginner, var_advanced);
    select voto, numerodivoti from Advanced where username = var_advanced into var_controllo, var_new_numero;
    if var_controllo = 0.0 then
		update Advanced set voto = var_voto, numerodivoti = 1, tokens = var_voto where username = var_advanced; 
    end if;
    set var_new_numero = var_new_numero + 1;
    set var_media = (var_controllo + var_voto)/var_new_numero;
	update Advanced set voto = var_media, numerodivoti = var_new_numero, tokens = tokens + var_voto where username = var_advanced;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure vota_playlist
-- -----------------------------------------------------

DELIMITER $$
USE `CinefyDB`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `vota_playlist`(IN var_voto int, IN var_id int, IN var_beginner varchar(16))
BEGIN
	declare var_controllo double;
    declare var_media double;
    declare var_new_numero int;
	/*insert into VotaPlaylist (`BeginnerName`, `idPlaylist`) values (var_beginner, var_id);*/
	select Voto, numerodivoti from Playlist where idPlaylist = var_id into var_controllo, var_new_numero;
    if var_controllo = 0.0 then
		update Playlist set Voto = var_voto, numerodivoti = 1 where idPlaylist = var_id;
	end if;
    set var_new_numero = var_new_numero + 1;
    set var_media = (var_controllo + var_voto)/var_new_numero;
	update Playlist set Voto = var_media, numerodivoti = var_new_numero where idPlaylist = var_id;
END$$

INSERT INTO `Film` (`FilmId`,`Titolo`,`Url`,`Regista`,`AttorePrincipale`,`Anno`,`Nazione`,`Genere`) VALUES
(1, 'Blade Runner', 'https://www.imdb.com/title/tt0083658/', 'Ridley Scott', 'Harrison Ford', 1982, 'USA', 'Sci-Fi'),
(2, 'Lost Highway', 'https://www.imdb.com/title/tt0116922/', 'David Lynch','Bill Pullman', 1997, 'USA', 'Thriller'),
(3,'Mother!','https://www.imdb.com/title/tt5109784/', 'Darren Aronofsky', 'Jennifer Lawrence', 2017, 'USA', 'Horror'),
(4, 'Mulholland Drive','https://www.imdb.com/title/tt0166924/?ref_=nv_sr_srsg_0', 'David Lynch', 'Naomi Watts', 2001,'USA', 'Thriller'),
(5, 'Wild At Heart', 'https://www.imdb.com/title/tt0100935/?ref_=nv_sr_srsg_0', 'David Lynch', 'Nicolas Cage', 1990, 'USA', 'Drama'),
(6, 'The Elephant Man','https://www.imdb.com/title/tt0080678/?ref_=nv_sr_srsg_0', 'David Lynch', 'John Hurt', 1989, 'USA', 'Drama'),
(7, 'The Thing', 'https://www.imdb.com/title/tt0084787/?ref_=nv_sr_srsg_0', 'John Carpenter', 'Kurt Russell', 1982, 'USA', 'Horror'),
(8, 'Videodrome', 'https://www.imdb.com/title/tt0086541/?ref_=fn_al_tt_1', 'David Cronenberg', 'James Woods', 1983, 'USA', 'Horror'),
(9, 'Jackie Brown', 'https://www.imdb.com/title/tt0119396/?ref_=nv_sr_srsg_0', 'Quentin Tarantino', 'Pam Grier', 1997, 'USA', 'Crime'),
(10, 'Reservoir Dogs', 'https://www.imdb.com/title/tt0105236/?ref_=nv_sr_srsg_0', 'Quentin Tarantino', 'Harvey Keitel', 1992, 'USA', 'Crime'),
(11, 'Django Unchained', 'https://www.imdb.com/title/tt1853728/?ref_=fn_al_tt_1', 'Quentin Tarantino', 'Jamie Foxx', 2012, 'USA', 'Western'),
(12, 'Once Upon a Time... in Hollywood', 'https://www.imdb.com/title/tt7131622/?ref_=nv_sr_srsg_0', 'Quentin Tarantino', 'Leonardo Di Caprio', 2019, 'USA', 'Drama'),
(13, 'Once Upon a Time in America', 'https://www.imdb.com/title/tt0087843/?ref_=nv_sr_srsg_3', 'Sergio Leone', 'Robert De Niro', 1984, 'Italy', 'Drama'),
(14, 'La la land', 'https://www.imdb.com/title/tt3783958/?ref_=nv_sr_srsg_0', 'Damien Chazelle', 'Ryan Gosling', 2016, 'USA','Musical'),
(15, 'A Clockwork Orange', 'https://www.imdb.com/title/tt0066921/?ref_=nv_sr_srsg_0', 'Stanley Kubrick', 'Malcolm McDowell', 1971, 'USA', 'Crime'),
(16, 'Eyes Wide Shut', 'https://www.imdb.com/title/tt0120663/?ref_=nv_sr_srsg_0', 'Stanley Kubrick', 'Tom Cruise', 1999, 'USA', 'Thriller'),
(17, 'Barry Lyndon', 'https://www.imdb.com/title/tt0072684/?ref_=fn_al_tt_1', 'Stanley Kubrick', 'Ryan O"Neal', 1975, 'USA', 'Drama'),
(18, '2001: A Space Odissey', 'https://www.imdb.com/title/tt0062622/?ref_=nv_sr_srsg_0', 'Stanley Kubrick', 'Keir Dullea', 1968, 'USA', 'Sci-Fi'),
(19, 'Dr. StrangeLove', 'https://www.imdb.com/title/tt0057012/?ref_=nv_sr_srsg_0', 'Stanley Kubrick', 'Peter Sellers', 1964, 'USA', 'Comedy'),
(20, 'Polytechnique', 'https://www.imdb.com/title/tt1194238/?ref_=nv_sr_srsg_0', 'Denis Villeneuve', 'Karin Vanasse', 2009, 'France', 'Crime');

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
