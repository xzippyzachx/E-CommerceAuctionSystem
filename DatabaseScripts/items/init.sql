CREATE DATABASE itemBeans;

\c itemBeans

CREATE OR REPLACE PROCEDURE item_data_reset()
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM itemBeans;
	ALTER SEQUENCE itm_id_sequence RESTART WITH 1;

	INSERT INTO itemBeans (itm_id, itm_name, itm_description , itm_shipping_cost)
	VALUES (nextval('itm_id_sequence'), 'Bike', 'Two wheel vehicle', 3);
	
	INSERT INTO itemBeans (itm_id, itm_name, itm_description , itm_shipping_cost)
	VALUES (nextval('itm_id_sequence'), 'Car', 'vehicle', 5);
	
	INSERT INTO itemBeans (itm_id, itm_name, itm_description , itm_shipping_cost)
	VALUES (nextval('itm_id_sequence'), 'Phone', 'Electornic Device', 4);
	
	INSERT INTO itemBeans (itm_id, itm_name, itm_description , itm_shipping_cost)
	VALUES (nextval('itm_id_sequence'), 'Laptop', 'i7-67000', 6);
	
END; $$;
