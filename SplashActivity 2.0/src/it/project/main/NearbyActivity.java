package it.project.main;

import it.project.main.utils.GPSTracker;

import java.util.ArrayList;

import com.example.fstest.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NearbyActivity extends Activity 
{
	
	private ProgressDialog spinner;
	private GPSTracker gps;
	
	
	
	private String query_maxid="SELECT COUNT() FROM "+Costants.tableId+" WHERE fsqid LIKE 'NF%25'";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		
		
		spinner=new ProgressDialog(this);
        spinner.setMessage("Caricamento...");
        spinner.setCancelable(false);
        spinner.setMax(100); 
        spinner.setProgress(0); 
        spinner.show();
		
		
        gps=new GPSTracker(this);
        if (gps.canGetLocation()) 
        	loadNearbyPlaces(gps.getLatitude(), gps.getLongitude(),"");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby, menu);
		//MenuItem searchItem = menu.findItem(R.id.action_search);
		
		/*MenuItem item_addvenue=(MenuItem)findViewById(R.id.action_addvenue);
		item_addvenue.setOnMenuItemClickListener(new OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem item) 
			{
				ftclient.setQuery("SELECT fsqid FROM "+Costants.tableId+" WHERE fsqid LIKE \NF%25\ ORDER BY fsqid DESC LIMIT 1");
				ftclient.queryOnNewThread("lastid");
				return false;
			}
		});*/
		final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new OnQueryTextListener() 
		{
			@Override
			public boolean onQueryTextSubmit(String query) 
			{
				searchView.setQuery("", false);
				searchView.setIconified(true); 
				//Toast.makeText(NearbyActivity.this, "No nearby places available", Toast.LENGTH_SHORT).show();
				if (gps.canGetLocation()) 
		        	loadNearbyPlaces(gps.getLatitude(), gps.getLongitude(), query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) 
			{
				// TODO Auto-generated method stub
				return false;
			}
		});
		/*searchView.setOnCloseListener(new OnCloseListener() 
		{
			@Override
			public boolean onClose() 
			{
				Toast.makeText(NearbyActivity.this, "No nearby places available", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		*/
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
        	case R.id.action_addvenue:
        						      break;
        	default:break;				    
        }
		return true;
    }
	
	private void loadNearbyPlaces(final double latitude, final double longitude, final String query) 
    {
    	spinner.show();
    	new Thread() 
    	{
    		@Override
    		public void run() 
    		{
    			int what = 0;
    			try 
    			{
    				if (query.isEmpty())
    				{
    					Log.d("Debug","Ricerca normale");
    					
    				}
    				else
    				{
    					Log.d("Debug","Ricerca con query");
    					
    				}
    			} 
    			catch (Exception e) 
    			{
    				what = 1;
    				e.printStackTrace();
    			}
    			mHandler.sendMessage(mHandler.obtainMessage(what));
    		}
    	}.start();
    }
     
     
    @SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() 
    {
	    @Override
	    public void handleMessage(Message msg) 
	    {
	    	spinner.dismiss();
	        if (msg.what == 0) 
	        {
	      			/*NearbyDialog ndialog=new NearbyDialog(NearbyActivity.this, nearbyList, ftclient);
	      			ndialog.show();*/
	      			//Toast.makeText(NearbyActivity.this, "ok", Toast.LENGTH_SHORT).show();
	      			loadList();
	      	
	        	/*mAdapter.setData(mNearbyList);
	      		mListView.setAdapter(mAdapter);*/
	      	}
	      	else 
	      	{
	      		Toast.makeText(NearbyActivity.this, "Failed to load nearby places", Toast.LENGTH_SHORT).show();
	      	}
	    }
    };
     
    private void loadList()
    {
 		
 		
 		ListView lv_nearby=(ListView)findViewById(R.id.lv_nearby2);
	   
	
		
		lv_nearby.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long d) 
			{
				
					Intent quiz_intent=new Intent(NearbyActivity.this, QuizActivity.class);
					
					NearbyActivity.this.startActivity(quiz_intent);
					NearbyActivity.this.finish();
					//dismiss();
				
			}
		});
    }
    
    public void createNewVenue(String maxid)
    {
    	Intent i=new Intent(NearbyActivity.this, NewVenueActivity.class);
    	Bundle bundle=new Bundle();
    	bundle.putString("maxid", maxid);
    	i.putExtras(bundle);
    	startActivity(i);
    	finish();
    }
}
