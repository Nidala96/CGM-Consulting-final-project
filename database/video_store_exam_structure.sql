CREATE DATABASE video_store_exam_master;
use video_store_exam_master;

CREATE TABLE customer (
  customer_id bigint NOT NULL AUTO_INCREMENT,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  email varchar(100) NOT NULL,
  PRIMARY KEY (customer_id),
  UNIQUE KEY uk_email (email)
);


CREATE TABLE staff (
  staff_id bigint NOT NULL AUTO_INCREMENT,
  firstname varchar(50) NOT NULL,
  lastname varchar(50) NOT NULL,
  dob date DEFAULT NULL,
  PRIMARY KEY (staff_id)
);


CREATE TABLE role (
  role_id bigint NOT NULL AUTO_INCREMENT,
  role_name varchar(30) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE KEY uk_role (role_name)
);

CREATE TABLE language (
  language_id bigint NOT NULL AUTO_INCREMENT,
  language_name varchar(20) NOT NULL,
  PRIMARY KEY (language_id),
  UNIQUE KEY uk_language (language_name)
);

CREATE TABLE genre (
  genre_id bigint NOT NULL AUTO_INCREMENT,
  genre_name varchar(30) NOT NULL,
  PRIMARY KEY (genre_id),
  UNIQUE KEY uk_genre (genre_name)
);

CREATE TABLE film (
  film_id bigint NOT NULL AUTO_INCREMENT,
  title varchar(100) NOT NULL,
  description varchar(255) NOT NULL,
  release_year smallint NOT NULL,
  language_id bigint NOT NULL,
  genre_id bigint NOT NULL,
  PRIMARY KEY (film_id),
  KEY fk_genre (genre_id),
  KEY fk_language (language_id),
  CONSTRAINT fk_genre FOREIGN KEY (genre_id) REFERENCES genre (genre_id) ,
  CONSTRAINT fk_language FOREIGN KEY (language_id) REFERENCES language (language_id)
);


CREATE TABLE film_staff (
  film_id bigint NOT NULL,
  staff_id bigint NOT NULL,
  role_id bigint NOT NULL,
  PRIMARY KEY (film_id,staff_id,role_id),
  KEY fk2_staff (staff_id),
  KEY fk_3_role (role_id),
  CONSTRAINT fk1_film FOREIGN KEY (film_id) REFERENCES film (film_id) ,
  CONSTRAINT fk2_staff FOREIGN KEY (staff_id) REFERENCES staff (staff_id) ,
  CONSTRAINT fk_3_role FOREIGN KEY (role_id) REFERENCES role (role_id) 
);

CREATE TABLE store (
  store_id bigint NOT NULL AUTO_INCREMENT,
  store_name varchar(60) NOT NULL,
  PRIMARY KEY (store_id),
  UNIQUE KEY UK_store (store_name)
);

CREATE TABLE inventory (
  inventory_id bigint NOT NULL AUTO_INCREMENT,
  store_id bigint NOT NULL,
  film_id bigint NOT NULL,
  PRIMARY KEY (inventory_id),
  KEY fk5_film (film_id),
  KEY fk6_store (store_id),
  CONSTRAINT fk5_film FOREIGN KEY (film_id) REFERENCES film (film_id) ,
  CONSTRAINT fk6_store FOREIGN KEY (store_id) REFERENCES store (store_id) 
);

CREATE TABLE rental (
  customer_id bigint NOT NULL,
  inventory_id bigint NOT NULL,
  rental_date datetime NOT NULL,
  rental_return datetime DEFAULT NULL,
  PRIMARY KEY (customer_id,inventory_id,rental_date),
  KEY fk11_inventory (inventory_id),
  CONSTRAINT fk10_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ,
  CONSTRAINT fk11_inventory FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id) 
);

