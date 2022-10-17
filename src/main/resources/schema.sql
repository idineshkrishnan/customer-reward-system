CREATE TABLE customers (
  id    BIGINT AUTO_INCREMENT PRIMARY KEY,
  name  VARCHAR(25) NOT NULL);


CREATE TABLE purchases (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  descriptions  VARCHAR(256) NOT NULL,
  purchase_date  DATE NOT NULL,
  amount        DOUBLE PRECISION NOT NULL,
  customer_id    BIGINT NOT NULL);