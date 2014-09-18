package it.SmartCity.Cesena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;


/*@COntroller permette di abilitare l'ascolto nel dispatcher Servlet ,
 */
@Controller
public class MyController {

	//Richiesta GET
	//@Request Mapping mappa la richiesta attraverso il metodo specificato
	 @RequestMapping(value="/pagina1", method=RequestMethod.GET)
	 //Response Body Converte Automaticamente il valore di ritorno
	 //della funzione da Oggetto a Json(Setta il Content Type a seconda del Client usato:
	 //text/Html per pagine Web e text/plain per applicazioni mobile)
	    public @ResponseBody String getVideoList() throws SQLException {
		 
		 
		 //PROVA DI CONNESSIONE CON JDBC CON QUERY
		/* Connection connection=null;
	  	    
	  	    connection = DriverManager.getConnection(
	  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
	  				"postgres");
	  	    Statement st = connection.createStatement();
	  	    ResultSet rs = st.executeQuery("SELECT * FROM mastertable");
	  	    while (rs.next()) {
	  	        //System.out.print("Column 4 _ Column 1 ");
	  	        System.out.println(rs.getString(4)+"_"+rs.getString(1));
	  	    }
	  	    rs.close();
	  	    st.close();*/
			return "qualcosa";
		}
	// Normalmente GET è utilizzato per richiedere piccole quantità di dati 
	// mentre POST per quantità piu importanti(come filmati, audio etc.)
	 
	 
	 @RequestMapping(value="/locazione/getposti", method=RequestMethod.GET)
	    public @ResponseBody Collection<String> getAllGeo(
	    		/*@RequestParam("stringa") String stringa*/
	    		) throws SQLException {
		 
		 
		 ArrayList<String> Location= new ArrayList<>();
		 ArrayList<String> NameLoc = new ArrayList<>();
		// if(stringa=="ok"){
		 
			 Connection connection=null;
		  	    
		  	    connection = DriverManager.getConnection(
		  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
		  				"postgres");
		  	    Statement st = connection.createStatement();
		  	    ResultSet rs = st.executeQuery("select geo,name from mastertable");
		  	    while (rs.next()) {
		  	        //System.out.print("Column 4 _ Column 1 ");
		  	        System.out.println(rs.getString(1));
		  	        Location.add(0,rs.getString(1));
		  	        //NameLoc.add(rs.getString(1));
		  	        
		  	        
		  	        
		  	    }
		  	    
		  	    
		  	    rs.close();
		  	    st.close();
			 
			 
		 
		 
		// Collection<String> list =new ArrayList<String>();
		//add some stuff
		//list.add("android");
		//list.add("apple");
		 
		 
			return Location;
		// }
		// return null;
		}
	 
	 @RequestMapping(value="/prova/passaggioparametri", method=RequestMethod.GET)
	    public @ResponseBody /*Collection<String>*/String get(
	    		@RequestParam ("title")String ciao
	    		) {
		 
		 if(ciao.compareTo("ok")==0){
		 
		 
		 Collection<String> list =new ArrayList<String>();
		//add some stuff
		list.add("android");
		list.add("apple");
		 
		 
			return "vabene"; //list;
		 }
		 return "non va bene";//null;
		}
	 
	 
	 
}
