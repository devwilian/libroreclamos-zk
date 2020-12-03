package com.libro.viewModel;

import javax.servlet.http.HttpSession;

import org.bouncycastle.asn1.cms.OtherRevocationInfoFormat;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.util.media.*;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zk.ui.Component;

import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.CEmail;
import com.libro.util.CReSizeImage;
import com.libro.util.ScannUtil;
import com.lowagie.text.DocumentException;
import com.lowagie.toolbox.plugins.Txt2Pdf;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class crearEstadoVM {
	private CReclamo reclamo;
	private CSolucion solucion;
	private CSolucionado solucionado;
	private CSedeOcurrencia sedeocurrencia;
	private CAreaOcurrencia areaocurrencia;
	private CTipoDocumento tipodocumento;
	private CTipoProblema tipoproblema;	
	private CSolicitante solicitante;
	
	private CreclamoDAO reclamoDAO;
	private CsolucionDAO solucionDAO;
	private CsolucionadoDAO solucionadoDAO;
	
	private ArrayList<CSolucion> listaSolucion;
	private ArrayList<CMotivoReclamo>listaMotivo;
	
	private String idreclamo;
	private String nombres;
	private String apellidos;
	private String correo;
	private String detallesSolucion;
	private String rutaDoc;
	private String urlAux;
	private String urlDocs;
	private String otraSolucion;
	private String user;
	
	private boolean solicitud;
	private boolean verOtraSolucion;
	private boolean agregarbd;
	
	private HttpSession ses;
	@Init
	public void initVM(){
		agregarbd=false;
		solicitud=false;
		verOtraSolucion=false;
		idreclamo="";
		nombres="";
		apellidos="";
		correo="";
		rutaDoc="";
		urlAux="";
		urlDocs="";
		
		ses = (HttpSession)Sessions.getCurrent().getNativeSession();
		user=(String)ses.getAttribute("usuario");
		
		reclamo=new CReclamo();
		solucion=new CSolucion();
		solucionado=new CSolucionado();
		sedeocurrencia=new CSedeOcurrencia();
		areaocurrencia=new CAreaOcurrencia();
		tipodocumento=new CTipoDocumento();
		tipoproblema=new CTipoProblema();
		solicitante=new CSolicitante();
		
		reclamoDAO=new CreclamoDAO();
		solucionDAO=new CsolucionDAO();
		solucionadoDAO=new CsolucionadoDAO();
		
		listaSolucion=new ArrayList<CSolucion>();
		listaMotivo=new ArrayList<CMotivoReclamo>();
		
		iniciar();
	}
	public void iniciar(){
		solucionDAO.asignarListaSolucion(solucionDAO.recuperarSolucion());
		listaSolucion=solucionDAO.getListaSolucion();
	}
	@GlobalCommand
	@NotifyChange({"nombres","apellidos","correo","reclamo","solicitud"})
	public void cargarIdreclamo(@BindingParam("idreclamo")String valor,@BindingParam("nombres")String nom,@BindingParam("apellidos")String apell,@BindingParam("correo")String email){
		//System.out.println(valor+" - "+nom+" - "+apell+" - "+email);
		idreclamo=valor;
		nombres=nom.trim();
		apellidos=apell.trim();
		correo=email;
		reclamoDAO.asginarReclamo(reclamoDAO.recuperarReclamoPorCodigo(idreclamo));
		reclamo=reclamoDAO.getReclamo();
		
		solicitante.setCorreo(correo);
		solicitante.setApellidos(apellidos);
		solicitante.setNombres(nombres);
		
		if (!reclamo.getSolicitudreclamo().toString().trim().equals("")) {
			solicitud=true;
		}
	}
	@Command
	@NotifyChange({"verOtraSolucion","otraSolucion","agregarbd"})
	public void changeSolucion(@BindingParam("id")String id,@BindingParam("valor")String valor){
		if (id.equals("0")) {
			verOtraSolucion=true;
			otraSolucion="";
			
		}else{
			verOtraSolucion=false;
			agregarbd=false;
		}
		solucion.setIdsolucion(Integer.parseInt(id));
		solucion.setDetalles(valor);
		System.out.println(" Id reclamo "+id+" valor "+valor+" "+verOtraSolucion);
	}
	@Command
	public void changeOtraSolicion(@BindingParam("valor")String valor){
		if (!valor.equals("")) {
			otraSolucion=valor;
		}
	}
	@Command
	public void checkAgregarBD(){
		agregarbd=!agregarbd;
	}
	@Command
	public void changeDetalles(@BindingParam("valor")String valor){
		detallesSolucion=valor;
	}
	@Command
	@NotifyChange({"listaSolucion","agregarbd"})
	public void solucionar(@BindingParam("comp")Component comp) throws IOException, DocumentException{
		System.out.println("El usuario que esta solucionando es "+user);
		solucionado.setCoduser(user);
		if(valido(comp)){
			solucionado.setIdreclamo(reclamo.getIdreclamo());
			solucionado.setDoc(rutaDoc);
			solucionado.setDetalles(detallesSolucion);
			solucionado.setIdsolucion(solucion.getIdsolucion());
			if(verOtraSolucion){
				if(agregarbd){
					CsolucionDAO solucionDAO=new CsolucionDAO();
					int cod = solucionDAO.Codigo(solucionDAO.insertarOtraSolucion(otraSolucion));
					solucion.setIdsolucion(cod);
					solucion.setDetalles(otraSolucion);
					solucionado.setIdsolucion(cod);
					if (solucionadoDAO.isOperationCorrect(solucionadoDAO.insertarSolucionado(solucionado))) {
						enviarCorreo();
						Clients.showNotification("Problema Solucionado!", Clients.NOTIFICATION_TYPE_INFO, comp, "top_center", 2500);
						iniciar();
					}
				}else{
					System.out.println("solucionado codigo "+solucionado.getIdsolucion());
					int cod = solucionadoDAO.codigoSolucionado(solucionadoDAO.insertarSolucionado(solucionado));
					solucionDAO.isOperationCorrect(solucionDAO.insertarSolucion(cod,otraSolucion));
					enviarCorreo();	
					agregarbd=false;
					Clients.showNotification("Problema Solucionado!", Clients.NOTIFICATION_TYPE_INFO, comp, "top_center", 2500);
				}
			}else{
				if(solucionadoDAO.isOperationCorrect(solucionadoDAO.insertarSolucionado(solucionado))){
					enviarCorreo();
					agregarbd=false;
					Clients.showNotification("Problema Solucionado!", Clients.NOTIFICATION_TYPE_INFO, comp, "top_center", 2500);
				}
			}
		}
		BindUtils.postNotifyChange(null, null, agregarbd, "agregarbd");
		BindUtils.postNotifyChange(null, null, listaSolucion, "listaSolucion");
	}
	public void enviarCorreo() throws IOException, DocumentException{		
		CEmail email=new CEmail();
		if(email.enviarCorreoSolucion(reclamo, solicitante, sedeocurrencia, areaocurrencia, tipodocumento, tipoproblema,solucionado,solucion, listaMotivo,urlDocs))
			System.out.println("Se envio Correo solucion");
		else
			System.out.println("Error envio correo solucion");
	}
	@Command
	public void uploadFile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
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
					urlAux = ScannUtil.getPathAuxFolder() + file.getName();
					urlDocs= ScannUtil.getPathDocumentos()+file.getName();
					b = ScannUtil.uploadFileSolucion(file);
					File fichero = new File(urlAux);
					System.out.println(urlDocs);
					boolean eliminar=fichero.delete();
					Clients.showNotification(file.getName() + " Se subio al servidor.", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2700);
					//System.out.println("Archivo en la nube "+urlDocs);
				}
			}
	}
	@Command
	public void cancelar(@BindingParam("comp")Component comp){
		File fichero = new File(urlDocs);
		boolean eliminar=fichero.delete();
		Clients.showNotification("Se elimino el Archivo",Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2700);
	}
	@GlobalCommand
	@NotifyChange({"idreclamo","nombres","apellidos","correo","reclamo"})
	public void SolucionarReclamo(
			@BindingParam("reclamo")CReclamo preclamo,
			@BindingParam("solicitante")CSolicitante psolicitante,
			@BindingParam("sedeocurrencia")CSedeOcurrencia psedeocurrencia,
			@BindingParam("areaocurrencia")CAreaOcurrencia pareaocurrencia,
			@BindingParam("tipodocumento")CTipoDocumento ptipodocumento,
			@BindingParam("tipoproblema")CTipoProblema ptipoproblema,
			@BindingParam("listamotivo")ArrayList<CMotivoReclamo> plistamotivo)
	{		
		idreclamo=preclamo.getIdreclamo();
		nombres=psolicitante.getNombres();
		apellidos=psolicitante.getApellidos();
		correo=psolicitante.getCorreo();
		this.reclamo=preclamo;
		this.solicitante=psolicitante;
		this.sedeocurrencia=psedeocurrencia;
		this.areaocurrencia=pareaocurrencia;
		this.tipodocumento=ptipodocumento;
		this.tipoproblema=ptipoproblema;
		this.listaMotivo=plistamotivo;
		
	}
	@Command
	public void pasarDatos(){
		System.out.println("Algo pasara "+detallesSolucion);
	}
	public boolean valido(Component comp){
		boolean valido=true;
		if(solucion.getDetalles()==""){
			valido=false;
			Clients.showNotification("Seleccione una solucion", Clients.NOTIFICATION_TYPE_ERROR,comp, "end_before", 2500);
		}
		return valido;
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
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
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public CReclamo getReclamo() {
		return reclamo;
	}
	public void setReclamo(CReclamo reclamo) {
		this.reclamo = reclamo;
	}
	public ArrayList<CSolucion> getListaSolucion() {
		return listaSolucion;
	}
	public void setListaSolucion(ArrayList<CSolucion> listaSolucion) {
		this.listaSolucion = listaSolucion;
	}
	public boolean isSolicitud() {
		return solicitud;
	}
	public void setSolicitud(boolean solicitud) {
		this.solicitud = solicitud;
	}
	public String getDetallesSolucion() {
		return detallesSolucion;
	}
	public void setDetallesSolucion(String detallesSolucion) {
		this.detallesSolucion = detallesSolucion;
	}
	public boolean isVerOtraSolucion() {
		return verOtraSolucion;
	}
	public void setVerOtraSolucion(boolean verOtraSolucion) {
		this.verOtraSolucion = verOtraSolucion;
	}
	public boolean isAgregarbd() {
		return agregarbd;
	}
	public void setAgregarbd(boolean agregarbd) {
		this.agregarbd = agregarbd;
	}
}
