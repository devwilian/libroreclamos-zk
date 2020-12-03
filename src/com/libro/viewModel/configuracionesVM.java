package com.libro.viewModel;

import org.zkoss.bind.annotation.*;

public class configuracionesVM {
	private boolean configprefijo;
	private boolean configcorreo;
	private boolean configareasede;
	private boolean configempresa;
	private boolean configmotivo;
	
	@Init
	public void initVM(){
		configprefijo=false;
		configcorreo=false;
		configareasede=false;
		configempresa=false;
		configmotivo=false;
	}
	@Command
	@NotifyChange({"configprefijo","configcorreo","configareasede","configempresa","configmotivo"})
	public void configurar(@BindingParam("id")String id){
		if (id.equals("prefijo")) {
			configprefijo=true;
			configcorreo=false;
			configareasede=false;
			configempresa=false;
			configmotivo=false;
		}else if (id.equals("correo")) {
			configprefijo=false;
			configcorreo=true;
			configareasede=false;
			configempresa=false;
			configmotivo=false;
		}else if (id.equals("sedearea")) {
			configprefijo=false;
			configcorreo=false;
			configareasede=true;
			configempresa=false;
			configmotivo=false;
		}else if(id.equals("empresa")){
			configprefijo=false;
			configcorreo=false;
			configareasede=false;
			configempresa=true;
			configmotivo=false;
		}else if(id.equals("motivo")){
			configprefijo=false;
			configcorreo=false;
			configareasede=false;
			configempresa=false;
			configmotivo=true;
		}
	}
	public boolean isConfigprefijo() {
		return configprefijo;
	}
	public void setConfigprefijo(boolean configprefijo) {
		this.configprefijo = configprefijo;
	}
	public boolean isConfigcorreo() {
		return configcorreo;
	}
	public void setConfigcorreo(boolean configcorreo) {
		this.configcorreo = configcorreo;
	}
	public boolean isConfigareasede() {
		return configareasede;
	}
	public void setConfigareasede(boolean configareasede) {
		this.configareasede = configareasede;
	}
	public boolean isConfigempresa() {
		return configempresa;
	}
	public void setConfigempresa(boolean configempresa) {
		this.configempresa = configempresa;
	}
	public boolean isConfigmotivo() {
		return configmotivo;
	}
	public void setConfigmotivo(boolean configmotivo) {
		this.configmotivo = configmotivo;
	}
}
