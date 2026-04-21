-- Sample user: carlos@example.com / password123
INSERT INTO users (name, email, password)
VALUES ('Carlos Ruiz', 'carlos@example.com', '$2b$10$bs57XgQIQveowYuRuYGDJuvJM8QfURay.GAqqZrGx3GwLopJXa.0u')
ON CONFLICT (email) DO NOTHING;

INSERT INTO cars (brand, model, year, plate_number, color, user_id)
VALUES
    ('Toyota', 'Corolla', 2020, 'ABC-123', 'Blanco', (SELECT id FROM users WHERE email = 'carlos@example.com')),
    ('Mazda',  'CX-5',    2022, 'XYZ-789', 'Rojo',   (SELECT id FROM users WHERE email = 'carlos@example.com'))
ON CONFLICT (plate_number) DO NOTHING;
