DROP TABLE DETALLE_SERVICIO CASCADE CONSTRAINTS;
DROP TABLE MANTENCION CASCADE CONSTRAINTS;
DROP TABLE AUTOMOVIL CASCADE CONSTRAINTS;
DROP TABLE MECANICO CASCADE CONSTRAINTS;
DROP TABLE SUCURSAL CASCADE CONSTRAINTS;
DROP TABLE PREMIUM CASCADE CONSTRAINTS;
DROP TABLE ESTANDAR CASCADE CONSTRAINTS;
DROP TABLE MODELO CASCADE CONSTRAINTS;
DROP TABLE CIUDAD CASCADE CONSTRAINTS;
DROP TABLE SERVICIO CASCADE CONSTRAINTS;
DROP TABLE CLIENTE CASCADE CONSTRAINTS;
DROP TABLE TIPO_AUTOMOVIL CASCADE CONSTRAINTS;
DROP TABLE MARCA CASCADE CONSTRAINTS;
DROP TABLE PAIS CASCADE CONSTRAINTS;

DROP SEQUENCE seq_servicio;
DROP SEQUENCE seq_ciudad;

CREATE TABLE PAIS (
    cod_pais NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 9 INCREMENT BY 3),
    nom_pais VARCHAR2(30),
    CONSTRAINT PAIS_PK PRIMARY KEY (cod_pais)
);

CREATE TABLE MARCA (
    cod_marca NUMBER(5),
    descripcion VARCHAR2(20),
    CONSTRAINT MARCA_PK PRIMARY KEY (cod_marca)
);

CREATE TABLE TIPO_AUTOMOVIL (
    cod_tipo CHAR(3),
    descripcion VARCHAR2(20),
    CONSTRAINT TIPO_AUTOMOVIL_PK PRIMARY KEY (cod_tipo)
);

CREATE TABLE CLIENTE (
    cl_rut NUMBER(8),
    dv CHAR(1),
    pnombre VARCHAR(20),
    snombre VARCHAR(20),
    apaterno VARCHAR(20),
    amaterno VARCHAR(20),
    telefono VARCHAR(12),
    email VARCHAR(40),
    tipo_cli CHAR(1),
    CONSTRAINT CLIENTE_PK PRIMARY KEY (cl_rut)
);

CREATE TABLE SERVICIO (
    id_servicio NUMBER(3) GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
    descripcion VARCHAR2(100) NOT NULL,
    costo NUMBER(7) NOT NULL,
    CONSTRAINT SERVICIO_PK PRIMARY KEY (id_servicio)
);



CREATE TABLE CIUDAD (
    cod_ciudad NUMBER(3),
    nom_ciudad VARCHAR2(30),
    cod_pais NUMBER, 
    CONSTRAINT CIUDAD_PK PRIMARY KEY(cod_ciudad),
    CONSTRAINT CIUDAD_FK_PAIS FOREIGN KEY (cod_pais) REFERENCES PAIS (cod_pais)
);

CREATE TABLE MODELO (
    cod_modelo NUMBER(5),
    cod_marca NUMBER(5),
    descripcion VARCHAR2(20),
    CONSTRAINT MODELO_PK PRIMARY KEY (cod_modelo, cod_marca),
    CONSTRAINT MODELO_KF_MARCA FOREIGN KEY (cod_marca) REFERENCES MARCA (cod_marca)
);

CREATE TABLE ESTANDAR (
    cl_rut NUMBER(8),
    puntaje_fidelidad NUMBER(10),
    CONSTRAINT ESTANDAR_PK PRIMARY KEY (cl_rut),
    CONSTRAINT ESTANDAR_FK_CLIENTE FOREIGN KEY (cl_rut) REFERENCES CLIENTE (cl_rut)
);

CREATE TABLE PREMIUM (
    cl_rut NUMBER(8),
    pesos_clientes NUMBER(10),
    monto_credito NUMBER(10),
    CONSTRAINT PREMIUM_PK PRIMARY KEY (cl_rut),
    CONSTRAINT PREMIUM_FK_CLIENTE FOREIGN KEY (cl_rut) REFERENCES CLIENTE (cl_rut)
);



CREATE TABLE SUCURSAL (
    cod_sucursal CHAR(3),
    nom_sucursal VARCHAR2(20),
    calle VARCHAR2(20),
    num_calle NUMBER(4),
    cod_ciudad NUMBER(3),
    CONSTRAINT SUCURSAL_PK PRIMARY KEY (cod_sucursal),
    CONSTRAINT SUCURSAL_FK_CIUDAD FOREIGN KEY (cod_ciudad) REFERENCES CIUDAD (cod_ciudad)
);

