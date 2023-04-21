CREATE TABLE camps (
                      id BIGINT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      location VARCHAR(255) NOT NULL,
                      price NUMERIC(10,2) NOT NULL,
                      stock INTEGER NOT NULL
);

CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE reservations (
                             id BIGINT PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             camp_id BIGINT NOT NULL,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             checked_out BOOLEAN NOT NULL,
                             number_of_spots INTEGER NOT NULL,
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (camp_id) REFERENCES camps(id)
);

CREATE TABLE ratings (
                         id BIGINT PRIMARY KEY,
                         reservation_id BIGINT NOT NULL,
                         score INTEGER NOT NULL,
                         comment VARCHAR(255) NOT NULL,
                         FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);


