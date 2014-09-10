package it.SmartCity.Cesena;

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
	    public @ResponseBody String getVideoList() {
			return "qualcosa";
		}
	// Normalmente GET è utilizzato per richiedere piccole quantità di dati 
	// mentre POST per quantità piu importanti(come filmati, audio etc.)
	
}
