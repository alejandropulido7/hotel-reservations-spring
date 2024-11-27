CREATE TABLE IF NOT EXISTS rooms (
    id_room INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_location VARCHAR(15) NOT NULL,
    location VARCHAR(15) NOT NULL,
    night_cost FLOAT NOT NULL,
    floor INT NOT NULL,
    type_room VARCHAR(15) NOT NULL,
    amount_guest INT NOT NULL,
    available BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS users (
    id_user VARCHAR(12) NOT NULL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS reservations(
   id INT GENERATED ALWAYS AS IDENTITY,
   id_user VARCHAR(15),
   name_user VARCHAR(30),
   email_user VARCHAR(50),
   room_id INT NOT NULL,
   check_in TIMESTAMP NOT NULL,
   check_out TIMESTAMP NOT NULL,
   CONSTRAINT fk_room_id
         FOREIGN KEY(room_id)
           REFERENCES rooms(id_room),
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id_user, username, password, name, email, role)
VALUES ('C1234', 'pepito', '1234', 'Pepito', 'pepito@mail.com', 'ADMIN') ON CONFLICT DO NOTHING;

INSERT INTO users (id_user, username, password, name, email, role)
VALUES ('C4321', 'fulanita', '1234', 'Fulanita', 'fulanita@mail.com', 'USER') ON CONFLICT DO NOTHING;


INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('1-BALCON', 'BALCON', 300000, 1, 'SUITE', 3, true) ON CONFLICT DO NOTHING;

INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('1-TERRAZA', 'TERRAZA', 400000, 1, 'PRESIDENTIAL', 5, true) ON CONFLICT DO NOTHING;

INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('1-CENTRO', 'CENTRO', 100000, 1, 'SINGLE', 1, true) ON CONFLICT DO NOTHING;

INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('2-BALCON', 'BALCON', 200000, 2, 'DOUBLE', 2, false) ON CONFLICT DO NOTHING;

INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('3-BALCON', 'BALCON', 300000, 3, 'SUITE', 3, true) ON CONFLICT DO NOTHING;

INSERT INTO rooms (id_location, location, night_cost, floor, type_room, amount_guest, available)
VALUES ('3-CENTRO', 'CENTRO', 200000, 3, 'DOUBLE', 2, true) ON CONFLICT DO NOTHING;