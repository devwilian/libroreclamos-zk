package com.libro.dao;
import java.util.*;
import com.libro.model.*;

public class CaccesoDAO extends CConexion{
	private CPerfil perfil;
	private ArrayList<CPerfil> listaPerfil;
	public CPerfil getPerfil() {
		return perfil;
	}
	public void setPerfil(CPerfil perfil) {
		this.perfil = perfil;
	}
	public ArrayList<CPerfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(ArrayList<CPerfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public List recuperarPerfilesBD(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarperfil");
	}
	public List insertarPerfil(String nombre,int cod){
		Object[]values={nombre,cod};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarperfil",values);
	}
	public List insertarAcceso(CAcceso acceso){
		Object[]values={
				acceso.isAccesoidioma(),
				acceso.isAccesoregusuario(),
				acceso.isAccesoreportes(),
				acceso.isAccesorecibidos(),
				acceso.isAccesoprocesos()
		};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertaracceso",values);
	}
	public void asignarListaPerfil(List list){
		listaPerfil=new ArrayList<CPerfil>();
		for (int i = 0; i < list.size(); i++) {
			Map row=(Map)list.get(i);
			CPerfil perfil=new CPerfil((int)row.get("idperfil"),(String)row.get("descripcion"),(int)row.get("idacceso"));
			listaPerfil.add(perfil);
		}
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int codigo(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		int cod=(int)row.get("cod");
		if(correcto)return cod;
		else return -1;
	}
}
