package it.project.main;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class TaskHttp extends AsyncTask<TextView,Object,ArrayList<String>> {
	
	private static final String API_URL = "http://192.168.1.101:8080/";
   
    private Activity sendMailActivity;
    private RestAdapter adapter;
    private ClientSvcApi github;
    private int tipo;
    private boolean flag;
    
    private String var;
    private ArrayList<String> varray;
    private TextView t;
    
    public TaskHttp(Activity activity, int tipo) {
       this.sendMailActivity = activity;
       this.tipo=tipo;
    }
   
    protected void onPreExecute() {
        Glob.statusDialog = new ProgressDialog(this.sendMailActivity);
        Glob.statusDialog.setMessage("Getting ready...");
        Glob.statusDialog.setIndeterminate(false);
        Glob.statusDialog.setCancelable(true);
        Glob.statusDialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(TextView... args) {
    			ArrayList<String> Lista = new ArrayList<String>();
    	     	this.t = args[0];
        try {
            Log.i("SendTaskGet", "About to instantiate...");
            publishProgress("Processing input....");
            
            this.adapter = new RestAdapter.Builder()
            .setServer(API_URL) // The base API endpoint.
            .build();

            this.github = this.adapter.create(ClientSvcApi.class);
            publishProgress("richiesta inviata attendere..");
            
            switch(this.tipo){
            case 0:
            	Lista.add(0,"sssss"/*github.getVideoList()*/);
            	flag=true;
            	break;
            case 1:this.varray=github.getAllLocation();
            	flag=false;
            	break;
            case 2:this.var=github.getLogin();
            	flag=true;
            	break;
            }
            publishProgress("inviata!!");
            Log.i("SendTaskGet", "Request Sent.");
           
            return Lista;
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendTaskGet", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
    	Glob.statusDialog.setMessage(values[0].toString());

    }

   /* @Override
    public void onPostExecute(ArrayList<String> result) {
    	Glob.statusDialog.dismiss();
        Log.i("SendTaskGet", ""+result+"");
        
    }*/
   
}
