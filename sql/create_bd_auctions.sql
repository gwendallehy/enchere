-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--
USE Auctions;
GO

DROP TABLE IF EXISTS BIDS;
DROP TABLE IF EXISTS PICKUPS;
DROP TABLE IF EXISTS ITEMS_SOLD;
DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS ROLES;

CREATE TABLE [ROLES](
                        [ROLE] [NVARCHAR](50) NOT NULL,
                        [IS_ADMIN] int NOT NULL ,
                        PRIMARY KEY ([ROLE],[IS_ADMIN]));


INSERT INTO [ROLES] ([ROLE],[IS_ADMIN]) VALUES ('ROLE_USER',0);
INSERT INTO [ROLES] ([ROLE],[IS_ADMIN]) VALUES ('ROLE_ADMIN',1);

CREATE TABLE CATEGORIES (
    category_id   INTEGER IDENTITY(1,1) NOT NULL,
    category_desc        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (category_id)

CREATE TABLE BIDS (
    user_id   INTEGER NOT NULL,
    item_id       INTEGER NOT NULL,
    bid_date     datetime NOT NULL,
	bid_price  INTEGER NOT NULL

)

ALTER TABLE BIDS ADD constraint bid_pk PRIMARY KEY (user_id, item_id)

CREATE TABLE PICKUPS (
	item_id         INTEGER NOT NULL,
    address              VARCHAR(60) NOT NULL,
    post_code      VARCHAR(15) NOT NULL,
    city            VARCHAR(30) NOT NULL
)

ALTER TABLE PICKUPS ADD constraint pickup_pk PRIMARY KEY  (item_id)

CREATE TABLE USERS (
    user_id   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL,
    lastname             VARCHAR(30) NOT NULL,
    firstname           VARCHAR(30) NOT NULL,
    email            VARCHAR(50) NOT NULL,
    phone_nb        VARCHAR(15),
    address              VARCHAR(30) NOT NULL,
    post_code      VARCHAR(10) NOT NULL,
    city            VARCHAR(30) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    credit           INTEGER NOT NULL,
    administrator   bit NOT NULL
)

ALTER TABLE USERS ADD constraint user_pk PRIMARY KEY (user_id)


CREATE TABLE ITEMS_SOLD (
    item_id                    INTEGER IDENTITY(1,1) NOT NULL,
    item_name                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	auction_date_begin           DATE NOT NULL,
    auction_date_end             DATE NOT NULL,
    price_init                  INTEGER,
    price_selling                    INTEGER,
    user_id               INTEGER NOT NULL,
    category_id                INTEGER NOT NULL,
    picture_url                 VARCHAR(250)
)

ALTER TABLE ITEMS_SOLD ADD constraint items_sold_pk PRIMARY KEY (item_id)

ALTER TABLE ITEMS_SOLD
    ADD CONSTRAINT user_auction_fk FOREIGN KEY ( user_id ) REFERENCES USERS ( user_id )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE BIDS
    ADD CONSTRAINT	bids_items_fk FOREIGN KEY ( item_id )
        REFERENCES ITEMS_SOLD ( item_id )
ON DELETE CASCADE 
    ON UPDATE no action 

ALTER TABLE PICKUPS
    ADD CONSTRAINT pickup_items_sold_fk FOREIGN KEY ( item_id )
        REFERENCES ITEMS_SOLD ( item_id )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ITEMS_SOLD
    ADD CONSTRAINT categories_items_sold_fk FOREIGN KEY ( category_id )
        REFERENCES categories ( category_id )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ITEMS_SOLD
    ADD CONSTRAINT user_sold_fk FOREIGN KEY ( user_id )
        REFERENCES users ( user_id )
ON DELETE NO ACTION 
    ON UPDATE no action

ALTER TABLE ITEMS_SOLD
    ADD status VARCHAR(50);

-- Met à jour les 3 premières lignes avec 'NC'
UPDATE TOP (3) ITEMS_SOLD
SET status = 'NC'
WHERE status IS NULL;

-- Met à jour les 3 suivantes avec 'EC'
UPDATE TOP (3) ITEMS_SOLD
SET status = 'EC'
WHERE status IS NULL;

-- Met à jour les 4 suivantes avec 'TR'
UPDATE TOP (4) ITEMS_SOLD
SET status = 'TR'
WHERE status IS NULL;



