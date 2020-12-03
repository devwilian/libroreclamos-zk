-- AGREGAR LAS COLUMNAS DE PAIS CIUDAD PROVINCIAS, DISTRITO
ALTER TABLE tsolicitante ADD COLUMN pais varchar(100);
ALTER TABLE tsolicitante ADD COLUMN ciudad varchar(100);
ALTER TABLE tsolicitante ADD COLUMN provincia varchar(100);
ALTER TABLE tsolicitante ADD COLUMN distrito varchar(100);

-- AGREGAR EMPRESAS