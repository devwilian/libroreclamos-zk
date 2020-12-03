package com.libro.dao;

import com.libro.model.*;
import java.util.*;

public class CsolucionDAO extends CConexion{
	private CSolucion solucion;
	private ArrayList<CSolucion> listaSolucion;

	public ArrayList<CSolucion> getListaSolucion() {
		return listaSolucion;
	}
	public void setListaSolucion(ArrayList<CSolucion> listaSolucion) {
		this.listaSolucion = listaSolucion;
	}
	public CSolucion getSolucion() {
		return solucion;
	}
	public void setSolucion(CSolucion solucion) {
		this.solucion = solucion;
	}
	public List recuperarSolucion(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolucion");
	}
	public List recuperarSolucionpPorCodigo(int cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarsolucionporcodigo",values);
	}
	public List recuperarOtraSolucionPorCodigo(int cod){
		Object[]values={cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarotrasolucionporcodigo",values);
	}
	public void asignarSolucion(List list){
		Map row=(Map)list.get(0);
		solucion=new CSolucion((int)row.get("idsolucion"), (String)row.get("detalles"));
	}
	public void asignarOtraSolucion(List list){
		Map row=(Map)list.get(0);
		solucion=new CSolucion((int)row.get("idotrasolucion"), (String)row.get("detalles"));
	}
	public List insertarSolucion(int cod,String valor){
		Object[]values={cod,valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarotrasolucion",values);
	}
	public List insertarOtraSolucion(String otro){
		Object[]values={otro};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarsolucion",values);
	}
	public void asignarListaSolucion(List list){
		System.out.println("Tamaño de solucion "+list.size());
		listaSolucion=new ArrayList<CSolucion>();
		for (int i = 0; i < list.size(); i++) {
			Map row=(Map)list.get(i);
			solucion=new CSolucion((int)row.get("idsolucion"), (String)row.get("detalles"));
			listaSolucion.add(solucion);
		}
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int Codigo(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cod");
		if(correcto)return cod;
		else return -1;
	}
}
