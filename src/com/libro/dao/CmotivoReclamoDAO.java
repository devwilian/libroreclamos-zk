package com.libro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.libro.model.*;

public class CmotivoReclamoDAO extends CConexion{
	private CMotivoReclamo motivoReclamo;
	private ArrayList<CMotivoReclamo> listaMotivoReclamo;
	public CMotivoReclamo getMotivoReclamo() {
		return motivoReclamo;
	}
	public void setMotivoReclamo(CMotivoReclamo motivoReclamo) {
		this.motivoReclamo = motivoReclamo;
	}
	public ArrayList<CMotivoReclamo> getListaMotivoReclamo() {
		return listaMotivoReclamo;
	}
	public void setListaMotivoReclamo(ArrayList<CMotivoReclamo> listaMotivoReclamo) {
		this.listaMotivoReclamo = listaMotivoReclamo;
	}
	public List obtenerMotivoOcurrencia(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarmotivoreclamo");
	}
	public List obtenerMotivoReclamoporReclamo(String cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarmotivoreclamoporreclamo",values);
	}
	public List obtenerOtroMotivoReclamoporReclamo(String cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarotromotivoreclamoporreclamo",values);
	}
	public List insertarListaMotivoReclamo(String idreclamo,int idmotivoreclamo){
		Object[]values={idreclamo,idmotivoreclamo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarlistamotivoreclamo",values);
	}
	public List insertarMotivoReclamo(String motivo){
		Object[]values={motivo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarmotivoreclamo",values);
	}
	public List insertarOtroMotivoReclamo(String idreclamo,String motivo){
		Object[]values={idreclamo,motivo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarotromotivo",values);
	}
	public List modificarMotivo(CMotivoReclamo motivo){
		Object[]values={motivo.getIdmotivoreclamo(),motivo.getDescripcion()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_actualizarmotivo", values);
	}
	public List eliminarmotivo(int codigo){
		Object[]values={codigo};
		return getEjecutorSQL().ejecutarProcedimiento("libro_eliminarmotivoreclamo",values);
	}
	public void asignarListaMotivoporMotivo(List lista)
	{
		System.out.println("Catidad de Motivos "+lista.size());
		listaMotivoReclamo=new ArrayList<CMotivoReclamo>();
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaMotivoReclamo.add(new CMotivoReclamo((int)row.get("codmotivo"),(String)row.get("descripcion")));
		}
	}
	public void asignarListaMotivoNormal(List lista)
	{
		System.out.println("Catidad de Motivos "+lista.size());
		listaMotivoReclamo=new ArrayList<CMotivoReclamo>();
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaMotivoReclamo.add(new CMotivoReclamo((int)row.get("idmotivoreclamo"),(String)row.get("descripcion")));
		}
	}
	public void asignarMotivo(List lista)
	{
		Map row=(Map)lista.get(0);
		motivoReclamo=new CMotivoReclamo((int)row.get("codmotivo"),(String)row.get("descripcion"));
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int codigoMotivoReclamo(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cod");
		if(correcto)return cod;
		else return -1;
	}
}
