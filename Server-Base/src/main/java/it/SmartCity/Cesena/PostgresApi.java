package it.SmartCity.Cesena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostgresApi {
	 private Connection connection=null;
	
	public List<String> getLastValue()throws SQLException{
		
		
		 List<String> lista = new ArrayList<String>();
		 Connection connection=null;
	  	    
	  	    connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	  System.out.println("connection ok");
	  	    ResultSet rs = st.executeQuery("select * from weather where id=(select max(id) from weather)");
	  	    while (rs.next()) {
	  	        //System.out.print("Column 4 _ Column 1 ");
	  	        System.out.println(rs.getString(2));
	  	        System.out.println(rs.getInt(3));
	  	      System.out.println(rs.getInt(4));
	  	    System.out.println(rs.getInt(5));
	  	       // Location.add(0,rs.getString(1));
	  	        //NameLoc.add(rs.getString(1));
	  	  lista.add(rs.getString(2));
	  	  lista.add((String.valueOf(rs.getInt(3))));
	  	  lista.add((String.valueOf(rs.getInt(4))));
	  	  lista.add((String.valueOf(rs.getInt(5))));
	  	    }
	  	  
	  	  return lista;
	  	}
	public void insertReport(ArrayList<String> listRepo) throws SQLException{

		//String timeStamp = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
		//System.out.println();
		
	  	 try
	        {
	  		this.connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	  System.out.println("connection ok");
	  	//INSERT INTO mastertable (fsqid,date,name,geo, accesslevel, comment, doorways,elevator,escalator, parking,use) 
		//VALUES ('fff','12333','ciaociao','(22.434,11.334325)','access','commento', 'dor','elev','esca','park','use');*/
	  	 
		
	  	
	  	//ResultSet rs =st.executeQuery("INSERT INTO mastertable (fsqid,date,name,geo, accesslevel, comment, doorways,elevator,escalator, parking,use) VALUES ('fff','12333','fia','(22.434,11.334325)','access','commento', 'dor','elev','esca','park','use')");

	  	ResultSet rs =st.executeQuery("INSERT INTO mastertable (fsqid,date,name,geo, accesslevel, comment, doorways,elevator,escalator, parking,use) VALUES"+
	  	"('no','data','','"+listRepo.get(6).toString()+"','"+listRepo.get(0).toString()+"','"+listRepo.get(5).toString()+"', '"+listRepo.get(1).toString()+"','"+listRepo.get(2).toString()+"','"+listRepo.get(3).toString()+"','"+listRepo.get(4).toString()+"','use')");

	  	
	  	  								//INSERT INTO userdetail3 (name,pass) VALUES ('ds2222222cs','ssfsfsfsfs111111adsfsdasd');
	  	/*ResultSet rs = st.executeQuery("INSERT INTO mastertable (fsqid,date,name,geo, accesslevel, comment, doorways,elevator,escalator, parking,use) "+
	  		  "	VALUES ("fff","+12333+",'123123123123123','(22.434,11.334325)','access','commento', 'dor','elev','esca','park','use')";*/
	  	    
	        }
	        catch (Exception e)
	        {
	        	System.out.println(e.getCause().toString());
	        }
	        finally
	        {
	        	try{
	            this.connection.close();
	            }
	        	catch(Exception t)
	        	{System.out.println(t.getCause().toString());}
	        }
	}
		
		
	
	public void insertUser(String nome,String pass)throws SQLException{
	  	String var=""; 
		try
	        {
	  		this.connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	  System.out.println("connection ok");
	  	  								//INSERT INTO userdetail3 (name,pass) VALUES ('ds2222222cs','ssfsfsfsfs111111adsfsdasd');
	  	   int rs = st.executeUpdate("INSERT INTO userdetail3 (name,pass) VALUES('"+nome+"','"+pass+"')");
	  	 	
	  	 	System.out.println("is ok");
	        }
	        catch (Exception e)
	        {
	        	//return var="NO";
	        	System.out.println("error SQL insert User");
	        	
	        }
	        
	  	 //return var;
	  	 
	}
	public boolean verfyUser(String name, String pass) throws SQLException{
		
		 boolean empty = true;
		 
	 	 try
	        {
	  		connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	  System.out.println("connection ok");
	  	  								//INSERT INTO userdetail3 (name,pass) VALUES ('ds2222222cs','ssfsfsfsfs111111adsfsdasd');
	  	ResultSet rs = st.executeQuery("SELECT name from mastertable where name=='"+name+"'");
	  	  
	  	while( rs.next() ) {
	  	    // ResultSet processing here
	  	    empty = false;
	  	}

	  	if( empty ) {
	  	    // Empty result set
	  	}
	  	    
	  	  
	  	 	        }
	        catch (Exception e)
	        {
	        	System.out.println("error SQL insert User");
	        	
	        }
	        finally
	        {
	            connection.close();
	        }
	  	 return empty;
		
		
	}
	public boolean existUtent(String name) throws SQLException{
		
		 try
	        {
	  		connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	  System.out.println("connection ok");
	  	  								//INSERT INTO userdetail3 (name,pass) VALUES ('ds2222222cs','ssfsfsfsfs111111adsfsdasd');
	  	    ResultSet rs = st.executeQuery("select name from userdetail3 where name='"+name+"'limit 1");
	  	  
	  	if( rs.next() ) {
	  	    // ci sono risultati
	  	   return true;
	  	}
	        }
	        catch (Exception e)
	        {
	        	System.out.println("error SQL insert User");
	        	
	        }
	        finally
	        {
	            connection.close();
	        }
		return false;
	}
	}


