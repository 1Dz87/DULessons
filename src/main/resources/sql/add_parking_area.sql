CREATE TABLE webdb.parking_area (id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                  side varchar(5) NOT NULL,
                                  parking int,
                                  FOREIGN KEY (parking) REFERENCES webdb.parking (id));