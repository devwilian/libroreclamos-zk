package com.libro.dao;

import com.libro.model.CAreaOcurrencia;
import com.libro.model.CSeguimiento;
import com.libro.model.CSolucionado;

import java.util.*;

public class CseguimientoDAO extends CConexion{
	private CSeguimiento seguimiento;

	public CSeguimiento getSeguimiento() {
		return seguimiento;
	}
	public void setSeguimiento(CSeguimiento seguimiento) {
		this.seguimiento = seguimiento;
	}
	public List recuperarReclamoporCodigo(String codigo){
		Object[]values={codigo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_mostrarestadoreclamoporcodigo", values);
	}
	public void asignarEstadoSeguimiento(List lista){
		Map row=(Map)lista.get(0);
		seguimiento=new CSeguimiento((Boolean)row.get("recib"),(Boolean)row.get("proc"),(Boolean)row.get("soluc"),(int)row.get("cod"));
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
