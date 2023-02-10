use apuestasbd;

select * from t_tipsters;

insert into t_banks values (null,'DAVID','BET365',124.71,6,'2023-02-02');
insert into t_banks values (null,'DAVID','BETFAIR',25,0,'2023-02-02');

select * from t_apuestas order by id desc;

insert into t_apuestas (id,fecha_alta,fecha_evento,tipster_id,tipo_id,live,reto,deporte_id,evento,apuesta,stake,cuota,cantidad_apostada,bruto,estado_id,encuentro,comentario,modificado)
values (null,'2023/02/02','2023/02/03',4,2,0,0,2,'NBA','HA',4,1.57,4,0,1,'LOS ANGELES CLIPPERS VS MILWAUKEE BUCKS','RANGO SUPER',0);

-- seleccionar las apuestas con fecha de evento hoy
select  id,fecha_evento,tipster_id,tipo_id ,reto ,evento,apuesta,stake,cuota,cantidad_apostada ,encuentro,comentario,estado_id 
from t_apuestas
where 1=1
   and fecha_evento = '2023/02/09'
  -- and tipster_id = 4
  and tipo_id in (1,2) -- 1 free / 2 premium
   and estado_id = 1 -- 1 pendiente / 2 ganada / 3 perdida / 4 push
 -- and id in (322)
 order by id desc;

-- actualizar apuesta
update t_apuestas
 -- set TIPSTER_ID = 14 --  evento = 'PORTUGAL'  -- fecha_evento  = '2023-01-19'
  set bruto = cuota * cantidad_apostada, estado_id = 2 -- ganada
 -- set bruto = 0, estado_id = 3 -- perdida
 -- set bruto = cantidad_apostada , estado_id = 4 -- push
where id in (619)

-- Cantidad en pendiente
 select fecha_evento Fecha,SUM(cantidad_apostada) 'Cantidad Apostada',SUM(cantidad_apostada * cuota) Potencial
from t_apuestas
where 1=1
-- and fecha_evento BETWEEN '2023/01/10' and '2023/01/11'
and estado_id in (1)
 and reto = 1
and tipo_id < 99
 group by fecha_evento

-- Estadisticas por d�a
 select fecha_evento Fecha,SUM(cantidad_apostada) 'Cantidad Apostada',SUM(bruto) 'Ganancias Brutas',SUM(bruto - cantidad_apostada) 'Ganancias Netas', (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) Yield ,SUM(cantidad_apostada * cuota) Potencial
-- select SUM(cantidad_apostada) 'Cantidad Apostada',SUM(bruto) 'Ganancias Brutas',SUM(bruto - cantidad_apostada) 'Ganancias Netas', (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) Yield 
from t_apuestas
where 1=1
 and fecha_evento BETWEEN '2022/01/10' and '2023/01/12'
and estado_id in (2,3,4)
 and reto = 1
and tipo_id < 99
 group by fecha_evento desc
 
 -- RACHA DE TIPSTERS
select ta.fecha_evento ,ta.tipster_id,tt.descripcion Tipster,ta.tipo_id,tta.descripcion Tipo,reto,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',
		SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield,
		count(1)'N�m. Apuestas',SUM(if(ta.estado_id = 2,1,0)) 'Ganadas', ((count(1) - SUM(if(ta.estado_id <> 2,1,0))) / count(1))*100 '%',SUM(if(ta.estado_id = 3,1,0)) 'Perdidas',SUM(if(ta.estado_id = 4,1,0)) 'Push'
from t_apuestas ta, t_tipsters tt, t_tipos_apuesta tta
where 1=1
and ta.tipster_id = tt.id
and ta.tipo_id = tta.id
and ta.fecha_evento BETWEEN '2022/01/01' and '2023/12/31'
and ta.estado_id in (2,3,4)
and ta.tipo_id < 99
and ta.tipster_id in (1)
and ta.tipo_id = 1
AND ta.reto = 0
group by ta.fecha_evento ,ta.tipster_id,ta.tipo_id,reto
order by ta.fecha_evento desc;

