package com.libro.viewModel;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import java.util.*;
import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.Fotos10;
import com.libro.util.ScannUtil;

public class reclamoVM {
	private CreclamoDAO reclamoDAO;
	private CtipoProblemaDAO tipoproblemaDAO;
	private CareaOcurrenciaDAO areaocurrenciaDAO;
	private CsedeOcurrenciaDAO sedeocurrenciaDAO;
	private CtipoDocumentoDAO tipodocumentoDAO;
	private CsolicitanteDAO solicitanteDAO;
	private CmotivoReclamoDAO motivoreclamoDAO;
	private CGaleriaDAO galeriaDAO;
	
	private CReclamo reclamo;
	private CTipoProblema tipoProblema;
	private CAreaOcurrencia areaOcurrencia;
	private CSedeOcurrencia sedeOcurrencia;
	private CTipoDocumento tipoDocumento;
	private CSolicitante solicitante;
	private CGaleria galeria;
	
	private String idreclamo;
	
	private ArrayList<CMotivoReclamo> listaMotivo;
	private ArrayList<CGaleria> listaGaleria;
	
	private boolean solicitud;
	private boolean crearEstado;
	private boolean razonsocial;
	private boolean verMarcarProceso;
	private boolean verImagenesSubidas;
	private boolean vertxtSolucionado;
	private boolean vertxtProceso;
	
