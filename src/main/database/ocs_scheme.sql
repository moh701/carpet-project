-- \begin{lstlisting}[language=SQL,
-- keywordstyle=\color{blue},
-- stringstyle=\color{mauve},
-- showstringspaces=false,
-- basicstyle=\ttfamily\footnotesize]

-- Database Creation
-- CREATE DATABASE Carpet_storedb OWNER store_ow ENCODING = 'UTF8';

-- Create new Schema
DROP SCHEMA IF EXISTS Carpet CASCADE;
CREATE SCHEMA Carpet;

-- Create new domains
CREATE DOMAIN Carpet.passwd AS VARCHAR(254)
    CONSTRAINT properpassword CHECK (((VALUE)::text~* '[A-Za-z0-9._%!]{8,}'::text));

CREATE DOMAIN Carpet.emailaddress AS VARCHAR(254)
    CONSTRAINT properemail CHECK (((VALUE)::text~* '^[A-Za-z0-9._%]+@[A-Za-z0-9.]+[.][A-Za-z]+$'::text));


-- Cerate a sequence and function for generating unique serial numbers
CREATE SEQUENCE Carpet.serialnumber_seq;

CREATE FUNCTION carpet.generate_new_serialno() RETURNS text AS $$
DECLARE
new_serial text;
BEGIN
	new_serial
:= concat('CS' ,extract(year from current_date) - 2000 , 'P', lpad(nextval('Carpet.serialnumber_seq')::text, 7, '0'));
RETURN new_serial;
END; $$
IMMUTABLE
LANGUAGE PLPGSQL;



-- Create new data types
CREATE TYPE Carpet.material AS ENUM (
    'Woolen',
    'Fiber',
    'Linen',
    'Silk'
);


CREATE TYPE Carpet.quality AS ENUM (
    'Low',
    'Medium',
    'High'
);

CREATE TYPE Carpet.categary AS ENUM (
    'Artificial',
    'Handicrafts',
    'Other'
);

CREATE TYPE Carpet.bargain_result AS ENUM (
    'Accepted',
    'Rejected'
);


-- Table Creation-----------
CREATE TABLE Carpet.producer
(
    producer_id   SERIAL PRIMARY KEY,
    name    VARCHAR(32)         NOT NULL,
    surname    VARCHAR(32)         NOT NULL,
    passwd        Carpet.passwd       NOT NULL,
    --CONSTRAINT properemail CHECK (((VALUE)::text~* '^[A-Za-z0-9._%]+@[A-Za-z0-9.]+[.][A-Za-z]+$'::text)),
    email_address Carpet.emailaddress NOT NULL UNIQUE, --Added UNIQUE constraint
    mobile_number CHAR(13)            NOT NULL CHECK (mobile_number ~ '^[0-9\+]*$'
) ,
    bank_account  CHAR(16) NOT NULL CHECK(bank_account ~ '^[0-9]*$'),
    address TEXT,
    brand VARCHAR(32)
);

CREATE TABLE Carpet.producer_reffer
(
    referer_Id INTEGER,
    refered_Id INTEGER,
    PRIMARY KEY (referer_Id),
    FOREIGN KEY (referer_Id) REFERENCES Carpet.producer (producer_id),
    FOREIGN KEY (refered_Id) REFERENCES Carpet.producer (producer_id)
);

CREATE TABLE Carpet.admin
(
    admin_id SERIAL PRIMARY KEY,
    email  VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL

);


CREATE TABLE Carpet.product
(
    product_id   SERIAL PRIMARY KEY,
    producer_id  INTEGER,
    product_name VARCHAR(32)  NOT NULL,
    picture      VARCHAR(500) NOT NULL,
    quantity     SMALLINT     NOT NULL CHECK (quantity > 0),
    no_color     INTEGER      NOT NULL CHECK (no_color > 0),
    dimension    VARCHAR(32)  NOT NULL CHECK (dimension ~ '^[0-9 \*]*$'
) ,
    material Carpet.material NOT NULL,
    price NUMERIC(8 ,2) NOT NULL CHECK(price > 0),
    category Carpet.categary NOT NULL,
    quality Carpet.quality,
    FOREIGN KEY (producer_id) REFERENCES Carpet.producer(producer_id)
);


CREATE TABLE Carpet.object
(
    serialnumber TEXT GENERATED ALWAYS AS (carpet.generate_new_serialno()) STORED,
    product_id   INTEGER,
    PRIMARY KEY (serialnumber),
    FOREIGN KEY (product_id) REFERENCES Carpet.product (product_id)
);


