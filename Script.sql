DROP DATABASE IF EXISTS SalonCaprichos;
CREATE DATABASE SalonCaprichos;
USE SalonCaprichos;

CREATE TABLE Cliente (
  id_cliente     char(6),
  nombre         varchar(25) not null,
  apellido       varchar(50),
  telefono       integer,
  
  constraint pk_cliente primary key (id_cliente)
);

CREATE TABLE Servivio (
  id_servicio     char(6),
  descripcion     varchar(50),
  
  constraint pk_servicio primary key (id_servicio)
);

CREATE TABLA Hisorial (
  id_cliente      char(6),
  id_servicio     char(6),

  constraint pk_historial primary key (id_cliente, id_servicio)
);
