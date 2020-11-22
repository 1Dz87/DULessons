CREATE TABLE webdb.photo (id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            file_name varchar(255) NOT NULL,
                            user int,
                            photo MEDIUMBLOB,
                            FOREIGN KEY (user) REFERENCES webdb.user (id),
                            UNIQUE KEY 'UNIQ_USER' ('user'));