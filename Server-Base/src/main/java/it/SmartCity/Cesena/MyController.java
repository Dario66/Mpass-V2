package it.SmartCity.Cesena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	private String a,b,c,d,e;
	private String comment;
	private PostgresApi w;
	
	 private Connection connection=null;
	 @RequestMapping(value="/Login", method=RequestMethod.GET)
	    public @ResponseBody String getVideoList(@RequestParam("par1") String par, @RequestParam("par2") String par2) throws SQLException {
		PostgresApi o = new PostgresApi();
		//String pp=o.existLogin(par,par2);
	//	if(pp.equals("presente")){
			//return "nome esistente";
		//}else 
	 return "nome non presente";
	 }
	 
	 
	 @RequestMapping(value="/locazione/getposti", method=RequestMethod.GET)
	    public @ResponseBody ArrayList<String> getAllGeo(
	    		/*@RequestParam("stringa") String stringa*/
	    		) throws SQLException {
		 
		 
		 ArrayList<String> Location= new ArrayList<>();
		 ArrayList<String> NameLoc = new ArrayList<>();
		// if(stringa=="ok"){
		 
			
		 		this.connection=null;
		  	    this.connection = DriverManager.getConnection(
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
	 //utilizzato postgresql-9.3-1102.jdbc4
	 @RequestMapping(value="/passaggioparametri", method=RequestMethod.GET)
	    public @ResponseBody /*Collection<String>*/String get(
	    		@RequestParam ("title")String ciao
	    		) throws SQLException{
		 
		 if(ciao.compareTo("ok")==0){
		 
		 
		 Collection<String> list =new ArrayList<String>();
		//add some stuff
		list.add("android");
		list.add("apple");
		 
		 
			return "vabene"; //list;
		 }
		 return "non va bene";//null;
		}
	 
	 
		@RequestMapping(value="/pag1", method=RequestMethod.GET)
		public @ResponseBody String simpleGet()throws SQLException {
			
			
			return "YousimpleGet";
		}
	 
		@RequestMapping(value="/pag6", method=RequestMethod.POST)
		public @ResponseBody String simplePost(@RequestParam("dati") String ciao)throws SQLException {
			String h="123";
			if(ciao.equals(h)){
				return "okPost";
				
			}
			
			return "NoPost";
		}
		@RequestMapping(value="/ss", method=RequestMethod.POST)
		public @ResponseBody String LoginReq(@RequestParam("para") String ciao,@RequestParam("para2")String ciao2)throws SQLException {
			
			
			this.connection=null;
			 this.connection = DriverManager.getConnection(
		  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
		  				"postgres");
		  	    Statement st = connection.createStatement();
		  	    ResultSet rs = st.executeQuery("INSERT INTO userdetail (name,pass) VALUES ('dario','dar')");
		  	    while (rs.next()) {
		  	        
		  	        System.out.println(rs.getString(0));
		//  	        Location.add(0,rs.getString(1));
		  	        //NameLoc.add(rs.getString(1));
		  	    }
			
			return "ok";
		}
		//QUESTO CONTROLLER è UTILIZZATO PER SALVARE NEL DATABASE UN NUOVO REPORT CREATO DALL'UTENTE
	
		
		
		@RequestMapping(value="/addp", method=RequestMethod.POST)
		public @ResponseBody String newPlace(@RequestParam ("arr")ArrayList<String> text) throws SQLException {
			String foo;
			int year,month,day;
			//Date now = new Date();
			/*Calendar calendar = Calendar.getInstance();
			day = calendar.get(Calendar.DAY_OF_WEEK); 
	        month=calendar.get(Calendar.MONTH);
	        year=calendar.get(Calendar.YEAR);
			String date=""+year+"-"+month+"-"+day+"";
			String foo=null;
			PRIMO CONTROLLO
			if(text==null||text.get(1)==null||text.get(0)==""){
				return "negato";
			}*/
			//SCRITTURA NEL DB
			//else{
				this.a=text.get(0);//access level
				this.b=text.get(1);//doorways
				this.c=text.get(2);//elevator
				this.d=text.get(3);//escaletor
				this.e=text.get(4);//parking
				this.comment=text.get(5);//comment
				
				
				PostgresApi db= new PostgresApi();
				db.insertReport(text);
				///CAVARE SOTTO IL CODICE DEL JDBC
				/*this.connection=null;
				 this.connection = DriverManager.getConnection(
			  				"jdbc:postgresql://localhost:5432/postgres", "postgres",
			  				"postgres");
			  	    Statement st = connection.createStatement();
			  	    
			  	    
			  	    
			  	    ResultSet rs = st.executeQuery("INSERT INTO userdetail (name,pass) VALUES ('dario','dar')");
			  	    */
			  	  //INSERT INTO mastertable (fsqid,date,name,geo, accesslevel, comment, doorways,elevator,escalator, parking,use) 
			  	   //VALUES ('fff','12333','ciaociao','(22.434,11.334325)','access','commento', 'dor','elev','esca','park','use');
			  	   /* while (rs.next()) {
			  	        
			  	        System.out.println(rs.getString(0));
			//  	        Location.add(0,rs.getString(1));
			  	        //NameLoc.add(rs.getString(1));
			  	    }*/
				
				//foo="OK";
			//}
			foo="ok";
			
			//return new ResponseEntity<Void>(HttpStatus.OK);
			return foo;
			
		}
		@RequestMapping(value="/Registration", method=RequestMethod.POST)
		public @ResponseBody String newRegistration(@RequestParam ("param")String l,@RequestParam ("param2")String l2) throws SQLException {
			this.w=new PostgresApi();
			//if(this.w.verfyUser(l, l2)){
			
			String status="";
			w.insertUser(l,l2);
			/* if(w.existUtent(l)){
				 status="no";
		    	  System.out.println("presente");
		      }else{
		    	  w.insertUser(l,l2);
		    	  status="si";
		    	  System.out.println("non presente");
		      }*/
			
			return status;
			
				
				
		//	}
			
		//return "no";
		}
}
