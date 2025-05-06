USE Auctions;
GO

-- Insertion des catégories
INSERT INTO CATEGORIES (category_desc) VALUES
                                           ('Informatique'),
                                           ('Ameublement'),
                                           ('Vêtement'),
                                           ('Sport&Loisirs');

-- Insertion des utilisateurs
INSERT INTO USERS (pseudo, lastname, firstname, email, phone_nb, address, post_code, city, password, credit, administrator) VALUES
                                                                                                                                ('jdupont', 'Dupont', 'Jean', 'j.dupont@mail.com', '0611223344', '1 rue des Lilas', '75001', 'Paris', 'mdp123', 100, 0),
                                                                                                                                ('msmith', 'Smith', 'Marie', 'm.smith@mail.com', '0622334455', '2 avenue Victor Hugo', '69002', 'Lyon', 'pass456', 150, 0),
                                                                                                                                ('tlee', 'Lee', 'Tom', 't.lee@mail.com', '0633445566', '3 bd Haussmann', '13001', 'Marseille', 'azerty', 200, 0),
                                                                                                                                ('ajackson', 'Jackson', 'Alice', 'a.jackson@mail.com', '0644556677', '4 rue Lafayette', '31000', 'Toulouse', '1234abcd', 50, 0),
                                                                                                                                ('nmartin', 'Martin', 'Nina', 'n.martin@mail.com', '0655667788', '5 place Bellecour', '44000', 'Nantes', 'mdp456', 120, 0),
                                                                                                                                ('bdurand', 'Durand', 'Bruno', 'b.durand@mail.com', '0666778899', '6 chemin Vert', '59000', 'Lille', 'secret', 300, 0),
                                                                                                                                ('cbertrand', 'Bertrand', 'Chloé', 'c.bertrand@mail.com', '0677889900', '7 allée des Roses', '67000', 'Strasbourg', 'lol123', 80, 0),
                                                                                                                                ('gpetit', 'Petit', 'Gilles', 'g.petit@mail.com', '0688990011', '8 impasse Dupuy', '06000', 'Nice', 'monmdp', 60, 0),
                                                                                                                                ('lmarchand', 'Marchand', 'Léa', 'l.marchand@mail.com', '0699001122', '9 rue Alsace', '80000', 'Amiens', 'hello12', 90, 0),
                                                                                                                                ('rrobert', 'Robert', 'Romain', 'r.robert@mail.com', '0600112233', '10 quai des Chartrons', '33000', 'Bordeaux', 'test321', 110, 0);

-- Insertion des articles
INSERT INTO ITEMS_SOLD (item_name, description, auction_date_begin, auction_date_end, price_init, price_selling, user_id, category_id, picture_url) VALUES
                                                                                                                                                        ('Ordinateur portable', 'PC portable i7 avec 16Go RAM', '2025-05-01', '2025-05-10', 400, NULL, 1, 1, '/static/images/ordinateur-portable.jpg'),
                                                                                                                                                        ('Fauteuil en cuir', 'Fauteuil relaxant en cuir noir', '2025-05-01', '2025-05-12', 150, NULL, 2, 2, NULL),
                                                                                                                                                        ('Veste en jean', 'Veste Levi’s taille M', '2025-05-02', '2025-05-11', 40, NULL, 3, 3, NULL),
                                                                                                                                                        ('Vélo de course', 'Vélo route en carbone', '2025-05-03', '2025-05-13', 500, NULL, 4, 4, NULL),
                                                                                                                                                        ('Écran 27 pouces', 'Écran gaming 144Hz', '2025-05-04', '2025-05-14', 200, NULL, 5, 1, NULL),
                                                                                                                                                        ('Canapé 3 places', 'Canapé tissu gris clair', '2025-05-05', '2025-05-15', 300, NULL, 6, 2, NULL),
                                                                                                                                                        ('Robe d’été', 'Robe légère imprimée fleurs', '2025-05-06', '2025-05-16', 25, NULL, 7, 3, NULL),
                                                                                                                                                        ('Tapis de yoga', 'Tapis antidérapant', '2025-05-07', '2025-05-17', 15, NULL, 8, 4, NULL),
                                                                                                                                                        ('Clavier mécanique', 'Clavier RGB AZERTY', '2025-05-08', '2025-05-18', 80, NULL, 9, 1, NULL),
                                                                                                                                                        ('Chaussures de sport', 'Baskets running Nike', '2025-05-09', '2025-05-19', 60, NULL, 10, 4, NULL);

-- Insertion des adresses de retrait
INSERT INTO PICKUPS (item_id, address, post_code, city) VALUES
                                                            (1, '11 rue des Violettes', '75010', 'Paris'),
                                                            (2, '12 avenue République', '69003', 'Lyon'),
                                                            (3, '13 bd Baille', '13005', 'Marseille'),
                                                            (4, '14 rue Matabiau', '31000', 'Toulouse'),
                                                            (5, '15 quai Malakoff', '44000', 'Nantes'),
                                                            (6, '16 place Rihour', '59000', 'Lille'),
                                                            (7, '17 rue du 22-Novembre', '67000', 'Strasbourg'),
                                                            (8, '18 promenade Anglais', '06000', 'Nice'),
                                                            (9, '19 rue Saint-Leu', '80000', 'Amiens'),
                                                            (10, '20 rue Sainte-Catherine', '33000', 'Bordeaux');

