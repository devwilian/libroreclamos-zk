package com.libro.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class EjecutorSQL {
	
	private JdbcTemplate jdbc;
	private DataSource ds;
	private Connection conn;
	private static EjecutorSQL instance;
	public static EjecutorSQL getEjecutorSQL(DataSource ds)
	{
		if(instance==null)
		{
			return instance=new EjecutorSQL(ds);
		}
		else
		{
			return instance;
		}
	}
	public EjecutorSQL(DataSource pds){
		ds=pds;
		jdbc=new JdbcTemplate(ds);
		try
		{
//			conn= jdbc.getDataSource().getConnection();
			//Obtener la conexión con la base de datos.
//	         Connection conn = pds.getConnection();
			//se ejecutan las operaciones con las bases de datos
//	         Statement stmt = conn.createStatement ();
			conn = ds.getConnection();
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("select * from user");
//            int cnt = 1;
//            while (rs.next()) {
//                System.out.println((cnt++)+". Host:" +rs.getString("Host")+
//                  " User:"+rs.getString("User")+" Password:"+rs.getString("Password"));
//            }
//            rs.close();
//            st.close();
		} catch (SQLException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public List ejecutarProcedimiento(String procedimiento,Object[] values){
		final Object[] val=values;
		procedimiento="SELECT * FROM "+procedimiento+"(";
   	 	int n =values.length;
   	 	for(int i=0;i<n;i++){
   	 		if(val[i].getClass().equals(Date.class))
	 			procedimiento=procedimiento+"?::date,";
	 		else if(val[i].getClass().equals(Double.class))
	 			procedimiento=procedimiento+"?::numeric,";
	 		else
	 			procedimiento=procedimiento+"?,";
   	 	}
   	 	final String proc=procedimiento.substring(0, procedimiento.length()-1)+")";
   	 	System.out.println(proc);
				PreparedStatement ps;
				try {
					
					ps = conn.prepareStatement(proc);
				
					for(int i=0;i<val.length;i++){
						if (val[i].getClass().equals(Integer.class)) {
							ps.setInt(i+1,(int) val[i]);
							System.out.println("int : "+ (int) val[i]);
					    }
					    else if (val[i].getClass().equals(String.class)) {
					    	ps.setString(i+1,(String) val[i]);
					    	System.out.println("string : "+ (String) val[i]);
					    }
					    else if (val[i].getClass().equals(Float.class)) {
					    	ps.setFloat(i+1,(Float) val[i]);
					    	System.out.println("float : "+ (Float) val[i]);
					    }
					    else if (val[i].getClass().equals(Double.class)) {
					    	ps.setDouble(i+1,(double) val[i]);
					    	System.out.println("double : "+ (double) val[i]);
					    }
					    else if (val[i].getClass().equals(Number.class)) {
					    	ps.setDouble(i+1,(double) val[i]);
					    	System.out.println("double : "+ (double) val[i]);
					    }
					    else if (val[i].getClass().equals(Boolean.class)) {
					    	ps.setBoolean(i+1,(boolean) val[i]);
					    	System.out.println("boolean : "+ (boolean) val[i]);
					    }
					    else if (val[i].getClass().equals(Long.class)) {
					    	ps.setLong(i+1,(long) val[i]);
					    	System.out.println("long : "+ (long) val[i]);
					    }
					    else
					    {
					    	java.sql.Date fecha=  new java.sql.Date(((Date)val[i]).getTime());
					    	ps.setDate(i+1,fecha);
					    	System.out.println("string : "+ fecha.toString());
					    }
		    			 
		    		 }
				System.out.println(ps.toString());
				ResultSet rs=ps.executeQuery();
				int columns = rs.getMetaData().getColumnCount();  
				List results = new ArrayList();  	  
			    while (rs.next()) 
			    {  
			        HashMap row = new HashMap();  
			        results.add(row);  
			        for(int i=1; i<=columns; i++)
			        {  
			          row.put(rs.getMetaData().getColumnName(i),rs.getObject(i));  
			        }
			    }  			    
				rs.close();
				ps.close();
				return results;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					return null;
				}
				// TODO Auto-generated method stub
	}
	
public List ejecutarProcedimiento(String procedimiento){
		
		procedimiento="SELECT * FROM "+procedimiento+"(";
   	   	 	
   	 	final String proc=procedimiento+")";
   	 
				PreparedStatement ps;
				
				try {
					
					ps = conn.prepareStatement(proc);
				
				ResultSet rs=ps.executeQuery();
				int columns = rs.getMetaData().getColumnCount();  
				List results = new ArrayList();  	  
			    while (rs.next()) 
			    {  
			        HashMap row = new HashMap();  
			        results.add(row);  
			        for(int i=1; i<=columns; i++)
			        {  
			          row.put(rs.getMetaData().getColumnName(i),rs.getObject(i));  
			        }
			    }  			    
				rs.close();
				ps.close();
				return results;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					return null;
				}
				// TODO Auto-generated method stub
	}
}
