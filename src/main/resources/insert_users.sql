-- Insert initial users for PostgreSQL
INSERT INTO customer (password, roles, username) VALUES 
('$2y$04$IgiMa8bMxHlHELpTX4FcMeOszUOHL57FGl/Ei/mh9yB4fvRQsORhe', 'ROLE_admin', 'gcastro')
ON CONFLICT (username) DO NOTHING;

INSERT INTO customer (password, roles, username) VALUES 
('$2y$04$Pcs40WPVivgF7GSHwbFSz.fQ0ZiX1Ds5EoyYkth4MYeKi7TOaf5eS', 'ROLE_test', 'test')
ON CONFLICT (username) DO NOTHING;