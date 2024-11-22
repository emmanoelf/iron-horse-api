-- Inserindo um usuário
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user1@example.com', 'Alice Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '1234567890', 'ADMIN');
INSERT INTO user (created_at, updated_at, email, name, password, phone, role) VALUES ('2024-10-08 01:10:00', '2024-10-08 01:10:00', 'user2@example.com', 'Diovana Silva', '$2a$10$5y2QutxOnBjHhE0fstJi6O8QTKPrU5Na40s73sbBY0C8NaHJ1H8ie', '2131231243', 'USER');

-- Inserindo informações do usuário
INSERT INTO user_info (created_at, updated_at, cpf, street_address, street_name, street_number, district, zipcode, city, state, latitude, longitude, driver_license, accept_comunication, is_terms_user, is_regularized, is_real_information, user_id) VALUES (NOW(), NOW(), '123.456.789-00', 'Rua A', 'Rua A', 123, 'Centro', '01000-000', 'São Paulo', 'SP', -23.5489, -46.6388, 'ABC123456', true, true, true, true, 1);

-- Inserindo dados para o user 1
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Honda', 'Civic', 2021, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Ford', 'Fiesta', 2018, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Toyota', 'Corolla', 2020, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Ford', 'Fusion', 2019, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Chevrolet', 'Onix', 2022, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'BMW', '320i', 2023, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Audi', 'A3', 2022, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Volkswagen', 'Gol', 2018, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Nissan', 'Sentra', 2021, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Hyundai', 'Elantra', 2020, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Kia', 'Seltos', 2023, 1);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id) VALUES (NOW(), NOW(), 'Hyundai', 'Tucson', 2021, 1);


-- Inserindo dados para o user 2
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Volkswagen', 'Fusca', 1970, 2);
INSERT INTO car (created_at, updated_at, brand, model, manufacture_year, user_id)VALUES (NOW(), NOW(), 'Chevrolet', 'Corsa', 2005, 2);


-- Inserindo dados em car_info
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity) VALUES(TRUE, 1, NOW(), NOW(), '1A2B3C4D5E67890', '2.0', 'Left', 'EN1234567890', 'Electric', 'Tesla Insurance', 'ABC-1234', '10000 km', '1234567890123', 'Automatic', 4, 2, 'black', 'LED', '450 liters'),(FALSE, 2, NOW(), NOW(), '2B3C4D5E6F78901', '3.5', 'Right', 'EN0987654321', 'Gasoline', 'Ford Insurance', 'XYZ-5678', '15000 km', '9876543210987', 'Manual', 4, 5, 'red', 'Halogen', '500 liters');
--INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity) VALUES(TRUE, 3, NOW(), NOW(), '1A2B3C4D5E67890', '2.0', 'Left', 'EN1234567890', 'Electric', 'Tesla Insurance', 'ABC-1234', '10000 km', '1234567890123', 'Automatic', 4, 2, 'black', 'LED', '450 liters'),(FALSE, 4, NOW(), NOW(), '2B3C4D5E6F78901', '3.5', 'Right', 'EN0987654321', 'Gasoline', 'Ford Insurance', 'XYZ-5678', '15000 km', '9876543210987', 'Manual', 4, 5, 'red', 'Halogen', '500 liters');

-- Para o carro Toyota Corolla 2020
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 3, NOW(), NOW(), '1T2A3B4C5D67891', '1.8', 'Left', 'EN2345678901', 'Gasoline', 'Toyota Insurance', 'DEF-5678', '20000 km', '2345678901234', 'Automatic', 4, 5, 'White', 'Halogen', '450 liters');

-- Para o carro Ford Fusion 2019
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 4, NOW(), NOW(), '1F2A3B4C5D67892', '2.5', 'Left', 'EN3456789012', 'Gasoline', 'Ford Insurance', 'GHI-9012', '30000 km', '3456789012345', 'Automatic', 4, 5, 'Blue', 'LED', '400 liters');

