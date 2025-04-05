CREATE TABLE IF NOT EXISTS hardware (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    category VARCHAR(255) NOT NULL,
    hardware_specs JSON
);

CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    home_address TEXT,
    credits DECIMAL(10, 2) NOT NULL DEFAULT 0,
    is_admin BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS shopping_cart (
    id SERIAL PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_email) REFERENCES app_user(email) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hardware_order (
    id SERIAL PRIMARY KEY,
    shopping_cart_id INT NOT NULL,
    hardware_id INT NOT NULL,
    quantity INT NOT NULL,
    is_completed BOOLEAN NOT NULL,
    FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id) ON DELETE CASCADE,
    FOREIGN KEY (hardware_id) REFERENCES hardware(id) ON DELETE CASCADE
);