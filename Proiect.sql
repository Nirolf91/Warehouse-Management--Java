CREATE TABLE Comenzi (
    ID_Comanda INT PRIMARY KEY,
    Data_Comenzii DATE,
    ID_Client INT,
    ID_Furnizor INT,
    ID_Angajat INT,
    ID_Material INT, -- Ad?ug?m aceast? linie
    Total_Comanda DECIMAL(10, 2),
    Statut_Comanda VARCHAR2(100),
    Tip_Comanda VARCHAR2(100),
    Cantitate INT,
    Pret_Total DECIMAL(10, 2),
    FOREIGN KEY (ID_Client) REFERENCES Clienti(ID_Client),
    FOREIGN KEY (ID_Angajat) REFERENCES Angajati(ID_Angajat),
    FOREIGN KEY (ID_Material) REFERENCES Materiale(ID_Material),
    FOREIGN KEY (ID_Furnizor) REFERENCES Furnizori(ID_Furnizor)
);

SELECT *FROM MATERIALE;
SELECT *FROM FURNIZORI;
SELECT *FROM ANGAJATI;
SELECT *FROM CLIENTI;
SELECT *FROM EVALUARI;
SELECT *FROM TRANSPORTATORI;
SELECT *FROM MATERIALE;
SELECT *FROM COMENZI;

CREATE SEQUENCE materiale_seq
START WITH 2
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER materiale_before_insert
BEFORE INSERT ON materiale
FOR EACH ROW
BEGIN
  SELECT materiale_seq.NEXTVAL
  INTO :new.ID_MATERIAL
  FROM dual;
END;

//Populare tabela angajati

INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (2, 'Andrei Georgescu', 'Logistician', 'Galati, Strada Trandafirilor, nr. 15 | 0722567890');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (3, 'Maria Ionescu', 'Supraveghetor de stoc', 'Galati, Strada Lalelelor, nr. 23 | 0723678901');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (4, 'Ion Dumitrescu', 'Aprovizionare', 'Galati, Strada Margaretelelor, nr. 12 | 0724789012');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (5, 'Cristina Stanescu', 'Inspector calitate', 'Galati, Strada Florilor, nr. 7 | 0725890123');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (6, 'George Munteanu', 'Manipulant marfa', 'Galati, Strada Panselutelor, nr. 21 | 0726901234');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (7, 'Elena Nicolescu', 'Responsabil depozit', 'Galati, Strada Viorelelor, nr. 18 | 0727012345');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (8, 'Victor Radulescu', 'Manager de depozit', 'Galati, Strada Orhideelor, nr. 33 | 0728123456');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (9, 'Iulia Munteanu', 'Logistician', 'Galati, Strada Irisilor, nr. 42 | 0729234567');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (10, 'Mihai Georgescu', 'Supraveghetor de stoc', 'Galati, Strada Zambilelor, nr. 6 | 0730345678');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (11, 'Andrei Popescu', 'Aprovizionare', 'Galati, Strada Violetei, nr. 11 | 0731456789');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (12, 'Maria Stanescu', 'Inspector calitate', 'Galati, Strada Freziilor, nr. 25 | 0732567890');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (13, 'George Ionescu', 'Manipulant marfa', 'Galati, Strada Ghioceilor, nr. 19 | 0733678901');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (14, 'Elena Popa', 'Responsabil depozit', 'Galati, Strada Bujorilor, nr. 13 | 0734789012');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (15, 'Victor Dumitrescu', 'Manager de depozit', 'Galati, Strada Papadiei, nr. 36 | 0735890123');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (16, 'Iulia Radulescu', 'Logistician', 'Galati, Strada Narciselor, nr. 29 | 0736901234');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (17, 'Andrei Nicolescu', 'Supraveghetor de stoc', 'Galati, Strada Caprifoiului, nr. 8 | 0738012345');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (18, 'Maria Dumitrescu', 'Aprovizionare', 'Galati, Strada Macilor, nr. 14 | 0739123456');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (19, 'George Popescu', 'Inspector calitate', 'Galati, Strada Crinilor, nr. 20 | 0740234567');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (20, 'Elena Stanescu', 'Manipulant marfa', 'Galati, Strada Hortensiilor, nr. 16 | 0741345678');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (21, 'Victor Ionescu', 'Responsabil depozit', 'Galati, Strada Trifoiului, nr. 27 | 0742456789');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (22, 'Iulia Munteanu', 'Manager de depozit', 'Galati, Strada Iedersilor, nr. 32 | 0743567890');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (23, 'Mihai Radulescu', 'Logistician', 'Galati, Strada Alunelor, nr. 10 | 0744678901');
INSERT INTO angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (24, 'Ana Dumitrescu', 'Supraveghetor de stoc', 'Galati, Strada Castanelor, nr. 22 | 0745789012');
commit;


-- Creeaz? secven?a temporar?
CREATE SEQUENCE temp_seq START WITH 1 INCREMENT BY 1;

-- Actualizeaz? direct valorile ID-urilor existente pentru a fi consecutive de la 1 la 24
UPDATE angajati
SET ID_ANGAJAT = temp_seq.NEXTVAL
WHERE ID_ANGAJAT IN (1, 5, 45, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);

-- ?terge secven?a temporar?
DROP SEQUENCE temp_seq;

DROP SEQUENCE angajati_seq;

CREATE SEQUENCE angajati_seq
START WITH 24
INCREMENT BY 1;

CREATE OR REPLACE TRIGGER angajati_before_insert
BEFORE INSERT ON angajati
FOR EACH ROW
BEGIN
  :new.ID_ANGAJAT := angajati_seq.NEXTVAL;
