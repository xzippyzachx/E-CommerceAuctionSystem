DELETE FROM bids;
ALTER SEQUENCE bid_id_sequence RESTART WITH 1;

DELETE FROM forward_auctions;
DELETE FROM auctions;
ALTER SEQUENCE auc_id_sequence RESTART WITH 1;

INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type)
VALUES (nextval('auc_id_sequence'), 100, null, 100, 'forward');
INSERT INTO forward_auctions (fwd_id, fwd_end_time)
VALUES (currval('auc_id_sequence'), CURRENT_TIMESTAMP + (10 * interval '1 minute'));

INSERT INTO auctions (auc_id, auc_current_price, auc_itm_id, auc_start_price, auc_type)
VALUES (nextval('auc_id_sequence'), 300, null, 300, 'forward');
INSERT INTO forward_auctions (fwd_id, fwd_end_time)
VALUES (currval('auc_id_sequence'), CURRENT_TIMESTAMP + (10 * interval '1 minute'));