-- Para o carro Chevrolet Onix 2022
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 5, NOW(), NOW(), '1C2A3B4C5D67893', '1.0', 'Left', 'EN4567890123', 'Flex', 'Chevrolet Insurance', 'JKL-3456', '5000 km', '4567890123456', 'Automatic', 4, 5, 'Red', 'Halogen', '320 liters');

-- Para o carro BMW 320i 2023
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 6, NOW(), NOW(), '1B2A3B4C5D67894', '2.0', 'Left', 'EN5678901234', 'Gasoline', 'BMW Insurance', 'MNO-4567', '1000 km', '5678901234567', 'Automatic', 4, 5, 'Black', 'LED', '480 liters');

-- Para o carro Audi A3 2022
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 7, NOW(), NOW(), '1A2A3B4C5D67895', '1.8', 'Left', 'EN6789012345', 'Diesel', 'Audi Insurance', 'PQR-5678', '12000 km', '6789012345678', 'Automatic', 4, 5, 'Gray', 'LED', '370 liters');

-- Para o carro Volkswagen Gol 2018
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 8, NOW(), NOW(), '1V2A3B4C5D67896', '1.6', 'Left', 'EN7890123456', 'Gasoline', 'Volkswagen Insurance', 'STU-6789', '40000 km', '7890123456789', 'Manual', 4, 5, 'Yellow', 'Halogen', '280 liters');

-- Para o carro Nissan Sentra 2021
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 9, NOW(), NOW(), '1N2A3B4C5D67897', '2.0', 'Left', 'EN8901234567', 'Gasoline', 'Nissan Insurance', 'VWX-7890', '18000 km', '8901234567890', 'Automatic', 4, 5, 'Green', 'Halogen', '350 liters');

-- Para o carro Hyundai Elantra 2020
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 10, NOW(), NOW(), '1H2A3B4C5D67898', '2.0', 'Left', 'EN9012345678', 'Gasoline', 'Hyundai Insurance', 'YZA-1234', '25000 km', '9012345678901', 'Automatic', 4, 5, 'Orange', 'LED', '380 liters');

-- Para o carro Kia Seltos 2023
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 11, NOW(), NOW(), '1K2A3B4C5D67899', '1.6', 'Left', 'EN0123456789', 'Gasoline', 'Kia Insurance', 'BCD-2345', '5000 km', '0123456789012', 'Automatic', 4, 5, 'Purple', 'LED', '400 liters');

-- Para o carro Hyundai Tucson 2021
INSERT INTO car_info (insurance, car_id, created_at, updated_at, chassi, cylinder_displacement, direction_type, engine_number, fuel_type, insurance_name, license_plate, mileage, renavam, transmission, num_doors, num_seats, color, headlight_bulb, trunk_capacity)VALUES (TRUE, 12, NOW(), NOW(), '1H2A3B4C5D67899', '2.0', 'Left', 'EN9876543210', 'Gasoline', 'Hyundai Insurance', 'XYZ-2021', '12000 km', '9876543210987', 'Automatic', 4, 5, 'Silver', 'LED', '500 liters');



-- Inserindo dados em car_features
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher,insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted,is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire,tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES(true, true, false, true, false, true, true, false, true, true, true, false, true, true,false, false, true, true, 1, NOW(), NOW()),(false, true, true, false, true, false, false, true, false, false, false, true,false, false, true, true, false, false, 2, NOW(), NOW());

