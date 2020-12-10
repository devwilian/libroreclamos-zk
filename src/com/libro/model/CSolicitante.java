package com.libro.model;

public class CSolicitante {
	private int idsolicitante;
	private int tipodocumento;
	private String nrodoc;
	private String nombres;
	private String apellidos;
	private String nrotelefono;
	private String correo;
	private String direccion;
	private String razonsocial;
	private String pais;
	private String ciudad;
	private String provincia;
	private String distrito;
	private String parentesco;
	private String representado;
	
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	public String getRepresentado() {
		return representado;
	}
	public void setRepresentado(String representado) {
		this.representado = representado;
	}
	public int getIdsolicitante() {
		return idsolicitante;
	}
	public void setIdsolicitante(int idsolicitante) {
		this.idsolicitante = idsolicitante;
	}
	public int getTipodocumento() {
		return tipodocumento;
	}
	public void setTipodocumento(int tipodocumento) {
		this.tipodocumento = tipodocumento;
	}
	public String getNrodoc() {
		return nrodoc;
	}
	public void setNrodoc(String nrodoc) {
		this.nrodoc = nrodoc;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getNrotelefono() {
		return nrotelefono;
	}
	public void setNrotelefono(String nrotelefono) {
		this.nrotelefono = nrotelefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public CSolicitante(int idsolicitante, int tipodocumento, String nrodoc, String nombres, String apellidos,
			String nrotelefono, String correo, String direccion, String razonsocial, String pais, String ciudad,
			String provincia, String distrito, String parentesco, String representado) {
		super();
		this.idsolicitante = idsolicitante;
		this.tipodocumento = tipodocumento;
		this.nrodoc = nrodoc;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.nrotelefono = nrotelefono;
		this.correo = correo;
		this.direccion = direccion;
		this.razonsocial = razonsocial;
		this.pais = pais;
		this.ciudad = ciudad;
		this.provincia = provincia;
		this.distrito = distrito;
		this.parentesco = parentesco;
		this.representado = representado;
	}
	public CSolicitante(){
		this.idsolicitante=0;
		this.tipodocumento=0;
		this.nrodoc="";
		this.nombres="";
		this.apellidos="";
		this.nrotelefono="";
		this.correo="";
		this.direccion="";
		this.razonsocial="";
		this.pais = "";
		this.ciudad = "";
		this.provincia = "";
		this.distrito = "";
		this.parentesco = "";
		this.representado = "";
	}
}
