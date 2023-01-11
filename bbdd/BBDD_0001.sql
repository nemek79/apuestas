#==========================================
# INSTALACION DE BASE DE DATOS DE APUESTAS
#------------------------------------------
# VERSION:  0_0_1
#==========================================

USE apuestasbd;

#-------------------------------------------
# TABLA MAESTRA DE TIPSTERS
#-------------------------------------------

CREATE TABLE t_tipsters
(
	id			INT UNSIGNED	PRIMARY KEY,
	descripcion	VARCHAR(64)		NOT NULL,
	comentario	VARCHAR(256)
	

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (1,'David',null);

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (2,'Apuestas JC',null);

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (3,'KinFutbol',null);

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (4,'KinNBA',null);

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (5,'Lobo',null);

INSERT INTO t_tipsters (id, descripcion, comentario)
VALUES (6,'Pensador Apuestas',null);

#-------------------------------------------
# TABLA MAESTRA DE TIPOS DE APUESTA
#-------------------------------------------

CREATE TABLE t_tipos_apuesta
(
	id			INT UNSIGNED	PRIMARY KEY,
	descripcion	VARCHAR(64)		NOT NULL,
	comentario	VARCHAR(256)
	

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_tipos_apuesta (id, descripcion, comentario)
VALUES (1,'Free',null);

INSERT INTO t_tipos_apuesta (id, descripcion, comentario)
VALUES (2,'Premium',null);

INSERT INTO t_tipos_apuesta (id, descripcion, comentario)
VALUES (99,'No válida',null);


#-------------------------------------------
# TABLA MAESTRA DE DEPORTES
#-------------------------------------------

CREATE TABLE t_deportes
(
	id			INT UNSIGNED	PRIMARY KEY,
	descripcion	VARCHAR(64)		NOT NULL,
	comentario	VARCHAR(256)
	

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_deportes (id, descripcion, comentario)
VALUES (1,'Fútbol',null);

INSERT INTO t_deportes (id, descripcion, comentario)
VALUES (2,'Baloncesto',null);

INSERT INTO t_deportes (id, descripcion, comentario)
VALUES (3,'Rugby',null);

INSERT INTO t_deportes (id, descripcion, comentario)
VALUES (99,'Desconocido',null);

#-------------------------------------------
# TABLA MAESTRA DE ESTADOS DE APUESTA
#-------------------------------------------

CREATE TABLE t_estados_apuesta
(
	id			INT UNSIGNED	PRIMARY KEY,
	descripcion	VARCHAR(64)		NOT NULL,
	comentario	VARCHAR(256)
	

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO t_estados_apuesta (id, descripcion, comentario)
VALUES (1,'Pendiente',null);

INSERT INTO t_estados_apuesta (id, descripcion, comentario)
VALUES (2,'Ganada',null);

INSERT INTO t_estados_apuesta (id, descripcion, comentario)
VALUES (3,'Perdida',null);

INSERT INTO t_estados_apuesta (id, descripcion, comentario)
VALUES (4,'Push',null);

INSERT INTO t_estados_apuesta (id, descripcion, comentario)
VALUES (99,'Cancelada',null);

#-------------------------------------------
# TABLA DE APUESTAS
#-------------------------------------------


CREATE TABLE t_apuestas
(
	id					INT	UNSIGNED	AUTO_INCREMENT	PRIMARY KEY,
	fecha_alta			DATE			NOT NULL,
	fecha_evento		DATE			NOT NULL,
	tipster_id			INT UNSIGNED	NOT NULL,
	tipo_id				INT UNSIGNED	NOT NULL,
	live				TINYINT(1)		NOT NULL,
	reto				TINYINT(1)		NOT NULL,
	deporte_id			INT UNSIGNED	NOT NULL,
	evento				VARCHAR(64)		NOT NULL,
	apuesta				VARCHAR(64)		NOT NULL,
	stake				DECIMAL(3,1)	NOT NULL,
	cuota				DECIMAL(8,3)	NOT NULL,
	cantidad_apostada	DECIMAL(8,3)	NOT NULL,
	bruto				DECIMAL(8,3)	NOT NULL,
	estado_id			INT UNSIGNED	NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE t_apuestas
	ADD CONSTRAINT fk_apuestas_tipster
	FOREIGN KEY (tipster_id)
		REFERENCES t_tipsters(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION;
		
ALTER TABLE t_apuestas
	ADD CONSTRAINT fk_apuestas_tipo
	FOREIGN KEY (tipo_id)
		REFERENCES t_tipos_apuesta(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION;
		
ALTER TABLE t_apuestas
	ADD CONSTRAINT fk_apuestas_deporte
	FOREIGN KEY (deporte_id)
		REFERENCES t_deportes(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION;
		
ALTER TABLE t_apuestas
	ADD CONSTRAINT fk_apuestas_estado
	FOREIGN KEY (estado_id)
		REFERENCES t_estados_apuesta(id)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION;

#-------------------------------------------
# TABLA DE ESTADISTICAS POR DIA
#-------------------------------------------

CREATE TABLE t_estadisticas_dia
(
	fecha				DATE			NOT NULL PRIMARY KEY,
	cantidad_apostada   DECIMAL(8,3)	NOT NULL,
	ganancia_bruta		DECIMAL(8,3)	NOT NULL,
	ganancia_neta		DECIMAL(8,3)	NOT NULL,
	yield				DECIMAL(5,2)	NOT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#-------------------------------------------
# TABLA DE ESTADISTICAS POR TIPSTER / DIA
#-------------------------------------------

CREATE TABLE t_estadisticas_tipster
(
	fecha				DATE			NOT NULL,
	tipster_id			INT UNSIGNED	NOT NULL,
	tipo_id				INT UNSIGNED	NOT NULL,
	cantidad_apostada   DECIMAL(8,3)	NOT NULL,
	ganancia_bruta		DECIMAL(8,3)	NOT NULL,
	ganancia_neta		DECIMAL(8,3)	NOT NULL,
	yield				DECIMAL(5,2)	NOT null,
	
	PRIMARY KEY (fecha, tipster_id, tipo_id)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;