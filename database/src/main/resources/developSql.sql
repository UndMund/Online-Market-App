CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE NOT NULL,
    email         VARCHAR(128) UNIQUE,
    phone_number  VARCHAR(16) UNIQUE,
    password      VARCHAR(32)        NOT NULL,
    money_balance NUMERIC DEFAULT 0
);

CREATE TABLE category
(
    id            SERIAL PRIMARY KEY,
    category_name VARCHAR(32)
);

CREATE TABLE product
(
    id           BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(64)                                 NOT NULL,
    price        NUMERIC                                     NOT NULL,
    description  VARCHAR(800)                                NOT NULL,
    status       VARCHAR(16)                                 NOT NULL,
    category_id  INT REFERENCES category (id)                NOT NULL,
    user_id      INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    UNIQUE (product_name, user_id)
);


CREATE TABLE orders
(
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT REFERENCES product (id)              NOT NULL,
    customer_id INT REFERENCES users (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE position
(
    id            SERIAL PRIMARY KEY,
    position_name VARCHAR(32) UNIQUE NOT NULL
);

CREATE TABLE user_position
(
    user_id     BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    position_id INT REFERENCES position (id)                   NOT NULL,
    UNIQUE (user_id, position_id)
);

INSERT INTO users(username, email, phone_number, password)
VALUES ('Nazar', 'zavadskiy.nazar@mail.ru', '+375336328517', '12345678'),
       ('Oleg', 'oleg2001@gmail.com', '+375296788943', '87654321'),
       ('Inna', 'innaOreh@mail.ru', '+375444562368', '12344321'),
       ('Petya', ' petyaEt@mail.ru', '+375293452334', '56788765');

INSERT INTO category(category_name)
VALUES ('Телефоны и аксессуары'),
       ('Ноутбуки и аксессуары'),
       ('Мототехника'),
       ('Музыкальные инструменты');

INSERT INTO product(product_name, price, description, status, category_id, user_id)
VALUES ('Xiaomi Mi 11 Lite', 300, 'Работает прекрасно, использовался 2 года', 'ON_SALE', 1, 1),
       ('MacBook Pro 15 2018', 1500, 'Все рабочее, внешние следы износа', 'SALES', 2, 1),
       ('Honda CRF 650L', 3500, 'Требуеется капитальный ремонт двигателя', 'ON_SALE', 3, 2),
       ('Гитара Crafter DE/7N', 500, 'Новый инсрумент', 'SALES', 4, 3),
       ('Чехол для телефона Xiaomi Mi 8', 2, 'Черный цвет', 'ON_SALE', 1, 4);

INSERT INTO orders(product_id, customer_id)
VALUES (2, 4),
       (4, 2);

INSERT INTO position(position_name)
VALUES ('ADMIN'),
       ('USER');

INSERT INTO user_position(user_id, position_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (4, 2);

DROP TABLE user_position;
DROP TABLE position;
DROP TABLE orders;
DROP TABLE product;
DROP TABLE users;
DROP TABLE category;

