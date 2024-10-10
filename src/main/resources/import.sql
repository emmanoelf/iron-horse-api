-- Inserindo roles
INSERT INTO role (created_at, id, updated_at, name) VALUES (NOW(), 1, NOW(), 'Renter');
INSERT INTO role (created_at, id, updated_at, name) VALUES (NOW(), 22, NOW(), 'Owner');

-- Inserindo um usuário
INSERT INTO user (created_at, updated_at, email, name, password, phone) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user1@example.com', 'Alice Silva', 'password123', '1234567890');
INSERT INTO user (created_at, updated_at, email, name, password, phone) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user2@example.com', 'Diovana Silva', 'password12323', '2131231243');

-- Inserindo informações do usuário
INSERT INTO user_info (created_at, updated_at, cpf, street_address, street_name, street_number, district, zipcode, city, state, driver_license, accept_comunication, is_terms_user, is_regularized, is_real_information, user_id) VALUES (NOW(), NOW(), '123.456.789-00', 'Rua A', 'Rua A', 123, 'Centro', '01000-000', 'São Paulo', 'SP', 'ABC123456', true, true, true, true, 1);

-- Inserindo dados em Car
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Honda', 'Civic', 2021, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Ford', 'Focus', 2019, 2);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Chevrolet', 'Onix', 2022, 2);

-- Inserindo dados em car_info
INSERT INTO car_info (created_at, updated_at, license_plate, transmission, direction_type, chassi, engine_number, engine_horse_power, mileage, fuel_type, renavam, insurance_name, insurance, insulfilm, tag_pike, anti_theft_secret, multimedia, air_conditioner, electric_windows_and_locks, triangle, monkey, wheel_wrench, spare_tire, fire_extinguisher, alarm, smokers_accepted, tag_activated, is_fines_belong_to_the_offender, is_terms_user, car_id) VALUES (NOW(), NOW(), 'ABC-1234', 'Automatic', 'Hydraulic', '1HGBH41JXMN109186', 'ENG123456', '150', '10000', 'Gasoline', 'REN123456', 'Insurance Co', TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE, TRUE, 1), (NOW(), NOW(), 'XYZ-5678', 'Manual', 'Electric', '2HGBH41JXMN109187', 'ENG654321', '120', '20000', 'Diesel', 'REN654321', 'Another Insurance', FALSE, FALSE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, TRUE, FALSE, TRUE, FALSE, TRUE, TRUE, TRUE, TRUE, FALSE, 2);

