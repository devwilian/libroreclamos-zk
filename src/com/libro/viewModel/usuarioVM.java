package com.libro.viewModel;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;

import com.libro.model.*;
import com.libro.util.CEmail;
import com.libro.util.ScannUtil;
import com.lowagie.text.DocumentException;

import pe.com.erp.crypto.Encryptar;

import com.libro.dao.*;
public class usuarioVM {
	private boolean elegir;
	private boolean subir;
	private boolean existente;
	private boolean nuevo;
	
	private CUsuarioLogin usuarioLogin;
	private CUsuario usuario;
	private CAcceso acceso;
	private CaccesoDAO accesoDAO;
	private CusuarioLoginDAO usuarioDAO;
	private CAcceso accesoaux;
	
	private String nombrePerfil;
	private String urlfoto;
	private HttpSession ses;
	
	private ArrayList<CPerfil> listaPerfil;
	
	private char[] caracteres={'0','1','2','3','4','5','6','8','9',
			   'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
			   '@','#','$','%',
			   'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	
	@Init
	public void initVM(){
		subir=true;
		elegir=false;
		existente=false;
		nuevo=false;
		nombrePerfil="";
		urlfoto="img/usuarios/user.jpg";
		
		usuario=new CUsuario();
		acceso=new CAcceso();
		accesoaux=new CAcceso();
		
		accesoDAO=new CaccesoDAO();
		usuarioDAO=new CusuarioLoginDAO();
		
		listaPerfil=new ArrayList<CPerfil>();
		
		accesoDAO.asignarListaPerfil(accesoDAO.recuperarPerfilesBD());
		listaPerfil=accesoDAO.getListaPerfil();
		
//		ses = (HttpSession)Sessions.getCurrent().getNativeSession();
//	    accesoaux=(CAcceso)ses.getAttribute("accesos");
	}
	@Command
	public void changeDNI(@BindingParam("valor")String valor){
		if (!valor.trim().equals("")) {
			usuario.setNrodoc(valor);
			usuario.setIdusuario(valor);
		}
	}
	@Command
	public void changeNombres(@BindingParam("valor")String valor){
		if (!valor.trim().equals("")) {
			usuario.setNombres(valor);
		}
	}
	@Command
	public void changeApellidos(@BindingParam("valor")String valor){
		if (!valor.trim().equals("")) {
			usuario.setApellidos(valor);
		}
	}
	@Command
	public void changeFechaNacimiento(@BindingParam("valor")Date valor){
		if (!valor.toString().trim().equals("")) {
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			String Fecha=sdf.format(valor);
			System.out.println("fecha es:"+Fecha);
			String dia=Fecha.substring(0,2);
			String mes=Fecha.substring(3,5);
			String anio=Fecha.substring(6,10);
			/*************Fecha Inicio*******************/
			Calendar cal=Calendar.getInstance();
			cal.set(Integer.parseInt(anio),Integer.parseInt(mes)-1,Integer.parseInt(dia));
			/*************Fecha Arribo***********************/
			usuario.setFechanacimiento(cal.getTime());
		}
	}
	@Command
	@NotifyChange({"existente","nuevo"})
	public void perfilExistente(){
		existente=true;
		nuevo=false;
	}
	@Command
	@NotifyChange({"existente","nuevo"})
	public void perfilNuevo(){
		existente=false;
		nuevo=true;
	}
	@Command
	public void changeCorreo(@BindingParam("valor")String valor){
		if (!valor.toString().trim().equals("")) {
			usuario.setCorreo(valor);
		}
	}
	@Command
	public void changeSexo(@BindingParam("valor")String valor){
		usuario.setSexo(valor);
	}
	@Command
	public void changePerfil(@BindingParam("valor")String valor){
		System.out.println("Perfil "+valor);
		if (!valor.trim().equals("")) {
			usuario.setIdperfil(Integer.parseInt(valor));
		}
	}
	@Command
	public void changeNombrePerfil(@BindingParam("valor")String valor){
		System.out.println("Nombre de Perfil "+valor);
		if (!valor.trim().equals("")) {
			nombrePerfil=valor;
		}
	}
	
	@Command
	public void setAcceso(@BindingParam("opcion")String valor){
		
		if(valor.equals("1")){
			if(acceso.isAccesoregusuario())acceso.setAccesoregusuario(false);
			else acceso.setAccesoregusuario(true);
		}else if(valor.equals("2")){
			if(acceso.isAccesorecibidos())acceso.setAccesorecibidos(false);
			else acceso.setAccesorecibidos(true);
		}else if(valor.equals("3")){
			if(acceso.isAccesoprocesos())acceso.setAccesoprocesos(false);
			else acceso.setAccesoprocesos(true);
		}else if(valor.equals("4")){
			if(acceso.isAccesoreportes())acceso.setAccesoreportes(false);
			else acceso.setAccesoreportes(true);
		}else if(valor.equals("5")){
			if(acceso.isAccesolistausuario())acceso.setAccesolistausuario(false);
			else acceso.setAccesolistausuario(true);
		}else if(valor.equals("6")){
			if(acceso.isAccesoconfiguracion())acceso.setAccesoconfiguracion(false);
			else acceso.setAccesoconfiguracion(true);
		}
		System.out.println("acceso a "+acceso.isAccesoreportes());
	}
	@Command
	@NotifyChange({"usuario"})
	public void guardarUsuario(@BindingParam("comp")Component comp) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, DocumentException{
		if(valido(comp)){
			if(existente){
				insertarUsuario(comp);
			}else if(nuevo){
				int idperfil = crearPerfil();
				usuario.setIdperfil(idperfil);
				//insertarUsuario(comp);
				if (insertarUsuario(comp)) {
					usuario=new CUsuario();
					BindUtils.postNotifyChange(null, null, this,"usuario");
				}
			}
		}
	}
	public boolean insertarUsuario(Component comp) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, DocumentException{
		//insertar usuario
		String contrasenia="";
		for(int i=0;i<8;i++)
		{
			Random rdn=new Random();
			int n=rdn.nextInt(65);
			contrasenia+=caracteres[n];
		}
		Encryptar encrip= new Encryptar();
		String auxContrasenia=contrasenia;
		contrasenia=encrip.encrypt(contrasenia);
		usuario.setClave(contrasenia);
	
		if (usuarioDAO.isOperationCorrect(usuarioDAO.insertarUsuario(usuario))) {
			enviarCorreo(auxContrasenia);
			Clients.showNotification("Se inserto el usuario!", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 3000);
		}
		return true;
	}
	public void enviarCorreo(String auxContra) throws IOException, DocumentException{
		CEmail email=new CEmail();
		email.enviarCorreoNuevoUsuario(usuario, auxContra);
	}
	public int crearPerfil(){
		int cod=accesoDAO.codigo(accesoDAO.insertarAcceso(acceso));
		int codperfil=accesoDAO.codigo(accesoDAO.insertarPerfil(nombrePerfil,cod));
		return codperfil;
	}
	public boolean valido(Component comp){
		boolean valido =true;
		if(usuario.getApellidos()==""){
			valido=false;
			Clients.showNotification("Apellido incorrecto!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(usuario.getCorreo()==""){
			valido=false;
			Clients.showNotification("Correo incorrecto!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(usuario.getFechanacimiento().toString()==""){
			valido=false;
			Clients.showNotification("Fecha Nacimiento!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(usuario.getNombres()==""){
			valido=false;
			Clients.showNotification("Nombres incorrecto!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(usuario.getNrodoc()==""){
			valido=false;
			Clients.showNotification("Nro documento incorrecto!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(usuario.getSexo()==""){
			valido=false;
			Clients.showNotification("Error en sexo!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
		if(existente){
			System.out.println(usuario.getIdperfil());
			if(usuario.getIdperfil()==0){
				valido=false;
				Clients.showNotification("Seleccione un perfil!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
			}
		}
		if(nuevo){
			if(nombrePerfil.equals("")){
				valido=false;
				Clients.showNotification("Error en el nombre de perfil!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
			}
		}
		return valido;
	}
	@Command
	@NotifyChange({"urlfoto"})
	public void uploadImagenes(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
			@BindingParam("reclamo")CReclamo reclamo,
			@BindingParam("comp") final Component comp)
	{
		org.zkoss.util.media.Media[] listaMedias = event.getMedias();
		//System.out.println("Lista Medias "+listaMedias.length);
		if (listaMedias != null) {
				for (Media media : listaMedias) {
					Media file = (Media) media;
					// Con este metodo(uploadFile) de clase guardo la imagen
					// en la ruta del servidor
					boolean b=ScannUtil.uploadAuxFolder(file);
					// ================================
					String urlAux = ScannUtil.getPathAuxFolder() + file.getName();
					String urlDocs= ScannUtil.getPathDocumentos()+file.getName();
					b = ScannUtil.uploadFileSolucion(file);
					File fichero = new File(urlAux);
					urlfoto=urlAux;
					boolean eliminar=fichero.delete();
					Clients.showNotification(file.getName() + " Se subio al servidor.", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2700);
					//System.out.println("Archivo en la nube "+urlDocs);
				}
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
	public boolean isElegir() {
		return elegir;
	}
	public void setElegir(boolean elegir) {
		this.elegir = elegir;
	}
	public boolean isSubir() {
		return subir;
	}
	public void setSubir(boolean subir) {
		this.subir = subir;
	}
	public CUsuarioLogin getUsuarioLogin() {
		return usuarioLogin;
	}
	public void setUsuarioLogin(CUsuarioLogin usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}
	public boolean isExistente() {
		return existente;
	}
	public void setExistente(boolean existente) {
		this.existente = existente;
	}
	public boolean isNuevo() {
		return nuevo;
	}
	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}
	public CUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(CUsuario usuario) {
		this.usuario = usuario;
	}
	public CAcceso getAcceso() {
		return acceso;
	}
	public void setAcceso(CAcceso acceso) {
		this.acceso = acceso;
	}
	public String getNombrePerfil() {
		return nombrePerfil;
	}
	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}
	public ArrayList<CPerfil> getListaPerfil() {
		return listaPerfil;
	}
	public void setListaPerfil(ArrayList<CPerfil> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}
	public String getUrlfoto() {
		return urlfoto;
	}
	public void setUrlfoto(String urlfoto) {
		this.urlfoto = urlfoto;
	}
	public CAcceso getAccesoaux() {
		return accesoaux;
	}
	public void setAccesoaux(CAcceso accesoaux) {
		this.accesoaux = accesoaux;
	}
}
