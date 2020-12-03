package com.libro.viewModel;

import java.util.ArrayList;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import com.libro.dao.CmotivoReclamoDAO;
import com.libro.model.CMotivoReclamo;

public class configmotivoVM {
	private CMotivoReclamo motivo;
	private CmotivoReclamoDAO motivoDAO;
	private ArrayList<CMotivoReclamo>listaMotivo;
	private boolean editable;
	private boolean verNuevo;
	@Init
	public void initVM(){
		editable=false;
		verNuevo=false;
		motivo=new CMotivoReclamo();
		motivoDAO=new CmotivoReclamoDAO();
		listaMotivo=new ArrayList<CMotivoReclamo>();
		iniciar();
	}
	@GlobalCommand
	public void iniciar(){
		motivoDAO.asignarListaMotivoNormal(motivoDAO.obtenerMotivoOcurrencia());
		listaMotivo=motivoDAO.getListaMotivoReclamo();
		BindUtils.postNotifyChange(null, null, this, "listaMotivo");
	}
	@Command
	@NotifyChange({"motivo","editable","verNuevo"})
	public void actualizarSede(@BindingParam("id")String id,@BindingParam("valor")String valor){
		motivo.setIdmotivoreclamo(Integer.parseInt(id));
		motivo.setDescripcion(valor.trim());
		editable=true;
		verNuevo=false;
	}
	@Command
	public void eliminarSede(@BindingParam("id")String id,@BindingParam("valor")String valor){
		Messagebox.show("Esta seguro de eliminar el Motivo?",
			    "Question", Messagebox.OK | Messagebox.CANCEL,
			    Messagebox.QUESTION,new EventListener<Event>() {						
					@Override
					public void onEvent(Event e) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_OK.equals(e.getName()))
						{
							if(motivoDAO.isOperationCorrect(motivoDAO.eliminarmotivo(Integer.parseInt(id)))){
								iniciar();
								BindUtils.postNotifyChange(null, null, this, "listaMotivo");
								Clients.showNotification("Se elimino correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 1500);
							}else{
								Clients.showNotification("No se puede eliminar el Motivo, Verifique que no sea \"Otro Motivo\"", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 5000);
							}
						}
						else if(Messagebox.ON_CANCEL.equals(e.getName()))
						{
			            }
					}});
	}
	@Command
	@NotifyChange({"listaMotivo","editable"})
	public void guardarModificado(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		System.out.println("El valor pasado desde la vista es "+valor);
		if (valor.trim()!="") {
			motivo.setDescripcion(valor);
			if (motivoDAO.isOperationCorrect(motivoDAO.modificarMotivo(motivo))) {
				iniciar();
				editable=false;
				Clients.showNotification("Se guardo correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 2500);
			} else {
				Clients.showNotification("Error!! en actualizar", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 2500);
			}
		}else{
			Clients.showNotification("Ingrese un valor valido", Clients.NOTIFICATION_TYPE_ERROR, comp, "top_center", 2500);
		}
	}
	@Command
	@NotifyChange({"editable","verNuevo"})
	public void cancelar(){
		editable=false;
		verNuevo=false;
	}
	@Command
	@NotifyChange({"verNuevo","editable"})
	public void verCampoNuevo(){
		verNuevo=true;
		editable=false;
	}
	@Command
	@NotifyChange({"verNuevo","listaMotivo"})
	public void guardarNuevoMotivo(@BindingParam("valor")String valor,@BindingParam("comp")Component comp){
		if (valor.trim()!="") {
			if (motivoDAO.isOperationCorrect(motivoDAO.insertarMotivoReclamo(valor))) {
				iniciar();
				verNuevo=false;
				Clients.showNotification("Se guardo correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 2500);
			} else {
				Clients.showNotification("Se guardo correctamente", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
			}
		}else{
			Clients.showNotification("Ingrese un valor valido", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	public CMotivoReclamo getMotivo() {
		return motivo;
	}
	public void setMotivo(CMotivoReclamo motivo) {
		this.motivo = motivo;
	}
	public ArrayList<CMotivoReclamo> getListaMotivo() {
		return listaMotivo;
	}
	public void setListaMotivo(ArrayList<CMotivoReclamo> listaMotivo) {
		this.listaMotivo = listaMotivo;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isVerNuevo() {
		return verNuevo;
	}
	public void setVerNuevo(boolean verNuevo) {
		this.verNuevo = verNuevo;
	}
}
