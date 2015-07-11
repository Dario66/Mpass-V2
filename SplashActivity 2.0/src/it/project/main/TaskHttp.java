package it.project.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

public class TaskHttp extends AsyncTask<String,Object,ArrayList<String>> {
	
	private static final String API_URL = "http://192.168.1.101:8080/";
   private ArrayList<String>container;
    private Activity sendMailActivity;
    private RestAdapter adapter;
    private ClientSvcApi github;
    private int tipo;
    private boolean flag;
    private ArrayList<String> Lista = new ArrayList<String>();
    private String var,s,q,k;
    private ArrayList<String> varray;
    private TextView t;
    
    
    private String nome,pass;
    
    public TaskHttp(Activity activity, int tipo) {
       this.sendMailActivity = activity;
       this.tipo=tipo;
    }
    public TaskHttp(Activity activity, int tipo,ArrayList<String> f) {
    	this.sendMailActivity = activity;
        this.tipo=tipo;
    	this.container=f;
      
     }

    public TaskHttp(Activity activity, int tipo,String nome,String pass) {
    	this.sendMailActivity = activity;
        this.tipo=tipo;
    	this.nome=nome;
    	this.pass=pass;
      
     }
    
   
   
    protected void onPreExecute() {
    	
    	
        Glob.statusDialog = new ProgressDialog(this.sendMailActivity);
        Glob.statusDialog.setMessage("Getting ready...");
        Glob.statusDialog.setIndeterminate(false);
        Glob.statusDialog.setCancelable(true);
        Glob.statusDialog.show();
    }
    
    @Override
    protected ArrayList<String> doInBackground(String... args) {
    	
    			Lista=null;
    	     	//this.t = args[0];
        try {
            Log.i("SendTaskGet", "About to instantiate...");
            publishProgress("Processing input....");
            
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            
            this.adapter = new RestAdapter.Builder()
            .setEndpoint(API_URL) // The base API endpoint.
            .build();

            this.github = this.adapter.create(ClientSvcApi.class);
            publishProgress("richiesta inviata attendere..");
            
            String j="";
            switch(this.tipo){
            case 0:
            	this.Lista.add(0,"sssss"/*github.getVideoList()*/);
            	flag=true;
            	break;
            case 1:this.varray=github.getAllLocation();
            Lista=varray;	
            flag=false;
            	break;
            case 2:
            	this.var=github.getLogin("nome","cognome");//FASE DI LOGIN PER RICHIEDERE L'ACCESSO
            	Lista.add(0,this.var);
            	//flag=true;
            	break;
            case 3: /*var=this.github.getLogin(w, y);//USATA PER RICAVARE TUTTI I PUNTI DELL'INTERA MAPPA
            	Lista.set(0, var);*/
            	break;
            case 4://this.container;
            	String testoDownload = "ciaociao";
            	Log.d("Testo scaricato", testoDownload);
            	//ArrayList<String> arr=new ArrayList<String>();
            	
            	j="";
            	j=github.addPlace(container);
            //	github.addPlace(container, call);
            	//github.addPlace(container);
            	//if(j.equals("Not Sent"))
            	Lista.add(0,j);
            	break;
            	
            case 5://FUNZIONE PER LA REGISTRAZIONE REGISTRATION ACTIVITY
            	j = github.addRegistration(this.nome,this.pass);//nome, pass, eta
            	// Thread.sleep(100);
            	
            	Lista.set(0,j);
            	break;
            }
            publishProgress("inviata!!");
            Log.i("SendTaskGet", "Request Sent.");
           
            return Lista;
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendTaskGet", e.getMessage(), e);
        }
        return Lista;
    }

    @Override
    public void onProgressUpdate(Object... values) {
    	
    	if(this.tipo==5 ||this.tipo==4){
    		
    	}else{
    	Glob.statusDialog.setMessage(values[0].toString());
    	}
    }

    @Override
    public void onPostExecute(ArrayList<String> result) {
    	Glob.statusDialog.cancel();
        Log.i("SendTaskGet", ""+result+"");
        
    }
   
}
