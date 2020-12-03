package com.libro.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.libro.model.CAcceso;
import com.libro.model.CUsuario;
import com.libro.model.CUsuarioLogin;

public class CusuarioLoginDAO extends CConexion implements Serializable{
	private static final long serialVersionUID = 1L;
	private CUsuarioLogin usuario;
	private CUsuarioLogin oUsuario;
	private ArrayList<CUsuario> listaUsuarios;
	CAcceso oAcceso;
	//====CONSTRUCTORES=====
	public CusuarioLoginDAO(){
		super();
		usuario=new CUsuarioLogin();
		oAcceso=new CAcceso();
		oUsuario=new CUsuarioLogin();
	}
	//====GETTER AND SETTER====
	public CUsuarioLogin getUsuario() {
		return usuario;
	}
	public void setUsuario(CUsuarioLogin usuario) {
		this.usuario = usuario;
	}
	public CAcceso getoAcceso() {
		return oAcceso;
	}
	public void setoAcceso(CAcceso oAcceso) {
		this.oAcceso = oAcceso;
	}
	public CUsuarioLogin getoUsuario() {
		return oUsuario;
	}
	public void setoUsuario(CUsuarioLogin oUsuario) {
		this.oUsuario = oUsuario;
	}
	
	public ArrayList<CUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(ArrayList<CUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	//--------------------------
	//=======METODOS==========
	public List validarLogin()
	{
		//System.out.println("LOS DATOS EN USUARIO LOGIN"+usuario.getcUsuarioCod()+" , "+usuario.getcClave());
		Object[] user={usuario.getcUsuarioCod(),usuario.getcClave()};
		return ejecutarProcedimiento("libro_validarlogin", user);
	}
	public List recuperarUsuario(String usuario,String password)
	{
		Object[] values={usuario,password};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarusuariologin", values);
	}
	public List obtenerSistemasPerfil(String subsistema)
	{
		return ejecutarProcedimiento("CP_sp_SistemasPerfil_Listar", new String[]{Integer.toString(usuario.getnPerfilCod()),subsistema});
	}
	public List recuperarAccesosUsuario(int codPerfil)
	{
		Object[] values={codPerfil};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperaraccesoperfil", values);
	}
	public List recuperarCodigoAccesosUsuario(int codPerfil){
		Object[]values={codPerfil};
		return getEjecutorSQL().ejecutarProcedimiento("libro_recuperarcodigodeacceso",values);
	}
	public List recuperarUsuariosBD()
	{
		//System.out.println("esta entrando aqui..?");
		return getEjecutorSQL().ejecutarProcedimiento("Pricing_sp_MostrarTodosUsuarios");
	}
	public List insertarUsuario(CUsuario user)
	{
//		System.out.println(user.getClave()+" | "+
//				user.getNrodoc()+" | "+
//				user.getNombres()+" | "+
//				user.getApellidos()+" | "+
//				user.getSexo()+" | "+
//				user.getFechanacimiento()+" | "+
//				user.getCorreo()+" | "+
//				true+" | "+
//				user.getIdperfil());
		Object[] values={
				user.getClave(),
				user.getNrodoc(),
				user.getNombres(),
				user.getApellidos(),
				user.getSexo(),
				user.getFechanacimiento(),
				user.getCorreo(),
				true,
				user.getIdperfil()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_insertarusuario", values);
	}
	public List modoficarUsuario(CUsuarioLogin usuario)
	{
		Object[] values={
				usuario.getcClave(),
				usuario.getcNroDoc(),
				usuario.getcNombres(),
				usuario.getApellidos(),
				usuario.getcSexo(),
				usuario.getdFechaNac(),
				usuario.getcCorreo(),
				true,
				usuario.getnPerfilCod(),
				usuario.getImgUsuario()};
		return getEjecutorSQL().ejecutarProcedimiento("libro_actualizarusuario", values);
	}
	public List modificarEstadoUsuario(String codUsuario,boolean estado){
		Object[]values={codUsuario,estado};
		return getEjecutorSQL().ejecutarProcedimiento("Pricing_sp_ModificarEstadoUsuario",values);
	}
	public void asignarListaUsuarios(List lista){
		System.out.println("entra en lista usuariosDAO..");
		listaUsuarios=new ArrayList<CUsuario>();
		for(int i=0;i<lista.size();i++)
		{
			Map row=(Map)lista.get(i);
			listaUsuarios.add(new CUsuario(
					(String)row.get("idusuario"),
					(String)row.get("clave"),
					(String)row.get("nrodoc"),
					(String)row.get("nombres"),
					(String)row.get("apellidos"),
					(String)row.get("sexo"),
					(Date)row.get("fechanacimiento"),
					(String)row.get("correo"),
					(boolean)row.get("estado"),
					(int)row.get("idperfil"),
					(Date)row.get("fechainicio"),
					(String)row.get("imagen")));
		}
	}
	
	public void asignarUsuario(List lista)
	{
		Map row=(Map)lista.get(0);
		  oUsuario.setcUsuarioCod((String)row.get("idusuario"));
		  oUsuario.setcClave((String)row.get("clave"));
		  oUsuario.setnPerfilCod((int)row.get("idperfil"));
		  oUsuario.setImgUsuario((String)row.get("imagen"));
		  oUsuario.setcNroDoc((String)row.get("nrodoc"));
		  oUsuario.setcNombres((String)row.get("nombres"));
		  oUsuario.setApellidos((String)row.get("apellidos"));
		  oUsuario.setcSexo((String)row.get("sexo"));
		  oUsuario.setcCorreo((String)row.get("correo"));
		  oUsuario.setbEstado((boolean)row.get("estado"));
		  oUsuario.setdFechaInicio((Date)row.get("fechainicio"));
		  oUsuario.setdFechaNac((Date)row.get("fechanacimiento"));
	}
	public void asignarAccesosUsuario(List lista)
	{
		Map row=(Map)lista.get(0);
		oAcceso=new CAcceso(
				(int)row.get("idacceso"),
				(boolean)row.get("accesoidioma"),
				(boolean)row.get("accesoregusuarios"),
				(boolean)row.get("accesoreportes"),
				(boolean)row.get("accesorecibidos"),
				(boolean)row.get("accesoprocesos"),
				(boolean)row.get("accesoconfiguracion"),
				(boolean)row.get("accesolistausuario"));
	}
	public boolean isOperationCorrect(List lista)
	{
		Map row=(Map)lista.get(0);
		boolean correcto=row.get("resultado").toString().equals("correcto");
		if(correcto)return true;
		else return false;
	}
	public int codigoAcceso(List lista)
	{
		Map row=(Map)lista.get(0);
		return (int)row.get("cod");
	}
    /*METODOS REDEFINIDOS*/
    public List ejecutarProcedimiento(String procedimiento){
    	return getEjecutorSQL().ejecutarProcedimiento(procedimiento);
    }
    public List ejecutarProcedimiento(String procedimiento,Object[] values){
    	return getEjecutorSQL().ejecutarProcedimiento(procedimiento, values);
    }
	public List eliminarUsuario(String cusuariocod) {
		Object[] values={cusuariocod};
		return getEjecutorSQL().ejecutarProcedimiento("pricing_sp_eliminarusuario", values);
	}
}
