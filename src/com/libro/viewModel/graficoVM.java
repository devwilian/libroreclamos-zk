package com.libro.viewModel;

import org.zkoss.bind.annotation.*;
import org.zkoss.zul.SimplePieModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;
import com.libro.model.*;
import com.libro.dao.*;

public class graficoVM {
	private Object[]datos;
	private int nroTotal;
	private int nroPerCusco;
	private int nroPerMachupicchu;
	private ArrayList<CLista>listaSedeColor;
	private ArrayList<CReportesede>listaReporteSede;
	private Double porcCusco;
	private Double porcMachu;
	private CsolicitanteDAO solicitanteDAO;
	private CsolucionadoDAO solucionadoDAO;
	private CsedeOcurrenciaDAO sedeDAO;
	private String[] estilo={"estilo1","estilo2","estilo3","estilo4","estilo5","estilo6","estilo7","estilo8","estilo9","estilo10"};
	
	@Init
	public void initVM(){
		datos=new Object[11];
		nroTotal=0;
		nroPerCusco=0;
		nroPerMachupicchu=0;
		solucionadoDAO=new CsolucionadoDAO();
		sedeDAO=new CsedeOcurrenciaDAO();
		listaSedeColor=new ArrayList<CLista>();
		listaReporteSede=new ArrayList<CReportesede>();
		iniciar();
	}
	public void iniciar(){
		sedeDAO.asignarListaSedeOcurrencia(sedeDAO.obtenerSedeOcurrencia());
		
		sedeDAO.asignarListaReporteporSede(sedeDAO.obtenerReporteporSede());
		listaReporteSede=sedeDAO.getListaReporteSede();
//		for (int i = 0; i < listaReporteSede.size(); i++) {
//			System.out.println(listaReporteSede.get(i).getNombresede()+" == "+listaReporteSede.get(i).getCantidad());
//		}
		for (int i = 0; i < listaReporteSede.size(); i++) {
			listaSedeColor.add(new CLista(estilo[i],listaReporteSede.get(i).getNombresede(),listaReporteSede.get(i).getCantidad()));
			//System.out.println(listaSedeColor.get(i).getEstilo()+" == "+listaSedeColor.get(i).getNombre());
		}
		datos=solucionadoDAO.obtenerReportes(solucionadoDAO.numeroReportes());
		try {
			int total=(int)datos[2];
			porcCusco=(double) (((int)datos[5]*100)/total);
			porcMachu=(double) (((int)datos[6]*100)/total);
		} catch (Exception e) {
			porcCusco=0.0;
			porcMachu=0.0;
		}
		
	}
	public SimplePieModel getGrafica(){
		System.out.println("Entra grafica pie model");
		String[]dats={"Reclamos","Quejas"};
		int cusco=(int)datos[5];
		int machu=(int)datos[6];
		SimplePieModel demoModel=new SimplePieModel();
//		demoModel.setValue("Cusco", cusco);
//		demoModel.setValue("Machupicchu", machu);
		
		for (int i = 0; i < listaSedeColor.size(); i++) {
			demoModel.setValue(listaSedeColor.get(i).getNombre(), listaSedeColor.get(i).getCantidad());
		}
		
//		demoModel.setValue(listaReporteSede.get(0).getNombresede(), listaReporteSede.get(0).getCantidad());
//		demoModel.setValue(listaReporteSede.get(1).getNombresede(), listaReporteSede.get(1).getCantidad());
//		demoModel.setValue(listaReporteSede.get(2).getNombresede(), listaReporteSede.get(2).getCantidad());
//		demoModel.setValue(listaReporteSede.get(3).getNombresede(), listaReporteSede.get(3).getCantidad());
		
//		demoModel.setValue("Machupicchu", 4);
//		demoModel.setValue("estilo3", 6);
//		demoModel.setValue("estilo4", 2);
//		demoModel.setValue("estilo5", 4);
//		demoModel.setValue("estilo6", 7);
//		demoModel.setValue("estilo7", 4);
//		demoModel.setValue("estilo8", 5);
//		demoModel.setValue("estilo9", 8);
//		demoModel.setValue("estilo10", 4);
		return demoModel;
	}
	public class CLista{
		private String estilo;
		private String nombre;
		private int cantidad;
		public int getCantidad() {
			return cantidad;
		}
		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
		public String getEstilo() {
			return estilo;
		}
		public void setEstilo(String estilo) {
			this.estilo = estilo;
		}
		public String getNombre() {
			return nombre;
		}
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
		public CLista(String estilo, String nombre,int cantidad) {
			this.estilo = estilo;
			this.nombre = nombre;
			this.cantidad=cantidad;
		}
		public CLista() {
			this.estilo = "";
			this.nombre = "";
			this.cantidad=0;
		}
	}
	public int getNroPerCusco() {
		return nroPerCusco;
	}
	public void setNroPerCusco(int nroPerCusco) {
		this.nroPerCusco = nroPerCusco;
	}
	public int getNroPerMachupicchu() {
		return nroPerMachupicchu;
	}
	public void setNroPerMachupicchu(int nroPerMachupicchu) {
		this.nroPerMachupicchu = nroPerMachupicchu;
	}
	public Double getPorcCusco() {
		return porcCusco;
	}
	public void setPorcCusco(Double porcCusco) {
		this.porcCusco = porcCusco;
	}
	public Double getPorcMachu() {
		return porcMachu;
	}
	public void setPorcMachu(Double porcMachu) {
		this.porcMachu = porcMachu;
	}
	public Object[] getDatos() {
		return datos;
	}
	public void setDatos(Object[] datos) {
		this.datos = datos;
	}
	public int getNroTotal() {
		return nroTotal;
	}
	public void setNroTotal(int nroTotal) {
		this.nroTotal = nroTotal;
	}
	public ArrayList<CLista> getListaSedeColor() {
		return listaSedeColor;
	}
	public void setListaSedeColor(ArrayList<CLista> listaSedeColor) {
		this.listaSedeColor = listaSedeColor;
	}
	public ArrayList<CReportesede> getListaReporteSede() {
		return listaReporteSede;
	}
	public void setListaReporteSede(ArrayList<CReportesede> listaReporteSede) {
		this.listaReporteSede = listaReporteSede;
	}
}
