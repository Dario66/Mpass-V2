package it.project.main;

import it.project.main.utils.GPSTracker;

import com.example.fstest.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NewVenueActivity extends Activity 
{
	private String maxid;
	private GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_venue);
		gps=new GPSTracker(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_venue, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        	case R.id.item_verify:EditText input=(EditText)findViewById(R.id.et_newvenue);
        						  if (input.getText().toString().isEmpty())
        							  Toast.makeText(NewVenueActivity.this, "Nome del luogo mancante!", Toast.LENGTH_SHORT).show();
        						  else
        						  {
	        						  //maxid=maxid.substring(2);
						    		  maxid=maxid.substring(2, maxid.length()-2);
						    		  Log.d("Debug", maxid);
						    		  int newid=Integer.parseInt(maxid)+1;
						    		  final String snewid="NF"+Integer.toString(newid);
	        						
									  Intent quiz_intent=new Intent(NewVenueActivity.this, QuizActivity.class);
									  
									  NewVenueActivity.this.startActivity(quiz_intent);
									  finish();
        						  }
        					      break;
        	default:break;
        }
        return true;
    }
	
	
}
