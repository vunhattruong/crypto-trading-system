-- Insert initial data into users table
INSERT INTO users (username, email, password) VALUES
('user1', 'user1@example.com', 'password1'),
('user2', 'user2@example.com', 'password2'),
('user3', 'user3@example.com', 'password3');

-- Insert initial data into wallets table (50,000 USDT initial balance for each user)
INSERT INTO wallets (user_id, currency, balance) VALUES
(1, 'USDT', 50000),
(1, 'BTC', 0),
(1, 'ETH', 0),
(2, 'USDT', 50000),
(2, 'BTC', 0),
(2, 'ETH', 0),
(3, 'USDT', 50000),
(3, 'BTC', 0),
(3, 'ETH', 0);