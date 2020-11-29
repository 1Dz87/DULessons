CREATE TABLE webdb.car (id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                        model varchar(255) NOT NULL,
                        parking_place int,
                        user int,
                        FOREIGN KEY (user) REFERENCES webdb.user (id),
                        FOREIGN KEY (parking_place) REFERENCES webdb.parking_place (id),
                        UNIQUE (user));