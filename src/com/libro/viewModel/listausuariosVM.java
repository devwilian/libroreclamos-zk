package com.libro.viewModel;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

import java.util.*;
import com.libro.dao.*;
import com.libro.model.*;

public class listausuariosVM {
	private CUsuario usuario;
	private CusuarioDAO usuarioDAO;
	
	private ArrayList<CUsuario>listaUsuario;
	@Init
	public void initVM(){
		usuario=new CUsuario();
		usuarioDAO=new CusuarioDAO();
		listaUsuario=new ArrayList<CUsuario>();
		
		iniciar();
	}
	@GlobalCommand
	public void iniciar(){
		usuarioDAO.asignarListaUsuarios(usuarioDAO.recuperarListaUsuarios());
		listaUsuario=usuarioDAO.getListaUsuarios();
		BindUtils.postNotifyChange(null, null, this, "listaUsuario");
	}
	@Command
	public void  eliminarUsuario(@BindingParam("valor")String valor){
		Messagebox.show("Esta seguro de eliminar el sede?",
			    "Question", Messagebox.OK | Messagebox.CANCEL,
			    Messagebox.QUESTION,new EventListener<Event>() {						
					@Override
					public void onEvent(Event e) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_OK.equals(e.getName()))
						{
							if(usuarioDAO.isOperationCorrect(usuarioDAO.eliminarUsuario(valor.trim()))){
								iniciar();
								BindUtils.postNotifyChange(null, null, this, "listaUsuario");
								Clients.showNotification("Se elimino correctamente", Clients.NOTIFICATION_TYPE_INFO, null, "top_center", 1500);
							}else{
								Clients.showNotification("No se puede eliminar el usuario, Verifique que no sea \"SUPERADMINISTRADOR\"", Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 5000);
							}
						}
						else if(Messagebox.ON_CANCEL.equals(e.getName()))
						{
			            }
					}});
	}
	public CUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(CUsuario usuario) {
		this.usuario = usuario;
	}
	public ArrayList<CUsuario> getListaUsuario() {
		return listaUsuario;
	}
	public void setListaUsuario(ArrayList<CUsuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}
}
