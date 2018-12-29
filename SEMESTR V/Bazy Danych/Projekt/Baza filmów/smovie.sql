-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Czas generowania: 21 Gru 2015, 23:34
-- Wersja serwera: 5.6.17
-- Wersja PHP: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `movie`
--

DELIMITER $$
--
-- Procedury
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_actorMovie` (IN `id` INT(11))  NO SQL
BEGIN
    SELECT * FROM `creator` c, `person` p WHERE c.idPerson = p.id AND c.creator = 0 and c.idMovie = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_addComment` (IN `idUser` INT(11), IN `idMovie` INT(11), IN `mark` INT(11), IN `description` VARCHAR(1000) CHARSET utf8, IN `date` VARCHAR(25) CHARSET utf8)  NO SQL
BEGIN
    SELECT idMovie, idUser, mark, description, date;
    SET @idMovie      = idMovie;
    SET @idUser       = idUser;
    SET @mark         = mark;
    SET @description  = description;
    SET @date         = date;
    SELECT @idMovie, @idUser, @mark, @description, @date;
    SELECT 1;

    INSERT INTO `comment` (`mark`, `description`, `date`) SELECT @mark, CONCAT("\"",@description,"\""), @date FROM dual WHERE NOT EXISTS (SELECT * FROM `comment` WHERE `description` = CONCAT("\"",@description,"\"") AND `mark` = @mark AND `date` = CONCAT("\"",@date,"\""));

    SELECT 2;

    SELECT id INTO @idComment FROM `comment` c WHERE c.description = CONCAT("\"",@description,"\"") AND c.mark = @mark AND c.date = CONCAT("\"",@date,"\"") LIMIT 1;
    SELECT 3;
    SELECT @idComment;
    SELECT 4;

    INSERT INTO `commentmovie`(`idMovie`, `idUser`, `idComment`) SELECT idMovie, idUser, @idComment FROM dual WHERE NOT EXISTS (SELECT * FROM `commentmovie` c WHERE c.`idMovie` = idMovie AND c.`idUser` = idUser AND c.`idComment` = @idCommnet);
    SELECT 5;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_awards` (IN `idMovie` INT(11))  NO SQL
BEGIN
    SELECT * FROM `award` a WHERE a.idMovie = idMovie ORDER BY a.date, a.won DESC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_comments` (IN `id` INT(11))  NO SQL
BEGIN
    SELECT * FROM `commentmovie` cm, `comment` c, `user` u WHERE cm.idMovie = id AND cm.idUser = u.id AND cm.idComment = c.id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_creatorMovie` (IN `id` INT(11))  NO SQL
BEGIN
    SELECT * FROM `creator` c, `person` p WHERE c.idPerson = p.id AND c.creator = 1 and c.idMovie = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_genres` ()  NO SQL
BEGIN

	SELECT genre FROM `movie` WHERE `genre` NOT LIKE '% %' AND `genre` NOT LIKE ' ' GROUP BY `genre` LIMIT 0, 50;
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_movie` (IN `id` INT(11))  BEGIN
    SELECT * FROM movie m WHERE m.`id` = id;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_movies` (IN `_from` INT(11), IN `_number` INT(11))  NO SQL
BEGIN 

