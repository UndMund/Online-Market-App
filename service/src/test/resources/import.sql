INSERT INTO position(position_name) VALUES ('ADMIN');
INSERT INTO position(position_name) VALUES ('USER');

INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Nazar', 'zavadskiy.nazar@mail.ru', '+375336328517', 1, 'Nazar17', 1000);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Oleg', 'oleg2001@gmail.com', '+375296788943', 2,'Oleg20', 100);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Inna', 'innaOreh@mail.ru', '+375444562368', 2, 'Inna20', 200);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Petya', ' petyaEt@mail.ru', '+375293452334', 2,'Petya20', 400);

INSERT INTO category(category_name) VALUES ('Телефоны и аксессуары');
INSERT INTO category(category_name) VALUES ('Ноутбуки и аксессуары');
INSERT INTO category(category_name) VALUES ('Мототехника');
INSERT INTO category(category_name) VALUES ('Музыкальные инструменты');

/*INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Xiaomi Mi 11 Lite', 300, 'Работает прекрасно, использовался 2 года', 'ON_SALE', 1, 1);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('MacBook Pro 15 2018', 1500, 'Все рабочее, внешние следы износа', 'SALES', 2, 1);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Honda CRF 650L', 3500, 'Требуеется капитальный ремонт двигателя', 'REVIEW', 3, 2);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Гитара Crafter DE/7N', 500, 'Новый инсрумент', 'SALES', 4, 3);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Чехол для телефона Xiaomi Mi 8', 2, 'Черный цвет', 'ON_SALE', 1, 4);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Аккордеон', 50, 'Прекрасный инструмент с долгой историей', 'ON_SALE', 4, 2);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('IPhone 12', 1000, 'Требуется замена батареи', 'ON_SALE', 1, 3);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('JBL TWS100', 40, 'Царапины, по работе вопросов нет', 'ON_SALE', 1, 1);
INSERT INTO product(product_name, price, description, status, category_id, user_id) VALUES ('Ноутбук ASUS Extensa 15', 400, 'Ноутбук новый', 'REVIEW', 2, 4);
*/
/*INSERT INTO orders(product_id, customer_id) VALUES (2, 4);
INSERT INTO orders(product_id, customer_id) VALUES (4, 2);*/