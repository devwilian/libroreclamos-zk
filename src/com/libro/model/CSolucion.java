package com.libro.model;

import java.util.Date;

public class CSolucion {
	private int idsolucion;
	private String detalles;
	public int getIdsolucion() {
		return idsolucion;
	}
	public void setIdsolucion(int idsolucion) {
		this.idsolucion = idsolucion;
	}
	public String getDetalles() {
		return detalles;
	}
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	public CSolucion(){
		idsolucion=0;
		detalles="";
	}
	public CSolucion(int pidsolucion,String pdetalles){
		idsolucion=pidsolucion;
		detalles=pdetalles;
	}
}
