package com.libro.viewModel;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Messagebox;

import pe.com.erp.crypto.Encryptar;

import pe.com.erp.crypto.Encryptar;

import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.*;

public class updateUsuarioVM {
	private CUsuarioLogin oUsuarioUpdate;
	private CusuarioLoginDAO usuarioDao;
	private boolean disableContrasenia;
	private boolean visibleContrasenia;
	private boolean nuevoclick;
	private boolean subir;
	private boolean elegir;
	
	private String tipoContrasena;
	//================getter an setter===
	public CUsuarioLogin getoUsuarioUpdate() {
		return oUsuarioUpdate;
	}
	public boolean isSubir() {
		return subir;
	}
	public void setSubir(boolean subir) {
		this.subir = subir;
	}
	public boolean isElegir() {
		return elegir;
	}
	public void setElegir(boolean elegir) {
		this.elegir = elegir;
	}
	public String getTipoContrasena() {
		return tipoContrasena;
	}
	public void setTipoContrasena(String tipoContrasena) {
		this.tipoContrasena = tipoContrasena;
	}
	public void setoUsuarioUpdate(CUsuarioLogin oUsuarioUpdate) {
		this.oUsuarioUpdate = oUsuarioUpdate;
	}
	public CusuarioLoginDAO getUsuarioDao() {
		return usuarioDao;
	}
	public void setUsuarioDao(CusuarioLoginDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	public boolean isDisableContrasenia() {
		return disableContrasenia;
	}
	public void setDisableContrasenia(boolean disableContrasenia) {
		this.disableContrasenia = disableContrasenia;
	}
	public boolean isVisibleContrasenia() {
		return visibleContrasenia;
	}
	public void setVisibleContrasenia(boolean visibleContrasenia) {
		this.visibleContrasenia = visibleContrasenia;
	}
	
	@Init
	public void InitVM(){
		subir=true;
		elegir=false;
		usuarioDao=new CusuarioLoginDAO();
		oUsuarioUpdate=new CUsuarioLogin();
		tipoContrasena="password";
		visibleContrasenia=false;
		try{
//				System.out.println("Aqui esta la contraseña desencriptada-->"+encrip.decrypt("cyS249O3OHZTsG0ww1rYrw=="));
			Execution exec = Executions.getCurrent();
			HttpSession ses = (HttpSession)Sessions.getCurrent().getNativeSession();
			String user=(String)ses.getAttribute("usuario");
		    String pas=(String)ses.getAttribute("clave");
		    //System.out.println(user+" desde la sesion "+pas);
		    if(user!=null && pas!=null)
		    {
		    	oUsuarioUpdate=(CUsuarioLogin)ses.getAttribute("usuarioActual");
		    }
		    /******************************************/
		}
		catch(Exception e)
		{
			irALogin();
		}
		Encryptar encrip= new Encryptar();
		try {
			System.out.println("Contraseña updateUsuarioVM.java "+oUsuarioUpdate.getcClave());
			oUsuarioUpdate.setcClave(encrip.decrypt(oUsuarioUpdate.getcClave()));
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void irALogin(){
		Executions.getCurrent().sendRedirect("/login.zul");
	}
	@Command
	@NotifyChange({"tipoContrasena"})
	public void vercontrasena(){
		visibleContrasenia=!visibleContrasenia;
		System.out.println(visibleContrasenia);
		if(visibleContrasenia){
			tipoContrasena="text";
		}else{
			tipoContrasena="password";
		}
	}
	@Command
	@NotifyChange({"subir","elegir"})
	public void subirFoto(){
		subir=true;
		elegir=false;
	}
	@Command
	@NotifyChange({"subir","elegir"})
	public void elegirFoto(){
		subir=false;
		elegir=true;
	}
	@Command
		@NotifyChange({"oUsuarioUpdate","visibleContrasenia","disableContrasenia"})
		public void actualizarUsuario(@BindingParam("componente")Component comp) throws KeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, GeneralSecurityException, IOException
		{
			visibleContrasenia=false;
			disableContrasenia=false;
			System.out.println("quiero saber si encrip o no"+oUsuarioUpdate.getcClave());
			if(!validoParaActualizar(comp))
				return;
			Encryptar encriptar=new Encryptar();
			try {
				oUsuarioUpdate.setcClave(encriptar.encrypt(oUsuarioUpdate.getcClave()));
			} catch (InvalidKeyException | UnsupportedEncodingException
					| NoSuchAlgorithmException | NoSuchPaddingException
					| InvalidAlgorithmParameterException
					| IllegalBlockSizeException | BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/**Actualizar datos de la etiqueta en la BD**/
			boolean correcto=usuarioDao.isOperationCorrect(usuarioDao.modoficarUsuario(oUsuarioUpdate));
			System.out.println("termina de entra aqui_?");
			oUsuarioUpdate.setcClave(encriptar.decrypt(oUsuarioUpdate.getcClave()));
			if(correcto)
				Clients.showNotification("Datos de usuario correctamente actualizados", Clients.NOTIFICATION_TYPE_INFO, comp,"before_start",2700);
			else
				Clients.showNotification("Error al actualizar datos", Clients.NOTIFICATION_TYPE_INFO, comp,"before_start",2700);
		}

	public boolean validoParaActualizar(Component comp) throws KeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, GeneralSecurityException, IOException
		{
			boolean valido=true;
			if(oUsuarioUpdate.getcClave().equals("") || !esContraseniaSegura(oUsuarioUpdate.getcClave()))
			{
				valido=false;
				Clients.showNotification("Se necesita un password seguro",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}else if(oUsuarioUpdate.getcNroDoc().equals("") || !isInteger(oUsuarioUpdate.getcNroDoc()))
			{
				valido=false;
				Clients.showNotification("Necesita ingresar un DNI valido!",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}
			if(oUsuarioUpdate.getcCorreo().equals("") || !mailValido(oUsuarioUpdate.getcCorreo()))
			{
				valido=false;
				Clients.showNotification("Necesita ingresar un correo valido!",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}else if(oUsuarioUpdate.getcNombres().equals(""))
			{
				valido=false;
				Clients.showNotification("Se necesita llenar su nombre",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}else if(oUsuarioUpdate.getdFechaNac().equals(""))
			{
				valido=false;
				Clients.showNotification("Se necesita una fecha de nacimiento",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}else if(oUsuarioUpdate.getImgUsuario().equals(""))
			{
				valido=false;
				Clients.showNotification("Se necesita una imagen para su perfil",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2700);
			}
		return valido;
		}



	//=================metodos de modificaciones en actualizar usuario======
		@Command
		public void modificarDni(@BindingParam("dni")String dni,@BindingParam("componente")Component comp)
		{
			if(!isInteger(dni)){
				Clients.showNotification("El numero de DNI tiene que estar compuesto por numeros",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);	
			}
		}
		
		@Command
		public void modificarContrasenia(@BindingParam("clave")String clave,@BindingParam("componente")Component comp)
		{
			int cantidadLetrasMinus=0;
			int cantidadLetrasMayus=0;
			int cantidadNumeros=0;
			int cantidadSimbolos=0;
	        int cantidadOtros=0;
			for(int i=0; i<clave.length();i++)
			{
				char pedaso=clave.charAt(i);
				String pas=String.valueOf(pedaso);
				if(pas.matches("[a-z]")){
					cantidadLetrasMinus++;
				}else if(pas.matches("[A-Z]")){
					cantidadLetrasMayus++;
				}else if(pas.matches("[0-9]")){
					cantidadNumeros++;
				}else if(pas.matches("[#@$%]")){
					cantidadSimbolos++;
				}else{
					cantidadOtros++;
				}
			}
			int suma=cantidadLetrasMayus+cantidadLetrasMinus+cantidadNumeros+cantidadSimbolos;
			if(cantidadLetrasMinus<1){
				Clients.showNotification("La contrasenia debe contener al menos un caracter en minuscula",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}else if(cantidadLetrasMayus<1){
				Clients.showNotification("La contrasenia debe contener al menos un caracter en MAYUSCULA ",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}else if(cantidadNumeros<1){
				Clients.showNotification("La contrasenia debe contener al menos un numero ",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}else if(cantidadSimbolos<1){
				Clients.showNotification("La contrasenia debe contener al menos uno de estos simbolos {#,@,$,%} ",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}else if(suma<8){
				Clients.showNotification("La contrasenia debe tener un tamanio minimo de 8 ",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}else if(cantidadOtros>0){
				Clients.showNotification("La contrasenia no puede contener otros simbolos que no pertenescan a {#,@,$,%}",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}
		}
		
		public boolean esContraseniaSegura(String clave)
		{
			Pattern pat = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[#@$%]).{8,}$");
			System.out.println("entra a contra segura");
		       Matcher mat = pat.matcher(clave);
		       if(mat.find()){
		    	   System.out.println("encuentra....");
		    	   return true;
		       }
		       else {
		    	   return false;
		       }
		}
		
		@Command
		public void modificarCelular(@BindingParam("celular")String celular,@BindingParam("componente")Component comp)
		{
			if(!isInteger(celular)){
				Clients.showNotification("El numero de celular tiene que estar compuesto por numeros",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}
		}
		
		@Command
		public void modificarCorreo(@BindingParam("correo")String correo,@BindingParam("componente")Component comp)
		{
			if(!mailValido(correo)){
				Clients.showNotification("El formato de correo no es valido",Clients.NOTIFICATION_TYPE_ERROR,comp,"before_start",2500);
			}
		}


	@Command
		public void uploadImagen(@BindingParam("componente")final Component comp) {
				 Fileupload.get(new EventListener<UploadEvent>(){
						public void onEvent(UploadEvent event) {
							org.zkoss.util.media.Media media = event.getMedia();
							if (media instanceof org.zkoss.image.Image) {
								org.zkoss.image.Image img = (org.zkoss.image.Image) media;
								//Con este metodo(uploadFile) de clase guardo la imagen en la ruta del servidor
					            boolean b=ScannUtil.uploadAuxFolder(img);
								// ================================
								String urlImagenAux = ScannUtil.getPathAuxFolder() + img.getName();
								String urlImagenReal= ScannUtil.getPathImagenUsuario()+img.getName();
								if(!CReSizeImage.tamanioSuficiente(urlImagenAux))
								{
									CReSizeImage.copyImage(urlImagenAux,urlImagenReal,img.getFormat());
									File fichero = new File(urlImagenAux);
									boolean eliminar=fichero.delete();
								}else
								{
									b = ScannUtil.uploadFileUsuario(img);
									File fichero = new File(urlImagenAux);
									boolean eliminar=fichero.delete();
								}
					            asignarUrlImagenServicio(img.getName());
					            Clients.showNotification(img.getName()+" Se inserto",Clients.NOTIFICATION_TYPE_INFO,comp,"before_start",2700);
							} else {
								Messagebox.show(media+"Error", "Error", Messagebox.OK, Messagebox.ERROR);
									}
						}
				     });
		}
		public void asignarUrlImagenServicio(String url)
		{
			System.out.println("==>:::"+url);
			oUsuarioUpdate.setImgUsuario("img/usuarios/"+url);
			BindUtils.postNotifyChange(null, null, oUsuarioUpdate,"imgUsuario");
		}
		
		@Command
		@NotifyChange({"disableContrasenia","visibleContrasenia"})
		public void habilitarContrasenia()
		{
			if(!nuevoclick)
			{
				disableContrasenia=true;
				visibleContrasenia=true;
				nuevoclick=true;
			}else{
				disableContrasenia=false;
				visibleContrasenia=false;
				nuevoclick=false;
			}
		}


	public boolean mailValido(String mail)
		{
			 Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		       Matcher mat = pat.matcher(mail);
		       if(mat.find()){
		          return true;
		       }else
		          return false;
		}
		public boolean isInteger(String n)
		{
			try {
				Integer.parseInt(n);
				return true;
			} catch (NumberFormatException nfe){
				return false;
			}
		}


}
