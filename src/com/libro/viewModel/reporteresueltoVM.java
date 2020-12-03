package com.libro.viewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

import javax.activation.MimetypesFileTypeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.*;

import com.libro.dao.*;
import com.libro.model.*;
import com.libro.util.*;

import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class reporteresueltoVM {
	private CSolucionado solucionado;
	private CsolucionadoDAO solucionadoDAO;
	private ArrayList<CListaResueltos> listaSolucionado;
	private boolean verDetallesSolucion;
	private String detallesSolucion;
	private int ordenar;
	@Init
	public void initVM(){
		ordenar=1;
		
		verDetallesSolucion=false;
		detallesSolucion="";
		
		solucionadoDAO=new CsolucionadoDAO();
		listaSolucionado=new ArrayList<CListaResueltos>();
		iniciar();		
	}
	public void iniciar(){
		solucionadoDAO.asignarListaSolucionados(solucionadoDAO.recuperarListaSolucionados(ordenar));
		listaSolucionado=solucionadoDAO.getListaSolucionado();
	}
	@Command
	@NotifyChange({"detallesSolucion","verDetallesSolucion"})
	public void changeDetalle(@BindingParam("detalles")String valor){
		detallesSolucion=valor;
		verDetallesSolucion=true;
	}
	@Command
	@NotifyChange({"verDetallesSolucion"})
	public void cerrar(){
		verDetallesSolucion=false;
	}
	@Command
	@NotifyChange({"listaSolucionado"})
	public void ordenar(@BindingParam("id")String valor){
		if(valor.equals("idrq")){
			ordenar=1;
		}else if(valor.equals("idr")){
			ordenar=2;
		}else if(valor.equals("idq")){
			ordenar=3;
		}
		iniciar();
	}
	@Command
	public void generarReporte(@BindingParam("comp")Component comp) throws Exception{
		try {
			convertirResueltos(listaSolucionado,comp);
		} catch (Exception e) {
			Clients.showNotification("Cierre el ultimo documento que se genero", Clients.NOTIFICATION_TYPE_ERROR, comp, "before_start", 2500);
		}
	}
	 public void convertirResueltos(ArrayList<CListaResueltos>list,Component comp) throws Exception {
		 String nombreArchivo="R_Resueltos.xls";
	        HSSFWorkbook workbook = new HSSFWorkbook();
	        HSSFSheet sheet = workbook.createSheet();
	        workbook.setSheetName(0, "Reporte Resueltos");

	        String[] headers = new String[]{
	            "Codigo",
	            "Nombres",
	            "Apellidos",
	            "Razon social",
	            "Fecha",
	            "Tipo",
	            "Solucion"
	        };

	        Object[][]data=new Object[list.size()][7];
	        for (int i = 0; i < list.size(); i++) {
				data[i][0]=list.get(i).getIdsolucionado();
				data[i][1]=list.get(i).getNombres();
				data[i][2]=list.get(i).getApellidos();
				data[i][3]=list.get(i).getRazonsocial();
				data[i][4]=list.get(i).getFecha();
				data[i][5]=list.get(i).getTipo();
				data[i][6]=list.get(i).getSolucion();
			}
	        	        
//	        for (int i = 0; i < list.size(); i++) {
//				for (int j = 0; j < 7; j++) {
//					System.out.print(data[i][j]+" ");
//				}
//				System.out.println();
//			}
	        CellStyle headerStyle = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        headerStyle.setFont(font);

	        CellStyle style = workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

	        HSSFRow headerRow = sheet.createRow(0);
	        for (int i = 0; i < headers.length; ++i) {
	            String header = headers[i];
	            HSSFCell cell = headerRow.createCell(i);
	            cell.setCellStyle(headerStyle);
	            cell.setCellValue(header);
	        }

	        for (int i = 0; i < data.length; ++i) {
	            HSSFRow dataRow = sheet.createRow(i + 1);

	            Object[] d = data[i];
	            String a = String.valueOf(d[0]);
	            String b = (String) d[1];
	            String c = (String) d[2];
	            String z = (String) d[3];
	            String e = (String) d[4];
	            String f = (String) d[5];
	            String g = (String) d[6];

	            dataRow.createCell(0).setCellValue(a);
	            dataRow.createCell(1).setCellValue(b);
	            dataRow.createCell(2).setCellValue(c);
	            dataRow.createCell(3).setCellValue(z);
	            dataRow.createCell(4).setCellValue(e);
	            dataRow.createCell(5).setCellValue(f);
	            dataRow.createCell(6).setCellValue(g);
	        }

	        HSSFRow dataRow = sheet.createRow(1 + data.length);
	        dataRow.createCell(0).setCellValue("Total");
	        dataRow.createCell(1).setCellValue(list.size());

	        FileOutputStream file = new FileOutputStream(ScannUtil.getPathExcel()+nombreArchivo);
	        workbook.write(file);
	        file.close();
	        download(nombreArchivo,comp);
	}
	 public void download(String archivo,Component comp) {
			System.out.println("Click");
	        FileInputStream inputStream;
	        try {
	            File dosfile = new File(ScannUtil.getPathExcel()+archivo);
	            if (dosfile.exists()) {
	                inputStream = new FileInputStream(dosfile);
	                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
	                //Clients.showNotification("Se completo la descarga", Clients.NOTIFICATION_TYPE_INFO, comp, "before_start", 2500);
	            } else
	                System.out.println("NO se pudo descargar");

	        } catch (FileNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	public CSolucionado getSolucionado() {
		return solucionado;
	}
	public void setSolucionado(CSolucionado solucionado) {
		this.solucionado = solucionado;
	}
	public ArrayList<CListaResueltos> getListaSolucionado() {
		return listaSolucionado;
	}
	public void setListaSolucionado(ArrayList<CListaResueltos> listaSolucionado) {
		this.listaSolucionado = listaSolucionado;
	}
	public boolean isVerDetallesSolucion() {
		return verDetallesSolucion;
	}
	public void setVerDetallesSolucion(boolean verDetallesSolucion) {
		this.verDetallesSolucion = verDetallesSolucion;
	}
	public String getDetallesSolucion() {
		return detallesSolucion;
	}
	public void setDetallesSolucion(String detallesSolucion) {
		this.detallesSolucion = detallesSolucion;
	}
	public int getOrdenar() {
		return ordenar;
	}
	public void setOrdenar(int ordenar) {
		this.ordenar = ordenar;
	}
}
