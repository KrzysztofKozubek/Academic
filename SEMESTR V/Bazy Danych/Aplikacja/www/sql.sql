//addComment
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
END