CREATE TABLE MECANICO(
    cod_mecanico NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 460 INCREMENT BY 7),
    pnombre VARCHAR(20),
    snombre VARCHAR(20),
    apaterno VARCHAR(20),
    amaterno VARCHAR(20),
    bono_jefatura NUMBER(10),
    sueldo NUMBER(10),
    monto_impuestos NUMBER(10),
    cod_supervisor NUMBER,
    CONSTRAINT MECANICO_PK PRIMARY KEY (cod_mecanico),
    CONSTRAINT MECANICO_FK FOREIGN KEY (cod_supervisor) REFERENCES MECANICO (cod_mecanico)
);

CREATE TABLE AUTOMOVIL (
    patente CHAR(8),
    annio NUMBER(4),
    cant_puertas NUMBER(1),
    km NUMBER(6),
    color VARCHAR2(30),
    cod_tipo_auto CHAR(3),
    cod_modelo NUMBER(5),
    cod_marca NUMBER(5), 
    cl_rut NUMBER(8),
    CONSTRAINT AUTOMOVIL_PK PRIMARY KEY (patente),
    CONSTRAINT MANT_FK_CLIENTE FOREIGN KEY (cl_rut) REFERENCES CLIENTE(cl_rut),
    CONSTRAINT MANT_FK_MODELO FOREIGN KEY (cod_modelo,cod_marca) REFERENCES MODELO(cod_modelo,cod_marca),
    CONSTRAINT MANT_FK_TIPO FOREIGN KEY (cod_tipo_auto) REFERENCES TIPO_AUTOMOVIL(cod_tipo)
);



CREATE TABLE MANTENCION (
    num_mantencion NUMBER GENERATED ALWAYS AS IDENTITY (START WITH 101 INCREMENT BY 1),
    cod_sucursal CHAR(3),
    fecha_ingreso DATE,
    fecha_salida DATE,
    patente_auto CHAR(8),
    cod_mecanico NUMBER, 
    costo_total NUMBER(7),
    estado VARCHAR2(15),
    CONSTRAINT MANTENCION_PK PRIMARY KEY (num_mantencion),
    CONSTRAINT MANT_FK_AUTOMOVIL FOREIGN KEY (patente_auto) REFERENCES AUTOMOVIL(patente),
    CONSTRAINT MANT_FK_MECANICO FOREIGN KEY (cod_mecanico) REFERENCES MECANICO(cod_mecanico),
    CONSTRAINT MANT_FK_SUCURSAL FOREIGN KEY (cod_sucursal) REFERENCES SUCURSAL(cod_sucursal)
);

CREATE TABLE DETALLE_SERVICIO (
    mantencion_num NUMBER(4),
    cod_servicio NUMBER(3),
    descuento_serv NUMBER(4,3),
    cantidad NUMBER(3),
    CONSTRAINT DETALLE_SERVICIO_PK PRIMARY KEY (mantencion_num, cod_servicio),
    CONSTRAINT DET_SERV_FK_MANTENCION FOREIGN KEY (mantencion_num) REFERENCES MANTENCION(num_mantencion),
    CONSTRAINT DET_SERV_FK_SERVICIO FOREIGN KEY (cod_servicio) REFERENCES SERVICIO(id_servicio)
);


ALTER TABLE MANTENCION DROP COLUMN costo_total;



ALTER TABLE DETALLE_SERVICIO DROP CONSTRAINT DET_SERV_FK_MANTENCION;
ALTER TABLE MANTENCION DROP CONSTRAINT MANTENCION_PK;
ALTER TABLE MANTENCION ADD CONSTRAINT MANTENCION_PK PRIMARY KEY (num_mantencion, cod_sucursal);
ALTER TABLE DETALLE_SERVICIO ADD cod_sucursal CHAR(3);
ALTER TABLE DETALLE_SERVICIO ADD CONSTRAINT DET_SERV_FK_MANTENCION FOREIGN KEY (mantencion_num, cod_sucursal) REFERENCES MANTENCION(num_mantencion, cod_sucursal);
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_EMAIL_UK UNIQUE (email);
ALTER TABLE CLIENTE ADD CONSTRAINT CLIENTE_DV_CHK CHECK (dv IN ('0','1','2','3','4','5','6','7','8','9','K'));
ALTER TABLE MECANICO ADD CONSTRAINT MECANICO_SUELDO_CHK CHECK (sueldo >= 510000);
ALTER TABLE MANTENCION ADD CONSTRAINT MANTENCION_ESTADO_CHK CHECK (estado IN ('Reserva', 'Ingresado', 'Entregado', 'Anulado'));
ALTER TABLE SERVICIO MODIFY id_servicio DROP IDENTITY;
CREATE SEQUENCE seq_servicio START WITH 400 INCREMENT BY 2;
CREATE SEQUENCE seq_ciudad START WITH 165 INCREMENT BY 5;

