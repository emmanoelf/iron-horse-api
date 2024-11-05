-- Inserindo um usuário
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user1@example.com', 'Alice Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '1234567890', 'ADMIN');
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user2@example.com', 'Diovana Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '2131231243', 'USER');

-- Inserindo informações do usuário
INSERT INTO user_info (created_at, updated_at, cpf, street_address, street_name, street_number, district, zipcode, city, state, latitude, longitude, driver_license, accept_comunication, is_terms_user, is_regularized, is_real_information, user_id) VALUES (NOW(), NOW(), '123.456.789-00', 'Rua A', 'Rua A', 123, 'Centro', '01000-000', 'São Paulo', 'SP', -23.5489, -46.6388, 'ABC123456', true, true, true, true, 1);

-- Inserindo dados em Car
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Honda', 'Civic', 2021, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Wolksvagen', 'Fusca', 2050, 2);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Ford', 'Focus', 2019, 2);


-- Inserindo dados em car_info
INSERT INTO car_info (license_plate, transmission, direction_type, chassi, engine_number, cylinder_displacement, mileage, fuel_type, renavam, car_id, created_at, updated_at)VALUES('ABC1234', 'Automático', 'Direção Hidráulica', '1A2B3C4D5E6F7G8H9', 'ENG123456', '2.0', '15000', 'Gasolina', 'REN123456', 1, NOW(), NOW()),('XYZ5678', 'Manual', 'Direção Elétrica', '9H8G7F6E5D4C3B2A1', 'ENG987654', '1.8', '20000', 'Álcool', 'REN987654', 2, NOW(), NOW());


-- Inserindo dados em car_features
INSERT INTO car_features (insurance_name, insurance, insulfilm, tag_pike, anti_theft_secret, multimedia, air_conditioner, electric_windows_and_locks, triangle, jack, wheel_wrench, spare_tire, fire_extinguisher, alarm, smokers_accepted, tag_activated, is_fines_belong_to_the_offender, is_terms_user, car_info_id, created_at, updated_at)VALUES('Seguro Completo', TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, 1, NOW(), NOW()),('Seguro Parcial', FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, FALSE, TRUE, TRUE, 2, NOW(), NOW());


-- Inserindo dados em car_images
INSERT INTO car_images (name, path, size, car_info_id, created_at, updated_at)VALUES('imagem1.jpg', '/imagens/cars/ABC1234/imagem1.jpg', 1024, 1, NOW(), NOW()),('imagem2.jpg', '/imagens/cars/ABC1234/imagem2.jpg', 2048, 1, NOW(), NOW()),('imagem3.jpg', '/imagens/cars/XYZ5678/imagem1.jpg', 512, 2, NOW(), NOW()),('imagem4.jpg', '/imagens/cars/XYZ5678/imagem2.jpg', 1536, 2, NOW(), NOW());





-- Inserindo em car_overview
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES ( 1, 1, 10, 150.00, 1, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.'),(1, 0, 5, 200.50, 2, NOW(), NOW(), 'Carro disponível apenas para reservas antecipadas.');

-- Inserindo em review

INSERT INTO `review` (car_id, created_at, rate, updated_at, user_id, pros, cons) VALUES (1, NOW(), 4, NOW(), 1, 'Ótima experiência! O carro estava em excelentes condições.', 'Carro meio ruim mas tudo bem'), (1, NOW(), 5, NOW(), 2, 'Carro muito confortável e fácil de dirigir.', 'Carro fusca né mano sobe em morro azul'), (2, NOW(), 3, NOW(), 1, 'Bom carro, mas o consumo de combustível poderia ser melhor.', 'Só tava afim de reclamar mas gostei do carro'), (2, NOW(), 4, NOW(), 2, 'Serviço excelente, carro em ótimo estado.', 'Era melhor ter usado o carro do pelé');

-- Inserindo em rental
INSERT INTO `rental` (start_date, expected_end_date, real_end_date, status, user_id, car_id, created_at, updated_at) VALUES ('2024-10-08 01:10:00', '2024-10-09 23:10:00', NULL, 'ACTIVE', 1, 1, NOW(), NOW());

