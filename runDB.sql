CREATE TABLE CLIENT (
                        idClient BIGINT IDENTITY(100000000,1) PRIMARY KEY,
                        fio VARCHAR(255)
);
CREATE TABLE Account (
                         idAccount BIGINT IDENTITY(1000200030004000500,1) PRIMARY KEY,
                         idClient BIGINT,
                         BALANCE INT,
                         FOREIGN KEY (idClient)  REFERENCES Client(idClient)
);

CREATE TABLE Card (
                      idCard BIGINT IDENTITY(1234234534564567589,1) PRIMARY KEY,
                      BALANCE INT,
                      STATUS VARCHAR,
                      idAccount BIGINT,
                       FOREIGN KEY (idAccount)  REFERENCES Account(idAccount)
);

INSERT INTO  client (fio) VALUES ('Ivanov Ivan');
INSERT INTO  client (fio) VALUES ('Fedorov Ilya');
INSERT INTO  client (fio) VALUES ('Orlov Roman');
INSERT INTO  client (fio) VALUES ('Loseva Maria');
INSERT INTO  client (fio) VALUES ('Rysev Nikolay');
INSERT INTO  client (fio) VALUES ('Esenina Alexandra');
-- Filling Accounts
INSERT INTO  account (idClient,balance) VALUES (100000000,0);
INSERT INTO  account (idClient,balance) VALUES (100000001,0);
INSERT INTO  account (idClient,balance) VALUES (100000002,0);
INSERT INTO  account (idClient,balance) VALUES (100000003,0);
INSERT INTO  account (idClient,balance) VALUES (100000004,0);
INSERT INTO  account (idClient,balance) VALUES (100000005,0);
--Filling Cards
INSERT INTO  card (balance,status, idAccount) VALUES (1000,'opened',1000200030004000500);
INSERT INTO  card (balance,status, idAccount) VALUES (1010,'opened',1000200030004000501);
INSERT INTO  card (balance,status, idAccount) VALUES (1100,'opened',1000200030004000502);
INSERT INTO  card (balance,status, idAccount) VALUES (2000,'opened',1000200030004000503);
INSERT INTO  card (balance,status, idAccount) VALUES (3000,'opened',1000200030004000504);
INSERT INTO  card (balance,status, idAccount) VALUES (10000,'opened',1000200030004000505);
INSERT INTO  card (balance,status, idAccount) VALUES (10000,'opened',1000200030004000505);
INSERT INTO  card (balance,status, idAccount) VALUES (10000,'opened',1000200030004000505);

--update balance Accounts
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000500))  WHERE idAccount =(1000200030004000500);
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000501))  WHERE idAccount =(1000200030004000501);
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000502))  WHERE idAccount =(1000200030004000502);
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000503))  WHERE idAccount =(1000200030004000503);
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000504))  WHERE idAccount =(1000200030004000504);
UPDATE account SET balance =(SELECT SUM(balance) FROM card WHERE idAccount =(1000200030004000505))  WHERE idAccount =(1000200030004000505);

