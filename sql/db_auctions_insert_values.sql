USE Auctions;
GO

INSERT INTO CATEGORIES (category_desc)
VALUES
('Céramique'),
('Montres'),
('Peintures'),
('Mobilier'),
('Jeux de société'),
('Instruments de musique'),
('Bijoux'),
('Jeux vidéo'),
('Vélos'),
('Sculptures');

INSERT INTO USERS (pseudo, lastname, firstname, email, phone_nb, address, post_code, city, password, credit, administrator)
VALUES
    ('johndoe', 'Doe', 'John', 'johndoe@email.com', '0123456789', '123 Main St', '75001', 'Paris', 'password123', 100, 0),
    ('janedoe', 'Doe', 'Jane', 'janedoe@email.com', '0987654321', '456 Elm St', '75002', 'Paris', 'password456', 150, 0),
    ('michael88', 'Smith', 'Michael', 'michael88@email.com', '0152457896', '789 Pine St', '75003', 'Paris', 'mypassword789', 200, 0),
    ('sarahjane', 'Williams', 'Sarah', 'sarahjane@email.com', '0223344556', '321 Oak St', '75004', 'Paris', 'securepass321', 250, 0),
    ('charliebrown', 'Brown', 'Charlie', 'charliebrown@email.com', '0334455667', '654 Birch St', '75005', 'Paris', 'passwordcharlie', 500, 1),
    ('amysmith', 'Smith', 'Amy', 'amysmith@email.com', '0445566778', '987 Cedar St', '75006', 'Paris', 'amysuperpass', 300, 0),
    ('davidlee', 'Lee', 'David', 'davidlee@email.com', '0556677889', '159 Maple St', '75007', 'Paris', 'davidpassword', 350, 1),
    ('emilyjohnson', 'Johnson', 'Emily', 'emilyjohnson@email.com', '0667788990', '753 Redwood St', '75008', 'Paris', 'emilysecurepass', 400, 0),
    ('richardking', 'King', 'Richard', 'richardking@email.com', '0778899001', '123 Palm St', '75009', 'Paris', 'kingrichardpass', 600, 1),
    ('lucyparker', 'Parker', 'Lucy', 'lucyparker@email.com', '0889900112', '456 Willow St', '75010', 'Paris', 'lucyparkerpass', 450, 0);

INSERT INTO ITEMS_SOLD (item_name, description, auction_date_begin, auction_date_end, price_init, price_selling, user_id, category_id)
VALUES
    ('Vase en céramique', 'Un vase en céramique ancien de style asiatique, parfait pour décorer un salon ou une bibliothèque.', '2025-04-01', '2025-04-15', 50, 120, 1, 2),
    ('Montre vintage', 'Montre à gousset vintage en or, parfaitement conservée. Idéale pour les collectionneurs d''objets rares.', '2025-04-02', '2025-04-16', 100, 200, 2, 1),
    ('Peinture abstraite', 'Peinture moderne abstraite réalisée par un artiste émergent, idéale pour donner du caractère à votre intérieur.', '2025-04-03', '2025-04-17', 150, 300, 3, 3),
    ('Chaise Louis XV', 'Chaise ancienne en bois de style Louis XV, avec coussin en velours bleu marine, en excellent état.', '2025-04-04', '2025-04-18', 80, 180, 4, 4),
    ('Jeu de société antique', 'Jeu de société antique avec toutes ses pièces d''origine, parfait pour les amateurs de collection.', '2025-04-05', '2025-04-19', 30, 75, 5, 5),
    ('Guitare électrique', 'Guitare électrique de marque Fender, en excellent état, avec son original et étui de transport.', '2025-04-06', '2025-04-20', 200, 450, 6, 6),
    ('Bague en diamants', 'Bague en platine avec un diamant central de 2 carats, idéale pour un cadeau de mariage ou d''anniversaire.', '2025-04-07', '2025-04-21', 500, 1200, 7, 7),
    ('Console rétro', 'Console de jeu rétro avec 50 jeux intégrés, parfaite pour les fans de rétrogaming.', '2025-04-08', '2025-04-22', 40, 90, 8, 8),
    ('Vélo de collection', 'Vélo de course vintage de collection, en parfait état, avec cadre en acier et roues en alliage léger.', '2025-04-09', '2025-04-23', 150, 300, 9, 9),
    ('Sculpture moderne', 'Sculpture en métal réalisée par un artiste contemporain, ajoutant une touche unique à tout espace.', '2025-04-10', '2025-04-24', 120, 250, 10, 10);

INSERT INTO BIDS (user_id, item_id, bid_date, bid_price)
VALUES
    (1, 1, '2025-04-01 14:30:00', 70),
    (2, 2, '2025-04-02 16:00:00', 150),
    (3, 3, '2025-04-03 09:45:00', 250),
    (4, 4, '2025-04-04 11:20:00', 120),
    (5, 5, '2025-04-05 13:15:00', 50),
    (6, 6, '2025-04-06 18:00:00', 300),
    (7, 7, '2025-04-07 20:10:00', 1000),
    (8, 8, '2025-04-08 21:00:00', 80),
    (9, 9, '2025-04-09 17:30:00', 220),
    (10, 10, '2025-04-10 19:05:00', 200);

INSERT INTO PICKUPS (item_id, address, post_code, city)
VALUES
    (1, '123 Main St', '75001', 'Paris'),
    (2, '456 Elm St', '75002', 'Paris'),
    (3, '789 Pine St', '75003', 'Paris'),
    (4, '321 Oak St', '75004', 'Paris'),
    (5, '654 Birch St', '75005', 'Paris'),
    (6, '987 Cedar St', '75006', 'Paris'),
    (7, '159 Maple St', '75007', 'Paris'),
    (8, '753 Redwood St', '75008', 'Paris'),
    (9, '123 Palm St', '75009', 'Paris'),
    (10, '456 Willow St', '75010', 'Paris');