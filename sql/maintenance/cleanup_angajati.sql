-- Cleans the current Angajati table without recreating the full schema.
-- Run this in Oracle SQL Developer using the same schema/user as the application.
-- IMPORTANT: use Run Script / F5, not only Run Statement / Ctrl+Enter.

PROMPT Cleaning invalid employee rows and normalizing IDs 1-25...

MERGE INTO Angajati a
USING (
    SELECT 1 id, 'Florin' nume, 'Casier' functie, 'str Trandafirilor' contact FROM dual UNION ALL
    SELECT 2, 'Marian Ionescu', 'Supraveghetor de stoc', 'Galati, Strada Lalelelor, nr. 23 | 0723678901' FROM dual UNION ALL
    SELECT 3, 'Ion Dumitrescu', 'Aprovizionare', 'Galati, Strada Margaretelelor, nr. 12 | 0724789012' FROM dual UNION ALL
    SELECT 4, 'Cristina Stanescu', 'Inspector calitate', 'Galati, Strada Florilor, nr. 7 | 0725890123' FROM dual UNION ALL
    SELECT 5, 'George Munteanu', 'Manipulant marfa', 'Galati, Strada Panselutelor, nr. 21 | 0726901234' FROM dual UNION ALL
    SELECT 6, 'Elena Nicolescu', 'Responsabil depozit', 'Galati, Strada Viorelelor, nr. 18 | 0727012345' FROM dual UNION ALL
    SELECT 7, 'Victor Radulescu', 'Manager de depozit', 'Galati, Strada Orhideelor, nr. 33 | 0728123456' FROM dual UNION ALL
    SELECT 8, 'Iulia Munteanu', 'Logistician', 'Galati, Strada Irisilor, nr. 42 | 0729234567' FROM dual UNION ALL
    SELECT 9, 'Mihai Georgescu', 'Supraveghetor de stoc', 'Galati, Strada Zambilelor, nr. 6 | 0730345678' FROM dual UNION ALL
    SELECT 10, 'Andrei Popescu', 'Aprovizionare', 'Galati, Strada Violetei, nr. 11 | 0731456789' FROM dual UNION ALL
    SELECT 11, 'Maria Stanescu', 'Inspector calitate', 'Galati, Strada Freziilor, nr. 25 | 0732567890' FROM dual UNION ALL
    SELECT 12, 'George Ionescu', 'Manipulant marfa', 'Galati, Strada Ghioceilor, nr. 19 | 0733678901' FROM dual UNION ALL
    SELECT 13, 'Elena Mihai', 'Responsabil mijloace fixe', 'Galati, Strada Bujorilor, nr. 13 | 0734789012' FROM dual UNION ALL
    SELECT 14, 'Victor Dumitrescu', 'Manager de depozit', 'Galati, Strada Papadiei, nr. 36 | 0735890123' FROM dual UNION ALL
    SELECT 15, 'Iulia Radulescu', 'Logistician', 'Galati, Strada Narciselor, nr. 29 | 0736901234' FROM dual UNION ALL
    SELECT 16, 'Andrei Nicolescu', 'Supraveghetor de stoc', 'Galati, Strada Caprifoiului, nr. 8 | 0738012345' FROM dual UNION ALL
    SELECT 17, 'Maria Dumitrescu', 'Aprovizionare', 'Galati, Strada Macilor, nr. 14 | 0739123456' FROM dual UNION ALL
    SELECT 18, 'George Popescu', 'Inspector calitate', 'Galati, Strada Crinilor, nr. 20 | 0740234567' FROM dual UNION ALL
    SELECT 19, 'Elena Stanescu', 'Manipulant marfa', 'Galati, Strada Hortensiilor, nr. 16 | 0741345678' FROM dual UNION ALL
    SELECT 20, 'Victor Ionescu', 'Responsabil depozit', 'Galati, Strada Trifoiului, nr. 27 | 0742456789' FROM dual UNION ALL
    SELECT 21, 'Iulia Munteanu', 'Manager de depozit', 'Galati, Strada Iedersilor, nr. 32 | 0743567890' FROM dual UNION ALL
    SELECT 22, 'Mihai Radulescu', 'Logistician', 'Galati, Strada Alunelor, nr. 10 | 0744678901' FROM dual UNION ALL
    SELECT 23, 'Ana Dumitrescu', 'Supraveghetor de stoc', 'Galati, Strada Castanelor, nr. 22 | 0745789012' FROM dual UNION ALL
    SELECT 24, 'Andrei Georgescu', 'Logistician', 'Galati, Strada Trandafirilor, nr. 15 | 0722567890' FROM dual UNION ALL
    SELECT 25, 'Mihai Pavel', 'Manipulant marfa', 'Galati, Strada Furnalistilor, nr. 60 | 0786456125' FROM dual
) s
ON (a.ID_ANGAJAT = s.id)
WHEN MATCHED THEN
    UPDATE SET a.NUME = s.nume,
               a.FUNCTIE = s.functie,
               a.DATE_DE_CONTACT = s.contact
WHEN NOT MATCHED THEN
    INSERT (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT)
    VALUES (s.id, s.nume, s.functie, s.contact);

UPDATE Comenzi
SET ID_ANGAJAT = 1
WHERE ID_ANGAJAT > 25;

DELETE FROM Angajati
WHERE ID_ANGAJAT > 25;

DELETE FROM Angajati
WHERE UPPER(TRIM(NUME)) IN ('ID_ANGAJAT', 'NUME')
   OR UPPER(TRIM(FUNCTIE)) IN ('NUME', 'FUNCTIE')
   OR NUME LIKE '%<script%'
   OR NUME LIKE '%'' OR%';

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE angajati_seq';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -2289 THEN
            RAISE;
        END IF;
END;
/

CREATE SEQUENCE angajati_seq START WITH 26 INCREMENT BY 1;

COMMIT;

PROMPT Remaining employees after cleanup:
SELECT ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT
FROM Angajati
ORDER BY ID_ANGAJAT;

PROMPT Invalid rows that should be zero:
SELECT COUNT(*) AS INVALID_EMPLOYEE_ROWS
FROM Angajati
WHERE ID_ANGAJAT > 25
   OR UPPER(TRIM(NUME)) IN ('ID_ANGAJAT', 'NUME')
   OR UPPER(TRIM(FUNCTIE)) IN ('NUME', 'FUNCTIE')
   OR NUME LIKE '%<script%'
   OR NUME LIKE '%'' OR%';
