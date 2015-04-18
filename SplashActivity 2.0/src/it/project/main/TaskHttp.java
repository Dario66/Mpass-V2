package it.project.main;

import java.util.ArrayList;

import org.scribe.builder.api.Api;

import retrofit.RestAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class TaskHttp extends AsyncTask<TextView,Object,String> {
	//private static final String API_URL = "http://jbosseapserver-idnarg55.rhcloud.com/";
	private static final String API_URL = "http://192.168.1.101:8080/";
   
    private ProgressDialog statusDialog2;
    private Activity sendMailActivity;
    RestAdapter adapter;
    ClientSvcApi github;
    String var;
    TextView t;
   
   // RemoteCallListener<String> listener;
    
    public TaskHttp(Activity activity) {
        sendMailActivity = activity;
       
    }
   
    protected void onPreExecute() {
        Glob.statusDialog = new ProgressDialog(sendMailActivity);
        Glob.statusDialog.setMessage("Getting ready...");
        Glob.statusDialog.setIndeterminate(false);
        Glob.statusDialog.setCancelable(true);
        Glob.statusDialog.show();


    }

    @Override
    protected String doInBackground(TextView... args) {
    	
    	this.t = args[0];
    	//this.var=args[0];
        try {
            Log.i("SendTaskGet", "About to instantiate...");
            publishProgress("Processing input....");
           
            
           /* ClientSvcApi videoSvc = new RestAdapter.Builder()
        	
        	.setServer("SERVER....").build()
        	.create(ClientSvcApi.class);*/
            RestAdapter restAdapter = new RestAdapter.Builder()
            .setServer(API_URL) // The base API endpoint.
            .build();

            github = restAdapter.create(ClientSvcApi.class);
            publishProgress("richiesta inviata attendere..");
            var="ok";
            publishProgress("inviata!!");
            Log.i("SendTaskGet", "Request Sent.");
           return var;
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

    @Override
    public void onPostExecute(String result) {
    	Glob.statusDialog.dismiss();
        Log.i("SendTaskGet", ""+result+"");
        //listener.onRemoteCallComplete(result);
       //t.setText(result);
        
    }
   
}