END;
/

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT)
VALUES
(1, 'Ion Popescu', 'Str. Primaverii, nr. 10, Galati', '0721123456'),
(2, 'Maria Ionescu', 'Bulevardul Libertatii, nr. 25, Tulcea', '0732234567'),
(3, 'Radu Georgescu', 'Aleea Teiului, nr. 8, Foc?ani', '0743345678'),
(4, 'Ana Dumitrescu', 'Str. Florilor, nr. 14, Galati', '0724456789'),
(5, 'Vasile Stanescu', 'B-dul Unirii, nr. 17, Tulcea', '0735567890'),
(6, 'Elena Popa', 'Aleea Mihai Viteazu, nr. 21, Galati', '0746678901'),
(7, 'Andrei Ionescu', 'Str. Crizantemelor, nr. 32, Foc?ani', '0727789012'),
(8, 'Gabriela Munteanu', 'Bulevardul Dacia, nr. 13, Galati', '0738890123'),
(9, 'Victor Radulescu', 'Aleea Rozelor, nr. 19, Tulcea', '0749001234'),
(10, 'Maria Nicolescu', 'Str. Trandafirilor, nr. 28, Foc?ani', '0720112345'),
(11, 'Alexandru Popescu', 'B-dul Carol I, nr. 7, Galati', '0731223456'),
(12, 'Elena Stanescu', 'Aleea Fagilor, nr. 16, Tulcea', '0742334567'),
(13, 'Ion Georgescu', 'Str. Violetelor, nr. 23, Foc?ani', '0723445678'),
(14, 'Ana Ionescu', 'Bulevardul Eroilor, nr. 12, Galati', '0734556789'),
(15, 'Radu Munteanu', 'Str. Magnoliilor, nr. 5, Tulcea', '0745667890'),
(16, 'Gabriela Popa', 'Aleea Castanelor, nr. 18, Foc?ani', '0726778901'),
(17, 'Victor Stanescu', 'B-dul 1 Mai, nr. 11, Galati', '0737889012'),
(18, 'Iulia Radulescu', 'Str. Zambilelor, nr. 26, Tulcea', '0748990123'),
(19, 'Andrei Popa', 'Aleea Crinilor, nr. 14, Foc?ani', '0729001234'),
(20, 'Elena Georgescu', 'Bulevardul Republicii, nr. 9, Galati', '0730112345'),
(21, 'Ion Dumitrescu', 'Str. Alunelor, nr. 22, Tulcea', '0741223456'),
(22, 'Maria Popescu', 'Aleea Lalelelor, nr. 33, Foc?ani', '0722334567'),
(23, 'Victor Munteanu', 'B-dul Traian, nr. 16, Galati', '0733445678'),
(24, 'Ana Ionescu', 'Str. Narciselor, nr. 20, Tulcea', '0744556789'),
(25, 'Radu Radulescu', 'Aleea Azaleelor, nr. 7, Foc?ani', '0725667890'),
(26, 'Elena Stanescu', 'Bulevardul Carol I, nr. 28, Galati', '0736778901'),
(27, 'Andrei Popescu', 'Str. Crizantemelor, nr. 15, Tulcea', '0747889012'),
(28, 'Gabriela Ionescu', 'Aleea Zambilelor, nr. 11, Foc?ani', '0728990123'),
(29, 'Victor Radulescu', 'Str. Viorelelor, nr. 26, Galati', '0739001234'),
(30, 'Ana Georgescu', 'B-dul Eroilor, nr. 19, Tulcea', '0740112345');

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (1, 'Ion Popescu', 'Str. Primaverii, nr. 10, Galati', '0721123456');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (2, 'Maria Ionescu', 'Bulevardul Libertatii, nr. 25, Tulcea', '0732234567');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (3, 'Radu Georgescu', 'Aleea Teiului, nr. 8, Foc?ani', '0743345678');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (4, 'Ana Dumitrescu', 'Str. Florilor, nr. 14, Galati', '0724456789');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (5, 'Vasile Stanescu', 'B-dul Unirii, nr. 17, Tulcea', '0735567890');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (6, 'Elena Popa', 'Aleea Mihai Viteazu, nr. 21, Galati', '0746678901');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (7, 'Andrei Ionescu', 'Str. Crizantemelor, nr. 32, Foc?ani', '0727789012');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (8, 'Gabriela Munteanu', 'Bulevardul Dacia, nr. 13, Galati', '0738890123');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (9, 'Victor Radulescu', 'Aleea Rozelor, nr. 19, Tulcea', '0749001234');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (10, 'Maria Nicolescu', 'Str. Trandafirilor, nr. 28, Foc?ani', '0720112345');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (11, 'Alexandru Popescu', 'B-dul Carol I, nr. 7, Galati', '0731223456');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (12, 'Elena Stanescu', 'Aleea Fagilor, nr. 16, Tulcea', '0742334567');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (13, 'Ion Georgescu', 'Str. Violetelor, nr. 23, Foc?ani', '0723445678');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (14, 'Ana Ionescu', 'Bulevardul Eroilor, nr. 12, Galati', '0734556789');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (15, 'Radu Munteanu', 'Str. Magnoliilor, nr. 5, Tulcea', '0745667890');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (16, 'Gabriela Popa', 'Aleea Castanelor, nr. 18, Foc?ani', '0726778901');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (17, 'Victor Stanescu', 'B-dul 1 Mai, nr. 11, Galati', '0737889012');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (18, 'Iulia Radulescu', 'Str. Zambilelor, nr. 26, Tulcea', '0748990123');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (19, 'Andrei Popa', 'Aleea Crinilor, nr. 14, Foc?ani', '0729001234');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (20, 'Elena Georgescu', 'Bulevardul Republicii, nr. 9, Galati', '0730112345');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (21, 'Ion Dumitrescu', 'Str. Alunelor, nr. 22, Tulcea', '0741223456');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (22, 'Maria Popescu', 'Aleea Lalelelor, nr. 33, Foc?ani', '0722334567');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (23, 'Victor Munteanu', 'B-dul Traian, nr. 16, Galati', '0733445678');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (24, 'Ana Ionescu', 'Str. Narciselor, nr. 20, Tulcea', '0744556789');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (25, 'Radu Radulescu', 'Aleea Azaleelor, nr. 7, Foc?ani', '0725667890');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (26, 'Elena Stanescu', 'Bulevardul Carol I, nr. 28, Galati', '0736778901');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (27, 'Andrei Popescu', 'Str. Crizantemelor, nr. 15, Tulcea', '0747889012');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (28, 'Gabriela Ionescu', 'Aleea Zambilelor, nr. 11, Foc?ani', '0728990123');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (29, 'Victor Radulescu', 'Str. Viorelelor, nr. 26, Galati', '0739001234');
COMMIT;

INSERT INTO clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (30, 'Ana Georgescu', 'B-dul Eroilor, nr. 19, Tulcea', '0740112345');
COMMIT;

UPDATE CLIENTI
SET 
  NUME = 'Fara Client',
  ADRESA = '-',
  CONTACT = '-'
WHERE
  ID_CLIENT = '0';


DROP SEQUENCE clienti_seq;

CREATE SEQUENCE clienti_seq
START WITH 33
INCREMENT BY 1;

DROP TRIGGER clienti_before_insert

CREATE OR REPLACE TRIGGER clienti_before_insert
BEFORE INSERT ON clienti
FOR EACH ROW
BEGIN
  :new.ID_CLIENT := CLIENTi_seq.NEXTVAL;
END;
/

-- Pentru ID_CLIENT = 10
UPDATE clienti SET ID_CLIENT = 0 WHERE ID_CLIENT = 34;

-- Pentru ID_CLIENT = 51
UPDATE clienti SET ID_CLIENT = 2 WHERE ID_CLIENT = 51;

-- Pentru ID_CLIENT = 31
UPDATE clienti SET ID_CLIENT = 3 WHERE ID_CLIENT = 31;

CREATE SEQUENCE clienti_seq
START WITH 1
INCREMENT BY 1;

UPDATE clienti
SET ID_CLIENT = clienti_seq.NEXTVAL
WHERE ID_CLIENT IN (10, 2, 3, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81);
SELECT * FROM clienti ORDER BY ID_CLIENT;


//Furnizori


UPDATE furnizori
SET ID_FURNIZOR = 101,
    NUME = 'Pietrigica',
    ADRESA = 'Blvd. George Cosbuc, nr.213, Galati',
    CONTACT = 755130778
WHERE ID_FURNIZOR = 178;


UPDATE furnizori
SET ID_FURNIZOR = 
    CASE 
        WHEN ID_FURNIZOR = 172 THEN 103
        WHEN ID_FURNIZOR = 180 THEN 104
        WHEN ID_FURNIZOR = 174 THEN 105
        WHEN ID_FURNIZOR = 166 THEN 106
        WHEN ID_FURNIZOR = 176 THEN 107
    END,
    NUME = 
    CASE 
        WHEN ID_FURNIZOR = 172 THEN 'Kamina'
        WHEN ID_FURNIZOR = 180 THEN 'Mairon'
        WHEN ID_FURNIZOR = 174 THEN 'Figran'
        WHEN ID_FURNIZOR = 166 THEN 'Dedeman'
        WHEN ID_FURNIZOR = 176 THEN 'Conmag'
    END,
    ADRESA = 
    CASE 
        WHEN ID_FURNIZOR = 172 THEN 'Blvd. George Cosbuc, nr. 209, Galati'
        WHEN ID_FURNIZOR = 180 THEN 'Calea Smardan, nr.9, Galati'
        WHEN ID_FURNIZOR = 174 THEN 'Blvd. George Cosbuc, nr.276'
        WHEN ID_FURNIZOR = 166 THEN 'Str. Combinatului, nr.9, Galati'
        WHEN ID_FURNIZOR = 176 THEN 'Str. Alexandru Cernat, nr. 110, Galati'
    END,
    CONTACT = 
    CASE 
        WHEN ID_FURNIZOR = 172 THEN 744582932
        WHEN ID_FURNIZOR = 180 THEN 236449393
        WHEN ID_FURNIZOR = 174 THEN 236410815
        WHEN ID_FURNIZOR = 166 THEN 234525525
        WHEN ID_FURNIZOR = 176 THEN 236689547
    END
