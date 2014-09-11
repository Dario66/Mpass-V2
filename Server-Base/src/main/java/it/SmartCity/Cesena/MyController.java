package it.SmartCity.Cesena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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
		 Connection connection=null;
	  	    
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
	  	    st.close();
			return "qualcosa";
		}
	// Normalmente GET � utilizzato per richiedere piccole quantit� di dati 
	// mentre POST per quantit� piu importanti(come filmati, audio etc.)
	
}
