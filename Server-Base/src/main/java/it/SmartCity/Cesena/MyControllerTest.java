package it.SmartCity.Cesena;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class MyControllerTest {

	//classe che ha il compito di testare i metodi con JUnit
	
	@Test
	public void TestsimpleGet(){
			
		MyController c=new MyController();
		String result = null;
		try {
			result = c.simpleGet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("YousimpleGet",result.toString());
		
	}

	
	
	@Test
	public void TestGetAllGeo() throws SQLException{
		MyController c=new MyController();
	ArrayList<String>Location = new ArrayList<>();
	ArrayList<String> results=null;
		Connection connection=null;
  	    
  	    connection = DriverManager.getConnection(
  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
  				"postgres");
  	    Statement st = connection.createStatement();
  	    ResultSet rs = st.executeQuery("select geo,name from mastertable");
  	    while (rs.next()) {
  	        System.out.println(rs.getString(1));
  	        Location.add(0,rs.getString(1));
  	        
  	    }
  	    rs.close();
  	    st.close();
		
		results=c.getAllGeo();
		
		 Object[] array = Location.toArray();
		 
		assertTrue(Location.equals(results));
		
	}
	@Test
	public void TestsimplePost() throws SQLException{
		MyController c=new MyController();
		String j;
		j=c.simplePost("123");
		assertEquals("okPost", j);
	}
}
