package com.libro.viewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.*;
import org.zkoss.json.JSONObject;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.libro.util.RecaptchaVerifier;
import com.lowagie.text.DocumentException;
import com.sun.xml.internal.ws.encoding.MtomCodec.MtomStreamWriterImpl;
import com.libro.util.*;
import com.libro.model.*;
import com.a.a.a.b.i;
import com.libro.dao.*;

@ToServerCommand("verify")
public class formReclamoVM extends SelectorComposer<Component>{
	//VARIABLES GENERALES
	private CSolicitante solicitante;
	private CReclamo reclamo;
	private CTipoDocumento tipoDocumento;
	private CSedeOcurrencia sedeOcurrencia;
	private CAreaOcurrencia areaOcurrencia;
	private CTipoProblema tipoProblema;
	private CMotivoReclamo otroMotivoReclamo;
	private CEmpresa empresa;
	
	private CempresaDAO empresaDAO;
	private CtipoDocumentoDAO tipoDocumentoDAO;
	private CsedeOcurrenciaDAO sedeOcurrenciaDAO;
	private CareaOcurrenciaDAO areaOcurrenciaDAO;
	private CmotivoReclamoDAO motivoReclamoDAO;
	private CreclamoDAO reclamoDAO;
	private CsolicitanteDAO solicitanteDAO;
	private CGaleriaDAO galeriaDAO;
	
	private ArrayList<CTipoDocumento> listatipoDocumento;
	private ArrayList<CSedeOcurrencia> listasedeOcurrencia;
	private ArrayList<CAreaOcurrencia> listaareaOcurrencia;
	private ArrayList<CMotivoReclamo> listamotivoReclamo;
	private ArrayList<String> listamotivo; 
	private ArrayList<CMotivoReclamo> listaMRS; 
	private ArrayList<CGaleria> listaGaleriaReclamo;
	
	private boolean activarTipoRuc;
	private boolean activarOtroMotivo;
	private boolean pasofinal;
	private boolean mostrarEnviar;
	private boolean verFormulario;
	
	private String apellidopaterno;
	private String apellidomaterno;
	private String correo;
	private String correoconfir;
	private String detallesOtroMotivo;
	private String detallereclamooqueja;
	private String solicitudreclamooqueja;
	
	//VARIABLES DE CAPTCHA
	
	private boolean disabled;
	private boolean checked;
	private boolean verForm;
	
	private String SECRET; //from reCaptcha
	private String PUBLIC;
	private String fecha;
	HttpSession seshttp;
	
