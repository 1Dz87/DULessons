CREATE TABLE webdb.parking_place (id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                                                  number int NOT NULL,
                                                                                  occupied boolean,
                                                                                  area int,
                                                                                  FOREIGN KEY (area) REFERENCES webdb.parking_area (id));