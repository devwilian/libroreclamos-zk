package com.libro.model;

public class CMotivoReclamo {
	private int idmotivoreclamo;
	private String descripcion;
	public int getIdmotivoreclamo() {
		return idmotivoreclamo;
	}
	public void setIdmotivoreclamo(int idmotivoreclamo) {
		this.idmotivoreclamo = idmotivoreclamo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public CMotivoReclamo(){
		idmotivoreclamo=0;
		descripcion="";
	}
	public CMotivoReclamo(int pidmotivoreclamo,String pdescripcion){
		idmotivoreclamo=pidmotivoreclamo;
		descripcion=pdescripcion;
	}
}
