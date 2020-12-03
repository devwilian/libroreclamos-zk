package com.libro.model;

public class CTipoProblema {
	private int idtipoproblema;
	private String descripcion;
	public int getIdtipoproblema() {
		return idtipoproblema;
	}
	public void setIdtipoproblema(int idtipoproblema) {
		this.idtipoproblema = idtipoproblema;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CTipoProblema(){
		idtipoproblema=0;
		descripcion="";
	}
	public CTipoProblema(int pidtiporproblema,String pdescripcion){
		idtipoproblema=pidtiporproblema;
		descripcion=pdescripcion;
	}	
}
