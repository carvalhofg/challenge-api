INSERT INTO CUSTOMER(name, email, phone_number, address) VALUES('Customer1', 'customer1@email.com', 48996594849, 'Address number one');
INSERT INTO CUSTOMER(name, email, phone_number, address) VALUES('Customer2', 'customer2@email.com', 48995483588, 'Address number two');

INSERT INTO RESPONSIBLE(name, speciality) VALUES('Responsible1', 'Eletronics');
INSERT INTO RESPONSIBLE(name, speciality) VALUES('Responsible2', 'Home appliances');

INSERT INTO SERVICE_ORDER(created_date, customer_id, responsible_id, product_Model, product_type, order_status, issue) VALUES('2019-05-05 20:00:00', 1, 1, 'Model1', 'Type1', 'REGISTERED', 'IssueDescription');
INSERT INTO SERVICE_ORDER(created_date, customer_id, responsible_id, product_Model, product_type, order_status, issue) VALUES('2019-05-05 21:00:00', 2, 2, 'Model2', 'Type2', 'REGISTERED', 'IssueDescription');