INSERT INTO PAIS (nom_pais) VALUES ('Chile');
INSERT INTO PAIS (nom_pais) VALUES ('Peru');
INSERT INTO PAIS (nom_pais) VALUES ('Colombia');

INSERT INTO SERVICIO (id_servicio, descripcion, costo) VALUES (seq_servicio.NEXTVAL, 'Cambio Luces', 45000);
INSERT INTO SERVICIO (id_servicio, descripcion, costo) VALUES (seq_servicio.NEXTVAL, 'Desabolladura', 67000);
INSERT INTO SERVICIO (id_servicio, descripcion, costo) VALUES (seq_servicio.NEXTVAL, 'Revisión Frenos', 30000);
INSERT INTO SERVICIO (id_servicio, descripcion, costo) VALUES (seq_servicio.NEXTVAL, 'Cambio Puerta Trasera', 50000);

INSERT INTO CIUDAD (cod_ciudad, nom_ciudad, cod_pais) VALUES (seq_ciudad.NEXTVAL, 'Santiago',9);
INSERT INTO CIUDAD (cod_ciudad, nom_ciudad, cod_pais) VALUES (seq_ciudad.NEXTVAL, 'Lima',12);
INSERT INTO CIUDAD (cod_ciudad, nom_ciudad, cod_pais) VALUES (seq_ciudad.NEXTVAL, 'Bogotá',15);

INSERT INTO SUCURSAL (cod_sucursal, nom_sucursal,calle,num_calle,cod_ciudad) VALUES ('S01', 'Providencia', 'Av. A. Varas',234,165);
INSERT INTO SUCURSAL (cod_sucursal, nom_sucursal,calle,num_calle,cod_ciudad) VALUES ('S02', 'Las 4 esquinas', 'Av. Latina',669,170);
INSERT INTO SUCURSAL (cod_sucursal, nom_sucursal,calle,num_calle,cod_ciudad) VALUES ('S03', 'El Cafetero', 'Av. El Faro',900,175);

INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Jorge','Pablo','Soto','Sierpe',5400000,2759000,223580,NULL);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Pedro','Jose',';Manriquez','Corral',null,759000,23980,NULL);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Sandra','Josefa','Letelier','S.',0,659000,23980,460);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Felipe','M.','Vidal','A.',null,759000,23580,460);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Jose','Miguel','Troncoso','B.',null,659000,44580,474);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Juan','Pablo','Sánchez','R.',null,859000,23380,474);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Carlos','Felipe','Soto','J.',0,597000,23580,474);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Alberto','P.','Cerda','Ramírez',null,559000,22380,460);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Alejandra','Gabriela','Infanti','R.',null,659000,22380,460);
INSERT INTO MECANICO (pnombre,snombre,apaterno,amaterno,bono_jefatura,sueldo,monto_impuestos,cod_supervisor) VALUES ('Roberto','Patricio','Gutierrez','Sosa',null,859000,22380,460);

INSERT INTO MANTENCION (cod_sucursal,fecha_ingreso,fecha_salida,patente_auto,cod_mecanico,estado)
VALUES ('S01', DATE '2023-04-12', null, null, 481, 'Reserva');

INSERT INTO MANTENCION (cod_sucursal,fecha_ingreso,fecha_salida,patente_auto,cod_mecanico,estado)
VALUES ('S02', DATE '2023-02-21', DATE '2023-02-21', null, 502, 'Entregado');

INSERT INTO MANTENCION (cod_sucursal,fecha_ingreso,fecha_salida,patente_auto,cod_mecanico,estado)
VALUES ('S02', DATE '2023-10-09', null, null, 502, 'Anulado');

INSERT INTO MANTENCION (cod_sucursal,fecha_ingreso,fecha_salida,patente_auto,cod_mecanico,estado)
VALUES ('S03', DATE '2023-08-11', DATE '2023-08-18', null, 509, 'Entregado');

INSERT INTO MANTENCION (cod_sucursal,fecha_ingreso,fecha_salida,patente_auto,cod_mecanico,estado)
VALUES ('S03', DATE '2023-12-03', null, null, 509, 'Ingresado');

SELECT 
    cod_mecanico AS "ID MECANICO",
    pnombre || ' ' || apaterno AS "NOMBRE MECANICO",
    sueldo AS "SALARIO",
    monto_impuestos AS "IMPUESTO ACTUAL",
    monto_impuestos * 0.8 AS "IMPUESTO REBAJADO",
    sueldo - (monto_impuestos * 0.8) AS "SUELDO CON REBAJA IMPUESTOS"
FROM 
    MECANICO
WHERE 
    bono_jefatura IS NULL 
    AND monto_impuestos < 40000
ORDER BY 
    monto_impuestos DESC, 
    apaterno ASC;
