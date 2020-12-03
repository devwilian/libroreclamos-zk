package com.libro.viewModel;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.*;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Image;
import org.zkoss.zul.Messagebox;

import com.libro.model.CUsuarioLogin;
import com.libro.util.*;
import com.libro.dao.*;

import pe.com.erp.crypto.Encryptar;

public class actualizarusuarioVM {
	@Wire
	Image image;
	private boolean subir;
	private boolean elegir;
	private CUsuarioLogin oUsuarioUpdate;
	private CusuarioLoginDAO usuarioDao;
	
	@Init
	public void initVM(){
		
		subir=true;
		elegir=false;
		usuarioDao=new CusuarioLoginDAO();
		oUsuarioUpdate=new CUsuarioLogin();
		try{
//				System.out.println("Aqui esta la contraseña desencriptada-->"+encrip.decrypt("cyS249O3OHZTsG0ww1rYrw=="));
				Execution exec = Executions.getCurrent();
				HttpSession ses = (HttpSession)Sessions.getCurrent().getNativeSession();
				String user=(String)ses.getAttribute("usuario");
			    String pas=(String)ses.getAttribute("clave");
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
	public CUsuarioLogin getoUsuarioUpdate() {
		return oUsuarioUpdate;
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
}
