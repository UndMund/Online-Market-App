INSERT INTO position(position_name) VALUES ('Admin');
INSERT INTO position(position_name) VALUES ('User');

INSERT INTO users(email, money, password, phone_number, username, position_id) VALUES ('helena@mail.ru',50,'$2a$12$GAADOouPtzvU0x6ZKwYQfeZxJdcN8PGrvhvB5KN3rMMFxsrghCySO','+375299575434','Helena',2);
INSERT INTO users(email, money, password, phone_number, username, position_id) VALUES ('zavadskiii@mail.ru',10,'$2a$12$gdPUahFEC7In4SwEv.iYneouX/kKvu8NMHfD25tqDcntDmUTfvMOC','+375296144667','Sergey',2);
INSERT INTO users(email, money, password, phone_number, username, position_id) VALUES ('zavadskiy.nazar@mail.ru',1030,'$2a$12$giEQlHzWsp1SVEzZIuz0POmkNTmk9//bZ7SQHQaOw9WbC9CnsMMLq','+375336328518','Nazar',1);
INSERT INTO users(email, money, password, phone_number, username, position_id) VALUES ('lina@mail.ru',3000,'$2a$10$LjcS4AReU4k0Y9Sup9TcEuwMkXrQTc9yYFJHgwrLRZqgZB7UhVND6','+375297328657','Lina',2);

INSERT INTO category(category_name) VALUES ('Phones and accessories');
INSERT INTO category(category_name) VALUES ('Laptops and accessories');
INSERT INTO category(category_name) VALUES ('Motorbikes');
INSERT INTO category(category_name) VALUES ('Musical instruments');

INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Engine after service. 8.4 motohours','fb.jpg',3000.00,'Honda CRF 250R','ON_SALE',3,3);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Wonderful instrument, new strings','crafter-de-7-n.jpg',500.00,'Crafter DE 7/N','SALES',4,3);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('New laptop. Ideal for work and design','-1_600.jpg',1500.00,'Asus Vivobook S 14X','ON_SALE',2,1);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Labtop is about 5 years old. Condition is very good. works well','lenovoideapad5-15ryzen55625u16gb512win1182sg004npb_2.jpg',700.00,'Lenovo IdeaPad 5','REVIEW',2,1);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('In use about 2 years. Batery service recomended' ,'9fedbdd1-a414-4061-8f5b-3504f7a29b49.jpg',400.00,'Xiaomi Poco f3','ON_SALE',1,1);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Wonderful instrument with long history','Piano-accordion.webp',500.00,'Accordeon','ON_SALE',4,2);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Problems with sound, but works fine','e9bf4f35243ccd02555cf107b74a457c.jpeg',300.00,'Xiaomi Mi 11 Lite','ON_SALE',1,4);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Wonderful sound! About one year old. I want to sale it because now i have new one','wave100tws-blk-naushniki-jbl-2_600.jpg',50.00,'JBL Wave 100TWS','ON_SALE',1,2);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Motorbike is perfect for adventures in off-road. Engine overhaul about 100 km ago','honda-xr650r-2003.jpg',3050.00,'Honda XR 650R','SALES',3,3);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Phone is new, i want diffrent color, that is why i sale it)','iPhone_13_Mini_(9)_1633111760137_1633111770414.webp',1500.00,'Iphone 13 mini','ON_SALE',1,4);
INSERT INTO product(description, image, price, product_name, status, category_id, user_id) VALUES ('Priceless phone. hardness like platinum. there is a snake game','b9595b48d21fc0761bc1a37840f39fd6.jpg',99999.00,'Nokia 3310','ON_SALE',1,2);

INSERT INTO orders(order_date, customer_id, product_id) VALUES ('2023-07-08', 1, 2);
INSERT INTO orders(order_date, customer_id, product_id) VALUES ('2023-07-09', 4, 9);
