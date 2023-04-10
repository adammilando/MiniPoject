CREATE TABLE camps (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      location VARCHAR(255) NOT NULL,
                      price NUMERIC(10,2) NOT NULL,
                      stock INTEGER NOT NULL
);

CREATE TABLE ratings (
                        id SERIAL PRIMARY KEY,
                        userId BIGINT NOT NULL,
                        campId BIGINT NOT NULL,
                        score INTEGER NOT NULL,
                        comment VARCHAR(255) NOT NULL,
                        FOREIGN KEY (userId) REFERENCES users(id),
                        FOREIGN KEY (campId) REFERENCES camps(id)
);

CREATE TABLE reservations (
                             id SERIAL PRIMARY KEY,
                             userId BIGINT NOT NULL,
                             campId BIGINT NOT NULL,
                             startDate DATE NOT NULL,
                             endDate DATE NOT NULL,
                             checkedOut BOOLEAN NOT NULL,
                             numberOfSpots INTEGER NOT NULL,
                             campStock INTEGER NOT NULL,
                             FOREIGN KEY (userId) REFERENCES users(id),
                             FOREIGN KEY (campId) REFERENCES camps(id)
);

CREATE TABLE users (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL
);
