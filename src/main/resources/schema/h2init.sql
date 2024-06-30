-- Create users table
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Create wallets table
CREATE TABLE wallets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance DOUBLE NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create trades table
CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    trade_type VARCHAR(10) NOT NULL,
    currency_pair VARCHAR(10) NOT NULL,
    price DOUBLE NOT NULL,
    amount DOUBLE NOT NULL,
    trade_time TIMESTAMP NOT NULL,
    CONSTRAINT fk_trade_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create prices table
CREATE TABLE prices (
    price_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    currency_pair VARCHAR(10) NOT NULL,
    best_bid_price DOUBLE NOT NULL,
    best_ask_price DOUBLE NOT NULL,
    source VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    update_time TIMESTAMP NOT NULL
);
