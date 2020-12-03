package com.libro.model;

import java.util.*;

public class CUsuario {
	private String idusuario;
	private String clave;
	private String nrodoc;
	private String nombres;
	private String apellidos;
	private String sexo;
	private Date fechanacimiento;
	private String correo;
	private boolean estado;
	private int idperfil;
	private Date fechainicio;
	private String imagen;
	private String perfil;
	public String getPerfil() {
		return perfil;
	}
	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getFechanacimiento() {
		return fechanacimiento;
	}
	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public int getIdperfil() {
		return idperfil;
	}
	public void setIdperfil(int idperfil) {
		this.idperfil = idperfil;
	}
	public Date getFechainicio() {
		return fechainicio;
	}
	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}
	public CUsuario(){
		this.idusuario = "";
		this.clave = "";
		this.nrodoc = "";
		this.nombres = "";
		this.apellidos = "";
		this.sexo = "";
		this.fechanacimiento = new Date();
		this.correo = "";
		this.estado = false;
		this.idperfil = 0;
		this.fechainicio = new Date();
		this.imagen="img/usuarios/user.png";
		this.perfil="";
	}
	public CUsuario(String idusuario, String clave, String nrodoc, String nombres, String apellidos, String sexo,
			Date fechanacimiento, String correo, boolean estado, int idperfil, Date fechainicio,String imagen) {
		super();
		this.idusuario = idusuario;
		this.clave = clave;
		this.nrodoc = nrodoc;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.sexo = sexo;
		this.fechanacimiento = fechanacimiento;
		this.correo = correo;
		this.estado = estado;
		this.idperfil = idperfil;
		this.fechainicio = fechainicio;
		this.imagen=imagen;
		this.perfil="";
	}
	public CUsuario(String idusuario, String clave, String nrodoc, String nombres, String apellidos, String sexo,
			Date fechanacimiento, String correo, boolean estado, int idperfil, Date fechainicio,String imagen,String perfil) {
		super();
		this.idusuario = idusuario;
		this.clave = clave;
		this.nrodoc = nrodoc;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.sexo = sexo;
		this.fechanacimiento = fechanacimiento;
		this.correo = correo;
		this.estado = estado;
		this.idperfil = idperfil;
		this.fechainicio = fechainicio;
		this.imagen=imagen;
		this.perfil=perfil;
	}
}
