package com.libro.viewModel;

import org.zkoss.bind.annotation.*;

public class reporteVM {
	private boolean visibleReporteResueltos;
	private boolean visibleReportePorSede;
	private boolean visibleReportePorFecha;
	private boolean visibleReportePorUsuario;
	private boolean visibleInicio;
	@Init
	public void initVM(){
		visibleReportePorFecha=false;
		visibleReportePorSede=false;
		visibleReporteResueltos=false;
		visibleReportePorUsuario=false;
		visibleInicio=true;
	}
	@Command
	@NotifyChange({"visibleReportePorFecha","visibleReportePorSede","visibleReporteResueltos","visibleInicio","visibleReportePorUsuario"})
	public void cambio(@BindingParam("id")String valor){
		if(valor.equals("reporteresueltos")){
			visibleReportePorFecha=false;
			visibleReportePorSede=false;
			visibleReporteResueltos=true;
			visibleInicio=false;
			visibleReportePorUsuario=false;
		}else if(valor.equals("reporteporsede")){
			visibleReportePorFecha=false;
			visibleReportePorSede=true;
			visibleReporteResueltos=false;
			visibleInicio=false;
			visibleReportePorUsuario=false;
		}else if(valor.equals("reporteporfecha")){
			visibleReportePorFecha=true;
			visibleReportePorSede=false;
			visibleReporteResueltos=false;
			visibleInicio=false;
			visibleReportePorUsuario=false;
		}else if(valor.equals("reporteporusuario")){
			visibleReportePorFecha=false;
			visibleReportePorSede=false;
			visibleReporteResueltos=false;
			visibleInicio=false;
			visibleReportePorUsuario=true;
		}
	}
	@GlobalCommand
	@NotifyChange({"visibleReporteResueltos","visibleReportePorFecha","visibleReportePorSede","visibleReporteResueltos","visibleInicio","visibleReportePorUsuario"})
	public void cerrar(){
		visibleReportePorFecha=false;
		visibleReportePorSede=false;
		visibleReporteResueltos=false;
		visibleReportePorUsuario=false;
		visibleInicio=true;
	}
	public boolean isVisibleReporteResueltos() {
		return visibleReporteResueltos;
	}
	public void setVisibleReporteResueltos(boolean visibleReporteResueltos) {
		this.visibleReporteResueltos = visibleReporteResueltos;
	}
	public boolean isVisibleReportePorSede() {
		return visibleReportePorSede;
	}
	public void setVisibleReportePorSede(boolean visibleReportePorSede) {
		this.visibleReportePorSede = visibleReportePorSede;
	}
	public boolean isVisibleReportePorFecha() {
		return visibleReportePorFecha;
	}
	public void setVisibleReportePorFecha(boolean visibleReportePorFecha) {
		this.visibleReportePorFecha = visibleReportePorFecha;
	}
	public boolean isVisibleInicio() {
		return visibleInicio;
	}
	public void setVisibleInicio(boolean visibleInicio) {
		this.visibleInicio = visibleInicio;
	}
	public boolean isVisibleReportePorUsuario() {
		return visibleReportePorUsuario;
	}
	public void setVisibleReportePorUsuario(boolean visibleReportePorUsuario) {
		this.visibleReportePorUsuario = visibleReportePorUsuario;
	}
}
