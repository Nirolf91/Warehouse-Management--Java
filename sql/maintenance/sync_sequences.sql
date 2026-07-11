-- Synchronizes Oracle sequences with the current maximum ID from each table.
-- Run this after manual deletes/imports if newly inserted rows receive IDs that are too high.
-- Use Run Script / F5 in Oracle SQL Developer.

DECLARE
    PROCEDURE reset_sequence(
        p_sequence_name IN VARCHAR2,
        p_table_name    IN VARCHAR2,
        p_id_column     IN VARCHAR2
    ) IS
        v_next_id NUMBER;
    BEGIN
        EXECUTE IMMEDIATE
            'SELECT NVL(MAX(' || p_id_column || '), 0) + 1 FROM ' || p_table_name
            INTO v_next_id;

        BEGIN
            EXECUTE IMMEDIATE 'DROP SEQUENCE ' || p_sequence_name;
        EXCEPTION
            WHEN OTHERS THEN
                IF SQLCODE != -2289 THEN
                    RAISE;
                END IF;
        END;

        EXECUTE IMMEDIATE
            'CREATE SEQUENCE ' || p_sequence_name ||
            ' START WITH ' || v_next_id ||
            ' INCREMENT BY 1 NOCACHE';

        DBMS_OUTPUT.PUT_LINE(p_sequence_name || ' reset to START WITH ' || v_next_id);
    END;
BEGIN
    reset_sequence('utilizatori_seq', 'Utilizatori', 'ID_UTILIZATOR');
    reset_sequence('angajati_seq', 'Angajati', 'ID_ANGAJAT');
    reset_sequence('clienti_seq', 'Clienti', 'ID_CLIENT');
    reset_sequence('furnizori_seq', 'Furnizori', 'ID_FURNIZOR');
    reset_sequence('transportatori_seq', 'Transportatori', 'ID_TRANSPORTATOR');
    reset_sequence('materiale_seq', 'Materiale', 'ID_MATERIAL');
    reset_sequence('comanda_seq', 'Comenzi', 'ID_COMANDA');
    reset_sequence('evaluare_seq', 'Evaluari', 'ID_EVALUARE');
    reset_sequence('logs_seq', 'Logs', 'ID_LOG');
END;
/

COMMIT;
