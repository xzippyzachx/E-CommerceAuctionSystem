CREATE DATABASE items;

\c items

CREATE OR REPLACE PROCEDURE item_data_reset()
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM items;
	ALTER SEQUENCE itm_id_sequence RESTART WITH 1;

	INSERT INTO items (itm_id, itm_name, itm_description , itm_shipping_cost,itm_expedited_cost)
	VALUES (nextval('itm_id_sequence'), 'Bike', 'Red bike in perfectly working condition. Has minor scratches', 10, 30);
	
	INSERT INTO items (itm_id, itm_name, itm_description , itm_shipping_cost,itm_expedited_cost)
	VALUES (nextval('itm_id_sequence'), 'Tesla Model 3', '4 door fully electric sedan. 0-100 km/h in 3.3 seconds.', 100, 30);
	
	INSERT INTO items (itm_id, itm_name, itm_description , itm_shipping_cost,itm_expedited_cost)
	VALUES (nextval('itm_id_sequence'), 'iPhone 14', 'Pro-level camera. Whoa-level pics. iOS 16. 5G. Accessibility features. All-day battery life. 6 colors.', 8, 30);
	
	INSERT INTO items (itm_id, itm_name, itm_description , itm_shipping_cost,itm_expedited_cost)
	VALUES (nextval('itm_id_sequence'), 'Asus Laptop', 'Specs: intel i7-8700k GTX2060 Ti 16GB RAM 1T SSD', 5, 30);
	
	INSERT INTO items (itm_id, itm_name, itm_description , itm_shipping_cost,itm_expedited_cost)
	VALUES (nextval('itm_id_sequence'), 'Dressor', 'White 4 drawer dressor', 12, 30);
	
END; $$;
