package com.libro.viewModel;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import com.libro.util.ServicioAutentificacion;

import pe.com.erp.crypto.Encryptar;

public class loginVM {
	private String userName;
	private String password;
	private String passEncrip;
	private boolean verclave;
	private String clave;
	ServicioAutentificacion auth;
	HttpSession seshttp;
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public ServicioAutentificacion getAuth() {
		return auth;
	}
	public void setAuth(ServicioAutentificacion auth) {
		this.auth = auth;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Init
	public void initVM(){
		userName="";
		password="";
		verclave=false;
		clave="password";
		auth=new ServicioAutentificacion();
	}
	@Command
	public void enviarUsername(@BindingParam("valor")String valor){
		userName=valor;
	}
	@Command
	public void enviarpassword(@BindingParam("valor")String valor){
		password=valor;
	}
	@Command
	public void ingresar(@BindingParam("user")String user,@BindingParam("pass")String pass,@BindingParam("compuser")Component compuser,@BindingParam("comppass")Component comppass,@BindingParam("comp")Component comp){
		if(user.toString().trim().equals("")){
			Clients.showNotification("Ingrese Usuario", Clients.NOTIFICATION_TYPE_ERROR, compuser, "end_before", 2500);
		}else{
			if(pass.toString().trim().equals("")){
				Clients.showNotification("Ingrese Contraseña", Clients.NOTIFICATION_TYPE_ERROR, comppass, "end_before", 2500);
			}else{
				ingresar(user.trim(),pass.trim(),comp);
			}
		}
	}
	public void ingresar(String user,String pass,Component comp){
		try {
			Encryptar encrip=new Encryptar();
			passEncrip=encrip.encrypt(pass);
			System.out.println(user+" , "+passEncrip);
			Object []resultadoLogin=auth.login(user, passEncrip);
			
			if((boolean)resultadoLogin[0]){
				System.out.println(resultadoLogin[2]+" loginVM");
				Execution exec=Executions.getCurrent();
				seshttp=(HttpSession)Sessions.getCurrent().getNativeSession();
				int codperfil=(int)resultadoLogin[3];
				seshttp.setAttribute("usuario", user);
				seshttp.setAttribute("clave", passEncrip);
				seshttp.setAttribute("perfil", codperfil);
				
				System.out.println("Perfil "+codperfil);
				Executions.getCurrent().sendRedirect("/panelAdmin.zul");
			}else{
				Clients.showNotification(resultadoLogin[2].toString(),Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",3000);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Command
	@NotifyChange({"clave"})
	public void verClave(){
		verclave=!verclave;
		if(verclave)
			clave="text";
		else
			clave="password";
	}
	public void inicioR(){
		
	}
}
