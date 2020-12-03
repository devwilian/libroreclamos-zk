package com.libro.model;

public class CSedeOcurrencia {
	private int idsedeocurrencia;
	private String nombre;
	public int getIdsedeocurrencia() {
		return idsedeocurrencia;
	}
	public void setIdsedeocurrencia(int idsedeocurrencia) {
		this.idsedeocurrencia = idsedeocurrencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public CSedeOcurrencia(){
		idsedeocurrencia=0;
		nombre="";
	}
	public CSedeOcurrencia(int pidocurrencia,String pnombre){
		idsedeocurrencia=pidocurrencia;
		nombre=pnombre;
	}
}