	private String fotos[]={"","","","","","","","","",""};
	@Init
	public void initVM(){
		idreclamo="";
		solicitud=false;
		crearEstado=false;
		razonsocial=false;
		verMarcarProceso=true;
		verImagenesSubidas=false;
		vertxtSolucionado=true;
		vertxtProceso=true;
		
		reclamo=new CReclamo();
		tipoProblema=new CTipoProblema();
		areaOcurrencia=new CAreaOcurrencia();
		sedeOcurrencia=new CSedeOcurrencia();
		tipoDocumento=new CTipoDocumento();
		solicitante=new CSolicitante();
		galeria=new CGaleria();
		
		reclamoDAO=new CreclamoDAO();
		tipodocumentoDAO=new CtipoDocumentoDAO();
		tipoproblemaDAO=new CtipoProblemaDAO();
		areaocurrenciaDAO=new CareaOcurrenciaDAO();
		sedeocurrenciaDAO=new CsedeOcurrenciaDAO();
		solicitanteDAO=new CsolicitanteDAO();
		motivoreclamoDAO=new CmotivoReclamoDAO();
		galeriaDAO=new CGaleriaDAO();
		
		listaMotivo=new ArrayList<CMotivoReclamo>();
		listaGaleria=new ArrayList<CGaleria>();
	}
	public CReclamo getReclamo() {
		return reclamo;
	}
	public void setReclamo(CReclamo reclamo) {
		this.reclamo = reclamo;
	}
	@GlobalCommand
	@NotifyChange({"solicitud","idreclamo","reclamo","solicitante","tipoDocumento","areaOcurrencia","sedeOcurrencia","listaMotivo","razonsocial","verMarcarProceso","listaGaleria"})
	public void pasarCodigoReclamo(@BindingParam("id")String id,@BindingParam("valor")String aux){
		System.out.println("esto viene de "+aux);
		if(aux.equals("listareclamos")){
			verMarcarProceso=true;
		}else if(aux.equals("listaproceso")){
			verMarcarProceso=false;
		}
		//System.out.println("Codigo desde reclamoVM "+id);
		idreclamo=id;
		reclamoDAO.asginarReclamo(reclamoDAO.recuperarReclamoPorCodigo(idreclamo));
		reclamo=reclamoDAO.getReclamo();
		
		solicitanteDAO.asginarSolicitantes(solicitanteDAO.recuperarSolicitanteCodigo(reclamo.getSolicitante()));
		solicitante=solicitanteDAO.getSolicitante();
		
		tipodocumentoDAO.asignarTipoDocuemnto(tipodocumentoDAO.obtenerTiposdeDocumentoCodigo(solicitante.getTipodocumento()));
		tipoDocumento=tipodocumentoDAO.getTipodoc();
		
		sedeocurrenciaDAO.asignarSedeOcurrencia(sedeocurrenciaDAO.obtenerSedeOcurrenciaCodigo(reclamo.getSedeocurrencia()));
		sedeOcurrencia=sedeocurrenciaDAO.getSedeOcurrencia();
		
		areaocurrenciaDAO.asignarAreaOcurrencia(areaocurrenciaDAO.obtenerAreaOcurrenciaporCodigo(reclamo.getAreaocurrencia()));
		areaOcurrencia=areaocurrenciaDAO.getAreaOcurrencia();
		if(!reclamo.getSolicitudreclamo().trim().equals("")){
			solicitud=true;
		}
		
		listaMotivo=new ArrayList<CMotivoReclamo>();
		motivoreclamoDAO.asignarListaMotivoporMotivo(motivoreclamoDAO.obtenerMotivoReclamoporReclamo(reclamo.getIdreclamo()));
		for (int i = 0; i < motivoreclamoDAO.getListaMotivoReclamo().size(); i++) {
			//System.out.println(motivoreclamoDAO.getListaMotivoReclamo().get(i)+" - "+motivoreclamoDAO.getListaMotivoReclamo().get(i));
			if(motivoreclamoDAO.getListaMotivoReclamo().get(i).getIdmotivoreclamo()==0){
				motivoreclamoDAO.asignarMotivo(motivoreclamoDAO.obtenerOtroMotivoReclamoporReclamo(reclamo.getIdreclamo()));
				listaMotivo.add(motivoreclamoDAO.getMotivoReclamo());
			}else{
				listaMotivo.add(motivoreclamoDAO.getListaMotivoReclamo().get(i));
			}
		}
		listaGaleria=new ArrayList<CGaleria>();
		if(reclamo.isDocmultimedia()){
			galeriaDAO.asignarListaImagenesReclamo(galeriaDAO.recuperarImagenesPaqueteBD(reclamo.getIdreclamo()));
			listaGaleria=galeriaDAO.getListaImagenesPaquete();
			for (int i = 0; i < listaGaleria.size(); i++) {
				fotos[i]=listaGaleria.get(i).getcRutaImagen();
			}
		}
		reclamo.setListaImagenes(listaGaleria);
		//System.out.println("Reclamo con imagenes");
		for (int i = 0; i < fotos.length; i++) {
			System.out.println(fotos[i]);
		}
		tipoproblemaDAO.asignarTipoProblema(tipoproblemaDAO.recuperarTipoProblema(reclamo.getTipoproblema()));
		tipoProblema=tipoproblemaDAO.getTipoProblema();
		if(tipoDocumento.getDescripcion().toLowerCase().equals("ruc")){
			razonsocial=true;
		}
	}
	@Command
	public void invocaImagenesSubidas() {
		Window win_imagenes = (Window) Executions.createComponents("/imagenesPaquetes.zul", null, null);
		win_imagenes.doModal();
	}
	
