USE apuestasbd;

ALTER TABLE t_tipsters
	ADD COLUMN valor_stake DECIMAL(3,1)	NOT NULL;

UPDATE t_tipsters
SET valor_stake = 1
WHERE id > 0;

ALTER TABLE t_apuestas
    ADD COLUMN modificado TINYINT(1) NOT NULL;

UPDATE t_apuestas
SET modificado = 0
WHERE id > 0;