-- Estadisticas totales en rango de fechas
select SUM(cantidad_apostada) 'Cantidad Apostada',SUM(bruto) 'Ganancias Brutas',SUM(bruto - cantidad_apostada) 'Ganancias Netas', (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) Yield 
from t_apuestas
where 1=1
-- and fecha_evento = '2023/01/06'
and fecha_evento BETWEEN '2022/01/01' and '2023/02/09'
and estado_id in (2,3,4)
 and reto = 1
-- and tipo_id = 1
and tipo_id < 99;

-- RACHA DE TIPSTERS
select ta.fecha_evento ,ta.tipster_id,tt.descripcion Tipster,ta.tipo_id,tta.descripcion Tipo,reto,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',
		SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield,
		count(1)'N�m. Apuestas',SUM(if(ta.estado_id = 2,1,0)) 'Ganadas', ((count(1) - SUM(if(ta.estado_id <> 2,1,0))) / count(1))*100 '%',SUM(if(ta.estado_id = 3,1,0)) 'Perdidas',SUM(if(ta.estado_id = 4,1,0)) 'Push'
from t_apuestas ta, t_tipsters tt, t_tipos_apuesta tta
where 1=1
and ta.tipster_id = tt.id
and ta.tipo_id = tta.id
and ta.fecha_evento BETWEEN '2022/01/01' and '2023/12/31'
and ta.estado_id in (2,3,4)
and ta.tipo_id < 99
and ta.tipster_id in (1)
and ta.tipo_id = 1
AND ta.reto = 0
group by ta.fecha_evento ,ta.tipster_id,ta.tipo_id,reto
order by ta.fecha_evento desc;

-- Estadisticas de los tipster por fecha (dia)
 select ta.fecha_evento,ta.tipster_id,tt.descripcion Tipster,ta.tipo_id,tta.descripcion Tipo,reto Reto,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield 
-- select ta.tipster_id,tt.descripcion Tipster,ta.tipo_id,tta.descripcion Tipo,reto Reto,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield 
from t_apuestas ta, t_tipsters tt, t_tipos_apuesta tta
where 1=1
and ta.tipster_id = tt.id
and ta.tipo_id = tta.id
and ta.fecha_evento BETWEEN '2023/01/11' and '2023/01/11'
and ta.estado_id in (2,3,4)
and ta.tipo_id < 99
-- and ta.tipster_id in (1)
and ta.tipo_id in (1,2)
-- and reto = 0
 group by ta.fecha_evento,ta.tipster_id,ta.tipo_id,reto

-- Estadisticas de los tipster TOTALES por rango de fechas
select ta.tipster_id,tt.descripcion Tipster,ta.tipo_id,tta.descripcion Tipo,reto,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',
		SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield,
		count(1)'N�m. Apuestas',SUM(if(ta.estado_id = 2,1,0)) 'Ganadas', ((count(1) - SUM(if(ta.estado_id <> 2,1,0))) / count(1))*100 '%',SUM(if(ta.estado_id = 3,1,0)) 'Perdidas',SUM(if(ta.estado_id = 4,1,0)) 'Push'
from t_apuestas ta, t_tipsters tt, t_tipos_apuesta tta
where 1=1
and ta.tipster_id = tt.id
and ta.tipo_id = tta.id
and ta.fecha_evento BETWEEN '2022/01/01' and '2023/12/31'
and ta.estado_id in (2,3,4)
and ta.tipo_id < 99
and ta.tipster_id in (1)
and ta.tipo_id in (1,2)
group by ta.tipster_id,ta.tipo_id,reto

