-- AGREGAR LAS COLUMNAS DE PAIS CIUDAD PROVINCIAS, DISTRITO
ALTER TABLE tsolicitante ADD COLUMN pais varchar(100);
ALTER TABLE tsolicitante ADD COLUMN ciudad varchar(100);
ALTER TABLE tsolicitante ADD COLUMN provincia varchar(100);
ALTER TABLE tsolicitante ADD COLUMN distrito varchar(100);

-- agregar columna de tipo de bien contratado en la tabla reclamo
ALTER TABLE treclamo ADD COLUMN tipodebiencontratado varchar(100);
ALTER TABLE treclamo ADD COLUMN montoreclamado varchar(100);

-- agregar columna para guardar el nombre de la persona agraviada
ALTER TABLE tsolicitante ADD COLUMN parentesco varchar(100);
ALTER TABLE tsolicitante ADD COLUMN representado varchar(100);



-- eliminar funciones que no sirven
DROP FUNCTION libro_insertarreclamo(integer, integer, integer, integer, date, character varying, character varying, boolean, boolean, boolean, boolean);
DROP FUNCTION libro_insertarreclamoaux(integer, integer, integer, integer, date, character varying, character varying, boolean, boolean, boolean, boolean);

-- PROCEDIMIENTOS ALMACENADOS
CREATE OR REPLACE FUNCTION public.libro_insertarsolicitante(
    IN idtipodoc integer,
    IN nrodoc character varying,
    IN nombres character varying,
    IN apellidos character varying,
    IN nrotelefono character varying,
    IN correo character varying,
    IN direccion character varying,
    IN razonsocial character varying,
    IN pais character varying,
    IN ciudad character varying,
    IN provincia character varying,
    IN distrito character varying,
    IN parentesco character varying,
    IN representado character varying
    )
  RETURNS TABLE(resultado character varying, mensaje character varying, cod integer) AS
$BODY$
begin
	
	cod=(select max(idsolicitante) from tsolicitante);
	if(cod is null)then
		cod=1;
	else
		cod=cod+1;
	end if;
	insert into tsolicitante values(cod,$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,$12,$13,$14);
	resultado='correcto';
	mensaje='Datos Registrados Correctamente';
	return Query select resultado,mensaje,cod;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.libro_insertarsolicitante(integer, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying,character varying,character varying)
  OWNER TO postgres;
  
-- identificar tipo del bien contratado
CREATE FUNCTION public.libro_insertar_reclamo(
    IN idsolicitante integer,
    IN idtipoproblema integer,
    IN idsedeocurrencia integer,
    IN idareaocurrencia integer,
    IN fecha date,
    IN detallereclamo character varying,
    IN solicitudreclamo character varying,
    IN docmultimedia boolean,
    IN recibido boolean,
    IN proceso boolean,
    IN solucionado boolean,
    IN tipobien character varying,
    IN agraviado character varying,
    IN montoreclamado character varying)
  RETURNS TABLE(resultado character varying, mensaje character varying, cod character varying) AS
$BODY$
declare codaux varchar(4);
declare nro integer;
begin
  codaux=(select prefijo from tprefijocodigo where idprefijocodigo=1);
  nro=(select * from length(trim(trailing from codaux)));
  cod=(select concat(concat(trim(codaux),'-'),right(concat('00000',count(r.idreclamo)+1),5)) from treclamo r where left(trim(r.idreclamo),(nro+1))=concat(trim(codaux),'-'));
  insert into treclamo values (cod,$1,$2,$3,$4,$5,$6,$7,$8,$9,$10,$11,now(),$12,$13,$14);
  resultado='correcto';
  mensaje='Datos Registrados Correctamente';
  return Query select resultado,mensaje,cod;
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.libro_insertar_reclamo(integer, integer, integer, integer, date, character varying, character varying, boolean, boolean, boolean, boolean,character varying,character varying,character varying)
  OWNER TO postgres;
