CREATE DATABASE payments;

\c payments

CREATE OR REPLACE PROCEDURE payment_data_reset()
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM payments;
	ALTER SEQUENCE pay_id_sequence RESTART WITH 1;
END; $$;