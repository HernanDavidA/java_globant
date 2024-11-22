CREATE TABLE rentals (
    id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    machine_id INT NOT NULL,
    start_date DATE NOT NULL, 
    end_date DATE NOT NULL, 
	estado VARCHAR(50) NOT NULL CHECK (estado IN ('activo', 'desactivado'))
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE,
    CONSTRAINT fk_machine FOREIGN KEY (machine_id) REFERENCES machines(id) ON DELETE CASCADE
);

CREATE TABLE machines (
    id SERIAL PRIMARY KEY, 
    model VARCHAR(255) NOT NULL, 
    serial_number VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL CHECK (status IN ('Available', 'Rented'))
    
);

CREATE TABLE clients (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL, 
    email VARCHAR(255) NOT NULL UNIQUE, 
    phone VARCHAR(20), 
    address TEXT 
);