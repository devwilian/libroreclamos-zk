package com.libro.dao;

import com.libro.model.*;
import java.util.*;

public class CsolicitanteDAO  extends CConexion{
	private CSolicitante solicitante;
	private ArrayList<CSolicitante> listaSolicitante;
	
	public CSolicitante getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(CSolicitante solicitante) {
		this.solicitante = solicitante;
	}
	public ArrayList<CSolicitante> getListaSolicitante() {
		return listaSolicitante;
	}
	public void setListaSolicitante(ArrayList<CSolicitante> listaSolicitante) {
		this.listaSolicitante = listaSolicitante;
	}
	public List recuperarSolicitantes(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolicitante");
	}
	public List recuperarSolicitanteCodigo(int val){
		Object[]values={val};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolicitanteporcodigo",values);
	}
	public List recuperarNumerodePersonas(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_numeropersonastotal");
	}
	public List insertarSolicitante(CSolicitante solicitante){
		Object[]values={
				solicitante.getTipodocumento(),
				solicitante.getNrodoc(),
				solicitante.getNombres(),
				solicitante.getApellidos(),
				solicitante.getNrotelefono(),
				solicitante.getCorreo(),
				solicitante.getDireccion(),
				solicitante.getRazonsocial()
		};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarsolicitante", values);
	}
	public void asginarListaSolicitantes(List lista){
		listaSolicitante=new ArrayList<CSolicitante>();
		for (int i = 0; i < lista.size(); i++) {
			Map row=(Map)lista.get(i);
			solicitante=new CSolicitante((int)row.get("idsolicitante"),(int)row.get("idtipodoc"),
					(String)row.get("nrodoc"),(String)row.get("nombres"),
					(String)row.get("apellidos"),(String)row.get("nrotelefono"),
					(String)row.get("correo"),(String)row.get("direccion"),(String)row.get("razonsocial"));
		}
	}
	public void asginarSolicitantes(List lista){
			Map row=(Map)lista.get(0);
			solicitante=new CSolicitante((int)row.get("idsolicitante"),(int)row.get("idtipodoc"),
					(String)row.get("nrodoc"),(String)row.get("nombres"),
					(String)row.get("apellidos"),(String)row.get("nrotelefono"),
					(String)row.get("correo"),(String)row.get("direccion"),(String)row.get("razonsocial"));
		
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int codigoPersona(List lista){
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cod");
		if(correcto) return cod;
		else return -1;
	}
	public int recuperarCantidad(List lista){
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cantidad");
		if(correcto) return cod;
		else return -1;
	}
}
