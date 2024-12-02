CREATE DATABASE IF NOT EXISTS flight_reservation;
USE flight_reservation;


CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE flights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50), -- FlightStatusEnums
    flight_date DATETIME,
    create_date DATETIME,
    update_date DATETIME,
    deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE seats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_id BIGINT,
    seat_number INT NOT NULL,
    price DOUBLE NOT NULL,
    status VARCHAR(50), -- SeatStatusEnums
    create_date DATETIME,
    update_date DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (flight_id) REFERENCES flights(id)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id BIGINT,
    create_date DATETIME,
    update_date DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_id BIGINT,
    seat_id BIGINT,
    user_id BIGINT,
    status VARCHAR(50) NOT NULL, -- ReservationStatusEnums
    create_date DATETIME,
    update_date DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (flight_id) REFERENCES flights(id),
    FOREIGN KEY (seat_id) REFERENCES seats(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reservation_id BIGINT,
    create_date DATETIME,
    update_date DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);

INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

INSERT INTO flights (name, description, status, flight_date, create_date)
VALUES
('Flight A', 'istanbul-frankfurt', 'ONBOARDING', '2024-12-15 10:00:00', NOW()),
('Flight B', 'ankra-izmir', 'AVAILABLE', '2024-12-20 15:05:00', NOW());
('Flight C', 'Description B', 'AVAILABLE', '2024-12-20 22:15:00', NOW());
('Flight D', 'Description B', 'AVAILABLE', '2024-12-20 08:30:00', NOW());
('Flight E', 'Description B', 'COMPLETED', '2024-11-12 18:00:00', NOW());

INSERT INTO users (name, surname, email, password, role_id, create_date)
VALUES
('John', 'Doe', 'john.doe@example.com', 'password123', 1, NOW()),
('Jane', 'Smith', 'jane.smith@example.com', 'password456', 2, NOW());

-- Seats tablosuna örnek veri ekleme
INSERT INTO seats (flight_id, seat_number, price, status, create_date)
VALUES
(1, 1, 100.0, 'AVAILABLE', NOW()),
(1, 2, 100.0, 'SOLD', NOW()),
(1, 3, 100.0, 'AVAILABLE', NOW()),
(2, 1, 150.0, 'RESERVED', NOW()),
(2, 2, 150.0, 'SOLD', NOW());

-- Reservations tablosuna örnek veri ekleme
INSERT INTO reservations (flight_id, seat_id, user_id, status, create_date)
VALUES
(1, 2, 1, 'WAITING', NOW()),
(2, 2, 2, 'WAITING', NOW());

INSERT INTO orders (reservation_id, create_date)
VALUES
(1, NOW()),
(2, NOW());
