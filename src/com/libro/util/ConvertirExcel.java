package com.libro.util;

import com.libro.model.*;

import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.*;

public class ConvertirExcel {
	public static void convertirResueltos(ArrayList<CListaResueltos> lista){
		try {
			System.out.println("desdecovnertir Excel"+lista.size());
    		WritableFont font1=new WritableFont(WritableFont.TAHOMA,12,WritableFont.BOLD);
    		WritableCellFormat formatCab=new WritableCellFormat(font1);
//    		
    		WritableFont font2=new WritableFont(WritableFont.TAHOMA,12,WritableFont.NO_BOLD);
    		WritableCellFormat format=new WritableCellFormat(font2);    	    
        	//Creates a writable workbook with the given file name
        	WritableWorkbook workbook = jxl.Workbook.createWorkbook(new File("E:/R_RQ_recibidos.xls"));
            WritableSheet sheet = workbook.createSheet("My Sheet", 0);
            
            // Create cell font and format
            WritableFont cellFont = new WritableFont(WritableFont.TAHOMA, 12);
            WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
            cellFormat.setAlignment(Alignment.CENTRE);
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

            //Titulo
            sheet.mergeCells(0, 0, 6, 0);
            sheet.addCell(new Label(0, 0, "REPORTE DE RECLAMOS Y QUEJAS RECIBIDOS", cellFormat));
            
            //codigo
            sheet.addCell(new jxl.write.Label(0,1,"Codigo",formatCab));
            //Nombres
            sheet.addCell(new jxl.write.Label(1,1,"Nombres",formatCab));
            //Apeelidos
            sheet.addCell(new jxl.write.Label(2,1,"Apellidos",formatCab));
            //Fecha
            sheet.addCell(new jxl.write.Label(3,1,"Fecha",formatCab));
            //Tipo
            sheet.addCell(new jxl.write.Label(4,1,"Tipo",formatCab));
            //Solucion
            sheet.addCell(new jxl.write.Label(5,1,"Solucion",formatCab));
            //Writes out the data held in this workbook in Excel format
            for (int i = 2; i < lista.size()+2; i++) {
    			sheet.addCell(new jxl.write.Number(0, i, lista.get(i).getIdsolucionado()));
    			sheet.addCell(new jxl.write.Label(1,i,lista.get(i).getNombres(),format));
    			sheet.addCell(new jxl.write.Label(2,i,lista.get(i).getApellidos(),format));
    			sheet.addCell(new jxl.write.Label(3,i,lista.get(i).getFecha(),format));
    			sheet.addCell(new jxl.write.Label(4,i,lista.get(i).getTipo(),format));
    			sheet.addCell(new jxl.write.Label(5,i,lista.get(i).getSolucion(),format));
			}
            workbook.write(); 
            
            //Close and free allocated memory 
            workbook.close(); 

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
