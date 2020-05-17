DROP TABLE IF EXISTS category;
CREATE TABLE category (
  name VARCHAR(250) NOT NULL PRIMARY KEY
);
INSERT INTO category ( name ) values ('Construcción');
INSERT INTO category ( name ) values ('Reformas');
INSERT INTO category ( name ) values ('Instaladores');

DROP TABLE IF EXISTS subcategory;
CREATE TABLE subcategory (
  name VARCHAR(250) NOT NULL PRIMARY KEY,
  category_id VARCHAR(250) NOT NULL
);
INSERT INTO subcategory ( name, category_id ) values ('Construcción casas', 'Construcción');
INSERT INTO subcategory ( name, category_id ) values ('Construcción edificios', 'Construcción');
INSERT INTO subcategory ( name, category_id ) values ('Construcción piscinas', 'Construcción');
INSERT INTO subcategory ( name, category_id ) values ('Reforma baños', 'Reformas');
INSERT INTO subcategory ( name, category_id ) values ('Reforma cocinas', 'Reformas');
INSERT INTO subcategory ( name, category_id ) values ('Reforma integral', 'Reformas');
INSERT INTO subcategory ( name, category_id ) values ('Calefacción', 'Instaladores');
INSERT INTO subcategory ( name, category_id ) values ('Aire acondicionado', 'Instaladores');

DROP TABLE IF EXISTS status;
CREATE TABLE status (
  name VARCHAR(250) NOT NULL PRIMARY KEY
);
INSERT INTO status ( name ) values ('Pendiente');
INSERT INTO status ( name ) values ('Publicada');
INSERT INTO status ( name ) values ('Descartada');

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  email VARCHAR(250) NOT NULL PRIMARY KEY,
  phone VARCHAR(250) NOT NULL,
  name VARCHAR(250) NOT NULL,
  address VARCHAR(250)
);
INSERT INTO user ( name, email, phone, address ) values ('Javier', 'user1\@gmail.com', '612345671', 'Calle1');
INSERT INTO user ( name, email, phone, address ) values ('Antonio', 'user2\@hotmail.com', '612345672', 'Calle2');
INSERT INTO user ( name, email, phone, address ) values ('Alberto', 'user3\@gmail.com', '612345673', 'Calle3');

DROP TABLE IF EXISTS budget;
CREATE TABLE budget (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250),
  date_preference VARCHAR(250),
  description BLOB NOT NULL,
  category_id VARCHAR(250),
  status_id VARCHAR(250),
  user_id VARCHAR(250)
);

DROP TABLE IF EXISTS description;
CREATE TABLE description (
  id INT AUTO_INCREMENT PRIMARY KEY,
  description BLOB NOT NULL,
  subcategory VARCHAR(250) NOT NULL
);

INSERT INTO description ( description, subcategory ) values ( 'Presupuesto de colocación de 1 termo a gas-butano de 11 litros junker.', 'Calefacción');

