
INSERT INTO person(username, firstName, lastName, email, password,enabled) VALUES ('guest',  'Guest', 'Guest', 'tomjaal@gmail.com', '$2a$10$0.ESlGysrPaiW5HaapKwoehzWt5AibgbPPOvMhDv8D6H26QQ/CwhS', TRUE);
INSERT INTO person(username, firstName, lastName, email, password,enabled) VALUES ('admin',  'Erdenebayar', 'Batsukh', 'eb_mln@yahoo.com', '$2a$10$S/wlXEo/APzf.Sn1cO2p4.V12EJmaw.uzrHelMvkpuahjmHWnSafe', TRUE);
INSERT INTO person(username, firstName, lastName, email, password,enabled) VALUES ('nati',  'Nati', 'Guest', 'natwinet@gmail.com', '$2a$10$0.ESlGysrPaiW5HaapKwoehzWt5AibgbPPOvMhDv8D6H26QQ/CwhS', TRUE);
INSERT INTO person(username, firstName, lastName, email, password,enabled) VALUES ('haben',  'Haben', 'Councelor', 'habzaid@gmail.com', '$2a$10$0.ESlGysrPaiW5HaapKwoehzWt5AibgbPPOvMhDv8D6H26QQ/CwhS', TRUE);
INSERT INTO person(username, firstName, lastName, email, password,enabled) VALUES ('tamir',  'tamir', 'Councelor', 'tamir.ganbat91@gmail.com', '$2a$10$0.ESlGysrPaiW5HaapKwoehzWt5AibgbPPOvMhDv8D6H26QQ/CwhS', TRUE);

INSERT INTO person_role (person_id, role) VALUES (1, 'ROLE_COUNCELOR');
INSERT INTO person_role (person_id, role) VALUES (1, 'ROLE_CUSTOMER');
INSERT INTO person_role (person_id, role) VALUES (2, 'ROLE_ADMIN');
INSERT INTO person_role (person_id, role) VALUES (2, 'ROLE_CUSTOMER');
INSERT INTO person_role (person_id, role) VALUES (2, 'ROLE_COUNCELOR');

INSERT INTO person_role (person_id, role) VALUES (3, 'ROLE_CUSTOMER');
INSERT INTO person_role (person_id, role) VALUES (4, 'ROLE_COUNCELOR');
INSERT INTO person_role (person_id, role) VALUES (4, 'ROLE_CUSTOMER');
INSERT INTO person_role (person_id, role) VALUES (5, 'ROLE_COUNCELOR');

# INSERT INTO  `MEMBER` (firstname, lastname,age,title,membernumber, member_id) VALUES ('Curious','George',12,'Boy Monkey', 8754,'admin');
# INSERT INTO `MEMBER` (firstname, lastname,age,title,membernumber,member_id) VALUES ('Allen','Rench',123,'Torque Master', 8733,'guest');

INSERT INTO location (building, roomNumber) VALUES ('Verill', '29');
INSERT INTO location (building, roomNumber) VALUES ('Verill', '32');

INSERT INTO session VALUES (1, 1, '2017/11/25', 30, 30, '12:00:00', 1, 1);
INSERT INTO session VALUES (2, 1, '2017/11/29', 30, 30, '12:00:00', 2, 1);

INSERT INTO appointment VALUES (NULL, 0, 1, 1);
INSERT INTO appointment VALUES (NULL, 0, 1, 2);
INSERT INTO appointment VALUES (NULL, 0, 2, 1);
INSERT INTO appointment VALUES (NULL, 0, 2, 2);
