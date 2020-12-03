package com.libro.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CReSizeImage {
	  //Ancho máximo
	    public static int MAX_WIDTH=400;
		//Alto máximo
	    public static int MAX_HEIGHT=400;

	    public static boolean tamanioSuficiente(String filePath)
	    {
	    	BufferedImage bimage = loadImage(filePath);
	    	if(bimage.getHeight()<=MAX_HEIGHT && bimage.getWidth()<=MAX_WIDTH)return true;
	    	else return false;
	    }
		/*Este método es el de la magia recibe la ruta al archivo original y la ruta donde vamos a guardar la copia
		copyImage("C:\\Users\\IngenioDS\\Desktop\\test.png","C:\\Users\\IngenioDS\\Desktop\\Copia\\test2.png");*/

	    public static void copyImage(String filePath, String copyPath,String formato) {
	        BufferedImage bimage = loadImage(filePath);
	        if(bimage.getHeight()>MAX_HEIGHT)
	        	bimage = resize(bimage, bimage.getWidth(), MAX_HEIGHT);
	        if(bimage.getWidth()>MAX_WIDTH)
	        	bimage=resize(bimage,MAX_WIDTH,bimage.getHeight());
	        saveImage(bimage, copyPath,formato);
	    }
		
		/*
		Este método se utiliza para cargar la imagen de disco
		*/
	    public static BufferedImage loadImage(String pathName) {
	        BufferedImage bimage = null;
	        try {
	            bimage = ImageIO.read(new File(pathName));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return bimage;
	    }

	    /*
		Este método se utiliza para almacenar la imagen en disco
		*/
		public static void saveImage(BufferedImage bufferedImage, String pathName,String formato) {
	        try {
	            File file =new File(pathName);
	            file.getParentFile().mkdirs();
	            ImageIO.write(bufferedImage, formato, file);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
		/*
		Este método se utiliza para redimensionar la imagen
		*/
	    public static BufferedImage resize(BufferedImage bufferedImage, int newW, int newH) {
	        int w = bufferedImage.getWidth();
	        int h = bufferedImage.getHeight();
	        BufferedImage bufim = new BufferedImage(newW, newH, bufferedImage.getType());
	        Graphics2D g = bufim.createGraphics();
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	        g.drawImage(bufferedImage, 0, 0, newW, newH, 0, 0, w, h, null);
	        g.dispose();
	        return bufim;
	    }
}
