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
	// Normalmente GET è utilizzato per richiedere piccole quantità di dati 
	// mentre POST per quantità piu importanti(come filmati, audio etc.)
	
	
	
	
	
	
	
	
	//ricordarsi di scrivere lo stesso path anche nella richiesta nel client.
	//la variabile title deve essere la stessa passata al server
	@RequestMapping(value="/prova/passaggioparametri", method=RequestMethod.GET)
	    public @ResponseBody String get(
	    		@RequestParam ("title")String ciao
	    		) {
		 //controlla che sia stato inviata una Stringa contenente "ok"
		 if(ciao.compareTo("ok")==0){
		/* Collection<String> list =new ArrayList<String>();
		//add some stuff
		list.add("android");
		list.add("apple");*/
		
			return "va bene"; //list;
		 }
		 return "non va bene";//null;
		}
		/*nell'activity dell'app si può aggiungere il seguente codice dentro la funzione
		sarà inviata la richiesta con un parametro di tipo String per poi ritornare una Stringa.
		
		try{
		String s = videoSvc.get("ok");
		ciao.setText(s.toString());
		 
		}catch(Exception e){
			ciao.setText(e.toString());
			Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
		}
		 nella ClientSvcApi inserire la richiesta:
		 
		 
		 @GET("/prova/passaggioparametri")
		  public String get(@Query("title") String title);
		  
		  
		  
		  
		*/
		
		
		
		
		
}
