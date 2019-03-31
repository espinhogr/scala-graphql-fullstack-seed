-- !Ups

CREATE TABLE products (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE pictures (
    id int NOT NULL AUTO_INCREMENT,
    productId int NOT NULL,
    width int NOT NULL,
    height int NOT NULL,
    url varchar(255),
    PRIMARY KEY (id)
);

INSERT INTO pictures (productId, width, height, url) VALUES
(1, 50, 50, '/assets/images/cheesecake1.jpeg'),
(1, 50, 50, '/assets/images/cheesecake2.jpeg'),
(2, 60, 30, '/assets/images/nutella.jpeg');

INSERT INTO products (name, description) VALUES
('Cheesecake', 'Tasty'),
('Health Potion', '+50 HP');


-- !Downs

DROP TABLE pictures;
DROP TABLE products;