	@Init
	public void initVM(){
		verForm=false;
		try {
			
			seshttp=(HttpSession)Sessions.getCurrent().getNativeSession();
			seshttp.setMaxInactiveInterval(5*60);	
			boolean val=(boolean)seshttp.getAttribute("valido");
			disabled=true;
			checked=false;
			activarTipoRuc=false;
			activarOtroMotivo=false;
			pasofinal=false;
			mostrarEnviar=false;
			
			listatipoDocumento=new ArrayList<CTipoDocumento>();
			listasedeOcurrencia=new ArrayList<CSedeOcurrencia>();
			listaareaOcurrencia=new ArrayList<CAreaOcurrencia>();
			listamotivoReclamo=new ArrayList<CMotivoReclamo>();
			listamotivo=new ArrayList<String>();
			listaMRS=new ArrayList<CMotivoReclamo>();
			listaGaleriaReclamo=new ArrayList<CGaleria>();
			
			tipoDocumentoDAO=new CtipoDocumentoDAO();
			sedeOcurrenciaDAO=new CsedeOcurrenciaDAO();
			areaOcurrenciaDAO=new CareaOcurrenciaDAO();
			motivoReclamoDAO=new CmotivoReclamoDAO();
			reclamoDAO=new CreclamoDAO();
			solicitanteDAO=new CsolicitanteDAO();
			galeriaDAO=new CGaleriaDAO();
			empresaDAO=new CempresaDAO();
			
			tipoDocumento=new CTipoDocumento();
			reclamo=new CReclamo();
			solicitante=new CSolicitante();
			sedeOcurrencia=new CSedeOcurrencia();
			areaOcurrencia=new CAreaOcurrencia();
			tipoProblema=new CTipoProblema();
			otroMotivoReclamo=new CMotivoReclamo();
			empresa=new CEmpresa();
			
			apellidomaterno="";
			apellidopaterno="";
			correo="";
			correoconfir="";
			detallesOtroMotivo="";
			solicitudreclamooqueja="";
			detallereclamooqueja="";
			
			verFormulario=reclamoDAO.esPriemeraVez(reclamoDAO.recuperarPrimeraVez());
			
			iniciarCombos();
			SECRET=empresa.getSecretKey();
		} catch (Exception e) {
			irInicio();
		}
	}
	public void irInicio(){
		Executions.getCurrent().sendRedirect("/");
	}
	public void iniciarCombos(){
		verForm=true;
		empresaDAO.pasarDatos(empresaDAO.recuperarDatosEmpresa());
		empresa=empresaDAO.getEmpresa();
		
		tipoDocumentoDAO.asignarListaTipoDocuemnto(tipoDocumentoDAO.obtenerTiposdeDocumento());
		listatipoDocumento=tipoDocumentoDAO.getListaTipoDocumento();
		
		sedeOcurrenciaDAO.asignarListaSedeOcurrencia(sedeOcurrenciaDAO.obtenerSedeOcurrencia());
		listasedeOcurrencia=sedeOcurrenciaDAO.getListaSedeOcurrencia();
		
		motivoReclamoDAO.asignarListaMotivoNormal(motivoReclamoDAO.obtenerMotivoOcurrencia());
		listamotivoReclamo=motivoReclamoDAO.getListaMotivoReclamo();
		//System.out.println("Cantidad de motivos reclamados "+listamotivoReclamo.size()+" "+listamotivoReclamo.get(0).getDescripcion());
	}
	@Command
	@NotifyChange({"activarTipoRuc"})
	public void changeTipoDocumento(@BindingParam("id")String id,@BindingParam("descripcion")String descripcion){
		System.out.println("Tipo documento "+id+"-->"+descripcion);
		if(id!=null && descripcion!=null){
			if(descripcion.trim().toLowerCase().equals("ruc")){
				activarTipoRuc=true;
			}else{
				activarTipoRuc=false;
				solicitante.setRazonsocial("");
			}
			tipoDocumento.setIdtipodocumento(Integer.parseInt(id));
			tipoDocumento.setDescripcion(descripcion);
			solicitante.setTipodocumento(Integer.parseInt(id));
		}
	}
	@GlobalCommand
	public void validarRUC(@BindingParam("valor")String valor,@BindingParam("com")Component comp){
		if(valor.length()>11){
			Clients.showNotification("Nro RUC incorrecto",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}
	}
	@Command
	public void validarNroDocs(@BindingParam("valor")String valor,@BindingParam("com")Component comp){
		System.out.println("Tipo documento "+tipoDocumento.getIdtipodocumento()+"-->"+tipoDocumento.getDescripcion());
		if (tipoDocumento.getDescripcion().toLowerCase().trim().equals("dni") || tipoDocumento.getDescripcion().toLowerCase().trim().equals("ruc")) {
			if (!nroDocValido(valor)) {
				Clients.showNotification("Error en el numero de documento",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
			}else{
				solicitante.setNrodoc(valor);
			}
		}else{
			if (!nroDocValidoExtra(valor)) {
				Clients.showNotification("Error en el numero de documento",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
			}else{
				solicitante.setNrodoc(valor);
			}
		}
	}
	@Command
	public void changeRazonSocial(@BindingParam("valor")String valor,@BindingParam("com")Component comp){
		solicitante.setRazonsocial(valor.toUpperCase());
	}
	@Command
	public void nombreOpcional(@BindingParam("valor")String valor,@BindingParam("com")Component comp){
		String aux=valor.toUpperCase();
		if (!validoText(aux)) {
			Clients.showNotification("Error en apellido paterno",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",1500);
		}else{
			solicitante.setNombres(aux.toUpperCase());
		}
	}
	@Command
	public void apellidosPaterno(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		String aux=valor.toUpperCase();
		if (!validoText(aux)) {
			Clients.showNotification("Error en apellido paterno",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",1500);
		}else{
			apellidopaterno=valor.toUpperCase();
		}
	}
	@Command
	public void apellidosMaterno(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		String aux=valor.toUpperCase();
		if (!validoText(aux)) {
			Clients.showNotification("Error en apellido materno",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}else{
			apellidomaterno=valor.toUpperCase();
		}
	}
	@Command
	public void nombres(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		String aux=valor.toUpperCase();
		if (!validoText(aux)) {
			Clients.showNotification("Error en el nombre",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}else{
			solicitante.setNombres(valor.toUpperCase());
		}
	}
	@Command
	public void changeTelefono(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (!telfValido(valor)) {
			Clients.showNotification("Error en el numero de telefono",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}else{
			solicitante.setNrotelefono(valor);
		}
	}
	@Command
	public void changeEmail(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (!mailValido(valor)) {
			Clients.showNotification("Error en el correo",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}else{
			correo=valor;
		}
	}
	@Command
	public void confirmEmail(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (!mailValido(valor)) {
			Clients.showNotification("Error en el correo",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
		}else{
			correoconfir=valor;
			if(!correo.equals(correoconfir)){
				Clients.showNotification("los correos no coinciden",Clients.NOTIFICATION_TYPE_ERROR,comp,"end_before",2500);
			}else{
				solicitante.setCorreo(valor);
			}
		}
	}
	@Command
	public void changeDireccion(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		solicitante.setDireccion(valor);
	}
	@Command
	@NotifyChange({"listaareaOcurrencia"})
	public void changeSedeOcurrencia(@BindingParam("id")String id,@BindingParam("descripcion")String descripcion){
		sedeOcurrencia.setIdsedeocurrencia(Integer.parseInt(id));
		sedeOcurrencia.setNombre(descripcion);
		reclamo.setSedeocurrencia(Integer.parseInt(id));
		areaOcurrenciaDAO.asignarListaAreaOcurrencia(areaOcurrenciaDAO.obtenerAreaOcurrenciaporSede(Integer.parseInt(id)));
		listaareaOcurrencia=areaOcurrenciaDAO.getListaAreaOcurrencia();
	}
	@Command
	public void changeAreaOcurrencia(@BindingParam("id")String id,@BindingParam("nombre")String nombre){
		areaOcurrencia.setIdareaocurrencia(Integer.parseInt(id));
		areaOcurrencia.setNombre(nombre);
		reclamo.setAreaocurrencia(Integer.parseInt(id));
	}
	@Command
	public void changeFecha(@BindingParam("fecha")Date fecha){
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
		String Fecha=sdf.format(fecha);
		System.out.println("fecha es:"+Fecha);
		String dia=Fecha.substring(0,2);
		String mes=Fecha.substring(3,5);
		String anio=Fecha.substring(6,10);
		/*************Fecha Inicio*******************/
		Calendar cal=Calendar.getInstance();
		cal.set(Integer.parseInt(anio),Integer.parseInt(mes)-1,Integer.parseInt(dia));
		/*************Fecha Arribo***********************/
		reclamo.setFecha(cal.getTime());
	}
	@Command
	@NotifyChange({"activarOtroMotivo","detallesOtroMotivo"})
	public void agregarListaMotivo(@BindingParam("id")String id,@BindingParam("valor")String valor){
		if(id.toString().equals("0")){
			activarOtroMotivo=!activarOtroMotivo;
		}
		if (listamotivo.indexOf(id+"/"+valor)==-1) {
			listamotivo.add(id+"/"+valor);
		}else{
			listamotivo.remove(id+"/"+valor);
		}
	}
	@Command
	public void changeOtroMotivo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (activarOtroMotivo) {
			detallesOtroMotivo=valor;
		}
	}
	@Command
	public void tipodeProblema(@BindingParam("valor")String valor){
		if (valor.toString().equals("reclamo")) {
			tipoProblema.setIdtipoproblema(1);
			tipoProblema.setDescripcion(valor);
			reclamo.setTipoproblema(1);
		}else if(valor.toString().equals("queja")){
			tipoProblema.setIdtipoproblema(2);
			tipoProblema.setDescripcion(valor);
			reclamo.setTipoproblema(2);
		}
		
	}
	@Command
	public void changeDescripcionReclamo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		detallereclamooqueja=valor;
		System.out.println(detallereclamooqueja);
	}
	@Command
	public void changeSolicitudReclamo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		solicitudreclamooqueja=valor;
		System.out.println(solicitudreclamooqueja);
	}

	@Command
	@NotifyChange({"checked","mostrarEnviar"})
	public void confirmarVeracidad(@BindingParam("comp")Component comp){
			pasarListaMotivos();
//			System.out.println("Lista de imagenes");
//			for (int i = 0; i < reclamo.getListaImagenes().size(); i++) {
//				reclamo.getListaImagenes().get(i).getcRutaImagen().toString();
//			}
			if(validoIdentificacionUsuario(comp) && validoIdentificacionAtencionBrindada(comp) && validodetallesReclamo(comp)){
				checked=true;
				solicitante.setApellidos(apellidopaterno+" "+apellidomaterno);
				solicitante.setCorreo(correo);
				reclamo.setDetallereclamo(detallereclamooqueja);
				reclamo.setSolicitudreclamo(solicitudreclamooqueja);
			}else{
				checked=false;
			}
			System.out.println(checked+" "+disabled);
			if (mostrarEnviar()) {
				mostrarEnviar=true;
			}else{
				mostrarEnviar=false;
			}
			//System.out.println(disabled+" "+checked+" "+mostrarEnviar);
	}
	@Command
	@NotifyChange({"pasofinal","reclamo"})
	public void changeInterface(@BindingParam("id")String id) throws IOException, DocumentException{
		if(id.toLowerCase().equals("btnenviar")){
			reclamo.setIdreclamo(enviarReclamoQueja());
		}else if(id.toLowerCase().equals("btnaceptar")){
			//System.out.println(id);
			Executions.getCurrent().sendRedirect("/");
		}
	}
	public String enviarReclamoQueja() throws IOException, DocumentException{
		System.out.println("Lista de Motivos reclamo");
		for (int i = 0; i < listaMRS.size(); i++) {
			System.out.println(listaMRS.get(i).getDescripcion());
		}
		reclamo.setListaImagenes(listaGaleriaReclamo);
		System.out.println("Tamanio de la lista de imagenes "+reclamo.getListaImagenes().size());
		reclamo.setSolicitante(solicitanteDAO.codigoPersona(solicitanteDAO.insertarSolicitante(solicitante)));
		String codigo=reclamoDAO.codigoReclamo(reclamoDAO.insertarReclamo(reclamo));
		reclamo.setIdreclamo(codigo);
		if (activarOtroMotivo) {
			motivoReclamoDAO.isOperationCorrect(motivoReclamoDAO.insertarOtroMotivoReclamo(codigo,detallesOtroMotivo));
		}
		if(codigo!=null){
			//System.out.println("El codigo de Reclamo es "+codigo);
			for (int i = 0; i < listaMRS.size(); i++) {
				boolean aux=motivoReclamoDAO.isOperationCorrect(motivoReclamoDAO.insertarListaMotivoReclamo(codigo,listaMRS.get(i).getIdmotivoreclamo()));
				//System.out.println("Lista Motivo Reclamo "+aux);
			}
			if(reclamo.getListaImagenes().size()>0){
				for (int i = 0; i < reclamo.getListaImagenes().size(); i++) {
					boolean aux=galeriaDAO.isOperationCorrect(galeriaDAO.insertarGaleria(reclamo.getListaImagenes().get(i),reclamo.getIdreclamo()));
					//System.out.println("Lista Motivo Reclamo "+aux);
				}
			}else{
				System.out.println("No hay imagenes");
			}
			pasofinal=true;
			if (pasofinal) {
				enviarcorreo();
			}
		}
		return codigo;
	}
	public void enviarcorreo() throws IOException, DocumentException{
		CEmail email=new CEmail();
		ArrayList<CMotivoReclamo> mot=listaMotivo();
		if (email.enviarCorreoReclamo(reclamo, solicitante, sedeOcurrencia, areaOcurrencia, tipoDocumento, tipoProblema, mot)) {
			System.out.println("Se envio correo");
		}else{
			System.out.println("Error en enviar correo");
		}
	}
	public ArrayList listaMotivo(){
		ArrayList<CMotivoReclamo> motivo=new ArrayList<>();
		for (int i = 0; i < listamotivo.size(); i++) {
			int cod=Integer.parseInt(listamotivo.get(i).toString().split("/")[0]);
			String descripcion=listamotivo.get(i).toString().split("/")[1];
			//System.out.println("Motivo Reclamo "+cod+" , "+descripcion);			
			if(cod==0){
				CMotivoReclamo problema=new CMotivoReclamo(0,detallesOtroMotivo);
				motivo.add(problema);
			}else{
				CMotivoReclamo problema=new CMotivoReclamo(cod,descripcion);
				motivo.add(problema);
			}
			
		}
		return motivo;
	}
	public void pasarListaMotivos(){
		for (int i = 0; i < listamotivo.size(); i++) {
			int cod=Integer.parseInt(listamotivo.get(i).toString().split("/")[0]);
			String descripcion=listamotivo.get(i).toString().split("/")[1];
			System.out.println("Motivo Reclamo "+cod+" , "+descripcion);
			CMotivoReclamo problema=new CMotivoReclamo(cod,descripcion);
			listaMRS.add(problema);
		}
		if(activarOtroMotivo && detallesOtroMotivo.trim()!=""){
			otroMotivoReclamo.setIdmotivoreclamo(0);
			otroMotivoReclamo.setDescripcion(detallesOtroMotivo);
		}
		//System.out.println("Motivo agregado "+codigoMotivo+" "+detallesOtroMotivo);
	}
	public boolean validoIdentificacionUsuario(Component comp){
		boolean valido=true;
		if (tipoDocumento.getIdtipodocumento()==0) {
			valido=false;
			Clients.showNotification("Selecciones un tipo de documento",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}else{
			if(tipoDocumento.getDescripcion().toLowerCase().equals("ruc")){
				if(solicitante.getRazonsocial()==""){
					valido=false;
					Clients.showNotification("Razon social incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}if (solicitante.getDireccion()=="") {
					valido=false;
					Clients.showNotification("Direccion incorrecta",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getCorreo()==""){
					valido=false;
					Clients.showNotification("Correo incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getNrotelefono()==""){
					valido=false;
					Clients.showNotification("Numero de telefono incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getNrodoc()==""){
					valido=false;
					Clients.showNotification("Nro doc Incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
			}else{
				if (solicitante.getDireccion()=="") {
					valido=false;
					Clients.showNotification("Direccion incorrecta",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getCorreo()==""){
					valido=false;
					Clients.showNotification("Correo incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getNrotelefono()==""){
					valido=false;
					Clients.showNotification("Numero de telefono incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if (solicitante.getNombres()=="") {
					valido=false;
					Clients.showNotification("Nombres Incorercto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if (apellidomaterno=="") {
					valido=false;
					Clients.showNotification("Apellido materno incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(apellidopaterno==""){
					valido=false;
					Clients.showNotification("Apellido paterno incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
				if(solicitante.getNrodoc()==""){
					valido=false;
					Clients.showNotification("Nro doc Incorrecto",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
				}
			}
		}
		
		return valido;
	}
	
	public boolean validoIdentificacionAtencionBrindada(Component comp){
		boolean valido=true;
		
		
		if(reclamo.getFecha().toString()==""){
			valido=false;
			Clients.showNotification("Fecha incorrecta",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		if(listaMRS.size()<1){
			valido=false;
			Clients.showNotification("Seleccione al menos un Motivo de queja o reclamo",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		if(reclamo.getAreaocurrencia()==0){
			valido=false;
			Clients.showNotification("Selecciona una area de ocurrencia",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		if (reclamo.getSedeocurrencia()==0) {
			valido=false;
			Clients.showNotification("Seleccione una sede de ocurrencia",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		return valido;
	}
	public boolean validodetallesReclamo(Component comp){
		boolean valido=true;
		if (tipoProblema.getIdtipoproblema()==0) {
			valido=false;
			Clients.showNotification("Seleccione Reclamo o Queja",Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		if (detallereclamooqueja.trim()=="") {
			valido=false;
			Clients.showNotification("Por favor detalle su "+tipoProblema.getDescripcion(),Clients.NOTIFICATION_TYPE_ERROR, comp,"before_start",2500);
		}
		return valido;
	}
	public boolean nroDocValido(String num)
	{
		boolean correcto=true;
		 for(int i=0;i<num.length();i++)
		 {
			 if((num.charAt(i)<'A' || num.charAt(i)>'Z') && (num.charAt(i)>'9' || num.charAt(i)<'0') && num.charAt(i)!='-' )
			 {
				 correcto=false;
				 break;
			 }
		 }
		 return correcto;
	}
	public boolean nroDocValidoExtra(String num){
		boolean valido=true;
		for(int i=0;i<num.length();i++)
		{
			 if((num.charAt(i)>'Z' || num.charAt(i)<'A') && (num.charAt(i)<'0' || num.charAt(i)>'9'))
			 {
				 valido=false;
				 break;
			 }
		}
		return valido;
	}
	public boolean validoNro(String num)
	{
		boolean correcto=true;
		 for(int i=0;i<num.length();i++)
		 {
			 if(num.charAt(i)>'9' || num.charAt(i)<'0')
			 {
				 correcto=false;
				 break;
			 }
		 }
		 return correcto;
	}
	public boolean telfValido(String telf)
	{
		boolean correcto=true;
		for(int i=0;i<telf.length();i++)
		{
			if((telf.charAt(i)<'0' || telf.charAt(i)>'9') && telf.charAt(i)!=' ' && telf.charAt(i)!='-' && telf.charAt(i)!='+')
			{
				correcto=false;
				break;
			}
		}
		return correcto;
	}
	public boolean validoText(String nombre)
	{
		System.out.println("Este es el nombre del contacto--> "+nombre);
		boolean correcto=true;
		for(int i=0;i<nombre.length();i++)
		{
			if((nombre.charAt(i)<'A' || nombre.charAt(i)>'Z') && nombre.charAt(i)!=' ' &&
					nombre.charAt(i)!='\u00c7' && nombre.charAt(i)!='\u00c3' && 
					nombre.charAt(i)!='\u00c2' && nombre.charAt(i)!='\u00c1' &&
					nombre.charAt(i)!='\u00c0' && nombre.charAt(i)!='\u00d5' &&
					nombre.charAt(i)!='\u00d4' && nombre.charAt(i)!='\u00d3' &&
					nombre.charAt(i)!='\u00ca' && nombre.charAt(i)!='\u00c9' &&
					nombre.charAt(i)!='\u00cd' && nombre.charAt(i)!='\u00c3' &&
					nombre.charAt(i)!='\u00da' && nombre.charAt(i)!='\u00d1' &&
					nombre.charAt(i)!='\u00f1')
			{
				correcto=false;
				break;
			}
		}
		return correcto;
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
	@Command
	public void uploadImagenes(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent event,
								@BindingParam("comp") final Component comp)
	{
		org.zkoss.util.media.Media[] listaMedias = event.getMedias();
		System.out.println("Lista Medias "+listaMedias.length);
		if (listaMedias != null) {
				for (Media media : listaMedias) {
					Media img = media;
					// Con este metodo(uploadFile) de clase guardo la imagen
					// en la ruta del servidor
					boolean b=ScannUtil.uploadAuxFolder(img);
					// ================================
					String urlImagenAux = ScannUtil.getPathAuxFolder() + img.getName();
					String urlImagenReal= ScannUtil.getPathImagenReclamos()+img.getName();
					
					//System.out.println("Datos del imagen "+b+" "+urlImagenAux+" "+urlImagenReal);
//					
//					if(!CReSizeImage.tamanioSuficiente(urlImagenAux))
//					{
//						CReSizeImage.copyImage(urlImagenAux,urlImagenReal,img.getFormat());
//						File fichero = new File(urlImagenAux);
//						boolean eliminar=fichero.delete();
//						//System.out.println("Eliminar if "+eliminar);
//					}else
//					{
						b = ScannUtil.uploadFileReclamos(img);
						File fichero = new File(urlImagenAux);
						boolean eliminar=fichero.delete();
						//System.out.println("Eliminar else "+eliminar);
//					}
					asignarRutaImagenPaquete(img.getName(),false);
					Clients.showNotification(img.getName()+" Se subio al servidor.",Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2700);
					reclamo.setDocmultimedia(true);
				}
			}
	}
	public void asignarRutaImagenPaquete(String nombreImagen,boolean imgExist)// ===paquete																																						// hotel=====
	{
		CGaleriaDAO gal=new CGaleriaDAO();
		String auxNombre=nombreImagen;
		if(imgExist && estaEnLaListaImagenes(auxNombre,reclamo))return;
		else if(estaEnLaListaImagenes("img/imagenesdereclamos/"+auxNombre,reclamo)){
			return;
		}
		CGaleria oGaleria = new CGaleria();
		if(imgExist)
			oGaleria.setcRutaImagen(auxNombre);
		else
			oGaleria.setcRutaImagen("img/imagenesdereclamos/"+auxNombre);
		oGaleria.setVisible(true);
		System.out.println("Ruta Imagen "+oGaleria.getcRutaImagen());
		listaGaleriaReclamo.add(oGaleria);
	}
	public boolean estaEnLaListaImagenes(String nameImagen,CReclamo paquete)
	{
		boolean esta=false;
		for(CGaleria galeria:paquete.getListaImagenes())
		{
			if(nameImagen.equals(galeria.getcRutaImagen()))
			{
				esta=true;
				break;
			}
		}
		return esta;
	}
	//**************************************** RECAPTCHA ******************************************//
//	@Command
//	@NotifyChange("mostrarEnviar")
//	public void verify(@BindingParam("response")String response) throws Exception{
//		JSONObject result = RecaptchaVerifier.verifyResponse(SECRET, response);
//		if (Boolean.parseBoolean(result.get("success").toString())){
//			disabled = true;
//			if(!checked){
//				Component comp=new formReclamoVM().getSelf();
//				Clients.showNotification("Por favor Confirme la veracidad de los datos",Clients.NOTIFICATION_TYPE_ERROR, comp,"middle_center",2500);
//			}
//		}else{
//			disabled=false;
//			String errorCode = result.get("error-codes").toString();
//			Clients.showNotification(errorCode);
//		}
//		if(mostrarEnviar()){
//			mostrarEnviar=true;
//		}else{
//			mostrarEnviar=false;
//		}
//		System.out.println(disabled+" "+checked+" "+mostrarEnviar);
//	}
	@Command
	public void irTerminosyCondiciones(){
		Executions.getCurrent().sendRedirect(empresa.getTerminosycondiciones(), "_blank");
	}
	@Command 
	@NotifyChange({"pasofinal","reclamo"})
	public void verify(@BindingParam("response")String response) throws Exception{
		JSONObject result = RecaptchaVerifier.verifyResponse(SECRET, response);
		if (Boolean.parseBoolean(result.get("success").toString())){
			reclamo.setIdreclamo(enviarReclamoQueja());
		}else{
			disabled=false;
			String errorCode = result.get("error-codes").toString();
			Clients.showNotification(errorCode);
		}
		if(mostrarEnviar()){
			mostrarEnviar=true;
		}else{
			mostrarEnviar=false;
		}
		System.out.println(disabled+" "+checked+" "+mostrarEnviar);

	}
	public boolean mostrarEnviar(){
		if(checked && disabled) return true;
		else return false;
	}
	//constructores
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public ArrayList<CTipoDocumento> getListatipoDocumento() {
		return listatipoDocumento;
	}
	public void setListatipoDocumento(ArrayList<CTipoDocumento> listatipoDocumento) {
		this.listatipoDocumento = listatipoDocumento;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public CReclamo getReclamo() {
		return reclamo;
	}
	public void setReclamo(CReclamo reclamo) {
		this.reclamo = reclamo;
	}
	public CSolicitante getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(CSolicitante solicitante) {
		this.solicitante = solicitante;
	}
	public ArrayList<CSedeOcurrencia> getListasedeOcurrencia() {
		return listasedeOcurrencia;
	}
	public void setListasedeOcurrencia(ArrayList<CSedeOcurrencia> listasedeOcurrencia) {
		this.listasedeOcurrencia = listasedeOcurrencia;
	}
	public ArrayList<CAreaOcurrencia> getListaareaOcurrencia() {
		return listaareaOcurrencia;
	}
	public void setListaareaOcurrencia(ArrayList<CAreaOcurrencia> listaareaOcurrencia) {
		this.listaareaOcurrencia = listaareaOcurrencia;
	}
	public ArrayList<CMotivoReclamo> getListamotivoReclamo() {
		return listamotivoReclamo;
	}
	public void setListamotivoReclamo(ArrayList<CMotivoReclamo> listamotivoReclamo) {
		this.listamotivoReclamo = listamotivoReclamo;
	}
	public boolean isActivarTipoRuc() {
		return activarTipoRuc;
	}
	public void setActivarTipoRuc(boolean activarTipoRuc) {
		this.activarTipoRuc = activarTipoRuc;
	}
	public boolean isActivarOtroMotivo() {
		return activarOtroMotivo;
	}
	public void setActivarOtroMotivo(boolean activarOtroMotivo) {
		this.activarOtroMotivo = activarOtroMotivo;
	}
	public boolean isPasofinal() {
		return pasofinal;
	}
	public void setPasofinal(boolean pasofinal) {
		this.pasofinal = pasofinal;
	}
	public boolean isMostrarEnviar() {
		return mostrarEnviar;
	}
	public void setMostrarEnviar(boolean mostrarEnviar) {
		this.mostrarEnviar = mostrarEnviar;
	}
	public boolean isVerFormulario() {
		return verFormulario;
	}
	public void setVerFormulario(boolean verFormulario) {
		this.verFormulario = verFormulario;
	}
	public CEmpresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(CEmpresa empresa) {
		this.empresa = empresa;
	}
	public String getPUBLIC() {
		return PUBLIC;
	}
	public void setPUBLIC(String pUBLIC) {
		PUBLIC = pUBLIC;
	}
	public boolean isVerForm() {
		return verForm;
	}
	public void setVerForm(boolean verForm) {
		this.verForm = verForm;
	}
}
