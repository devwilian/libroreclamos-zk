package com.libro.util;

import java.util.*;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.libro.dao.CusuarioLoginDAO;
import com.libro.model.CUsuarioLogin;

public class ServicioAutentificacion {
	CusuarioLoginDAO usuarioDAO;
	public ServicioAutentificacion(){
		usuarioDAO=new CusuarioLoginDAO();
	}
	public CUsuarioLogin getCusuarioLoginDAO(){
		Session sess = Sessions.getCurrent();
		CUsuarioLogin user = (CUsuarioLogin)sess.getAttribute("usuario");
		if(user==null){
			user = new CUsuarioLogin();//new a anonymous user and set to session
			sess.setAttribute("usuario",user.getcUsuarioCod());
		}
		return user;
	}
    public Object[] login(String us,String password)
    {
    	System.out.println("Datos usuario "+us+" , "+password);
    	Object[] Respuesta=new Object[4];
    	//------------------------------
    	usuarioDAO.getUsuario().setcUsuarioCod(us);
    	usuarioDAO.getUsuario().setcClave(password);
    	List aux=usuarioDAO.validarLogin();
    	Map user=(Map)aux.get(0);
//    	System.out.println("TAMAÑO RECUPERADO USUARIO"+aux.size());
    	
    	
    	Respuesta[1]=(String)user.get("resultado");
    	Respuesta[2]=(String)user.get("mensaje");
    	Respuesta[3]=(int)user.get("perfilcod");
    	
//    	System.out.println("USUARIO RECUPERADO ");
//    	System.out.println(Respuesta[1]);
//    	System.out.println(Respuesta[2]);
//    	System.out.println(Respuesta[3]);
    	
    	//=========
    	Session sesion=Sessions.getCurrent();
    	if(Respuesta[1].toString().equals("correcto")){
//    		System.out.println("entra en esta parte..");
    		Respuesta[0]=true;
    		return Respuesta;
    	}else{
    		Respuesta[0]=false;
    		return Respuesta;
    	}   	    	
    }
    public void logout(){ 
    	Session sesion=Sessions.getCurrent();
    	sesion.removeAttribute("usuario");
    }
    public CusuarioLoginDAO retornarUsuario(){
    	return null;
    }
}
