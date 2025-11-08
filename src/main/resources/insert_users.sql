-- Insert initial users for PostgreSQL
INSERT INTO customer (password, roles, username) VALUES 
('$2b$12$IOgIns4pL.ij3K3fiTpGq.WqwFR.717/j.vDrGTUKoTXDOvhQke3.', 'ROLE_admin', 'gcastro')
ON CONFLICT (username) DO NOTHING;

INSERT INTO customer (password, roles, username) VALUES 
('$2b$12$ctwuUcby1qzI.SkNWHEy1.wWPmbG8q/hudihUr1sgRS4j4u3DPP7e', 'ROLE_test', 'test')
ON CONFLICT (username) DO NOTHING;