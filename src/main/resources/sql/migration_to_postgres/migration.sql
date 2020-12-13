CREATE TABLE public.user (id SERIAL NOT NULL PRIMARY KEY,
                          login varchar(255) NOT NULL,
                          password varchar(255) NOT NULL,
                          first_name varchar(255) NOT NULL,
                          last_name varchar(255) NOT NULL,
                          role int);

CREATE TABLE public.photo (id SERIAL NOT NULL PRIMARY KEY,
                           file_name varchar(255) NOT NULL,
                           users bigint,
                           photo bytea,
                           FOREIGN KEY (users) REFERENCES public.user (id),
                           UNIQUE (users));

ALTER TABLE public.user ADD COLUMN photo bigint;

CREATE TABLE public.parking (id SERIAL NOT NULL PRIMARY KEY,
                             name varchar(255) NOT NULL);

CREATE TABLE public.parking_area (id SERIAL NOT NULL PRIMARY KEY,
                                  side varchar(5) NOT NULL,
                                  parking int,
                                  FOREIGN KEY (parking) REFERENCES public.parking (id));

CREATE TABLE public.parking_place (id SERIAL NOT NULL PRIMARY KEY,
                                   number int NOT NULL,
                                   occupied boolean,
                                   area int,
                                   FOREIGN KEY (area) REFERENCES public.parking_area (id));

CREATE TABLE public.car (id SERIAL NOT NULL PRIMARY KEY,
                         model varchar(255) NOT NULL,
                         parking_place int,
                         users int,
                         FOREIGN KEY (users) REFERENCES public.user (id),
                         FOREIGN KEY (parking_place) REFERENCES public.parking_place (id),
                         UNIQUE (users));