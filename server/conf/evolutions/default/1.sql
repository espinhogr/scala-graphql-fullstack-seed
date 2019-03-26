-- !Ups

CREATE TABLE product (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE picture (
    id int NOT NULL AUTO_INCREMENT,
    productId int NOT NULL,
    width int NOT NULL,
    height int NOT NULL,
    url varchar(255),
    PRIMARY KEY (id)
);

INSERT INTO picture (productId, width, height, url) VALUES
(1, 50, 50, '/assets/images/cheesecake1.jpeg'),
(1, 50, 50, '/assets/images/cheesecake2.jpeg'),
(2, 60, 30, '/assets/images/nutella.jpeg');

INSERT INTO product (name, description) VALUES
('Cheesecake', 'Tasty'),
('Health Potion', '+50 HP');

CREATE TABLE loginInfo (
    id varchar(255) NOT NULL,
    hasher varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    salt varchar(255),
    PRIMARY KEY (id)
);

--
-- This record adds by default the user with credentials:
-- username: admin
-- password: admin
-- should not be kept in production
--
INSERT INTO loginInfo (id, hasher, password) values
('credentials:admin', 'bcrypt', '$2a$10$QpRYc9HiCvQ2FKhe7SO8vuyy9ZJInVJAj6i3f5l1z9gYzjh9pNMEy');

CREATE TABLE userSession(
    id varchar(256) NOT NULL,
    loginInfoId varchar(255) NOT NULL,
    loginInfoKey varchar(255) NOT NULL,
    lastUsed datetime NOT NULL,
    expiration datetime NOT NULL,
    idleTimeout int,
    creation datetime DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

-- !Downs

DROP TABLE userSession;
DROP TABLE loginInfo;
DROP TABLE picture;
DROP TABLE product;
