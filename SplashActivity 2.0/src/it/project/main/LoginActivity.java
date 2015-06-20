package it.project.main;

import retrofit.RestAdapter;

import com.example.fstest.R;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) @SuppressLint("NewApi") 

public class LoginActivity extends Activity {
	
	
	private static final String SERVER = "http://192.168.1.102:8080";
	
	EditText uno;
	EditText due;
	
private ClientSvcApi videoSvc = new RestAdapter.Builder()
	
	.setServer(SERVER).build()
	.create(ClientSvcApi.class);

	@Override
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) 
    @SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		uno=(EditText)findViewById(R.id.editText1);
		due=(EditText)findViewById(R.id.editText3);
		
		StrictMode.ThreadPolicy policy = 
        		new StrictMode.ThreadPolicy.Builder().permitAll().build();
        	StrictMode.setThreadPolicy(policy);
	}
	
	public void Login(View v){
		
			try{
				//String s = videoSvc.getVideoList();
				//ciao.setText(s.toString());
					String username=uno.getText().toString();
					String passw="bla";
					String a=videoSvc.getLogin(/*username*/);//simpleGet();//getQuery("ok");
					Toast.makeText(getApplicationContext(),""+a+"", Toast.LENGTH_SHORT).show();
					
				}catch(Exception e){
					//ciao.setText(e.toString());
					//Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(),"Errore di connnessione", Toast.LENGTH_SHORT).show();
					
				}
		
		
		
	}
	public void registration(View v){
		Intent i=new Intent(LoginActivity.this,RegistrationActivity.class);
		startActivity(i);
	}
}
