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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela trzyma informacje o nagrodach jakie uzyskał film oraz dla kogo została przyznana.';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `mark` int(11) NOT NULL COMMENT 'ocena filmu, skala od 1 do 10',
  `description` varchar(255) COLLATE utf8_polish_ci NOT NULL COMMENT 'opis słowny komentarza',
  `date` varchar(25) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela trzyma informacje o komentarzach zostawionych przez użytkowników pod filmami ';

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
  `creator` int(11) NOT NULL COMMENT '0 = aktor, 1 = twórca(np: dzwiękowiec, reżyser)',
  `participation` varchar(100) COLLATE utf8_polish_ci NOT NULL COMMENT 'wkład w tworzenie filmu np(nazwa postaci granej przez aktora lub rola jaką odbył poza kadrem'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela zawiera informacje o twórcach filmu jak i ich wkładzie do stworzenia filmu';

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `frienduser`
--

CREATE TABLE `frienduser` (
  `idUser` int(11) NOT NULL,
  `idFriend` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci COMMENT='tabela zawiera informacje o osobach obserwowanych przez użytkownika';

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
  `mark` double NOT NULL COMMENT 'ocena filmu, nie jest średnia wartości z ocen, ustawiana jest na podstawie innych stron',
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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26283;
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