	@Command
	@NotifyChange({"verImagenesSubidas"})
	public void invocaImagenesReclamo(){
		verImagenesSubidas=true;
	}
	public boolean exiteOtroMotivo(ArrayList<CMotivoReclamo>listaMotivo){
		boolean val=false;
		for (int i = 0; i < listaMotivo.size(); i++) {
			//System.out.println("Otro Motivo "+listaMotivo.get(i).getIdmotivoreclamo()+" "+listaMotivo.get(i).getDescripcion());
			if(listaMotivo.get(i).getIdmotivoreclamo()==0){
				val=true;
			}
		}
		return val;
	}
	@Command
	public void marcarComoProceso(@BindingParam("comp")Component comp){
		String aux=reclamoDAO.recuperarMensaje(reclamoDAO.marcaEnProceso(reclamo.getIdreclamo()));
		if(aux.equals("correcto")){
			Clients.showNotification("Se marco en proceso", Clients.NOTIFICATION_TYPE_INFO, comp, "before_center", 2500);
			BindUtils.postNotifyChange(null,null,this,"listadeReclamos");
		}else if(aux.equals("error")){
			Clients.showNotification("El Reclamo ya se mando a procesos", Clients.NOTIFICATION_TYPE_WARNING, comp, "before_center", 2500);
			vertxtProceso=false;
		}
		BindUtils.postNotifyChange(null, null, vertxtProceso, "vertxtProceso");
	}
	@Command
	@NotifyChange({"crearEstado"})
	public void verCrearEstado(@BindingParam("comp")Component comp){
		CreclamoDAO rec=new CreclamoDAO();
		rec.asginarReclamo(rec.recuperarReclamoPorCodigo(reclamo.getIdreclamo()));
		if(rec.getReclamo().isSolucionado()){
			vertxtSolucionado=false;
			vertxtProceso=false;
			Clients.showNotification("EL reclamo ya esta Solucionado", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}else{
			crearEstado=true;
		}
		BindUtils.postNotifyChange(null, null, vertxtSolucionado, "vertxtSolucionado");
		BindUtils.postNotifyChange(null, null, vertxtProceso, "vertxtProceso");
	}
	@GlobalCommand
	@NotifyChange({"crearEstado","listadeReclamos"})
	public void cerrarCrearEstado(){
		crearEstado=false;
		mostrarListadeReclamos();
	}
	public void mostrarListadeReclamos(){
		
	}
	public String getIdreclamo() {
		return idreclamo;
	}
	public void setIdreclamo(String idreclamo) {
		this.idreclamo = idreclamo;
	}
	public CTipoProblema getTipoProblema() {
		return tipoProblema;
	}
	public void setTipoProblema(CTipoProblema tipoProblema) {
		this.tipoProblema = tipoProblema;
	}
	public CAreaOcurrencia getAreaOcurrencia() {
		return areaOcurrencia;
	}
	public void setAreaOcurrencia(CAreaOcurrencia areaOcurrencia) {
		this.areaOcurrencia = areaOcurrencia;
	}
	public CSedeOcurrencia getSedeOcurrencia() {
		return sedeOcurrencia;
	}
	public void setSedeOcurrencia(CSedeOcurrencia sedeOcurrencia) {
		this.sedeOcurrencia = sedeOcurrencia;
	}
	public CTipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(CTipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public CSolicitante getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(CSolicitante solicitante) {
		this.solicitante = solicitante;
	}
	public boolean isSolicitud() {
		return solicitud;
	}
	public void setSolicitud(boolean solicitud) {
		this.solicitud = solicitud;
	}
	public ArrayList<CMotivoReclamo> getListaMotivo() {
		return listaMotivo;
	}
	public void setListaMotivo(ArrayList<CMotivoReclamo> listaMotivo) {
		this.listaMotivo = listaMotivo;
	}
	public boolean isCrearEstado() {
		return crearEstado;
	}
	public void setCrearEstado(boolean crearEstado) {
		this.crearEstado = crearEstado;
	}
	public CGaleria getGaleria() {
		return galeria;
	}
	public void setGaleria(CGaleria galeria) {
		this.galeria = galeria;
	}
	public ArrayList<CGaleria> getListaGaleria() {
		return listaGaleria;
	}
	public void setListaGaleria(ArrayList<CGaleria> listaGaleria) {
		this.listaGaleria = listaGaleria;
	}
	public boolean isRazonsocial() {
		return razonsocial;
	}
	public void setRazonsocial(boolean razonsocial) {
		this.razonsocial = razonsocial;
	}
	public boolean isVerMarcarProceso() {
		return verMarcarProceso;
	}
	public void setVerMarcarProceso(boolean verMarcarProceso) {
		this.verMarcarProceso = verMarcarProceso;
	}
	public String[] getFotos() {
		return fotos;
	}
	public void setFotos(String[] fotos) {
		this.fotos = fotos;
	}
	public boolean isVerImagenesSubidas() {
		return verImagenesSubidas;
	}
	public void setVerImagenesSubidas(boolean verImagenesSubidas) {
		this.verImagenesSubidas = verImagenesSubidas;
	}
}