SELECT id, poster, title, description FROM movie WHERE releaseDate <= CURDATE() ORDER BY releaseDate DESC LIMIT _from, _number;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_persons` (IN `_from` INT(11), IN `_number` INT(11))  NO SQL
BEGIN
	SELECT * FROM person ORDER BY name DESC LIMIT _from, _number;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `p_personsSearch` (IN `_from` INT(11), IN `_number` INT(11), IN `_search` VARCHAR(255))  NO SQL
BEGIN 

	SELECT * FROM person p WHERE p.name LIKE CONCAT("'%",_search,"%'") ORDER BY name DESC LIMIT _from, _number;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `award`
--

CREATE TABLE `award` (
  `idMovie` int(11) DEFAULT NULL COMMENT '0 = aktor',
  `idPerson` int(11) DEFAULT NULL COMMENT '0 = film',
  `description` varchar(1000) COLLATE utf8_polish_ci NOT NULL,
  `date` text COLLATE utf8_polish_ci NOT NULL,
  `info` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `won` text COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela trzyma informacje o nagrodach. Jeśli otrzymał film idUser jest puste...';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `mark` int(11) NOT NULL COMMENT 'skala od 1 do 10',
  `description` varchar(255) COLLATE utf8_polish_ci NOT NULL COMMENT 'opis słowny komentarza',
  `date` varchar(25) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='informacje o komentarzach zostawionych przez użytkowników pod filmami ';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `commentmovie`
--

CREATE TABLE `commentmovie` (
  `idMovie` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `idComment` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela zawiera połączenie między komentarzami użytkowników a filmami';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `creator`
--

CREATE TABLE `creator` (
  `idMovie` int(11) NOT NULL,
  `idPerson` int(11) NOT NULL,
  `creator` int(11) NOT NULL COMMENT '0 = aktor, 1 = twórca(np: dziękowiec, reżyser)',
  `participation` varchar(100) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='zawiera info o twórcach filmu jak i ich wkładzie do stworzenia filmu';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `frienduser`
--

CREATE TABLE `frienduser` (
  `idUser` int(11) NOT NULL,
  `idFriend` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `movie`
--

CREATE TABLE `movie` (
  `id` int(11) NOT NULL,
  `title` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `releaseDate` date NOT NULL,
  `description` varchar(1000) COLLATE utf8_polish_ci NOT NULL,
  `length` int(11) NOT NULL COMMENT 'podawany w minutach',
  `mark` double NOT NULL,
  `genre` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `poster` varchar(255) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela trzyma informacje o filmach';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `person`
--

CREATE TABLE `person` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `dateOfBirth` varchar(10) COLLATE utf8_polish_ci NOT NULL,
  `photo` varchar(255) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='zawiera informacje o twórcach(włączając w to aktorów) filmów';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  `age` int(11) NOT NULL,
  `sex` int(11) NOT NULL COMMENT '0 = mężczyzna a 1 = kobieta',
  `password` varchar(255) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='informacje o użytkownikach zarejestrowanych';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `userlog`
--

CREATE TABLE `userlog` (
  `idUser` int(11) NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela trzyma informacje o czasie logowania się użytkownika do aplikacji';

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `award`
--
ALTER TABLE `award`
  ADD KEY `idMovie` (`idMovie`),
  ADD KEY `idUser` (`idPerson`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `commentmovie`
--
ALTER TABLE `commentmovie`
  ADD KEY `idMovie` (`idMovie`,`idUser`),
  ADD KEY `idUser` (`idUser`),
  ADD KEY `idComment` (`idComment`),
  ADD KEY `idComment_2` (`idComment`),
  ADD KEY `idComment_3` (`idComment`);

--
-- Indexes for table `creator`
--
ALTER TABLE `creator`
  ADD KEY `idMovie` (`idMovie`,`idPerson`),
  ADD KEY `idPerson` (`idPerson`),
  ADD KEY `idMovie_2` (`idMovie`),
  ADD KEY `idPerson_2` (`idPerson`);

--
-- Indexes for table `frienduser`
--
ALTER TABLE `frienduser`
  ADD KEY `idUser` (`idUser`,`idFriend`),
  ADD KEY `idFriend` (`idFriend`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `userlog`
--
ALTER TABLE `userlog`
  ADD KEY `idUser` (`idUser`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26302;
--
-- AUTO_INCREMENT dla tabeli `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12929;
--
-- AUTO_INCREMENT dla tabeli `person`
--
ALTER TABLE `person`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28383;
--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13586;
--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `award`
--
ALTER TABLE `award`
  ADD CONSTRAINT `award_ibfk_1` FOREIGN KEY (`idMovie`) REFERENCES `movie` (`id`),
  ADD CONSTRAINT `award_ibfk_2` FOREIGN KEY (`idPerson`) REFERENCES `person` (`id`);

--
-- Ograniczenia dla tabeli `commentmovie`
--
ALTER TABLE `commentmovie`
  ADD CONSTRAINT `commentmovie_ibfk_1` FOREIGN KEY (`idMovie`) REFERENCES `movie` (`id`),
  ADD CONSTRAINT `commentmovie_ibfk_2` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `commentmovie_ibfk_3` FOREIGN KEY (`idComment`) REFERENCES `comment` (`id`);

--
-- Ograniczenia dla tabeli `creator`
--
ALTER TABLE `creator`
  ADD CONSTRAINT `creator_ibfk_1` FOREIGN KEY (`idMovie`) REFERENCES `movie` (`id`),
  ADD CONSTRAINT `creator_ibfk_2` FOREIGN KEY (`idPerson`) REFERENCES `person` (`id`);

--
-- Ograniczenia dla tabeli `frienduser`
--
ALTER TABLE `frienduser`
  ADD CONSTRAINT `frienduser_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `frienduser_ibfk_2` FOREIGN KEY (`idFriend`) REFERENCES `user` (`id`);

--
-- Ograniczenia dla tabeli `userlog`
--
ALTER TABLE `userlog`
  ADD CONSTRAINT `userlog_ibfk_1` FOREIGN KEY (`idUser`) REFERENCES `user` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