-- Inserindo em car_overview
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES ( 1, 1, 10, 150.00, 1, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.'),(1, 0, 5, 200.50, 2, NOW(), NOW(), 'Carro disponível apenas para reservas antecipadas.');

-- Para o carro Toyota Corolla 2020
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 3, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Ford Fusion 2019
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 4, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Chevrolet Onix 2022
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 5, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro BMW 320i 2023
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 6, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Audi A3 2022
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 7, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Volkswagen Gol 2018
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 8, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Nissan Sentra 2021
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 9, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Hyundai Elantra 2020
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 10, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Kia Seltos 2023
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 11, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');

-- Para o carro Hyundai Tucson 2021
INSERT INTO `car_overview` (is_active, is_available, number_trips, price, car_id, created_at, updated_at, description) VALUES (1, 1, 10, 150.00, 12, NOW(), NOW(), 'Carro em ótimo estado, pronto para viagens.');




-- Inserindo em review

INSERT INTO `review` (car_id, created_at, rate, updated_at, user_id, pros, cons) VALUES (1, NOW(), 4, NOW(), 1, 'Ótima experiência! O carro estava em excelentes condições.', 'Carro meio ruim mas tudo bem'), (1, NOW(), 5, NOW(), 2, 'Carro muito confortável e fácil de dirigir.', 'Carro fusca né mano sobe em morro azul'), (2, NOW(), 3, NOW(), 1, 'Bom carro, mas o consumo de combustível poderia ser melhor.', 'Só tava afim de reclamar mas gostei do carro'), (2, NOW(), 4, NOW(), 2, 'Serviço excelente, carro em ótimo estado.', 'Era melhor ter usado o carro do pelé');

-- Inserindo em rental
INSERT INTO `rental` (start_date, expected_end_date, real_end_date, status, user_id, car_id, created_at, updated_at) VALUES ('2024-10-08 01:10:00', '2024-10-09 23:10:00', NULL, 'ACTIVE', 1, 1, NOW(), NOW());


-- Para o carro Toyota Corolla 2020 (car_id = 3)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (false, true, true, false, true, false, false, true, false, false, false, true, false, false, true, true, false, false, 3, NOW(), NOW());

-- Para o carro Ford Fusion 2019 (car_id = 4)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (true, true, false, true, false, true, true, false, true, true, true, false, true, true, false, false, true, true, 4, NOW(), NOW());

-- Para o carro Chevrolet Onix 2022 (car_id = 5)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (true, false, true, true, false, true, true, false, true, true, true, false, true, false, true, true, false, true, 5, NOW(), NOW());

-- Para o carro BMW 320i 2023 (car_id = 6)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (false, true, false, false, true, false, true, true, false, false, true, false, false, true, true, true, true, false, 6, NOW(), NOW());

-- Para o carro Audi A3 2022 (car_id = 7)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (true, true, true, false, false, true, true, false, true, false, true, true, true, false, true, false, true, true, 7, NOW(), NOW());

-- Para o carro Volkswagen Gol 2018 (car_id = 8)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (false, true, false, true, true, false, true, true, false, false, true, false, false, true, true, true, true, false, 8, NOW(), NOW());

-- Para o carro Nissan Sentra 2021 (car_id = 9)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (true, false, true, true, false, true, true, false, true, false, true, false, true, false, false, true, true, true, 9, NOW(), NOW());

-- Para o carro Hyundai Elantra 2020 (car_id = 10)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (true, true, false, true, true, true, true, false, false, true, true, false, true, true, true, false, false, true, 10, NOW(), NOW());

-- Para o carro Kia Seltos 2023 (car_id = 11)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm, is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information, is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES (false, true, true, false, true, false, true, false, true, false, true, true, false, true, false, true, false, true, 11, NOW(), NOW());

-- Para o carro Hyundai Tucson 2021 (car_id = 12)
INSERT INTO car_features (air_conditioner, alarm, anti_theft_secret, electric_windows_and_locks, fire_extinguisher, insulfilm,is_docs_upto_date, is_fines_belong_to_the_offender, is_smokers_accepted, is_tag_activated, is_true_information,is_veicle_modified, jack, multimedia, spare_tire, tag_pike, triangle, wheel_wrench, car_info_id, created_at, updated_at)VALUES(false, true, true, false, true, false, true, false, true, false, true, true, false, true, false, true, false, true, 12, NOW(), NOW());