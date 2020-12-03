package com.libro.dao;

import com.libro.model.*;
import java.util.*;
public class CusuarioDAO extends CConexion{
	private CUsuario usuario;
	private ArrayList<CUsuario> listaUsuarios;
	public ArrayList<CUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(ArrayList<CUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	public CUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(CUsuario usuario) {
		this.usuario = usuario;
	}
	public List recuperarUsuarios(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarusuarios");
	}
	public List recuperarListaUsuarios(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarlistausuario");
	}
	public List recuperarUsuario(String valor){
		Object[]values={valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarusuario",values);
	}
	public List eliminarUsuario(String valor){
		Object[]values={valor};
		return getEjecutorSQL().ejecutarProcedimiento("libro_eliminarusuario",values);
	}
	public void asignarUsuario(List list){
		Map row=(Map)list.get(0);
		usuario=new CUsuario((String)row.get("idusuario"), (String)row.get("clave"), (String)row.get("nrodoc"), (String)row.get("nombres"), 
				(String)row.get("apellidos"), (String)row.get("sexo"), (Date)row.get("fechanacimiento"), (String)row.get("correo"), 
				(boolean)row.get("estado"), (int)row.get("idperfil"), (Date)row.get("fechainicio"), (String)row.get("imagen"));
	}
	public void asignarListaUsuario(List list){
		listaUsuarios=new ArrayList<CUsuario>();
		for (int i = 0; i < list.size(); i++) {
			Map row=(Map)list.get(i);
			CUsuario usuario=new CUsuario((String)row.get("idusuario"), (String)row.get("clave"), (String)row.get("nrodoc"), (String)row.get("nombres"), (String)row.get("apellidos"), (String)row.get("sexo"), (Date)row.get("fechanacimiento"),(String)row.get("correo"), (boolean)row.get("estado"), (int)row.get("idperfil"), (Date)row.get("fechainicio"), (String)row.get("imagen"));
			listaUsuarios.add(usuario);
		}
	}
	public void asignarListaUsuarios(List list){
		listaUsuarios=new ArrayList<CUsuario>();
		for (int i = 0; i < list.size(); i++) {
			Map row=(Map)list.get(i);
			System.out.println("Perfil "+row.get("descripcion"));
			CUsuario usuario=new CUsuario((String)row.get("idusuario"), (String)row.get("clave"), (String)row.get("nrodoc"), (String)row.get("nombres"), (String)row.get("apellidos"), (String)row.get("sexo"), (Date)row.get("fechanacimiento"),(String)row.get("correo"), (boolean)row.get("estado"), (int)row.get("idperfil"), (Date)row.get("fechainicio"), (String)row.get("imagen"),(String)row.get("nombreperfil"));
			listaUsuarios.add(usuario);
		}
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
