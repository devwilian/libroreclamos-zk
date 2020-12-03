package com.libro.dao;

import com.libro.model.CGaleria;
import com.libro.model.CTipoProblema;
import java.util.*;

public class CtipoProblemaDAO extends CConexion{
	private CTipoProblema tipoProblema;

	public CTipoProblema getTipoProblema() {
		return tipoProblema;
	}

	public void setTipoProblema(CTipoProblema tipoProblema) {
		this.tipoProblema = tipoProblema;
	}
	public List recuperarTipoProblema(int cod){
		Object []values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperartipoproblemacodigo",values);
	}
	public void asignarTipoProblema(List list){
		Map row=(Map)list.get(0);
		tipoProblema=new CTipoProblema((int)row.get("idtipoproblema"),(String)row.get("descripcion"));
	}
}
