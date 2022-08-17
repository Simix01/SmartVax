CREATE TABLE CentriVaccinali (
	nomeCentro varchar(50) PRIMARY KEY,
	tipoLuogo varchar(10) NOT NULL,
	nomeLuogo varchar (50) NOT NULL,
	numCivico varchar (10) NOT NULL,
	Comune varchar (25) NOT NULL,
	siglaProvincia varchar (3) NOT NULL,
	Cap int NOT NULL,
	Tipologia varchar (15) NOT NULL
);

CREATE TABLE CittadiniRegistrati (
	userId serial PRIMARY KEY,
	Nome varchar(25) NOT NULL,
	Cognome varchar(40) NOT NULL,
	codFiscale varchar(16) NOT NULL,
	Mail varchar(50) NOT NULL,
	Username varchar(50) NOT NULL,
	Password varchar(50) NOT NULL
);

CREATE TABLE Vaccinazioni (
	codFiscale varchar(16) NOT NULL,
	idVaccinazione int NOT NULL,
	PRIMARY KEY(codFiscale, idVaccinazione)
);

CREATE TABLE EventiAvversi (
	idEventoAvverso serial PRIMARY KEY,
	tipoEvento varchar(50) NOT NULL,
	valoreGravita int NOT NULL,
	Commento varchar(255),
	nomeCentro varchar(50) REFERENCES CentriVaccinali(nomeCentro)
);

ALTER TABLE CittadiniRegistrati ADD CONSTRAINT mail_unique
UNIQUE(Mail);

ALTER TABLE CittadiniRegistrati ADD CONSTRAINT user_unique
UNIQUE(Username);

ALTER TABLE CittadiniRegistrati ADD CONSTRAINT cf_unique1
UNIQUE(codFiscale);

	
