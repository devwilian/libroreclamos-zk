package com.libro.viewModel;

import javax.servlet.http.HttpSession;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import pe.com.erp.crypto.Encryptar;

import com.libro.model.*;
import com.libro.dao.*;
public class panelAdminVM{
	private boolean cargarAdmin;
	private boolean visibleRecibidos;
	private boolean visibleProceso;
	private boolean visibleReporte;
	private boolean visibleReclamo;
	private boolean elegirfotos;
	private boolean visiblecrearUsuario;
	private boolean visibleConfiguracion;
	private boolean visibleprimeravez;
	private boolean visiblelistausuario;
	//private crearUsuarioVM editarUsuario;
	/****************************************************/
	private CusuarioLoginDAO usuarioDao;
	private CAcceso oAcceso;
	private CUsuarioLogin oUsuario;
	private HttpSession ses;
	@Init
	public void initVM(){
		elegirfotos=false;
		cargarAdmin=false;
		visibleprimeravez=false;
//		ses = (HttpSession)Sessions.getCurrent().getNativeSession();
//		System.out.println((String)ses.getAttribute("usuario")+" "+(String)ses.getAttribute("clave")+" "+(int)ses.getAttribute("perfil"));
		try
		{
			Encryptar encrip= new Encryptar();
//			System.out.println("Aqui esta la contraseña desencriptada-->"+encrip.decrypt("cyS249O3OHZTsG0ww1rYrw=="));
			Execution exec = Executions.getCurrent();
			ses = (HttpSession)Sessions.getCurrent().getNativeSession();
		    String user=(String)ses.getAttribute("usuario");
		    String pas=(String)ses.getAttribute("clave");
		    int perfil=(int)ses.getAttribute("perfil");
		    /******************************************/
		    iniciarPanelAdministrador(user,pas,perfil);
//		    System.out.println("Visible primera vez "+visibleprimeravez);
		}
		catch(Exception e)
		{
			irALogin();
		}
	}
	public void irALogin()
	{
//		System.out.println("Entro para ir a login.zul");
		Executions.getCurrent().sendRedirect("/login.zul");
	}
	@Command
	public void cerrarSession()
	{
		
		ses.removeAttribute("usuario");
		ses.removeAttribute("clave");
		ses.removeAttribute("perfil");
//		ses.invalidate();
//		System.out.println(ses.getAttribute("usuario")+" | "+
//				ses.getAttribute("clave")+" | "+
//				ses.getAttribute("perfil")
//				);
		irALogin();
	}
	public void iniciarPanelAdministrador(String usuario,String password,int codPerfil)
	{
		cargarAdmin=true;
		usuarioDao=new CusuarioLoginDAO();
		oAcceso=new CAcceso();
		oUsuario=new CUsuarioLogin();
		//ses.setAttribute("accesos", oAcceso);
		
		CreclamoDAO r=new CreclamoDAO();
		visibleprimeravez=r.esPriemeraVez(r.recuperarPrimeraVez());
		
		int aux=usuarioDao.codigoAcceso(usuarioDao.recuperarCodigoAccesosUsuario(codPerfil));
//		System.out.println("Codigo acceso "+aux);
		
		usuarioDao.asignarAccesosUsuario(usuarioDao.recuperarAccesosUsuario(aux));
		setoAcceso(usuarioDao.getoAcceso());
		
		usuarioDao.asignarUsuario(usuarioDao.recuperarUsuario(usuario, password));
		setoUsuario(usuarioDao.getoUsuario());
		
		ses.setAttribute("usuarioActual",this.oUsuario);
		
		if (oAcceso.isAccesorecibidos()) {
			visibleRecibidos=true;
			visiblecrearUsuario=false;
			visibleProceso=false;
			visibleReporte=false;
			visibleReclamo=false;
			visibleConfiguracion=false;
		}else if(oAcceso.isAccesoprocesos()){
			visibleRecibidos=false;
			visiblecrearUsuario=false;
			visibleProceso=true;
			visibleReporte=false;
			visibleReclamo=false;
			visibleConfiguracion=false;
		}else if(oAcceso.isAccesoreportes()){
			visibleRecibidos=false;
			visiblecrearUsuario=false;
			visibleProceso=false;
			visibleReporte=true;
			visibleReclamo=false;
			visibleConfiguracion=false;
		}else if(oAcceso.isAccesoregusuario()){
			visibleRecibidos=false;
			visiblecrearUsuario=true;
			visibleProceso=false;
			visibleReporte=false;
			visibleReclamo=false;
			visibleConfiguracion=false;
		}else if (oAcceso.isAccesoconfiguracion()) {
			visibleRecibidos=false;
			visiblecrearUsuario=false;
			visibleProceso=false;
			visibleReporte=false;
			visibleReclamo=false;
			visibleConfiguracion=true;
		}else if (oAcceso.isAccesolistausuario()) {
			visibleRecibidos=false;
			visiblecrearUsuario=false;
			visibleProceso=false;
			visibleReporte=false;
			visibleReclamo=false;
			visibleConfiguracion=false;
			visiblelistausuario=true;
		}
	}
	@Command
	@GlobalCommand
	@NotifyChange({"visibleRecibidos","visibleProceso","visibleReporte","visibleReclamo","visiblecrearUsuario","visibleConfiguracion","visiblelistausuario"})
	public void Cambio(@BindingParam("cambioInterfaz")String id){
		System.out.println("Entra a cambiar a "+id);
		if(id.equals("itemRecibidos")){
			visibleRecibidos=true;
			visibleProceso=false;
			visibleReporte=false;
			visiblecrearUsuario=false;
			visibleConfiguracion=false;
			visiblelistausuario=false;
		}else if(id.equals("itemProcesos")){
			visibleRecibidos=false;
			visibleProceso=true;
			visibleReporte=false;
			visiblecrearUsuario=false;
			visibleConfiguracion=false;
			visiblelistausuario=false;
		}else if(id.equals("itemReportes")){
			visibleRecibidos=false;
			visibleProceso=false;
			visibleReporte=true;
			visiblecrearUsuario=false;
			visibleConfiguracion=false;
			visiblelistausuario=false;
		}else if(id.equals("itemCrearUsuario") || id.equals("itemCrearUsuarioPhone")){
			visibleRecibidos=false;
			visibleProceso=false;
			visibleReporte=false;
			visiblecrearUsuario=true;
			visibleConfiguracion=false;
			visiblelistausuario=false;
		}else if(id.equals("itemConfiguracion")){
			visibleRecibidos=false;
			visibleProceso=false;
			visibleReporte=false;
			visiblecrearUsuario=false;
			visibleConfiguracion=true;
			visiblelistausuario=false;
		}else if(id.equals("itemListaUsuario")){
			visibleRecibidos=false;
			visibleProceso=false;
			visibleReporte=false;
			visiblecrearUsuario=false;
			visibleConfiguracion=false;
			visiblelistausuario=true;
		}
	}
	@Command @NotifyChange({"visibleprimeravez"})
	public void insertarPrefijo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (valor.trim()!="") {
			CreclamoDAO recla=new CreclamoDAO();
			if(recla.isOperationCorrect(recla.insertarPrefijoCodigo(valor.trim().toUpperCase()))){
				visibleprimeravez=false;
				Clients.showNotification("SE GUARDO EL PREFIJO DE LA BASE DE DATOS CON "+valor.toUpperCase(), Clients.NOTIFICATION_TYPE_INFO,null, "top_center", 3000);
			}else{
				Clients.showNotification("ESTAMOS PRESENCIANDO INCONVENIENTES CON NUESTRA BASE DE DATOS POR FAVOR INTENTE MAS TARDE"+valor.toUpperCase(), Clients.NOTIFICATION_TYPE_INFO,null, "top_center", 3000);
			}
		}else{
			Clients.showNotification("Ingrese un prefijo valido!", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_center", 2500);
		}
	}
	@Command
	@NotifyChange({"elegirfotos"})
	public void verUsuario(){
		elegirfotos=true;
	}
	@GlobalCommand
	@NotifyChange({"elegirfotos"})
	public void cerrarVentana(){
		elegirfotos=false;
	}
	public boolean isCargarAdmin() {
		return cargarAdmin;
	}
	public void setCargarAdmin(boolean cargarAdmin) {
		this.cargarAdmin = cargarAdmin;
	}
	public boolean isVisibleRecibidos() {
		return visibleRecibidos;
	}
	public void setVisibleRecibidos(boolean visibleRecibidos) {
		this.visibleRecibidos = visibleRecibidos;
	}
	public boolean isVisibleProceso() {
		return visibleProceso;
	}
	public void setVisibleProceso(boolean visibleProceso) {
		this.visibleProceso = visibleProceso;
	}
	public boolean isVisibleReporte() {
		return visibleReporte;
	}
	public void setVisibleReporte(boolean visibleReporte) {
		this.visibleReporte = visibleReporte;
	}
	public CusuarioLoginDAO getUsuarioDao() {
		return usuarioDao;
	}
	public void setUsuarioDao(CusuarioLoginDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	public CAcceso getoAcceso() {
		return oAcceso;
	}
	public void setoAcceso(CAcceso oAcceso) {
		this.oAcceso = oAcceso;
	}
	public CUsuarioLogin getoUsuario() {
		return oUsuario;
	}
	public void setoUsuario(CUsuarioLogin oUsuario) {
		this.oUsuario = oUsuario;
	}
	public HttpSession getSes() {
		return ses;
	}
	public void setSes(HttpSession ses) {
		this.ses = ses;
	}
	public boolean isVisibleReclamo() {
		return visibleReclamo;
	}
	public void setVisibleReclamo(boolean visibleReclamo) {
		this.visibleReclamo = visibleReclamo;
	}
	public boolean isElegirfotos() {
		return elegirfotos;
	}
	public void setElegirfotos(boolean elegirfotos) {
		this.elegirfotos = elegirfotos;
	}
	public boolean isVisiblecrearUsuario() {
		return visiblecrearUsuario;
	}
	public void setVisiblecrearUsuario(boolean visiblecrearUsuario) {
		this.visiblecrearUsuario = visiblecrearUsuario;
	}
	public boolean isVisibleConfiguracion() {
		return visibleConfiguracion;
	}
	public void setVisibleConfiguracion(boolean visibleConfiguracion) {
		this.visibleConfiguracion = visibleConfiguracion;
	}
	public boolean isVisibleprimeravez() {
		return visibleprimeravez;
	}
	public void setVisibleprimeravez(boolean visibleprimeravez) {
		this.visibleprimeravez = visibleprimeravez;
	}
	public boolean isVisiblelistausuario() {
		return visiblelistausuario;
	}
	public void setVisiblelistausuario(boolean visiblelistausuario) {
		this.visiblelistausuario = visiblelistausuario;
	}
}