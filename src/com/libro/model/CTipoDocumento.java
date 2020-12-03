package com.libro.model;

public class CTipoDocumento {
	private int idtipodocumento;
	private String descripcion;
	
	public int getIdtipodocumento() {
		return idtipodocumento;
	}
	public void setIdtipodocumento(int idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CTipoDocumento(){
		idtipodocumento=0;
		descripcion="";
	}
	public CTipoDocumento(int pidtipodocumento,String pdescripcion){
		idtipodocumento=pidtipodocumento;
		descripcion=pdescripcion;
	}
}
