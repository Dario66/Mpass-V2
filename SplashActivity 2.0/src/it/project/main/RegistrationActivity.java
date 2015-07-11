package it.project.main;

import it.project.main.utils.GPSTracker;

import java.util.ArrayList;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import com.example.fstest.R;
import com.example.fstest.R.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity {

	
	EditText t1,t2,t3;
	Button b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		t1=(EditText)findViewById(R.id.editText1);
		t2=(EditText)findViewById(R.id.editText2);
		t3=(EditText)findViewById(R.id.editText3);
		b=(Button)findViewById(R.id.Button7);
		//}
	
		}
	
	public void regis(View v){
		
		String nome= t1.getText().toString();
		String pass= t3.getText().toString();
		String age= t2.getText().toString();
		
		// TODO Auto-generated method stub
		try{
    		new TaskHttp(RegistrationActivity.this,5, nome,pass){
			
		    public void onPostExecute(ArrayList<String> result) {
		    	//Toast.makeText(getApplicationContext(), result.get(0).toString(), Toast.LENGTH_LONG).show();
		    		
		    	//Log.d("", result.get(0));
		    	Glob.statusDialog.cancel();
		     }
		}.execute("");
    		}catch(Exception e){
    			//Toast.makeText(RegistrationActivity.this, e.getCause().toString(), Toast.LENGTH_LONG).show();	
    		}
		
	
		}
		
	}
