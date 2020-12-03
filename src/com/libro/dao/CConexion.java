package com.libro.dao;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import com.libro.util.EjecutorSQL;
public class CConexion 
{
	private DataSource ds;
    private EjecutorSQL ejec;
   // private DriverManagerDataSource dsmanager;
    private Boolean estaconectado;
   // private Connection conexion;
    
    public DataSource getDataSource(){    	
    	return ds;
    }
    public EjecutorSQL getEjecutorSQL(){
    	return ejec;
    }
    public CConexion(){    	
    	try {
    		//introduce las condiciones de la conexion.
    		 PoolProperties p = new PoolProperties();
             p.setUrl("jdbc:postgresql://localhost:5432/DBReclamos");
//             p.setUrl("jdbc:postgresql://94.237.61.223:5432/DBDemo");
             p.setDriverClassName("org.postgresql.Driver");
             p.setUsername("postgres");
             p.setPassword("12345");
//             p.setPassword("lrv2020@@");
             p.setJmxEnabled(true);
             p.setTestWhileIdle(false);
             p.setTestOnBorrow(true);
             p.setValidationQuery("SELECT 1");
             p.setTestOnReturn(false);
             p.setValidationInterval(30000);
             p.setTimeBetweenEvictionRunsMillis(30000);
             p.setMaxActive(100);
             p.setInitialSize(20);
             p.setMaxWait(10000);
             p.setRemoveAbandonedTimeout(60);
             p.setMinEvictableIdleTimeMillis(30000);
             p.setMinIdle(10);
//             p.setLogAbandoned(true);
//             p.setRemoveAbandoned(true);
             p.setJdbcInterceptors(
               "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
               "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
             ds = new DataSource();
             ds.setPoolProperties(p);
            ejec=EjecutorSQL.getEjecutorSQL(ds);
		} catch (Exception e){
			// TODO: handle exception
			estaconectado=false;
			System.out.println(e.toString());
		}
    }
	public Boolean getEstaconectado() {
		return estaconectado;
	}
}
