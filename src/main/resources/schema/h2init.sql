-- Create users table
CREATE TABLE IF NOT EXISTS users (
user_id SERIAL PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
email VARCHAR(100) NOT NULL UNIQUE,
password VARCHAR(100) NOT NULL
);

-- Create wallets table
CREATE TABLE IF NOT EXISTS wallets (
id SERIAL PRIMARY KEY,
user_id INTEGER NOT NULL,
currency VARCHAR(10) NOT NULL,
balance DECIMAL(10, 2) NOT NULL,
CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create transactions table
CREATE TABLE IF NOT EXISTS transactions (
transaction_id SERIAL PRIMARY KEY,
user_id INTEGER NOT NULL,
trade_type VARCHAR(10) NOT NULL,
currency_pair VARCHAR(10) NOT NULL,
price DECIMAL(10, 2) NOT NULL,
amount DECIMAL(10, 2) NOT NULL,
trade_time TIMESTAMP NOT NULL,
CONSTRAINT fk_trade_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create prices table
CREATE TABLE IF NOT EXISTS prices (
price_id SERIAL PRIMARY KEY,
currency_pair VARCHAR(10) NOT NULL,
best_bid_price DECIMAL(10, 2) NOT NULL,
best_ask_price DECIMAL(10, 2) NOT NULL,
source VARCHAR(50) NOT NULL,
created_at TIMESTAMP NOT NULL,
update_time TIMESTAMP NOT NULL
);