CREATE DATABASE web2012 DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

GRANT ALL ON web2012.* TO 'web'@'localhost' IDENTIFIED BY 'asdf';

use web2012;


CREATE TABLE boardinfo (
	number INT AUTO_INCREMENT PRIMARY KEY,
	groupnumber INT(200) null,
	content VARCHAR(1000) not null,
	userid VARCHAR(255) not null,
	pwd VARCHAR(255) not null,
	count INT(255) 
);

INSERT INTO boardinfo VALUES (1, 1, '안녕하세요~~~', 'jic', '123', null);
INSERT INTO boardinfo VALUES (2, 2, '여기는 어딘가~', 'kim', '123', null);
INSERT INTO boardinfo VALUES (3, 3, '행쇼~~~~~~~', 'jyg', '123', null);
INSERT INTO boardinfo VALUES (4, 4, '지친다....ㅜㅜ', 'sys', '123', null);