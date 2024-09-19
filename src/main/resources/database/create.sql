CREATE TABLE arrestanter (
	id IDENTITY NOT NULL PRIMARY KEY,
    navn VARCHAR(500) NOT NULL,
    alder VARCHAR(500) NOT NULL,
    kjonn VARCHAR(10) NOT NULL,
    fengslingsDatoFra TIMESTAMP,
    fengslingsDatoTil TIMESTAMP,
    celleNummer INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);