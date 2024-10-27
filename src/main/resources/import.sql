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
INSERT INTO car_info (created_at, updated_at, license_plate, transmission, direction_type, chassi, engine_number, engine_horse_power, mileage, fuel_type, renavam, insurance_name, insurance, insulfilm, tag_pike, anti_theft_secret, multimedia, air_conditioner, electric_windows_and_locks, triangle, monkey, wheel_wrench, spare_tire, fire_extinguisher, alarm, smokers_accepted, tag_activated, is_fines_belong_to_the_offender, is_terms_user, car_id) VALUES (NOW(), NOW(), 'ABC-1234', 'Automatic', 'Hydraulic', '1HGBH41JXMN109186', 'ENG123456', '150', '10000', 'Gasoline', 'REN123456', 'Insurance Co', TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE, TRUE, 1), (NOW(), NOW(), 'XYZ-5678', 'Manual', 'Electric', '2HGBH41JXMN109187', 'ENG654321', '120', '20000', 'Diesel', 'REN654321', 'Another Insurance', FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, 2);

-- Inserindo em car_overview
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES ( 1, 1, 10, 150.00, 1, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.'),(1, 0, 5, 200.50, 2, NOW(), NOW(), 'Carro disponível apenas para reservas antecipadas.');

-- Inserindo em review

INSERT INTO `review` (car_id, created_at, rate, updated_at, user_id, pros, cons) VALUES (1, NOW(), 4, NOW(), 1, 'Ótima experiência! O carro estava em excelentes condições.', 'Carro meio ruim mas tudo bem'), (1, NOW(), 5, NOW(), 2, 'Carro muito confortável e fácil de dirigir.', 'Carro fusca né mano sobe em morro azul'), (2, NOW(), 3, NOW(), 1, 'Bom carro, mas o consumo de combustível poderia ser melhor.', 'Só tava afim de reclamar mas gostei do carro'), (2, NOW(), 4, NOW(), 2, 'Serviço excelente, carro em ótimo estado.', 'Era melhor ter usado o carro do pelé');