CREATE TABLE Carpet.customer
(
    customer_id   SERIAL PRIMARY KEY,
    name    VARCHAR(32)         NOT NULL,
    surname     VARCHAR(32)         NOT NULL,
    passwd        Carpet.passwd       NOT NULL,
    -- CONSTRAINT properemail CHECK (((VALUE)::text~* '^[A-Za-z0-9._%]+@[A-Za-z0-9.]+[.][A-Za-z]+$'::text)),
    email_address Carpet.emailaddress NOT NULL UNIQUE, --Added UNIQUE constraint
    mobile_number CHAR(13)            NOT NULL CHECK (mobile_number ~ '^[0-9\+]*$'
) ,
    bank_account  CHAR(16) CHECK(bank_account ~ '^[0-9]*$'),
	address  TEXT
);

CREATE TABLE Carpet.invoice
(
    invoice_id  SERIAL PRIMARY KEY,
    customer_id INTEGER   NOT NULL,
    serialno    TEXT      NOT NULL,
    datetime    TIMESTAMP NOT NULL,
    FOREIGN KEY (serialno) REFERENCES Carpet.object (serialnumber),
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id)
);

CREATE TABLE Carpet.return_reciept
(
    return_reciept_id SERIAL PRIMARY KEY,
    customer_id       INTEGER   NOT NULL,
    serialno          TEXT      NOT NULL,
    datetime          TIMESTAMP NOT NULL,
    reason            TEXT,
    FOREIGN KEY (serialno) REFERENCES Carpet.object (serialnumber),
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id)
);

CREATE TABLE Carpet.bargain_history
(
    bargain_id     SERIAL PRIMARY KEY,
    customer_id    INTEGER       NOT NULL,
    product_id     INTEGER       NOT NULL,
    price          NUMERIC(8, 2) NOT NULL CHECK (price > 0),
    datetime       TIMESTAMP     NOT NULL,
    bargain_result Carpet.bargain_result NULL,
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id),
    FOREIGN KEY (product_id) REFERENCES Carpet.product (product_id)
);

CREATE TABLE Carpet.customer_reffer
(
    referer_id INTEGER,
    refered_id INTEGER,
    PRIMARY KEY (referer_id),
    FOREIGN KEY (referer_id) REFERENCES Carpet.customer (customer_id),
    FOREIGN KEY (refered_id) REFERENCES Carpet.customer (customer_id)
);

CREATE TABLE Carpet.comment
(
    comment_id      SERIAL PRIMARY KEY,
    customer_id     INTEGER      NOT NULL,
    product_id      INTEGER      NOT NULL,
    comment_content VARCHAR(300) NOT NULL,
    reply_No        SMALLINT DEFAULT 0,
    like_No         SMALLINT DEFAULT 0,
    dislike_No      SMALLINT DEFAULT 0,
    datetime        TIMESTAMP    NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id),
    FOREIGN KEY (product_id) REFERENCES Carpet.product (product_id)
);


CREATE TABLE Carpet.reply
(
    reply_id   INTEGER PRIMARY KEY,
    replied_id INTEGER NOT NULL,
    FOREIGN KEY (reply_id) REFERENCES Carpet.comment (comment_id),
    FOREIGN KEY (replied_id) REFERENCES Carpet.comment (comment_id)
);


CREATE TABLE Carpet.like_dislike
(
    comment_id  INTEGER,
    customer_id INTEGER,
    interest    BOOLEAN NOT NULL,
    PRIMARY KEY (comment_id, customer_id),
    FOREIGN KEY (comment_id) REFERENCES Carpet.comment (comment_id),
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id)
);

CREATE TABLE Carpet.rate
(
    product_id  INTEGER,
    customer_id INTEGER,
    grade       INTEGER NOT NULL CHECK (grade > 0 AND grade <= 5),
    PRIMARY KEY (product_id, customer_id),
    FOREIGN KEY (product_id) REFERENCES Carpet.product (product_id),
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id)
);

CREATE TABLE Carpet.chat_history
(
    chat_id         SERIAL PRIMARY KEY,
    producer_id     INTEGER   NOT NULL,
    customer_id     INTEGER   NOT NULL,
    datetime        TIMESTAMP NOT NULL,
    message_content TEXT      NOT NULL,
    FOREIGN KEY (producer_id) REFERENCES Carpet.producer (producer_id),
    FOREIGN KEY (customer_id) REFERENCES Carpet.customer (customer_id)
);