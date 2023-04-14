CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE NOT NULL,
    position      VARCHAR(16) NOT NULL,
    email         VARCHAR(128) UNIQUE,
    phone_number  VARCHAR(16) UNIQUE,
    password      VARCHAR(32)        NOT NULL,
    money_balance NUMERIC DEFAULT 0
);

CREATE TABLE product
(
    id           BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(64)               NOT NULL,
    price        NUMERIC                   NOT NULL,
    description  VARCHAR(800)              NOT NULL,
    status       VARCHAR(16)               NOT NULL,
    category     VARCHAR(32)               NOT NULL,
    user_id      INT REFERENCES users (id) ON DELETE CASCADE NOT NULL ,
    UNIQUE (product_name, user_id)
);

CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY ,
    product_id BIGINT REFERENCES product(id) NOT NULL ,
    customer_id INT REFERENCES users(id) ON DELETE CASCADE NOT NULL
);

INSERT INTO users(username, position, email, phone_number, password)
VALUES ('Nazar', 'ADMIN', 'zavadskiy.nazar@mail.ru', '+375336328517', '12345678'),
       ('Oleg', 'USER', 'oleg2001@gmail.com', '+375296788943', '87654321'),
       ('Inna',  'USER','innaOreh@mail.ru', '+375444562368', '12344321'),
       ('Petya', 'USER', 'petyaEt@mail.ru', '+375293452334', '56788765');

INSERT INTO product(product_name, price, description, status, category, user_id)
VALUES ('Xiaomi Mi 11 Lite', 300, 'Работает прекрасно, использовался 2 года', 'ON_SALE','Телефоны и аксессуары', 1),
       ('MacBook Pro 15 2018', 1500, 'Все рабочее, внешние следы износа', 'SALES','Ноутбуки и аксессуары' , 1),
       ('Honda CRF 650L', 3500, 'Требуеется капитальный ремонт двигателя', 'ON_SALE','Мототехника' ,2),
       ('Гитара Crafter DE/7N', 500, 'Новый инсрумент', 'SALES','Музыкальные инструменты' , 3),
       ('Чехол для телефона Xiaomi Mi 8', 2, 'Цвета: графит, крассный, белый, черный', 'ON_SALE', 'Телефоны и аксессуары', 4);

INSERT INTO orders(product_id, customer_id)
VALUES (2, 4),
       (4, 2);