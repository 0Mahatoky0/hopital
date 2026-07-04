ALTER TABLE centre_sante 
ALTER COLUMN amenity TYPE INT USING amenity::INT;

ALTER TABLE centre_sante
ADD CONSTRAINT fk_centre_sante_amenity
FOREIGN KEY (amenity) REFERENCES amenity(id);

ALTER TABLE amenity
ADD CONSTRAINT pk_amenity PRIMARY KEY (id);