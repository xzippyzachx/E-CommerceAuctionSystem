CREATE DATABASE auctions;

\c auctions

CREATE OR REPLACE PROCEDURE auction_data_reset()
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM bids;
	ALTER SEQUENCE bid_id_sequence RESTART WITH 1;

	DELETE FROM forward_auctions;
	DELETE FROM dutch_auctions;
	DELETE FROM auctions;
	ALTER SEQUENCE auc_id_sequence RESTART WITH 1;

	INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type, auc_state)
	VALUES (nextval('auc_id_sequence'), 100, 1, 100, 'forward', 'running');
	INSERT INTO forward_auctions (fwd_id, fwd_end_time)
	VALUES (currval('auc_id_sequence'), (CURRENT_TIMESTAMP AT time zone 'UTC') + (5 * interval '1 minute'));

	INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type, auc_state)
	VALUES (nextval('auc_id_sequence'), 300, 2, 300, 'forward', 'running');
	INSERT INTO forward_auctions (fwd_id, fwd_end_time)
	VALUES (currval('auc_id_sequence'), (CURRENT_TIMESTAMP AT time zone 'UTC') + (5 * interval '1 minute'));

	INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type, auc_state)
	VALUES (nextval('auc_id_sequence'), 1000, 3, 1000, 'dutch', 'running');
	INSERT INTO dutch_auctions (dch_id, dch_decrease_amount, dch_decrease_interval, dch_min_price, dch_end_delay)
	VALUES (currval('auc_id_sequence'), 50, 10, 100, 10);
	INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type, auc_state)
	
	VALUES (nextval('auc_id_sequence'), 1500, 4, 1000, 'dutch', 'running');
	INSERT INTO dutch_auctions (dch_id, dch_decrease_amount, dch_decrease_interval, dch_min_price, dch_end_delay)
	VALUES (currval('auc_id_sequence'), 100, 10, 100, 10);
	
	INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type, auc_state)
	VALUES (nextval('auc_id_sequence'), 150, 5, 150, 'forward', 'running');
	INSERT INTO forward_auctions (fwd_id, fwd_end_time)
	VALUES (currval('auc_id_sequence'), (CURRENT_TIMESTAMP AT time zone 'UTC') + (10 * interval '1 minute'));
END; $$;
