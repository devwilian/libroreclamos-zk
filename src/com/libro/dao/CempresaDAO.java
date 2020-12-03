package com.libro.dao;
import java.util.List;
import java.util.Map;

import com.libro.model.CAcceso;
import com.libro.model.CEmpresa;
import com.libro.model.CUsuarioLogin;

public class CempresaDAO extends CConexion{
	private CEmpresa empresa;
	//====CONSTRUCTORES=====
	
	public CEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(CEmpresa empresa) {
		this.empresa = empresa;
	}
	public List recuperarDatosEmpresa(){
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperardatosempresa");
	}
	public List insertarDatosEmpresa(CEmpresa empresa){
		Object[]values={
				empresa.getNombre(),
				empresa.getTelefono1(),
				empresa.getTelefono2(),
				empresa.getCorreo(),
				empresa.getDireccion(),
				empresa.getPublicKey(),
				empresa.getSecretKey(),
				empresa.getTerminosycondiciones()
		};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertardatosempresa",values);
	}
	public void pasarDatos(List list){
		Map row=(Map)list.get(0);
		empresa=new CEmpresa(
				(int)row.get("idempresa"),
				(String)row.get("nombre"), 
				(String)row.get("telefono1"), 
				(String)row.get("telefono2"), 
				(String)row.get("correo"), 
				(String)row.get("direccion"), 
				(String)row.get("pkcaptcha"), 
				(String)row.get("skcaptcha"),
				(String)row.get("terminosycondiciones"));
	}
	public void asignarEmpresaRecuperada(List lista){
		Map row=(Map)lista.get(0);
		empresa =new CEmpresa();
		empresa.setIdempresa((int)row.get("idempresa"));
		empresa.setNombre((String)row.get("nombre"));
		empresa.setTelefono1((String)row.get("telefono1"));
		empresa.setTelefono2((String)row.get("telefono2"));
		empresa.setCorreo((String)row.get("correo")); 
		empresa.setDireccion((String)row.get("direccion"));
		empresa.setPublicKey((String)row.get("pkcaptcha"));
		empresa.setSecretKey((String)row.get("skcaptcha"));
		empresa.setTerminosycondiciones((String)row.get("terminosycondiciones"));
	}
	
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
}
