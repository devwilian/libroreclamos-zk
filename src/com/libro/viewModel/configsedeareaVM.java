package com.libro.viewModel;

import java.util.*;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.libro.dao.CGaleriaDAO;
import com.libro.dao.CareaOcurrenciaDAO;
import com.libro.dao.CmotivoReclamoDAO;
import com.libro.dao.CreclamoDAO;
import com.libro.dao.CsedeOcurrenciaDAO;
import com.libro.dao.CsolicitanteDAO;
import com.libro.dao.CtipoDocumentoDAO;
import com.libro.model.*;
import com.sun.security.ntlm.NTLMException;

public class configsedeareaVM {
	private ArrayList<CSedeOcurrencia> listasedeOcurrencia;
	private ArrayList<CAreaOcurrencia> listaareaOcurrencia;
	
	private CsedeOcurrenciaDAO sedeOcurrenciaDAO;
	private CareaOcurrenciaDAO areaOcurrenciaDAO;
	private CSedeOcurrencia sedeOcurrencia;
	private CAreaOcurrencia areaOcurrencia;
	private CSedeOcurrencia sedeNuevo;
	private CAreaOcurrencia areaNuevo;
	
	private boolean editable;
	private boolean edit;
	private boolean verArea;
	private boolean verbtnEditar;
	private boolean verSedeNuevo;
	private boolean verbtnEditarSede;
	private boolean verNuevoSede;
	
	@Init
	public void initVM(){
		verbtnEditarSede=false;
		verbtnEditar=false;
		editable =false;
		edit=false;
		verArea=false;
		verSedeNuevo=false;
		
		listasedeOcurrencia=new ArrayList<CSedeOcurrencia>();
		listaareaOcurrencia=new ArrayList<CAreaOcurrencia>();
		
		sedeOcurrenciaDAO=new CsedeOcurrenciaDAO();
		areaOcurrenciaDAO=new CareaOcurrenciaDAO();
		sedeNuevo=new CSedeOcurrencia();
		areaNuevo=new CAreaOcurrencia();
		
		sedeOcurrencia=new CSedeOcurrencia();
		areaOcurrencia=new CAreaOcurrencia();
		iniciarCombos();
	}
	@GlobalCommand
	public void iniciarCombos(){
		sedeOcurrenciaDAO.asignarListaSedeOcurrencia(sedeOcurrenciaDAO.obtenerSedeOcurrencia());
		listasedeOcurrencia=sedeOcurrenciaDAO.getListaSedeOcurrencia();
		BindUtils.postNotifyChange(null, null, this, "listasedeOcurrencia");
	}
	@GlobalCommand
	public void iniciarArea(){
		areaOcurrenciaDAO.asignarListaAreaOcurrencia(areaOcurrenciaDAO.obtenerAreaOcurrenciaporSede(sedeOcurrencia.getIdsedeocurrencia()));
		listaareaOcurrencia=areaOcurrenciaDAO.getListaAreaOcurrencia();
		verArea=true;
		BindUtils.postNotifyChange(null, null, this, "listaareaOcurrencia");
	}
	@Command
	@NotifyChange({"listaareaOcurrencia","verArea","sedeOcurrencia"})
	public void changeSedeOcurrencia(@BindingParam("id")String id,@BindingParam("descripcion")String descripcion){
		sedeOcurrencia.setIdsedeocurrencia(Integer.parseInt(id));
		sedeOcurrencia.setNombre(descripcion);
		
		areaOcurrenciaDAO.asignarListaAreaOcurrencia(areaOcurrenciaDAO.obtenerAreaOcurrenciaporSede(Integer.parseInt(id)));
		listaareaOcurrencia=areaOcurrenciaDAO.getListaAreaOcurrencia();
		
		verArea=true;
	}
	
