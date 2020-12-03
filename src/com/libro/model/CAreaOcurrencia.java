package com.libro.model;

public class CAreaOcurrencia {
	private int idareaocurrencia;
	private String nombre;
	private int idsedeocurrencia;
	public int getIdsedeocurrencia() {
		return idsedeocurrencia;
	}
	public void setIdsedeocurrencia(int idsedeocurrencia) {
		this.idsedeocurrencia = idsedeocurrencia;
	}
	public int getIdareaocurrencia() {
		return idareaocurrencia;
	}
	public void setIdareaocurrencia(int idareaocurrencia) {
		this.idareaocurrencia = idareaocurrencia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public CAreaOcurrencia(){
		idareaocurrencia=0;
		nombre="";
		idsedeocurrencia=0;
	}
	public CAreaOcurrencia(int pidareaocurrencia,String pnombre,int pidsedeocurrencia){
		idareaocurrencia=pidareaocurrencia;
		nombre=pnombre;
		idsedeocurrencia=pidsedeocurrencia;
	}
}
