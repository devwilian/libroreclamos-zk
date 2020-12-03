package com.libro.viewModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.annotation.*;

import com.libro.util.CEmail;
import com.lowagie.text.DocumentException;

public class imagenesreclamoVM {
	private String label;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Init
	public void initVM() throws IOException, DocumentException{
//		String fecha=new SimpleDateFormat("yyyy-MM-dd").format(dfecha);
		String fecha="2018-12-24";
		String anio=fecha.split("-")[0];
		String mes=fecha.split("-")[1];
		String dia=fecha.split("-")[2];
		String mmes="";
		switch (mes) {
		case "01":mmes="Enero";
			break;
		case "02":mmes="Febrero";
			break;
		case "03":mmes="Marzo";
			break;
		case "04":mmes="Abril";
			break;
		case "05":mmes="Mayo";
			break;
		case "06":mmes="Junio";
			break;
		case "07":mmes="Julio";
			break;
		case "08":mmes="Agosto";
			break;
		case "09":mmes="Setiembre";
			break;
		case "10":mmes="Octubre";
			break;
		case "11":mmes="Noviembre";
			break;
		case "12":mmes="Diciembre";
			break;
		}
		System.out.println(dia+" "+mmes+" "+anio);
	}
}
