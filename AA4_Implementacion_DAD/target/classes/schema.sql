CREATE TABLE persona (
  id VARCHAR(20) PRIMARY KEY,
  nombre VARCHAR(100),
  direccion VARCHAR(100),
  telefono VARCHAR(20)
);

CREATE TABLE cliente (
  id VARCHAR(20) PRIMARY KEY,
  mail VARCHAR(100),
  CONSTRAINT fk_cliente_persona FOREIGN KEY (id) REFERENCES persona(id)
);

CREATE TABLE empleado (
  id VARCHAR(20) PRIMARY KEY,
  cargo VARCHAR(50),
  CONSTRAINT fk_empleado_persona FOREIGN KEY (id) REFERENCES persona(id)
);

CREATE TABLE prenda (
  ref VARCHAR(20) PRIMARY KEY,
  color VARCHAR(50),
  marca VARCHAR(50),
  talla VARCHAR(10),
  valor DOUBLE,
  tipo VARCHAR(20)
);

CREATE TABLE servicio_alquiler (
  numero INT PRIMARY KEY,
  fechaSolic DATE,
  fechaAlqui DATE,
  cliente_id VARCHAR(20),
  empleado_id VARCHAR(20)
);

CREATE TABLE servicio_prenda (
  numero INT,
  ref VARCHAR(20)
);