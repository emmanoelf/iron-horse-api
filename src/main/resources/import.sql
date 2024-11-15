-- Inserindo um usuário
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user1@example.com', 'Alice Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '1234567890', 'ADMIN');
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user2@example.com', 'Diovana Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '2131231243', 'USER');

-- Inserindo informações do usuário
INSERT INTO user_info (created_at, updated_at, cpf, street_address, street_name, street_number, district, zipcode, city, state, latitude, longitude, driver_license, accept_comunication, is_terms_user, is_regularized, is_real_information, user_id) VALUES (NOW(), NOW(), '123.456.789-00', 'Rua A', 'Rua A', 123, 'Centro', '01000-000', 'São Paulo', 'SP', -23.5489, -46.6388, 'ABC123456', true, true, true, true, 1);

-- Inserindo dados para o user 1
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Honda', 'Civic', 2021, 1);

INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Ford', 'Fiesta', 2018, 1);

-- Inserindo dados para o user 2
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Volkswagen', 'Fusca', 1970, 2);

INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Chevrolet', 'Corsa', 2005, 2);


-- Inserindo dados em car_info
INSERT INTO car_info(insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type,engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission)VALUES(TRUE, 1, NOW(), NOW(), '1A2B3C4D5E67890', '2.0', 'Left', 'EN1234567890', 'Electric','Tesla Insurance', 'ABC-1234', '10000 km', '1234567890123', 'Automatic'),(FALSE, 2, NOW(), NOW(), '2B3C4D5E6F78901', '3.5', 'Right', 'EN0987654321', 'Gasoline','Ford Insurance', 'XYZ-5678', '15000 km', '9876543210987', 'Manual')

-- Inserindo dados em car_features
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at) VALUES (true, true, false, true, false, true, true, false, true, true, true, false, true, true, false, false, true, true, 1, NOW(), NOW()), (false, true, true, false, true, false, false, true, false, false, false, true, false, false, true, true, false, false, 2, NOW(), NOW());

-- Inserindo em car_overview
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES ( 1, 1, 10, 150.00, 1, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.'),(1, 0, 5, 200.50, 2, NOW(), NOW(), 'Carro disponível apenas para reservas antecipadas.');

-- Inserindo em review

INSERT INTO `review` (car_id, created_at, rate, updated_at, user_id, pros, cons) VALUES (1, NOW(), 4, NOW(), 1, 'Ótima experiência! O carro estava em excelentes condições.', 'Carro meio ruim mas tudo bem'), (1, NOW(), 5, NOW(), 2, 'Carro muito confortável e fácil de dirigir.', 'Carro fusca né mano sobe em morro azul'), (2, NOW(), 3, NOW(), 1, 'Bom carro, mas o consumo de combustível poderia ser melhor.', 'Só tava afim de reclamar mas gostei do carro'), (2, NOW(), 4, NOW(), 2, 'Serviço excelente, carro em ótimo estado.', 'Era melhor ter usado o carro do pelé');

-- Inserindo em rental
INSERT INTO `rental` (start_date, expected_end_date, real_end_date, status, user_id, car_id, created_at, updated_at) VALUES ('2024-10-08 01:10:00', '2024-10-09 23:10:00', NULL, 'ACTIVE', 1, 1, NOW(), NOW());

