-- Repairs the supplier insert trigger in the current Oracle schema.
-- Run with F5 / Run Script in Oracle SQL Developer.

BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER FURNIZORI_BEFORE_INSERT';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -4080 THEN
            RAISE;
        END IF;
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_furnizori_id';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -4080 THEN
            RAISE;
        END IF;
END;
/

CREATE OR REPLACE TRIGGER trg_furnizori_id
BEFORE INSERT ON Furnizori
FOR EACH ROW
BEGIN
    IF :NEW.ID_FURNIZOR IS NULL THEN
        :NEW.ID_FURNIZOR := furnizori_seq.NEXTVAL;
    END IF;
END;
/

ALTER TRIGGER trg_furnizori_id ENABLE;

SHOW ERRORS TRIGGER trg_furnizori_id;

COMMIT;
