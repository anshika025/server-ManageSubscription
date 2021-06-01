
CREATE TABLE mindbowser.signUpTable
(
  id serial NOT NULL,
  firstName character varying(100),
  lastName character varying(100),
  companyName character varying(100),
  address character varying(100),
  emailId character varying(100) primary key,
  password text,
  salt character varying(100),
  birthDate date
  );