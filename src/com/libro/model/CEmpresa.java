package com.libro.model;

public class CEmpresa {
	private int idempresa;
	private String nombre;
	private String telefono1;
	private String telefono2;
	private String correo;
	private String direccion;
	private String publicKey;
	private String secretKey;
	private String terminosycondiciones;
	public String getTerminosycondiciones() {
		return terminosycondiciones;
	}
	public void setTerminosycondiciones(String terminosycondiciones) {
		this.terminosycondiciones = terminosycondiciones;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono1() {
		return telefono1;
	}
	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}
	public String getTelefono2() {
		return telefono2;
	}
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public int getIdempresa() {
		return idempresa;
	}
	public void setIdempresa(int idempresa) {
		this.idempresa = idempresa;
	}
	public CEmpresa() {
		super();
		this.idempresa=0;
		this.nombre="";
		this.telefono1 = "";
		this.telefono2 = "";
		this.correo = "";
		this.direccion="";
		this.publicKey = "";
		this.secretKey = "";
		this.terminosycondiciones="";
	}
	public CEmpresa(int idempresa, String nombre, String telefono1, String telefono2, String correo, String direccion,
			String publicKey, String secretKey,String terminos) {
		super();
		this.idempresa = idempresa;
		this.nombre = nombre;
		this.telefono1 = telefono1;
		this.telefono2 = telefono2;
		this.correo = correo;
		this.direccion = direccion;
		this.publicKey = publicKey;
		this.secretKey = secretKey;
		this.terminosycondiciones=terminos;
	}
}
