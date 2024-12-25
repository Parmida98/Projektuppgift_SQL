CREATE TABLE IF NOT EXISTS work_role (
                           role_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                           title VARCHAR(50) NOT NULL,
                           description VARCHAR(50) NOT NULL,
                           salary DOUBLE NOT NULL,
                           creation_date DATE NOT NULL
);


CREATE TABLE IF NOT EXISTS employee (
                          employee_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          email VARCHAR(70) NOT NULL UNIQUE,
                          password VARCHAR(20) NOT NULL,
                          role_id INT NOT NULL,
                          FOREIGN KEY (role_id) REFERENCES work_role(role_id)
);

SELECT * FROM INFORMATION_SCHEMA.TABLES;
SET SCHEMA PUBLIC;
GRANT ALL ON work_role TO PUBLIC;
GRANT ALL ON employee TO PUBLIC;
