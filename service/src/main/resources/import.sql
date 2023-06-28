INSERT INTO position(position_name) VALUES ('Admin');
INSERT INTO position(position_name) VALUES ('User');

INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Nazar', 'zavadskiy.nazar@mail.ru', '+375336328517', 1, 'Nazar17', 1000);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Oleg', 'oleg2001@gmail.com', '+375296788943', 2,'Oleg20', 100);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Inna', 'innaOreh@mail.ru', '+375444562368', 2, 'Inna20', 200);
INSERT INTO users(username, email, phone_number, position_id, password, money) VALUES ('Petya', ' petyaEt@mail.ru', '+375293452334', 2,'Petya20', 400);

INSERT INTO category(category_name) VALUES ('Телефоны и аксессуары');
INSERT INTO category(category_name) VALUES ('Ноутбуки и аксессуары');
INSERT INTO category(category_name) VALUES ('Мототехника');
INSERT INTO category(category_name) VALUES ('Музыкальные инструменты');