-- Insertion des enchères (chaque utilisateur mise sur un article qu'il n'a pas mis en vente)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (2, 1, '2025-05-02 14:00:00', 420),
                                                             (3, 2, '2025-05-03 15:30:00', 160),
                                                             (4, 3, '2025-05-04 16:00:00', 45),
                                                             (5, 4, '2025-05-05 17:00:00', 520),
                                                             (6, 5, '2025-05-06 18:00:00', 210),
                                                             (7, 6, '2025-05-07 19:00:00', 310),
                                                             (8, 7, '2025-05-08 10:00:00', 30),
                                                             (9, 8, '2025-05-09 11:00:00', 18),
                                                             (10, 9, '2025-05-10 12:00:00', 85),
                                                             (1, 10, '2025-05-11 13:00:00', 65);

-- Mise à jour des prix de vente (price_selling) après enchères
UPDATE ITEMS_SOLD SET price_selling = 420 WHERE item_id = 1;
UPDATE ITEMS_SOLD SET price_selling = 160 WHERE item_id = 2;
UPDATE ITEMS_SOLD SET price_selling = 45  WHERE item_id = 3;
UPDATE ITEMS_SOLD SET price_selling = 520 WHERE item_id = 4;
UPDATE ITEMS_SOLD SET price_selling = 210 WHERE item_id = 5;
UPDATE ITEMS_SOLD SET price_selling = 310 WHERE item_id = 6;
UPDATE ITEMS_SOLD SET price_selling = 30  WHERE item_id = 7;
UPDATE ITEMS_SOLD SET price_selling = 18  WHERE item_id = 8;
UPDATE ITEMS_SOLD SET price_selling = 85  WHERE item_id = 9;
UPDATE ITEMS_SOLD SET price_selling = 65  WHERE item_id = 10;

-- Enchères supplémentaires pour chaque article (3 par article sauf dernier avec 2)
-- Article 1 (vendu par user 1) : enchères par users 2, 3, 4
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (3, 1, '2025-05-02 15:00:00', 430),
                                                             (4, 1, '2025-05-02 16:00:00', 450);

-- Article 2 (vendu par user 2)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (4, 2, '2025-05-03 16:00:00', 170),
                                                             (5, 2, '2025-05-03 17:00:00', 180);

-- Article 3 (vendu par user 3)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (5, 3, '2025-05-04 17:00:00', 48),
                                                             (6, 3, '2025-05-04 18:00:00', 50);

-- Article 4 (vendu par user 4)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (6, 4, '2025-05-05 18:00:00', 530),
                                                             (7, 4, '2025-05-05 19:00:00', 550);

-- Article 5 (vendu par user 5)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (7, 5, '2025-05-06 19:00:00', 220),
                                                             (8, 5, '2025-05-06 20:00:00', 225);

-- Article 6 (vendu par user 6)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (8, 6, '2025-05-07 20:00:00', 320),
                                                             (9, 6, '2025-05-07 21:00:00', 330);

-- Article 7 (vendu par user 7)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (9, 7, '2025-05-08 11:00:00', 35),
                                                             (10, 7, '2025-05-08 12:00:00', 37);

-- Article 8 (vendu par user 8)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (10, 8, '2025-05-09 12:00:00', 20),
                                                             (1, 8, '2025-05-09 13:00:00', 22);

-- Article 9 (vendu par user 9)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
                                                             (1, 9, '2025-05-10 13:00:00', 90),
                                                             (2, 9, '2025-05-10 14:00:00', 95);

-- Article 10 (vendu par user 10)
INSERT INTO BIDS (user_id, item_id, bid_date, bid_price) VALUES
    (2, 10, '2025-05-11 14:00:00', 70);

UPDATE ITEMS_SOLD SET price_selling = 450 WHERE item_id = 1;
UPDATE ITEMS_SOLD SET price_selling = 180 WHERE item_id = 2;
UPDATE ITEMS_SOLD SET price_selling = 50  WHERE item_id = 3;
UPDATE ITEMS_SOLD SET price_selling = 550 WHERE item_id = 4;
UPDATE ITEMS_SOLD SET price_selling = 225 WHERE item_id = 5;
UPDATE ITEMS_SOLD SET price_selling = 330 WHERE item_id = 6;
UPDATE ITEMS_SOLD SET price_selling = 37  WHERE item_id = 7;
UPDATE ITEMS_SOLD SET price_selling = 22  WHERE item_id = 8;
UPDATE ITEMS_SOLD SET price_selling = 95  WHERE item_id = 9;
UPDATE ITEMS_SOLD SET price_selling = 70  WHERE item_id = 10;

UPDATE USERS SET password = '{bcrypt}$2y$10$hQ1s0HeofglfopbL4LM6qu2NJkMLz68SKYzKIYEp9hQOYK0CAG07a' WHERE user_id = 1;