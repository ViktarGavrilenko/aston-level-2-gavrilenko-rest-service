CREATE TABLE IF NOT EXISTS buyers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS orders (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     number INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS items(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     price INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS buyer_order(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     id_buyer INTEGER NOT NULL,
     id_order INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     id_order INTEGER NOT NULL,
     id_item INTEGER NOT NULL
);

INSERT INTO buyers(name)
    VALUES
    ('Вася'),
    ('Дима'),
    ('Иван'),
    ('Саша'),
    ('Олег');

INSERT INTO orders(number)
    VALUES
    (1),
    (2),
    (3),
    (4),
    (5);

INSERT INTO items(name, price)
    VALUES
    ('Ручка', 2),
    ('Стол', 10),
    ('Чайник', 4),
    ('Телефон', 12),
    ('Бумага', 3);

INSERT INTO buyer_order(id_buyer, id_order)
    VALUES
    (1, 1),
    (2, 3),
    (1, 2),
    (3, 4),
    (5, 5);

INSERT INTO order_items(id_order, id_item)
    VALUES
    (1, 1),
    (1, 3),
    (2, 1),
    (3, 2),
    (3, 4),
    (3, 1),
    (4, 5),
    (4, 2),
    (5, 1),
    (5, 5);