WHERE ID_FURNIZOR IN (172, 180, 174, 166, 176);

DROP SEQUENCE furnizori_seq;

CREATE SEQUENCE furnizori_seq
START WITH 108
INCREMENT BY 1;


DROP TRIGGER FURNIZORI_BEFORE_INSERT;

CREATE OR REPLACE TRIGGER FURNIZORI_before_insert
BEFORE INSERT ON furnizori
FOR EACH ROW
BEGIN
  SELECT furnizori_seq.NEXTVAL
  INTO :new.ID_FURNIZOR
  FROM dual;
END;

//Furnziori

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES
  (1, 'Ciment', 'Material de construc?ii', 100, 30.50, 101),
  (2, 'Placi de gips-carton', 'Material pentru constructii interioare', 50, 15.75, 102),
  (3, 'Beton', 'Material de constructii', 75, 45.20, 101),
  (4, 'Vopsea lavabila', 'Material pentru finisaje interioare', 30, 25.00, 103),
  (5, 'Fier beton', 'Material pentru constructii', 120, 12.30, 104),
  (6, 'Tigla metalica', 'Material pentru acoperis', 40, 60.80, 105),
  (7, 'Tencuiala decorativa', 'Material pentru finisaje exterioare', 20, 35.90, 102),
  (8, 'Parchet laminat', 'Material pentru pardoseli', 60, 40.50, 104),
  (9, 'Instalatii sanitare', 'Material pentru instalaii sanitare', 35, 18.60, 106),
  (10, 'Geamuri termopan', 'Material pentru ferestre', 25, 70.00, 107);




INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (2, 'Ciment', 'Material de constructii', 100, 30.50, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (3, 'Beton', 'Material de constructii', 75, 45.20, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (4, 'Vopsea lavabila', 'Material pentru finisaje interioare', 30, 25.00, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (5, 'Fier beton', 'Material pentru constructii', 120, 12.30, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (6, 'Tigla metalica', 'Material pentru acoperis', 40, 60.80, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (7, 'Tencuiala decorativa', 'Material pentru finisaje exterioare', 20, 35.90, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (8, 'Parchet laminat', 'Material pentru pardoseli', 60, 40.50, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (9, 'Instalatii sanitare', 'Material pentru instalaii sanitare', 35, 18.60, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (10, 'Geamuri termopan', 'Material pentru ferestre', 25, 70.00, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (11, 'Faianta', 'Material pentru finisaje interioare', 80, 28.50, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (12, 'Piatra decorativa', 'Material pentru finisaje exterioare', 45, 40.00, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (13, 'Rigips', 'Material pentru constructii interioare', 60, 18.75, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (14, 'Hidroizolatie', 'Material pentru acoperis', 25, 55.20, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (15, 'Mocheta', 'Material pentru pardoseli', 30, 32.90, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (16, 'Bordura', 'Material pentru exterior', 40, 22.40, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (17, 'Gresie', 'Material pentru pardoseli', 70, 37.50, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (18, 'Tapet', 'Material pentru finisaje interioare', 40, 19.80, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (19, 'Folie termoizolanta', 'Material pentru acoperis', 15, 48.90, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (20, 'Lambriu lemn', 'Material pentru finisaje exterioare', 25, 55.60, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (21, 'Lavoar baie', 'Material pentru instala?ii sanitare', 20, 75.00, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (22, 'Ferestre termopan', 'Material pentru ferestre', 30, 95.00, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (23, 'Tigla ceramica', 'Material pentru acoperis', 50, 72.30, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (24, 'Lemn de construc?ie', 'Material pentru construc?ii', 80, 28.90, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (25, 'Var alb', 'Material pentru finisaje interioare', 25, 15.20, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (26, 'Parchet lemn masiv', 'Material pentru pardoseli', 30, 62.50, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (27, 'Caramida aparenta', 'Material pentru construc?ii', 60, 42.80, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (28, 'Cablu electric', 'Material pentru instala?ii electrice', 40, 5.70, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (29, 'Piatra de rau', 'Material pentru construc?ii', 70, 38.90, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (30, 'Becuri LED', 'Material pentru iluminat', 50, 9.80, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (31, 'Tigla metalica', 'Material pentru acoperis', 60, 55.20, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (32, 'Glet', 'Material pentru finisaje interioare', 40, 10.50, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (33, 'PVC pentru instalatii electrice', 'Material pentru instala?ii electrice', 35, 8.90, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (34, 'Vitralii decorative', 'Material pentru ferestre', 20, 45.60, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (35, 'Gresie', 'Material pentru pardoseli', 55, 33.20, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (36, 'Lustra moderna', 'Material pentru iluminat', 30, 75.90, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (37, 'Piatra decorativa', 'Material pentru finisaje exterioare', 25, 28.70, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (38, 'Radiator', 'Material pentru înc?lzire', 18, 120.50, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (39, 'Usa termopan', 'Material pentru u?i', 22, 160.00, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (40, 'Cabluri sanitare', 'Material pentru instala?ii sanitare', 30, 7.80, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (41, 'Polistiren expandat', 'Material izolant termic', 90, 18.75, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (42, 'C?r?mizi refractare', 'Material pentru construc?ii rezistente la c?ldur?', 60, 40.90, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (43, 'Plante ornamentale', 'Material pentru peisagistic?', 35, 15.20, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (44, 'Placaj', 'Material pentru mobilier', 80, 28.60, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (45, 'Lavabil? lavanda', 'Vopsea pentru finisaje interioare', 25, 32.40, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (46, 'Cablu electric', 'Material pentru instala?ii electrice', 40, 9.80, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (47, 'Plac? OSB', 'Material pentru structuri de lemn', 55, 22.30, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (48, 'Mocheta', 'Material pentru pardoseli', 30, 18.90, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (49, 'Tapet', 'Material pentru finisaje interioare', 20, 14.70, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (50, 'Lustra clasic?', 'Material pentru iluminat', 18, 90.50, 107);

-- Materiale cu ID-uri cresc?toare
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (51, 'Piatr? decorativ?', 'Material pentru finisaje exterioare', 35, 27.80, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (52, 'Folie termoizolant?', 'Material pentru izola?ii termice', 70, 15.40, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (53, 'Lemn de esen?? tare', 'Material pentru construc?ii ?i mobilier', 45, 33.70, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (54, 'Lambriu PVC', 'Material pentru finisaje interioare', 25, 19.90, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (55, 'C?priori din lemn', 'Material pentru structuri de construc?ii', 60, 12.60, 104);

-- Materiale cu ID-uri aleatoare
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (56, 'Var alb', 'Material pentru finisaje interioare', 40, 9.20, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (57, 'Poliplan', 'Material pentru ?lefuire ?i finisare', 15, 44.50, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (58, 'Geotextil', 'Material pentru lucr?ri de terasament', 30, 18.20, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (59, 'Pardoseal? flotant?', 'Material pentru pardoseli', 50, 32.90, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (60, 'Vat? mineral?', 'Material izolant termic', 25, 15.60, 101);
-- Materiale cu ID-uri cresc?toare ?i furnizor 100
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (61, 'Lavabil lavand?', 'Vopsea lavabil? pentru interior', 20, 28.90, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (62, 'Pardoseal? vinilic?', 'Material pentru pardoseli', 40, 20.50, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (63, 'Lambriu din lemn masiv', 'Material pentru finisaje interioare', 15, 55.70, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (64, 'Chit pentru rosturi', 'Material pentru finisaje', 30, 9.80, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (65, 'Polistiren extrudat', 'Material pentru izola?ii termice', 25, 16.30, 100);

-- Materiale cu ID-uri aleatoare ?i furnizor 100
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (66, 'Pl?ci OSB', 'Material pentru construc?ii', 35, 22.40, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (67, 'Tigl? ceramic?', 'Material pentru acoperi?', 18, 45.60, 100);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (68, 'Glet de finisare', 'Material pentru finisaje interioare', 30, 14.20, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (69, 'Hârtie abraziv?', 'Material pentru prelucrarea lemnului', 50, 7.90, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (70, 'Folie polietilen?', 'Material pentru protec?ia împotriva umidit??ii', 40, 11.50, 104);

-- Continuare cu ID-uri cresc?toare ?i furnizori diferi?i
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (71, 'Cabluri coaxiale', 'Material pentru instala?ii TV', 25, 12.90, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (72, 'Tigla bituminoas?', 'Material pentru acoperi?', 30, 55.40, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (73, 'Lavabil? albastr?', 'Vopsea pentru finisaje interioare', 22, 29.80, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (74, 'Conducte PVC', 'Material pentru instala?ii sanitare', 40, 8.50, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (75, 'Folie geotextil?', 'Material pentru construc?ii', 35, 17.20, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (76, 'Tencuial? decorativ? exterior', 'Material pentru finisaje exterioare', 28, 38.60, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (77, 'Por?i metalice', 'Material pentru u?i ?i ferestre', 15, 120.00, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (78, 'Beton celular autoclavizat', 'Material pentru construc?ii u?oare', 50, 26.80, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (79, 'Profile din aluminiu', 'Material pentru tâmpl?rie', 60, 15.90, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (80, 'Gresie ?i faian??', 'Material pentru finisaje interioare', 42, 32.50, 101);

-- Continuare cu ID-uri cresc?toare ?i furnizori diferi?i
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (81, 'Lavabil? verde', 'Vopsea pentru finisaje interioare', 15, 31.50, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (82, 'Faianta decorativa', 'Material pentru finisaje interioare', 28, 23.80, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (83, 'Lustre vintage', 'Material pentru iluminat', 20, 68.90, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (84, 'C?r?mizi decorative', 'Material pentru finisaje interioare', 25, 18.60, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (85, 'Parchet din lemn masiv', 'Material pentru pardoseli', 18, 55.30, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (86, 'Por?elanuri sanitare', 'Material pentru instala?ii sanitare', 30, 32.70, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (87, 'Folie termoizolant?', 'Material pentru izola?ii', 40, 15.90, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (88, 'Balustrade din inox', 'Material pentru balustrade', 15, 95.40, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (89, 'Materiale pentru gr?din?rit', 'Material pentru gr?din?', 22, 14.20, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (90, 'Central? termic?', 'Material pentru înc?lzire', 10, 380.00, 106);
-- Continuare cu ID-uri cresc?toare ?i furnizori aleatorii
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (91, 'Feronerie pentru u?i', 'Material pentru u?i', 25, 14.30, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (92, 'Lavabil? albastr?', 'Vopsea pentru finisaje interioare', 18, 29.80, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (93, 'Instala?ii electrice complete', 'Material pentru instala?ii electrice', 30, 75.20, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (94, 'Placi solare', 'Material pentru înc?lzire', 12, 180.50, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (95, 'Usi de garaj', 'Material pentru u?i', 15, 320.00, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (96, 'Chit pentru rosturi', 'Material pentru finisaje interioare', 40, 7.90, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (97, 'Gazebo din lemn', 'Material pentru gr?din?', 8, 280.40, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (98, 'Dulap metalic', 'Material pentru depozitare', 20, 120.90, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (99, 'Vopsea anti-mucegai', 'Material pentru finisaje interioare', 22, 18.60, 105);
-- ID-uri începând de la 100 ?i furnizori aleatorii
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (100, 'Ciment', 'Material de constructii', 100, 30.50, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (101, 'Beton', 'Material de constructii', 75, 45.20, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (102, 'Vopsea lavabila', 'Material pentru finisaje interioare', 30, 25.00, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (103, 'Fier beton', 'Material pentru constructii', 120, 12.30, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (104, 'Tigla metalica', 'Material pentru acoperis', 40, 60.80, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (105, 'Tencuiala decorativa', 'Material pentru finisaje exterioare', 20, 35.90, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (106, 'Parchet laminat', 'Material pentru pardoseli', 60, 40.50, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (107, 'Instalatii sanitare', 'Material pentru instalaii sanitare', 35, 18.60, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (108, 'Geamuri termopan', 'Material pentru ferestre', 25, 70.00, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (109, 'Faianta', 'Material pentru finisaje interioare', 80, 28.50, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (110, 'Piatra decorativa', 'Material pentru finisaje exterioare', 45, 40.00, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (111, 'Rigips', 'Material pentru constructii interioare', 60, 18.75, 102);

-- ID-uri începând de la 112 ?i furnizori aleatorii
INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (112, 'Hidroizolatie bituminoasa', 'Material pentru acoperis', 50, 35.80, 105);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (113, 'Gresie exterior', 'Material pentru pardoseli', 45, 28.00, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (114, 'Placaje decorative', 'Material pentru finisaje interioare', 55, 19.50, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (115, 'Sticla securizata', 'Material pentru ferestre', 30, 42.90, 108);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (116, 'Parchet masiv', 'Material pentru pardoseli', 70, 55.60, 101);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (117, 'Vopsea decorativa', 'Material pentru finisaje exterioare', 25, 30.80, 102);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (118, 'Cabluri electrice', 'Material pentru instala?ii electrice', 40, 12.50, 103);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (119, 'Piatra naturala', 'Material pentru finisaje exterioare', 20, 75.00, 104);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (120, 'Usa metalica', 'Material pentru u?i', 18, 120.00, 106);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (121, 'Dulapuri bucatarie', 'Material pentru mobilier', 22, 90.00, 107);

INSERT INTO materiale (ID_MATERIAL, NUME, DESCRIERE, CANTITATE_IN_STOC, PRET_UNITAR, ID_FURNIZOR)
VALUES (122, 'Panouri solare', 'Material pentru instala?ii sanitare', 15, 220.50, 108);



DROP SEQUENCE materiale_seq


CREATE SEQUENCE materiale_seq
START WITH 123
INCREMENT BY 1;

DROP TRIGGER MATERIALE_BEFORE_INSERT;


CREATE OR REPLACE TRIGGER materiale_before_insert
BEFORE INSERT ON materiale
FOR EACH ROW
BEGIN
  SELECT materiale_seq.NEXTVAL
  INTO :new.ID_MATERIAL
  FROM dual;
END;
//Transportatori
DROP SEQUENCE transportatori_seq

CREATE SEQUENCE transportatori_seq
START WITH 8
INCREMENT BY 1;

DROP TRIGGER TRANSPORTATORI_BEFORE_INSERT;


CREATE OR REPLACE TRIGGER transportatori_before_insert
BEFORE INSERT ON transportatori
FOR EACH ROW
BEGIN
  SELECT transportatori_seq.NEXTVAL
  INTO :new.ID_TRANSPORTATOR
  FROM dual;
END;

UPDATE TRANSPORTATORI SET ID_TRANSPORTATOR = 3 WHERE ID_TRANSPORTATOR = 45;

UPDATE TRANSPORTATORI SET ID_TRANSPORTATOR = 5 WHERE ID_TRANSPORTATOR = 25;

UPDATE TRANSPORTATORI SET ID_TRANSPORTATOR = 6 WHERE ID_TRANSPORTATOR = 46;

UPDATE TRANSPORTATORI SET ID_TRANSPORTATOR = 8 WHERE ID_TRANSPORTATOR = 47;

//COMENZI

DROP SEQUENCE comenzi_seq

CREATE SEQUENCE comenzi_seq
START WITH 65
INCREMENT BY 1;

DROP TRIGGER COMENZI_BEFORE_INSERT;


CREATE OR REPLACE TRIGGER comenzi_before_insert
BEFORE INSERT ON COMENZI
FOR EACH ROW
BEGIN
  SELECT comenzi_seq.NEXTVAL
  INTO :new.ID_COMANDA
  FROM dual;
END;

-- Comanda 1
INSERT INTO COMENZI
VALUES (2, TO_DATE('16-01-2024', 'DD-MM-YYYY'), 3, 101, 5, 7, 50, 'In procesare', 'Aprovizionare', 100, 500.00);

-- Comanda 2
INSERT INTO TABELA_COMENZI
VALUES (2, TO_DATE('17-01-2024', 'DD-MM-YYYY'), 1, 102, 6, 14, 30, 'In asteptare', 'Aprovizionare', 20, 608.00);

-- Comanda 3
INSERT INTO TABELA_COMENZI
VALUES (3, TO_DATE('18-01-2024', 'DD-MM-YYYY'), 2, 103, 7, 8, 40, 'Finalizata', 'Vanzare', 15, 405.00);

-- Comanda 4
INSERT INTO TABELA_COMENZI
VALUES (4, TO_DATE('19-01-2024', 'DD-MM-YYYY'), 4, 104, 10, 18, 60, 'In procesare', 'Vanzare', 30, 1012.50);

-- Comanda 3 (Exemplu de comand? de expediere)
INSERT INTO COMENZI
VALUES (3, TO_DATE('16-01-2024', 'DD-MM-YYYY'), 1, 0, 6, 8, 30, 'In procesare', 'Expediere', 80, 240.00);
-- Comanda 4 (Exemplu de comand? de aprovizionare)
INSERT INTO COMENZI
VALUES (4, TO_DATE('17-01-2024', 'DD-MM-YYYY'), 5, 102, 4, 12, 25, 'Neprocesat?', 'Aprovizionare', 120, 305.00);

-- Comanda 5
INSERT INTO COMENZI VALUES (5, TO_DATE('18-01-2024', 'DD-MM-YYYY'), 13, 103, 3, 19, 40, 'Procesat?', 'Expediere', 70, 800.00);

-- Comanda 6
INSERT INTO COMENZI VALUES (6, TO_DATE('19-01-2024', 'DD-MM-YYYY'), 9, 104, 7, 27, 15, 'Procesat?', 'Aprovizionare', 25, 300.00);

-- Comanda 7
INSERT INTO COMENZI VALUES (7, TO_DATE('20-01-2024', 'DD-MM-YYYY'), 22, 105, 11, 16, 60, 'Neprocesat?', 'Expediere', 35, 450.00);

-- Comanda 8
INSERT INTO COMENZI VALUES (8, TO_DATE('21-01-2024', 'DD-MM-YYYY'), 16, 106, 10, 33, 20, 'Neprocesat?', 'Aprovizionare', 40, 600.00);

-- Comanda 9
INSERT INTO COMENZI VALUES (9, TO_DATE('22-01-2024', 'DD-MM-YYYY'), 18, 107, 6, 22, 55, 'Procesat?', 'Expediere', 50, 1100.00);

-- Comanda 10
INSERT INTO COMENZI VALUES (10, TO_DATE('23-01-2024', 'DD-MM-YYYY'), 20, 108, 9, 34, 10, 'Procesat?', 'Aprovizionare', 30, 400.00);

-- Comanda 11
INSERT INTO COMENZI VALUES (11, TO_DATE('24-01-2024', 'DD-MM-YYYY'), 21, 101, 5, 11, 75, 'Neprocesat?', 'Expediere', 65, 950.00);

-- Comanda 12
INSERT INTO COMENZI VALUES (12, TO_DATE('25-01-2024', 'DD-MM-YYYY'), 23, 102, 8, 28, 18, 'Procesat?', 'Aprovizionare', 20, 250.00);

-- Comanda 13
INSERT INTO COMENZI VALUES (13, TO_DATE('26-01-2024', 'DD-MM-YYYY'), 25, 103, 4, 24, 45, 'Neprocesat?', 'Expediere', 38, 500.00);

-- Comanda 14
INSERT INTO COMENZI VALUES (14, TO_DATE('27-01-2024', 'DD-MM-YYYY'), 27, 104, 12, 29, 22, 'Procesat?', 'Aprovizionare', 30, 350.00);

-- Comanda 15
INSERT INTO COMENZI VALUES (15, TO_DATE('28-01-2024', 'DD-MM-YYYY'), 28, 105, 15, 56, 28, 'Neprocesat?', 'Expediere', 20, 280.00);

-- Comanda 16
INSERT INTO COMENZI VALUES (16, TO_DATE('29-01-2024', 'DD-MM-YYYY'), 30, 106, 18, 71, 40, 'Procesat?', 'Aprovizionare', 55, 720.00);

-- Comanda 17
INSERT INTO COMENZI VALUES (17, TO_DATE('30-01-2024', 'DD-MM-YYYY'), 32, 107, 23, 62, 33, 'Neprocesat?', 'Expediere', 25, 320.00);

-- Comanda 18
INSERT INTO COMENZI VALUES (18, TO_DATE('31-01-2024', 'DD-MM-YYYY'), 35, 108, 26, 79, 48, 'Procesat?', 'Aprovizionare', 30, 380.00);

-- Comanda 19
INSERT INTO COMENZI VALUES (19, TO_DATE('18-03-2024', 'DD-MM-YYYY'), 23, 103, 3, 19, 40, 'Procesat?', 'Expediere', 70, 800.00);

-- Comanda 20
INSERT INTO COMENZI VALUES (20, TO_DATE('18-03-2022', 'DD-MM-YYYY'), 7, 100, 7, 11, 55, 'Procesat?', 'Expediere', 50, 850.00);

INSERT INTO COMENZI
VALUES (21, TO_DATE('17-03-2023', 'DD-MM-YYYY'), 0, 101, 5, 7, 50, 'Procesata', 'Aprovizionare', 80, 550.00);

-- Comanda 22
INSERT INTO COMENZI VALUES (22, TO_DATE('18-03-2023', 'DD-MM-YYYY'), 4, 102, 6, 8, 30, 'Neprocesat?', 'Expediere', 70, 400.00);

-- Comanda 23
INSERT INTO COMENZI VALUES (23, TO_DATE('19-03-2023', 'DD-MM-YYYY'), 8, 103, 7, 27, 15, 'Procesat?', 'Aprovizionare', 25, 300.00);

-- Comanda 24
INSERT INTO COMENZI VALUES (24, TO_DATE('20-03-2023', 'DD-MM-YYYY'), 10, 104, 8, 32, 40, 'Procesat?', 'Expediere', 60, 900.00);

-- Comanda 25
INSERT INTO COMENZI VALUES (25, TO_DATE('21-03-2023', 'DD-MM-YYYY'), 12, 105, 9, 21, 25, 'Neprocesat?', 'Aprovizionare', 35, 700.00);

-- Comanda 26
INSERT INTO COMENZI VALUES (26, TO_DATE('22-03-2023', 'DD-MM-YYYY'), 15, 106, 10, 33, 20, 'Procesat?', 'Expediere', 50, 800.00);
-- Comanda 29
INSERT INTO COMENZI VALUES (29, TO_DATE('17-03-2023', 'DD-MM-YYYY'), 19, 0, 3, 20, 55, 'Procesat?', 'Aprovizionare', 70, 800.00);

-- Comanda 30
INSERT INTO COMENZI VALUES (30, TO_DATE('18-03-2023', 'DD-MM-YYYY'), 1, 0, 6, 8, 30, 'Procesat?', 'Expediere', 80, 240.00);

-- Comanda 31
INSERT INTO COMENZI VALUES (31, TO_DATE('19-03-2023', 'DD-MM-YYYY'), 2, 0, 7, 27, 15, 'Neprocesat?', 'Aprovizionare', 25, 300.00);

-- Comanda 32
INSERT INTO COMENZI VALUES (32, TO_DATE('20-03-2023', 'DD-MM-YYYY'), 4, 0, 9, 34, 10, 'Neprocesat?', 'Expediere', 35, 450.00);

-- Comanda 33
INSERT INTO COMENZI VALUES (33, TO_DATE('21-03-2023', 'DD-MM-YYYY'), 5, 0, 11, 16, 60, 'Procesat?', 'Aprovizionare', 40, 600.00);

-- Comanda 34
INSERT INTO COMENZI VALUES (34, TO_DATE('22-03-2023', 'DD-MM-YYYY'), 6, 0, 12, 33, 20, 'Procesat?', 'Expediere', 50, 1100.00);

-- Comanda 35
INSERT INTO COMENZI VALUES (35, TO_DATE('23-03-2023', 'DD-MM-YYYY'), 7, 0, 13, 14, 45, 'Neprocesat?', 'Aprovizionare', 30, 400.00);

-- Comanda 36
INSERT INTO COMENZI VALUES (36, TO_DATE('24-03-2023', 'DD-MM-YYYY'), 8, 0, 14, 42, 30, 'Procesat?', 'Expediere', 55, 750.00);

-- Comanda 37
INSERT INTO COMENZI VALUES (37, TO_DATE('25-03-2023', 'DD-MM-YYYY'), 9, 0, 15, 45, 35, 'Procesat?', 'Aprovizionare', 22, 280.00);

-- Comanda 38
INSERT INTO COMENZI VALUES (38, TO_DATE('26-03-2023', 'DD-MM-YYYY'), 10, 0, 16, 48, 28, 'Neprocesat?', 'Expediere', 30, 400.00);

-- Comanda 39
INSERT INTO COMENZI VALUES (39, TO_DATE('27-03-2023', 'DD-MM-YYYY'), 11, 0, 17, 18, 40, 'Procesat?', 'Aprovizionare', 28, 340.00);

-- Comanda 40
INSERT INTO COMENZI VALUES (40, TO_DATE('28-03-2023', 'DD-MM-YYYY'), 12, 0, 18, 19, 50, 'Neprocesat?', 'Expediere', 45, 600.00);

-- Comanda 41
INSERT INTO COMENZI VALUES (41, TO_DATE('29-03-2023', 'DD-MM-YYYY'), 13, 0, 19, 20, 60, 'Procesat?', 'Aprovizionare', 33, 420.00);

-- Comanda 42
INSERT INTO COMENZI VALUES (42, TO_DATE('30-03-2023', 'DD-MM-YYYY'), 14, 0, 20, 21, 25, 'Procesat?', 'Expediere', 18, 270.00);

-- Comanda 43
INSERT INTO COMENZI VALUES (43, TO_DATE('31-03-2023', 'DD-MM-YYYY'), 15, 0, 21, 22, 35, 'Neprocesat?', 'Aprovizionare', 20, 250.00);

-- Comanda 44
INSERT INTO COMENZI VALUES (44, TO_DATE('01-04-2023', 'DD-MM-YYYY'), 16, 0, 22, 23, 55, 'Procesat?', 'Expediere', 42, 620.00);

-- Comanda 45
INSERT INTO COMENZI VALUES (45, TO_DATE('02-04-2023', 'DD-MM-YYYY'), 17, 0, 23, 25, 45, 'Neprocesat?', 'Aprovizionare', 28, 340.00);

-- Comanda 46
INSERT INTO COMENZI VALUES (46, TO_DATE('03-04-2023', 'DD-MM-YYYY'), 18, 0, 24, 26, 30, 'Procesat?', 'Expediere', 60, 900.00);

-- Comanda 47
INSERT INTO COMENZI VALUES (47, TO_DATE('04-04-2023', 'DD-MM-YYYY'), 19, 0, 25, 27, 20, 'Procesat?', 'Aprovizionare', 38, 570.00);

-- Comanda 48
INSERT INTO COMENZI VALUES (48, TO_DATE('05-04-2023', 'DD-MM-YYYY'), 20, 0, 26, 28, 35, 'Neprocesat?', 'Expediere', 25, 300.00);

-- Comanda 49
INSERT INTO COMENZI VALUES (49, TO_DATE('22-04-2023', 'DD-MM-YYYY'), 20, 0, 11, 57, 62, 'Procesat?', 'Expediere', 40, 960.00);

-- Comanda 50
INSERT INTO COMENZI VALUES (50, TO_DATE('23-04-2023', 'DD-MM-YYYY'), 11, 0, 13, 59, 40, 'Neprocesat?', 'Aprovizionare', 35, 450.00);

-- Comanda 51
INSERT INTO COMENZI VALUES (51, TO_DATE('24-04-2023', 'DD-MM-YYYY'), 21, 0, 12, 61, 55, 'Procesat?', 'Expediere', 50, 600.00);

-- Comanda 52
INSERT INTO COMENZI VALUES (52, TO_DATE('25-04-2023', 'DD-MM-YYYY'), 14, 0, 8, 63, 20, 'Procesat?', 'Aprovizionare', 30, 400.00);

-- Comanda 53
INSERT INTO COMENZI VALUES (53, TO_DATE('26-04-2023', 'DD-MM-YYYY'), 65, 0, 4, 65, 30, 'Neprocesat?', 'Aprovizionare', 55, 800.00);

-- Comanda 54
INSERT INTO COMENZI VALUES (54, TO_DATE('27-04-2023', 'DD-MM-YYYY'), 12, 0, 2, 67, 45, 'Neprocesat?', 'Aprovizionare', 40, 700.00);

-- Comanda 55
INSERT INTO COMENZI VALUES (67, TO_DATE('28-04-2023', 'DD-MM-YYYY'), 17, 0, 5, 69, 25, 'Procesat?', 'Aprovizionare', 65, 950.00);

-- Comanda 56
INSERT INTO COMENZI VALUES (56, TO_DATE('29-04-2023', 'DD-MM-YYYY'), 21, 0, 23, 71, 35, 'Procesat?', 'Aprovizionare', 20, 300.00);

-- Comanda 57
INSERT INTO COMENZI VALUES (57, TO_DATE('30-04-2023', 'DD-MM-YYYY'), 15, 0, 16, 73, 60, 'Neprocesat?', 'Expediere', 45, 580.00);

-- Comanda 58
INSERT INTO COMENZI VALUES (58, TO_DATE('01-05-2023', 'DD-MM-YYYY'), 14, 0, 19, 75, 28, 'Procesat?', 'Aprovizionare', 25, 420.00);

INSERT INTO COMENZI VALUES (70, TO_DATE('20-03-2022', 'DD-MM-YYYY'), 0, 102, 8, 15, 70, 'Procesat?', 'Aprovizionare', 55, 1005.00);

INSERT INTO COMENZI VALUES (71, TO_DATE('20-03-2022', 'DD-MM-YYYY'), 11, 0, 10, 17, 65, 'Procesat?', 'Expediere', 55, 1005.00);

-- Comanda 75 (pentru client)
INSERT INTO COMENZI VALUES (72, TO_DATE('22-04-2022', 'DD-MM-YYYY'), 0, 115, 7, 12, 62, 'Neprocesat', 'Expediere', 50, 890.00);

-- Comanda 76 (pentru furnizor)
INSERT INTO COMENZI VALUES (73, TO_DATE('25-04-2022', 'DD-MM-YYYY'), 0, 103, 8, 14, 68, 'Procesat', 'Aprovizionare', 40, 1200.50);

-- Comanda 77 (pentru client)
INSERT INTO COMENZI VALUES (74, TO_DATE('28-04-2022', 'DD-MM-YYYY'), 10, 0, 6, 11, 55, 'Procesat', 'Expediere', 45, 950.20);

-- Comanda 78 (pentru furnizor)
INSERT INTO COMENZI VALUES (75, TO_DATE('01-05-2022', 'DD-MM-YYYY'), 10, 0, 5, 8, 50, 'Neprocesat', 'In Asteptare', 30, 700.75);

-- Comanda 79 (pentru client)
INSERT INTO COMENZI VALUES (76, TO_DATE('04-05-2022', 'DD-MM-YYYY'), 25, 0, 7, 13, 60, 'Procesat', 'Expediere', 52, 1100.50);


Pentru a face un UPDATE pentru un singur rând în baza ta de date, po?i folosi o instruc?iune de genul:


UPDATE COMENZI SET ID_COMANDA = 2 WHERE ID_COMANDA=1006;

UPDATE COMENZI SET ID_COMANDA = 18 WHERE ID_COMANDA=1005;

UPDATE COMENZI SET ID_COMANDA = 48 WHERE ID_COMANDA=67;
UPDATE COMENZI SET ID_COMANDA = 53 WHERE ID_COMANDA=69;
UPDATE COMENZI SET ID_COMANDA = 55 WHERE ID_COMANDA=70;
UPDATE COMENZI SET ID_COMANDA = 59 WHERE ID_COMANDA=71;
UPDATE COMENZI SET ID_COMANDA = 60 WHERE ID_COMANDA=73;
UPDATE COMENZI SET ID_COMANDA = 61 WHERE ID_COMANDA=74;
UPDATE COMENZI SET ID_COMANDA = 62 WHERE ID_COMANDA=75;
UPDATE COMENZI SET ID_COMANDA = 63 WHERE ID_COMANDA=76;
UPDATE COMENZI SET ID_COMANDA = 64 WHERE ID_COMANDA=77;
UPDATE COMENZI SET ID_COMANDA = 65 WHERE ID_COMANDA=78;

//Evaluari


DROP SEQUENCE evaluare_seq

CREATE SEQUENCE evaluare_seq
START WITH 65
INCREMENT BY 1;

DROP TRIGGER EVALUARE_BEFORE_INSERT;


CREATE OR REPLACE TRIGGER evaluare_before_insert
BEFORE INSERT ON evaluare
FOR EACH ROW
BEGIN
  SELECT evaluare_seq.NEXTVAL
  INTO :new.ID_EVALUARE
  FROM dual;
END;


INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (5, 10, 5, 'Serviciu excelent!', TO_DATE('2022-01-15', 'YYYY-MM-DD'));


INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (1, 15, 4, 'Personal amabil ?i profesionist.', TO_DATE('2022-02-02', 'YYYY-MM-DD'));


INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (2, 8, 78, 'Produs de calitate, dar livrarea a durat prea mult.', TO_DATE('2022-03-10', 'YYYY-MM-DD'));

-- Inserare evaluare 4
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (3, 12, 4, 'Recomand cu încredere!', TO_DATE('2022-04-25', 'YYYY-MM-DD'));

-- Inserare evaluare 5
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (9, 5, 3, 'Pre? competitiv, dar ambalarea poate fi îmbun?t??it?.', TO_DATE('2022-05-18', 'YYYY-MM-DD'));

-- Inserare evaluare 6
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (9, 7, 4, 'Produsul a fost bun, dar livrarea a întârziat un pic.', TO_DATE('2022-06-10', 'YYYY-MM-DD'));

-- Inserare evaluare 7
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (10, 20, 2, 'Produsul a avut defecte, nu sunt mul?umit.', TO_DATE('2022-07-05', 'YYYY-MM-DD'));

-- Inserare evaluare 8
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (11, 14, 5, 'Serviciu excelent, livrare rapid?!', TO_DATE('2022-08-20', 'YYYY-MM-DD'));

-- Inserare evaluare 9
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (12, 11, 3, 'Produsul a fost în regul?, dar pre?ul putea fi mai bun.', TO_DATE('2022-09-15', 'YYYY-MM-DD'));

-- Inserare evaluare 10
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (13, 18, 1, 'Calitate proast?, nu recomand.', TO_DATE('2022-10-01', 'YYYY-MM-DD'));

-- Inserare evaluare 11
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (14, 22, 4, 'Produs de calitate, dar ambalajul a fost deteriorat la livrare.', TO_DATE('2022-11-12', 'YYYY-MM-DD'));

-- Inserare evaluare 12
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (15, 9, 2, 'Livrarea a întârziat ?i nu am fost mul?umit de serviciu.', TO_DATE('2022-12-05', 'YYYY-MM-DD'));

-- Inserare evaluare 13
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (16, 16, 5, 'Serviciu de excep?ie, livrare prompt? ?i produs de calitate!', TO_DATE('2023-01-20', 'YYYY-MM-DD'));

-- Inserare evaluare 14
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (17, 13, 3, 'Produsul a fost OK, dar am avut unele probleme la retur.', TO_DATE('2023-02-15', 'YYYY-MM-DD'));

-- Inserare evaluare 15
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (18, 25, 1, 'Serviciu dezam?gitor, produsul a fost defect.', TO_DATE('2023-03-01', 'YYYY-MM-DD'));

-- Inserare evaluare 16
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (19, 8, 4, 'Serviciu prompt ?i produs de calitate.', TO_DATE('2023-04-10', 'YYYY-MM-DD'));

-- Inserare evaluare 17
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (20, 17, 2, 'Nu am fost mul?umit de produs, a venit cu defecte.', TO_DATE('2023-05-05', 'YYYY-MM-DD'));

-- Inserare evaluare 18
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (21, 10, 5, 'Experien?? excelent?, produsul a dep??it a?tept?rile.', TO_DATE('2023-06-20', 'YYYY-MM-DD'));

-- Inserare evaluare 19
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (22, 14, 3, 'Calitatea produsului a fost medie, dar serviciul a fost bun.', TO_DATE('2023-07-15', 'YYYY-MM-DD'));

-- Inserare evaluare 20
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (23, 23, 1, 'Produsul a fost defect ?i nu am primit niciun r?spuns de la suportul clien?i.', TO_DATE('2023-08-01', 'YYYY-MM-DD'));

-- Inserare evaluare 24
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (24, 19, 4, 'Serviciu bun, livrare la timp.', TO_DATE('2023-09-10', 'YYYY-MM-DD'));

-- Inserare evaluare 25
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (25, 12, 2, 'Produsul nu a fost la fel ca în descriere.', TO_DATE('2023-10-05', 'YYYY-MM-DD'));

-- Inserare evaluare 26
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (26, 15, 5, 'Experien?? excelent?, produsul a fost exact ce m? a?teptam.', TO_DATE('2023-11-20', 'YYYY-MM-DD'));

-- Inserare evaluare 27
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (27, 18, 3, 'Produsul a avut o calitate acceptabil?, dar a durat mult pân? a fost livrat.', TO_DATE('2023-12-15', 'YYYY-MM-DD'));

-- Inserare evaluare 28
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (28, 21, 1, 'Serviciu slab, nu recomand.', TO_DATE('2024-01-01', 'YYYY-MM-DD'));

-- Inserare evaluare 29
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (29, 24, 4, 'Produs excelent, livrare rapid?.', TO_DATE('2024-02-10', 'YYYY-MM-DD'));

-- Inserare evaluare 30
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (30, 11, 2, 'Calitatea produsului nu a fost la fel ca în poze.', TO_DATE('2024-03-05', 'YYYY-MM-DD'));

-- Inserare evaluare 31
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (31, 26, 5, 'Foarte mul?umit, produsul a fost exact ceea ce c?utam.', TO_DATE('2024-04-20', 'YYYY-MM-DD'));

-- Inserare evaluare 32
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (32, 16, 3, 'Produs bun, dar ambalajul a fost deteriorat.', TO_DATE('2024-05-15', 'YYYY-MM-DD'));

-- Inserare evaluare 33
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (33, 29, 1, 'Serviciu dezam?gitor, produsul a fost defect.', TO_DATE('2024-06-01', 'YYYY-MM-DD'));

-- Inserare evaluare 34
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (34, 20, 4, 'Serviciu impecabil, produsul a fost bine ambalat.', TO_DATE('2024-07-10', 'YYYY-MM-DD'));

-- Inserare evaluare 35
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (35, 13, 2, 'Nu am fost impresionat de calitatea produsului.', TO_DATE('2024-08-05', 'YYYY-MM-DD'));

-- Inserare evaluare 36
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (36, 23, 5, 'Produsul a fost excelent, ?i livrarea a fost rapid?.', TO_DATE('2024-09-20', 'YYYY-MM-DD'));

-- Inserare evaluare 37
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (37, 17, 3, 'Calitatea a fost în regul?, dar serviciul ar putea fi îmbun?t??it.', TO_DATE('2024-10-15', 'YYYY-MM-DD'));

-- Inserare evaluare 38
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (38, 30, 1, 'Experien?? dezam?gitoare, nu recomand.', TO_DATE('2024-11-01', 'YYYY-MM-DD'));

-- Inserare evaluare 39
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (39, 25, 4, 'Serviciu bun ?i produs de calitate.', TO_DATE('2024-12-10', 'YYYY-MM-DD'));

-- Inserare evaluare 40
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (40, 19, 2, 'Am întâmpinat probleme cu livrarea, altfel produsul era ok.', TO_DATE('2025-01-05', 'YYYY-MM-DD'));

-- Inserare evaluare 41
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (41, 27, 5, 'Produsul a fost exact ce mi-am dorit, mul?umit de serviciu.', TO_DATE('2025-02-20', 'YYYY-MM-DD'));

-- Inserare evaluare 42
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (42, 22, 3, 'Nu am fost complet satisf?cut de produs, dar a fost livrat la timp.', TO_DATE('2025-03-15', 'YYYY-MM-DD'));

-- Inserare evaluare 43
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (43, 31, 1, 'Experien?? foarte proast?, produsul a fost defect.', TO_DATE('2025-04-01', 'YYYY-MM-DD'));

-- Inserare evaluare 44
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (44, 28, 4, 'Produs de calitate ?i livrare prompt?.', TO_DATE('2025-05-10', 'YYYY-MM-DD'));

-- Inserare evaluare 45
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (45, 21, 2, 'Am întâmpinat dificult??i cu returul, altfel totul a fost ok.', TO_DATE('2025-06-05', 'YYYY-MM-DD'));

-- Inserare evaluare 46
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (46, 29, 5, 'Serviciu excelent ?i produs de calitate superioar?.', TO_DATE('2025-07-20', 'YYYY-MM-DD'));

-- Inserare evaluare 47
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (47, 24, 3, 'Calitatea produsului a fost medie, dar livrarea a fost rapid?.', TO_DATE('2025-08-15', 'YYYY-MM-DD'));

-- Inserare evaluare 48
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (48, 32, 1, 'Nemul?umit de produs, a fost diferit fa?? de descriere.', TO_DATE('2025-09-01', 'YYYY-MM-DD'));

-- Inserare evaluare 49
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (49, 26, 4, 'Produsul a fost de calitate ?i a ajuns la timp.', TO_DATE('2025-10-10', 'YYYY-MM-DD'));

-- Inserare evaluare 50
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (50, 23, 2, 'Nu am fost mul?umit de serviciu, produsul a întârziat.', TO_DATE('2025-11-05', 'YYYY-MM-DD'));

-- Inserare evaluare 51
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (51, 33, 5, 'Experien?? excelent?, produsul a fost exact ce am comandat.', TO_DATE('2025-12-20', 'YYYY-MM-DD'));

-- Inserare evaluare 52
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (52, 27, 3, 'Produsul a fost acceptabil, dar ambalajul a fost deteriorat.', TO_DATE('2026-01-15', 'YYYY-MM-DD'));

-- Inserare evaluare 53
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (53, 31, 1, 'Serviciu foarte slab, produsul a fost defect.', TO_DATE('2026-02-01', 'YYYY-MM-DD'));

-- Inserare evaluare 54
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (54, 30, 4, 'Serviciu bun ?i produs de calitate.', TO_DATE('2026-03-10', 'YYYY-MM-DD'));

-- Inserare evaluare 55
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (55, 25, 2, 'Calitatea a fost sub a?tept?ri, dar livrarea a fost rapid?.', TO_DATE('2026-04-05', 'YYYY-MM-DD'));

-- Inserare evaluare 56
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (56, 15, 5, 'Experien?? excelent?, produs de calitate superioar?.', TO_DATE('2026-05-20', 'YYYY-MM-DD'));

-- Inserare evaluare 57
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (57, 29, 3, 'Produsul a fost OK, dar ambalajul nu a fost bun.', TO_DATE('2026-06-15', 'YYYY-MM-DD'));

-- Inserare evaluare 58
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (58, 23, 1, 'Nemul?umit de produs, a venit cu defecte.', TO_DATE('2026-07-01', 'YYYY-MM-DD'));

-- Inserare evaluare 59
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (59, 31, 4, 'Serviciu prompt ?i produs de calitate.', TO_DATE('2026-08-10', 'YYYY-MM-DD'));

-- Inserare evaluare 60
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (60, 28, 2, 'Calitatea produsului nu a fost la fel ca în descriere.', TO_DATE('2026-09-05', 'YYYY-MM-DD'));

-- Inserare evaluare 61
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (61, 14, 5, 'Produs excelent, serviciu de calitate.', TO_DATE('2026-10-20', 'YYYY-MM-DD'));

-- Inserare evaluare 62
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (62, 32, 3, 'Produsul a fost bun, dar a durat mult pân? la livrare.', TO_DATE('2026-11-15', 'YYYY-MM-DD'));

-- Inserare evaluare 63
INSERT INTO EVALUARI (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII)
VALUES (63, 38, 1, 'Experien?? dezam?gitoare, produsul a fost defect.', TO_DATE('2026-12-01', 'YYYY-MM-DD'));
