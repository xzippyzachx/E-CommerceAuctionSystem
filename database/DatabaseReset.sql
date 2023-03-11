DELETE FROM bids;
ALTER SEQUENCE bid_id_sequence RESTART WITH 1;

DELETE FROM auctions;
ALTER SEQUENCE auc_id_sequence RESTART WITH 1;

INSERT INTO auctions (auc_id, auc_current_price, auc_end_time, auc_itm_id, auc_start_price, auc_type)
VALUES (nextval('auc_id_sequence'), 100, CURRENT_TIMESTAMP + (10 * interval '1 minute'), null, 100, 'forward');

INSERT INTO auctions (auc_id, auc_current_price, auc_end_time, auc_itm_id, auc_start_price, auc_type)
VALUES (nextval('auc_id_sequence'), 300, CURRENT_TIMESTAMP + (10 * interval '1 minute'), null, 300, 'forward');