	@Command
	@NotifyChange({"listaareaOcurrencia","verArea","areaOcurrencia"})
	public void changeAreaOcurrencia(@BindingParam("id")String id,@BindingParam("nombre")String nombre){
		areaOcurrencia.setIdareaocurrencia(Integer.parseInt(id));
		areaOcurrencia.setNombre(nombre);
		
		verArea=true;
		//System.out.println(areaOcurrencia.getIdareaocurrencia()+" "+areaOcurrencia.getNombre());
	}
	@Command
	public void guardar(@BindingParam("comp")Component comp){
		System.out.println(sedeOcurrencia.getIdsedeocurrencia());
		System.out.println(sedeOcurrencia.getNombre());
	}
	@Command
	public void guardarArea(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if(!valor.trim().equals("")){
			System.out.println("Se insertara a la bd"+valor);
			iniciarCombos();
		}
	}
	@Command
	@NotifyChange({"sedeOcurrencia","verSedeNuevo","verArea","editable"})
	public void actualizarSede(@BindingParam("id")String id,@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		sedeOcurrencia.setIdsedeocurrencia(Integer.parseInt(id));
		sedeOcurrencia.setNombre(valor);
		editable=true;
		verArea=false;
		verSedeNuevo=false;
	}
	@Command
	public void eliminarSede(@BindingParam("id")String id,@BindingParam("valor")String valor){
		Messagebox.show("Esta seguro de eliminar el sede?",
			    "Question", Messagebox.OK | Messagebox.CANCEL,
			    Messagebox.QUESTION,new EventListener<Event>() {						
					@Override
					public void onEvent(Event e) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_OK.equals(e.getName()))
						{
							if(sedeOcurrenciaDAO.isOperationCorrect(sedeOcurrenciaDAO.eliminarSedeOcurrencia(Integer.parseInt(id)))){
								editable=false;
								verArea=false;
								verSedeNuevo=false;
								iniciarCombos();
								BindUtils.postNotifyChange(null, null, this, "listasedeOcurrencia");
								BindUtils.postNotifyChange(null, null, this, "sedeOcurrencia");
								BindUtils.postNotifyChange(null, null, this, "verSedeNuevo");
								BindUtils.postNotifyChange(null, null, this, "verArea");
								BindUtils.postNotifyChange(null, null, this, "editable");
								
								Clients.showNotification("Se elimino correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 1500);
							}else{
								Clients.showNotification("No se puede eliminar la sede, puesto que se tiene registro de reclamos/quejas con esta sede", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 5000);
							}
						}
						else if(Messagebox.ON_CANCEL.equals(e.getName()))
						{
			            }
					}});
		
	}
	@Command
	public void eliminarArea(@BindingParam("id")String id,@BindingParam("valor")String valor){
		Messagebox.show("Esta seguro de Eliminar el Servicio?",
			    "Question", Messagebox.OK | Messagebox.CANCEL,
			    Messagebox.QUESTION,new EventListener<Event>() {						
					@Override
					public void onEvent(Event e) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_OK.equals(e.getName()))
						{
							if(areaOcurrenciaDAO.isOperationCorrect(areaOcurrenciaDAO.eliminarAreaOcurrencia(Integer.parseInt(id)))){
								edit=false;
								verArea=false;
								verSedeNuevo=false;
								iniciarArea();
								BindUtils.postNotifyChange(null, null, this, "listaareaOcurrencia");
								BindUtils.postNotifyChange(null, null, this, "sedeOcurrencia");
								BindUtils.postNotifyChange(null, null, this, "verSedeNuevo");
								BindUtils.postNotifyChange(null, null, this, "verArea");
								BindUtils.postNotifyChange(null, null, this, "edit");
								Clients.showNotification("Se Elimino Correctamente",Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 2500);
							}else{
								Clients.showNotification("No se pudo eliminar el area, puesto que existe reclamos/quejas en dicha area",Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 5000);
							}
						}
						else if(Messagebox.ON_CANCEL.equals(e.getName()))
						{
			            }
					}});
		
	}
	@Command
	@NotifyChange({"edit","verNuevoSede","areaOcurrencia"})
	public void updateSede(@BindingParam("id")String id,@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		areaOcurrencia.setIdareaocurrencia(Integer.parseInt(id));
		areaOcurrencia.setNombre(valor);
		edit=true;
		verNuevoSede=false;
	}
	@Command
	@NotifyChange({"listaareaOcurrencia","listasedeOcurrencia","verSedeNuevo","editable"})
	public void guardarModificado(@BindingParam("comp")Component comp){
		System.out.println("Se actualizara "+sedeOcurrencia.getIdsedeocurrencia()+" "+sedeOcurrencia.getNombre());
		if(sedeOcurrenciaDAO.isOperationCorrect(sedeOcurrenciaDAO.actualizarSede(sedeOcurrencia))){
			Clients.showNotification("Se actualizo "+sedeOcurrencia.getNombre(), Clients.NOTIFICATION_TYPE_INFO, comp, "top_center", 2500);
			verSedeNuevo=false;
			editable=false;
			iniciarCombos();
		}else{
			Clients.showNotification("Error en  actualizar", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	@Command
	@NotifyChange({"listaareaOcurrencia","listasedeOcurrencia","verSedeNuevo","editable"})
	public void guardarNuevo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		sedeNuevo.setNombre(valor);
		if(sedeOcurrenciaDAO.isOperationCorrect(sedeOcurrenciaDAO.insertarNuevoSede(sedeNuevo.getNombre().toUpperCase()))){
			Clients.showNotification("Se guardo correctamente "+sedeNuevo.getNombre(), Clients.NOTIFICATION_TYPE_INFO, comp, "top_center", 2500);
			verSedeNuevo=false;
			editable=false;
			iniciarCombos();
		}else{
			Clients.showNotification("Error en Guardar"+sedeOcurrencia.getNombre(), Clients.NOTIFICATION_TYPE_ERROR, comp, "top_center", 2500);
		}
	}
	@Command
	@NotifyChange({"listaareaOcurrencia","listasedeOcurrencia","verSedeNuevo","edit"})
	public void guardarModificadoArea(@BindingParam("comp")Component comp){
		System.out.println("Se actualizara area "+areaOcurrencia.getIdareaocurrencia()+" "+areaOcurrencia.getNombre());
		if(areaOcurrenciaDAO.isOperationCorrect(areaOcurrenciaDAO.actualizarArea(areaOcurrencia))){
			Clients.showNotification("Se actualizo correctamente", Clients.NOTIFICATION_TYPE_INFO,comp, "top_center", 2500);
			verSedeNuevo=false;
			edit=false;
			iniciarArea();
		}else{
			Clients.showNotification("Error en actualizar sede", Clients.NOTIFICATION_TYPE_ERROR,comp, "top_center", 2500);
		}
	}
	@Command
	@NotifyChange({"listaareaOcurrencia","listasedeOcurrencia","verNuevoSede","editable"})
	public void guardarNuevoSede(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		sedeNuevo.setNombre(valor.toUpperCase());
		//System.out.println(areaNuevo.getNombre());
		if(sedeOcurrenciaDAO.isOperationCorrect(sedeOcurrenciaDAO.insertarNuevoSede(sedeNuevo.getNombre()))){
			Clients.showNotification("Se guardo correctamente el Sede", Clients.NOTIFICATION_TYPE_INFO,comp, "top_center", 2500);
			verNuevoSede=false;
			iniciarCombos();
		}else{
			Clients.showNotification("Error en actualizar Sede", Clients.NOTIFICATION_TYPE_ERROR,comp, "top_center", 2500);
		}
	}
	@Command
	@NotifyChange({"verSedeNuevo","listaareaOcurrencia"})
	public void guardarNuevoArea(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		areaNuevo.setNombre(valor.toUpperCase());
		areaNuevo.setIdsedeocurrencia(sedeOcurrencia.getIdsedeocurrencia());
		if(areaOcurrenciaDAO.isOperationCorrect(areaOcurrenciaDAO.insertarAreaOcurrencia(areaNuevo.getNombre(), areaNuevo.getIdsedeocurrencia()))){
			Clients.showNotification("Se guardo correctamente el Area", Clients.NOTIFICATION_TYPE_INFO,comp, "top_center", 2500);
			verSedeNuevo=false;
			iniciarArea();
		}else{
			Clients.showNotification("Error en guardar Area", Clients.NOTIFICATION_TYPE_INFO,comp, "top_center", 2500);
		}
	}
	@Command
	public void actualizarArea(@BindingParam("id")String id,@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		System.out.println("Se editara "+id+" "+valor);
	}
	@Command
	@NotifyChange({"verbtnEditar"})
	public void activarEditar(){
		verbtnEditar=true;
	}
	
	@Command
	@NotifyChange({"verbtnEditarSede","edit"})
	public void activarEditarSede(){
		verbtnEditarSede=true;
		edit=false;
	}
	@Command
	@NotifyChange({"editable","verArea","verbtnEditar","verSedeNuevo"})
	public void Nuevo(){
		editable=true;
		verArea=false;
		verSedeNuevo=true;
		editable=false;
	}
	@Command
	@NotifyChange({"edit"})
	public void editarArea(){
		edit=true;
	}
	@Command
	@NotifyChange({"verNuevoSede","edit"})
	public void editarSede(){
		verNuevoSede=true;
		edit=false;
	}
	@Command
	@NotifyChange({"verSedeNuevo"})
	public void activarNuevo(){
		verSedeNuevo=true;
	}
	@Command
	@NotifyChange({"editable","verbtnEditar","verSedeNuevo","verArea","verNuevoSede","edit","listaareaOcurrencia"})
	public void cancelar(){
		System.out.println(verArea+""+editable+""+verSedeNuevo);
		editable=false;
		edit=false;
		verSedeNuevo=false;
		verNuevoSede=false;
		if(sedeOcurrencia.getIdsedeocurrencia()==0){
			verbtnEditar=false;
		}
		System.out.println("id "+sedeOcurrencia.getIdsedeocurrencia()+" Nombre "+sedeOcurrencia.getNombre());
		if(sedeOcurrencia.getNombre()!=""){
			verArea=true;
			iniciarArea();
		}else{
			verArea=false;
			
		}
	}
	@Command
	@NotifyChange({"edit"})
	public void cancelarArea(){
		edit=false;
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
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isverArea() {
		return verArea;
	}
	public void setverArea(boolean verArea) {
		this.verArea = verArea;
	}
	public boolean isEdit() {
		return edit;
	}
	public void setEdit(boolean edit) {
		this.edit = edit;
	}
	public boolean isVerbtnEditar() {
		return verbtnEditar;
	}
	public void setVerbtnEditar(boolean verbtnEditar) {
		this.verbtnEditar = verbtnEditar;
	}
	public CSedeOcurrencia getSedeOcurrencia() {
		return sedeOcurrencia;
	}
	public void setSedeOcurrencia(CSedeOcurrencia sedeOcurrencia) {
		this.sedeOcurrencia = sedeOcurrencia;
	}
	public CAreaOcurrencia getAreaOcurrencia() {
		return areaOcurrencia;
	}
	public void setAreaOcurrencia(CAreaOcurrencia areaOcurrencia) {
		this.areaOcurrencia = areaOcurrencia;
	}
	public boolean isVerSedeNuevo() {
		return verSedeNuevo;
	}
	public void setVerSedeNuevo(boolean verSedeNuevo) {
		this.verSedeNuevo = verSedeNuevo;
	}
	public boolean isVerbtnEditarSede() {
		return verbtnEditarSede;
	}
	public void setVerbtnEditarSede(boolean verbtnEditarSede) {
		this.verbtnEditarSede = verbtnEditarSede;
	}
	public boolean isVerNuevoSede() {
		return verNuevoSede;
	}
	public void setVerNuevoSede(boolean verNuevoSede) {
		this.verNuevoSede = verNuevoSede;
	}
}
