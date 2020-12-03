package com.libro.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.libro.model.*;

public class CareaOcurrenciaDAO extends CConexion{
	private CAreaOcurrencia areaOcurrencia;
	private ArrayList<CAreaOcurrencia> listaAreaOcurrencia;
	public CAreaOcurrencia getAreaOcurrencia() {
		return areaOcurrencia;
	}
	public void setAreaOcurrencia(CAreaOcurrencia areaOcurrencia) {
		this.areaOcurrencia = areaOcurrencia;
	}
	public ArrayList<CAreaOcurrencia> getListaAreaOcurrencia() {
		return listaAreaOcurrencia;
	}
	public void setListaAreaOcurrenciaporSede(ArrayList<CAreaOcurrencia> listaAreaOcurrencia) {
		this.listaAreaOcurrencia = listaAreaOcurrencia;
	}
	public List obtenerAreaOcurrenciaporSede(int pid){
		//System.out.println("Estamos en procedimiento");
		Object[]values={pid};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarareaocurrenciaporsede",values);
	}
	public List obtenerAreaOcurrenciaporCodigo(int pid){
		//System.out.println("Estamos en procedimiento");
		Object[]values={pid};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarareaocurrenciaporcodigo",values);
	}
	public List insertarAreaOcurrencia(String nombre,int integer){
		Object[]values={nombre,integer};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarareaocurrencia",values);
	}
	public List actualizarArea(CAreaOcurrencia area){
		Object[]values={area.getIdareaocurrencia(),area.getNombre().toUpperCase()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_actualizararea",values);
	}
	public List eliminarAreaOcurrencia(int cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_eliminarareaocurrencia",values);
	}
	public void asignarListaAreaOcurrencia(List lista)
	{
		//System.out.println("Catidad de Sede Ocurrencia "+lista.size());
		listaAreaOcurrencia=new ArrayList<CAreaOcurrencia>();	
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaAreaOcurrencia.add(new CAreaOcurrencia((int)row.get("idareaocurrencia"),(String)row.get("nombre"),(int)row.get("idsedeocurrencia")));
		}
	}
	public void asignarAreaOcurrencia(List lista)
	{
		Map row=(Map)lista.get(0);
		areaOcurrencia= new CAreaOcurrencia((int)row.get("idareaocurrencia"),(String)row.get("nombre"),(int)row.get("idsedeocurrencia"));
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