-- Estadisticas por tipo de apuesta (free, premium, reto)
-- select fecha_evento Fecha,SUM(cantidad_apostada) 'Cantidad Apostada',SUM(bruto) 'Ganancias Brutas',SUM(bruto - cantidad_apostada) 'Ganancias Netas', (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) Yield 
select SUM(cantidad_apostada) 'Cantidad Apostada',SUM(bruto) 'Ganancias Brutas',SUM(bruto - cantidad_apostada) 'Ganancias Netas', (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) Yield 
from t_apuestas
where 1=1
 and fecha_evento BETWEEN '2023/01/01' and '2023/01/09'
and estado_id in (2,3,4)
and reto = 1
and tipo_id = 2
and tipo_id < 99;
group by fecha_evento


select tt.descripcion ,ta.id,fecha_evento,stake,cuota,if(estado_id = 3,'PERDIDA','GANADA') Estado
from t_apuestas ta, t_tipsters tt 
where 1 = 1
and ta.tipster_id  = tt.id
and fecha_evento BETWEEN '2022/01/01' and '2023/12/31'
and tipster_id = 3
and tipo_id = 1
and reto = 0
and live = 0
and cuota < 1.75
and estado_id in (2,3,4)


select * 
from t_estadisticas_tipster;

-- ===============================================================
-- GENERACION DE ESTADISTICAS
-- ===============================================================

-- Generar estadisticas por dia <-- debe ejecutarse despues de las 23:59 del dia

select * from t_estadisticas_dia 
order by fecha desc;

select SUM(cantidad_apostada),SUM(ganancia_bruta),SUM(ganancia_neta)
from t_estadisticas_dia 

delete from t_estadisticas_dia ;
-- where fecha =  '2023/01/14';

-- hasta el dia 18 (incluido) ok

insert into t_estadisticas_dia 
select fecha_evento fecha,SUM(cantidad_apostada) cantidad_apostada,SUM(bruto) ganancia_bruta,SUM(bruto - cantidad_apostada) ganancia_neta, (SUM(bruto - cantidad_apostada) / SUM(cantidad_apostada)* 100 ) yield 
from t_apuestas
where 1=1
and fecha_evento BETWEEN '2022/01/16' and '2023/02/03'
and estado_id in (2,3,4)
and tipo_id < 99
group by fecha_evento DESC;

-- Generar estadisticas de tipster por dia

-- hasta el dia 18 (incluido) ok

select tet.fecha Fecha,tt.descripcion Tipster, tta.descripcion 'Tipo Apuesta',cantidad_apostada Apostado,ganancia_bruta Bruto, ganancia_neta Neto,yield Yield 
from t_estadisticas_tipster tet , t_tipsters tt , t_tipos_apuesta tta 
where 1=1
and tet.tipster_id = tt.id 
and tet.tipo_id = tta.id 
and fecha BETWEEN '2023/02/01' and '2023/12/31'
-- and tet.tipster_id = 4
--  and tet.tipo_id = 1
order by fecha desc;

delete from t_estadisticas_tipster ;
-- where fecha =  '2023/01/14';

insert into t_estadisticas_tipster
select ta.fecha_evento,ta.tipster_id,ta.tipo_id,SUM(ta.cantidad_apostada) 'Cantidad Apostada',SUM(ta.bruto) 'Ganancias Brutas',SUM(ta.bruto - ta.cantidad_apostada) 'Ganancias Netas', (SUM(ta.bruto - ta.cantidad_apostada) / SUM(ta.cantidad_apostada)* 100 ) Yield 
from t_apuestas ta, t_tipsters tt, t_tipos_apuesta tta
where 1=1
and ta.tipster_id = tt.id
and ta.tipo_id = tta.id
and ta.fecha_evento BETWEEN '2022/01/16' and '2023/02/01'
and ta.estado_id in (2,3,4)
and ta.tipo_id < 99
group by ta.fecha_evento,ta.tipster_id,ta.tipo_id

select *
from t_tipsters;


update t_tipsters 
set valor_stake = 2
where id in (4,6,7,10)
