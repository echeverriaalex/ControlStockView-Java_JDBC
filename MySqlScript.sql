drop database control_de_stock;
create database control_de_stock;
use control_de_stock;

create table producto(
	id int auto_increment,
    nombre varchar(50) not null,
    descripcion varchar(255),
    cantidad int not null default 0,
    primary key (id)
)engine=InnoDB;

delete from producto where id >= 31;

alter table producto add column categoria_id int;
alter table producto add foreign key (categoria_id) references categoria(id);

insert into producto(nombre, descripcion, cantidad)
	values 	("Mesa", "Mesa de 4 lugares", 10),
			("Celular", "Calular Samsung", 50);
UPDATE PRODUCTO SET NOMBRE= "Auto", DESCRIPCION = "Auto picada", CANTIDAD = 20 WHERE ID = 12;
UPDATE PRODUCTO SET categoria_id= 1 WHERE ID = 1;

/* 
	FUNCIONA: experimento mio, actualizar la cantidad de stock 
    a un producto, traer la cantidad que tiene actualmente
    mas la cantidad que le quiero sumar
    Ejemplo: monitor eran 23, a eso le sumo 7 para tener 30 en total
*/
UPDATE PRODUCTO SET cantidad= (select cantidad from producto WHERE ID = 18) + 7  WHERE ID = 18;
select * from producto;

select c.id, c.nombre, p.id, p.nombre, p.cantidad from categoria c 
	inner join producto p on c.id = p.categoria_id;

create table categoria(
	id int auto_increment,
    nombre varchar(50) not null,
    primary key (id)
)engine=InnoDB;

insert into categoria(nombre)
	values 	("Muebles"), ("Tecnologia"), ("Accesorios"), 
			("Cocina"), ("Calzados"), ("Otros");

select * from categoria;

/*
Puede haber un problema para estandarizar la inserción 
de la categoría porque el campo sería libre para que los 
usuarios puedan escribir como quieran, generando muchas 
variaciones para una misma categoría. Eso perjudica la 
utilización de filtros por categoría.

Como cada usuario iba a poder escribir libremente podrían 
haber diferentes formas en el nombre de las categorías. 
Podrían haber errores de tipeo o caracteres adicionales. 
Y eso dificulta el filtrado de los productos por ser casi 
imposible cubrir todos los escenarios.

*/

select * from producto p inner join categoria c on p.categoria_id = c.id;

show processlist;