package com.libro.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.libro.dao.*;
import com.libro.model.*;
import com.lowagie.text.DocumentException;


public class CEmail {
	private String remitente;
	private String password;
	private DecimalFormat df;
	private DecimalFormatSymbols simbolos;
	private CEmpresa empresa;
	private CempresaDAO empresaDAO;
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public CEmail()
	{
		/**Recuperamos la configuracion de SMTP de la Base de Datos**/
		CCorreoSmtpDAO correoSmtpDao=new CCorreoSmtpDAO();
		correoSmtpDao.asignarConfiguracionCorreoSMTP(correoSmtpDao.recuperarCorreoSmtpDB());
		/*******************/
		// TODO Auto-generated constructor stub
		this.remitente = correoSmtpDao.getoCorreoSmtp().getcSMTPUserName();
        this.password = correoSmtpDao.getoCorreoSmtp().getcSMTPPassword();
        System.out.println("Correo "+remitente+" Clave "+password);
        //=================
        simbolos= new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		df=new DecimalFormat("########0.00",simbolos);
		empresaDAO=new CempresaDAO();
		empresaDAO.asignarEmpresaRecuperada(empresaDAO.recuperarDatosEmpresa());
		empresa=empresaDAO.getEmpresa();
		System.out.println("Informacion empresa en cmail"+empresa.getNombre());
	}
	public boolean sendMail(String destinatario, String asunto,String mensaje,String urlPdf,int opcion)
	{
//		System.out.println("Destinatario "+destinatario);
//		System.out.println("Remitente "+remitente);
//		System.out.println("Password "+password);
		try
        {
			Properties props = new Properties();
			props=System.getProperties();
			configurandoProperties(props);
            Session session = Session.getDefaultInstance(props,null);
//             session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setContent(mensaje, "text/html");

            // Se compone el adjunto con el pdf
            BodyPart adjunto = new MimeBodyPart();
            if(opcion==1)//Hay que adjuntar pdf (eso es que el cliente efectuo un pago)
            {
                adjunto.setDataHandler(
                    new DataHandler(new FileDataSource(urlPdf)));
                adjunto.setFileName("DatosReserva.pdf");
            }

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            if(opcion==1)
            	multiParte.addBodyPart(adjunto);

            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
         // A quien va dirigido, con copia y a quien responder
         	InternetAddress address = new InternetAddress(remitente);
         	InternetAddress[] dir={address};
         	message.setReplyTo(dir);
         	message.reply(true);
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect(remitente, password);
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
	}
	public void configurandoProperties(Properties props)
	{
		/**Recuperamos la configuracion de SMTP de la Base de Datos**/
		CCorreoSmtpDAO correoSmtpDao=new CCorreoSmtpDAO();
		correoSmtpDao.asignarConfiguracionCorreoSMTP(correoSmtpDao.recuperarCorreoSmtpDB());
		/*******************/
		if(correoSmtpDao.getoCorreoSmtp().isbTLS())
		{
			// Nombre del host de correo, es smtp.gmail.com
			props.put("mail.smtp.host", correoSmtpDao.getoCorreoSmtp().getcSMTPHost());
			// TLS si est� disponible
			props.setProperty("mail.smtp.starttls.enable", "true");

			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port",""+correoSmtpDao.getoCorreoSmtp().getnSMTPPort());

			// Nombre del usuario
			props.setProperty("mail.smtp.user", remitente);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth","true");
		}else if(correoSmtpDao.getoCorreoSmtp().isbSSL())
		{
			// Nombre del host de correo, es smtp.gmail.com
			props.put("mail.smtp.host", correoSmtpDao.getoCorreoSmtp().getcSMTPHost());
			// Puerto de gmail para envio de correos
			props.setProperty("mail.smtp.port",""+correoSmtpDao.getoCorreoSmtp().getnSMTPPort());
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.ssl.enable", "true");
        	props.setProperty("mail.smtp.socketFactory.port", "465");
        	props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        // Nombre del usuario
	        props.setProperty("mail.smtp.user", remitente);
		}
	}
	public boolean enviarCorreoReclamo(
			CReclamo reclamo,
			CSolicitante solicitante,
			CSedeOcurrencia sedeocurrencia,
			CAreaOcurrencia areaocurrencia,
			CTipoDocumento tipodocumento,
			CTipoProblema tipoproblema,
			ArrayList<CMotivoReclamo> listaMotivo) throws IOException, DocumentException
	{
		/******************************************/
		String solicitud="<strong>Pedido : </strong>";
		
//		String dirfondo="https://image.ibb.co/bSUbbx/fondocorreo.png";
//		String dirlibro="https://image.ibb.co/in6NGx/libro.png";
//		String dircabecera="https://image.ibb.co/eRi2Gx/fondo.png";
		
		String dirfondo=ScannUtil.getPathGeneral()+"img/correo/fondocorreo.png";
		String dirlibro=ScannUtil.getPathGeneral()+"img/correo/libro.png";
		String dircabecera=ScannUtil.getPathGeneral()+"img/correo/fondo.png";
		
		if(!reclamo.getSolicitudreclamo().trim().equals(""))
			solicitud+=reclamo.getSolicitudreclamo()+"<br><br>";
		
		String motivos="<strong>Motivos :</strong> ";
		for (int i = 0; i < listaMotivo.size(); i++) {
			motivos+=listaMotivo.get(i).getDescripcion()+"<br>";
		}
		String idntUsuario="";
		if (tipodocumento.getDescripcion().toLowerCase().equals("ruc")) {
			idntUsuario="<strong>1. IDENTIFICACIÓN DEL CONSUMIDOR RECLAMANTE</strong><br>"+
					"<strong>Tipo Documento :</strong>("+tipodocumento.getDescripcion().toUpperCase()+")"+solicitante.getNrodoc()+"<br>";
					if(!solicitante.getNombres().trim().equals("")){
						idntUsuario=idntUsuario+
								"<strong>Apellidos y Nombres :</strong> "+solicitante.getApellidos()+" "+solicitante.getNombres()+"<br>";
					}
			idntUsuario=idntUsuario+
					"<strong>Telefono:</strong> "+solicitante.getNrotelefono()+"<br>"+
					"<strong>Email:</strong>"+solicitante.getCorreo().toLowerCase()+"<br>"+
					"<strong>Direccion:</strong>"+solicitante.getDireccion()+"<br>";
		} else {
			idntUsuario="<strong>1. IDENTIFICACIÓN DEL CONSUMIDOR RECLAMANTE</strong><br>"+
						"<strong>Tipo Documento :</strong>("+tipodocumento.getDescripcion().toUpperCase()+")"+solicitante.getNrodoc()+"<br>"+
						"<strong>Apellidos y Nombres :</strong> "+solicitante.getApellidos()+" "+solicitante.getNombres()+"<br>"+
						"<strong>Telefono:</strong>"+solicitante.getNrotelefono()+"<br>"+
						"<strong>Email:</strong>"+solicitante.getCorreo().toLowerCase()+"<br>"+
						"<strong>Direccion:</strong>"+solicitante.getDireccion()+"<br>";
		}
		
		String mensajeHTML=""+
		"<!DOCTYPE html>"+
		"<html>"+
		"<head>"+
			"<title></title>"+
		"</head>"+
		"<link  href='https://fonts.googleapis.com/css?family=Titillium Web' rel='stylesheet'>"+
		"<body>"+
		"<div>"+
			"<div style='background-color: #fff;width: 600px;padding-top: 0px; border: 2px solid #e1e1e1;border-radius: 5px;'>"+
			"<div style='display: flex;justify-content: center;align-items: center;background-color:#E7E6E7;background-size: 100%;background-position: top;background-repeat: no-repeat;'>"+
				"<div style='width: 33%;'></div>"+
				"<div style='width: 33%;text-align: right;padding-top: 20px;'><span style='font-family: Arial;font-size: 26px;font-weight: bold;'></span></div>"+
				"<div style='width: 33%;font-family:\"Titillium Web\";font-size: 10px;text-align: center;'>"+
				"</div>"+
			"</div>"+
				"<div style='background-color: black;width: 600px;height: 5px;'></div>"+
				"<div style='display: flex;width: 600px;font-family: \"Titillium Web\";font-size: 12px;'>"+
					"<div style='background-image: url(https://image.ibb.co/bSUbbx/fondocorreo.png);background-size: 100% 100%;background-position: top;background-repeat: no-repeat;padding-left: 40px;padding-bottom: 50px;'>"+
						"<table>"+
							"<tr>"+
								"<td align='center'>"+
									"<strong>HOJA DE RECLAMACIONES Código : "+reclamo.getIdreclamo()+"</strong>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									"<strong>CONFIRMACIÓN DE ENVÍO</strong><br>"+
									"Estimado cliente <b>"+solicitante.getApellidos()+" "+solicitante.getNombres()+"</b><br>"+
									"Su reclamo ha sido enviado satisfactoriamente. Nuestro departamento de atención al cliente se pondrá en comunicación con usted."+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									"<strong>COPIA DE RECLAMO</strong><br>"+
									"<strong>Libro de Reclamaciones</strong><br>"+
									"<strong>Fecha:</strong> "+forFecha(reclamo.getFechareclamo())+"<br>"+
									empresa.getNombre()+" "+empresa.getTelefono1()+ "<br>"+
									empresa.getDireccion()+"<br>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									idntUsuario+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									"<strong>2. IDENTIFICACIONES DEL BIEN CONTRATADO</strong><br>"+
									"<strong>Lugar de Ocurrencia :</strong>"+sedeocurrencia.getNombre()+"<br>"+
									"<strong>Area de Ocurrencia :</strong> "+areaocurrencia.getNombre()+"<br>"+
//									"<strong>Fecha de Ocurrencia:</strong> "+forFecha(reclamo.getFecha())+"<br>"+
									"<strong>Reclamo por:</strong>Producto<br>"+
									motivos+
									"<strong>Monto reclamado:</strong>Producto<br>"+
									"<strong>Descripción:</strong>Producto<br>"+

								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td>"+
									"<strong>3. DETALLE DE LA RECLAMACIÓN Y PEDIDO DEL CONSUMIDOR</strong><br>"+
									"<strong>Tipo : </strong>"+tipoproblema.getDescripcion()+"<br>"+
									"<strong>Detalle:</strong> "+reclamo.getDetallereclamo()+"<br>"+
									solicitud+
									"Atentamente,</br></br>"+empresa.getNombre()+"<br><br>"+
									"<strong>NOTA:</strong>"+empresa.getIdempresa()+"queda facultada a efectuar respuesta de la queja o reclamo en el correo electrónico consignado por el consumidor, la misma que se realizará en los términos concedidos por la norma.<br><br>"+
									"<strong>RECLAMO:</strong> Disconformidad relacionada a los productos o servicios adquiridos.<br></br>"+
									"<strong>QUEJA:</strong>  Disconformidad no relacionada a los productos o servicios; o malestar o descontento respecto a la atención al público.<br>"+
								"</td>"+
							"</tr>"+
							"<tr>"+
								"<td style='font-family: 'Titillium Web';'>"+
									"<span style='padding-left: 20px;'>* La formulación del reclamo no impide acudir a otras vías de solución de controversias ni es requisito previo para interponer una denuncia ante el INDECOPI.</span><br>"+
									"<span style='padding-left: 20px;'>* El Proveedor deberá dar respuesta al reclamo en un plazo no mayor a treinta (30) días calendario, pudiendo ampliar el plazo hasta por treinta (30) días más, previa comunicación al consumidor</span>"+
								"</td>"+
							"</tr>"+
						"</table>"+
					"</div>"+
				"</div>"+
			"</div>"+
		"</div>"+
		"</body>"+
		"</html>";
		//convertHTML_PDF.convertirHtml2Pdf(mensajeHTML,urlPdf);
		sendMail(this.remitente, "NUEVO RECLAMO", mensajeHTML, "", 2);
		return sendMail(solicitante.getCorreo(),"DETALLES DE SU RECLAMO",mensajeHTML,"",2);
		
	}
	public boolean enviarCorreoSolucion(
			CReclamo reclamo,
			CSolicitante solicitante,
			CSedeOcurrencia sedeocurrencia,
			CAreaOcurrencia areaocurrencia,
			CTipoDocumento tipodocumento,
			CTipoProblema tipoproblema,
			CSolucionado solucionado,
			CSolucion solucion,
			ArrayList<CMotivoReclamo> listaMotivo,
			String urlDocs) throws IOException, DocumentException
	{
		int modo=-1;
		if(urlDocs.trim().equals("")){
			modo=2;
		}else{
			modo=1;
		}
		/******************************************/
//		System.out.println("DATOS EN EL CORREO");
//		System.out.println(solicitante.getCorreo());
		String mensajeHTML=""+
		"<!DOCTYPE html>"+
		"<html>"+
		"<head>"+
			"<title></title>"+
		"</head>"+
		"<link  href='https://fonts.googleapis.com/css?family=Titillium Web' rel='stylesheet'>"+
		"<body>"+
			"<div>"+
				"<h2>"+solucion.getDetalles().toUpperCase()+"</h2>"+
			"</div>"+
			"<div>"+
				solucionado.getDetalles()+"<br>"+
			"</div>"+
		"</body>"+
		"</html>";
		//convertHTML_PDF.convertirHtml2Pdf(mensajeHTML,urlPdf);
		//sendMail(this.remitente, "NUEVO RECLAMO", mensajeHTML, "", 1);
		return sendMail(solicitante.getCorreo(),"Respuesta Reclamo "+empresa.getNombre()+" "+reclamo.getFechareclamo()+" Nro. "+reclamo.getIdreclamo(),mensajeHTML,urlDocs,modo);
	}
	public boolean enviarCorreoNuevoUsuario(
			CUsuario usuario,String auxContra) throws IOException, DocumentException
	{
		/******************************************/
		String mensajeHTML=""+
		"<!DOCTYPE html>"+
		"<html>"+
		"<head>"+
			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"+
			"<title></title>"+
		"</head>"+
		"<link  href='https://fonts.googleapis.com/css?family=Titillium Web' rel='stylesheet'>"+
		"<body>"+
			"<div>"+
				"<table style='border:1px solid gray;border-radius: 3px;'>"+
					"<tr>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>Nombres y Apellidos</td>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>: "+usuario.getNombres().toUpperCase()+" "+usuario.getApellidos().toUpperCase()+"</td>"+
					"</tr>"+
					"<tr>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>Usuario</td>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>: "+usuario.getIdusuario()+"</td>"+
					"</tr>"+
					"<tr>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>Contraseña</td>"+
						"<td style='font-family: \"Bahnschrift\";font-size: 20px;'>: "+auxContra+"</td>"+
					"</tr>"+
					"<tr>"+
						"<td colspan='2' style='font-family: \"Bahnschrift\";text-align: center;'><h2>"+empresa.getNombre()+" </h2></td>"+
					"</tr>"+
				"</table>"+
			"</div>"+
		"</body>"+
		"</html>";
		//convertHTML_PDF.convertirHtml2Pdf(mensajeHTML,urlPdf);
		//sendMail(this.remitente, "NUEVO RECLAMO", mensajeHTML, "", 1);
		return sendMail(usuario.getCorreo(),"DATOS NUEVO USUARIO "+empresa.getNombre()+" ",mensajeHTML,"",2);
	}


	public String forFecha(Date dfecha){
		String fecha=new SimpleDateFormat("yyyy-MM-dd").format(dfecha);
		String anio=fecha.split("-")[0];
		String mes=fecha.split("-")[1];
		String dia=fecha.split("-")[2];
		String mmes="";
		switch (mes) {
			case "01":mmes="Enero";break;
			case "02":mmes="Febrero";break;
			case "03":mmes="Marzo";break;
			case "04":mmes="Abril";break;
			case "05":mmes="Mayo";break;
			case "06":mmes="Junio";break;
			case "07":mmes="Julio";break;
			case "08":mmes="Agosto";break;
			case "09":mmes="Setiembre";break;
			case "10":mmes="Octubre";break;
			case "11":mmes="Noviembre";break;
			case "12":mmes="Diciembre";break;
		}
		return dia+" de "+mmes+" del "+anio;
	}
}
