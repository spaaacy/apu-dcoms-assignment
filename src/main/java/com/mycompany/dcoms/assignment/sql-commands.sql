/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  aakif
 * Created: Feb 23, 2023
 */

CREATE TABLE TBLPRODUCT(
product_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
product_name VARCHAR(24) NOT NULL,
price DOUBLE NOT NULL,
total_supply INT NOT NULL
);

CREATE TABLE TBLUSER(
username VARCHAR(16) NOT NULL PRIMARY KEY,
password VARCHAR(24) NOT NULL,
first_name VARCHAR(24) NOT NULL,
last_name VARCHAR(24) NOT NULL,
ic_number VARCHAR(24) NOT NULL UNIQUE
);

CREATE TABLE TBLORDER(
order_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
quantity INT NOT NULL,
username VARCHAR(16) NOT NULL,
product_id INT NOT NULL,
FOREIGN KEY (username) REFERENCES TBLUSER(username),
FOREIGN KEY (product_id) REFERENCES TBLPRODUCT(product_id)
);

INSERT INTO TBLPRODUCT (product_name, price, total_supply) VALUES 
('iPhone 14', 799.99, 65),
('Baby Shampoo', 10.00, 97),
('Color Pencils', 7.99, 33),
('Kitchen Knife', 12.35, 27),
('Baby Formula', 10.50, 75),
('Samsung Galaxy S23', 999.99, 44),
('Water Bottle 1L', 15.50, 16);
