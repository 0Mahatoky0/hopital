CREATE DATABASE sig_hopital;
\c sig_hopital;
CREATE EXTENSION postgis;
CREATE EXTENSION postgis_topology;


SELECT DISTINCT(amenity) FROM centre_sante;
SELECT DISTINCT(healthcare) FROM centre_sante;

SELECT * FROM centre_sante WHERE amenity != healthcare;

UPDATE centre_sante SET healthcare = 'doctors' WHERE healthcare = 'doctor';

CREATE TABLE amenity (
    id SERIAL ,
    libelle VARCHAR(50)
);

UPDATE 