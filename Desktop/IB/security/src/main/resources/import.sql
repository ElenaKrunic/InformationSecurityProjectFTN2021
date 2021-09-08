INSERT INTO clinic (id, _address, _name, _description) values (1, 'Dvanaest beba bb', 'Univerzitetska bolnica Klinicki centar Banja Luka', 'Najveca i najznacajnija javna zdravstvena ustanova u Republici Srpskoj');
INSERT INTO clinic (id, _address, _name, _description) values (2, 'Hajduk Veljkova 1', 'Klinicki centar Vojvodine', 'Najveca i najznacajnija javna zdravstvena ustanova u Vojvodini');

INSERT INTO role (id, _name) values (1, 'CLINIC_CENTER_ADMIN');
INSERT INTO role (id, _name) values (2, 'CLINIC_ADMIN');
INSERT INTO role (id, _name) values (3, 'DOCTOR');
INSERT INTO role (id, _name) values (4, 'NURSE');
INSERT INTO role (id, _name) values (5, 'PATIENT');

INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (1, 'Gavrila Principa 4', 'lelekrunic1@gmail.com', 'DZ_Teslic1010', 'Elena', 'pereCvetka!8', 'Krunic', '063224144', 1, '2022-11-05', 1)
INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (2, 'Vojvode Supljikca 43', 'testingformzapp@gmail.com', 'DZ_Ljubinje2201', 'Svetozar', 'Testing12345!', 'Brboric', '063332709', 1, '2022-12-01', 1)
INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (3, 'Valentina Vodnika 1', 'miletamaletia@gmail.com', 'KC_BanjaLuka2903', 'Mileta', 'pass', 'Maletic', '065332709', 1, '2022-01-01', 1)
INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (4, '1003 kaplara 2', 'jevrosimatajna@gmail.com', 'KC_NoviSad2201', 'Jevrosima', 'pass', 'Cutuk', '063332709', 1, '2022-05-04', 2)
INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (5, 'Mise Dimitrijevica 38', 'markomarkovic@gmail.com', 'KC_NoviSad4421', 'Marko', 'pass', 'Markovic', '061223657', 1, '2022-10-10', 2)
INSERT INTO user (id, address, email, identifier, firstname, pass, lastname, phone_number, validated, expire, clinic_id) values (6, 'Jovana Ducica bb', 'sanjapozder@gmail.com', 'KC_BanjaLuka231', 'Sanja', 'pass', 'Pozderovic', '066441975', 1, '2022-02-19', 1)

--za pocetak 2 predefinisana admina kc-a
insert into role_has_user (user_id, role_id) values (1, 1)
insert into role_has_user (user_id, role_id) values (2, 1)
insert into role_has_user (user_id, role_id) values (3, 2)
insert into role_has_user (user_id, role_id) values (4, 3)
insert into role_has_user (user_id, role_id) values (5, 4)
insert into role_has_user (user_id, role_id) values (6, 5)

insert into health_service (id, _name, price, clinic_id) values (1, 'Vadjenje zuba', 200, 1)
insert into health_service (id, _name, price, clinic_id) values (2, 'Pregled pluca', 700, 2)
insert into health_service (id, _name, price, clinic_id) values (3, 'Mjerenje dioptrije', 133, 1)
insert into health_service (id, _name, price, clinic_id) values (4, 'Vadjenje krvi', 122,  2)
insert into health_service (id, _name, price, clinic_id) values (5, 'Snimanje kostiju sake', 655, 1)

insert into examination (id, data_about_examination, _date, _discount, _duration, doctor, health_ser_id, medical_sister, patient) values (1, 'Uspjesno izvadjen zubic', '2021-09-07', 0, 4, 5, 1, 5, 6)

insert into assessment (id, assessment_clinic, assessment_doctor, examination_id) values (1, 10, 10, 1)

insert into medical_record (id, certified, note, therapy, time, examination_id) values (1, 1, 'Uspjesno uradjena operacija', 'Terapija je bespotrebna', '2021-09-07', 1)


