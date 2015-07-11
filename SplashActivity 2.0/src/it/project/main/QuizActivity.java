package it.project.main;

import it.project.main.log.LogDbManager;
import it.project.main.utils.GPSTracker;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import retrofit.http.POST;

import com.example.fstest.R;

import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity 
{
	private String name;
	private String fsqid;
	private String geo;
	private ProgressDialog spinner;
	private User user;
	private LogDbManager log;
	private String action_for_log;
	private String date_for_log;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
		user=new User(this);
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.quiz_layout);
		rl.setGravity(Gravity.CENTER_HORIZONTAL);
		
		//Crea interfaccia per il questionario
		
		TextView venue_name=new TextView(this);
		
		venue_name.setId(41);
		venue_name.setPadding(0, 0, 0, 20);
		rl.addView(venue_name);
		
		//Crea i vari radiogroup tramite l'apposita procedura
		createRadioGroup("Questo luogo è accessibile?","Accessibile","Parzialmente accessibile","Non accessibile", 1, 11);
		createRadioGroup("Ci sono delle porte?","Si","No","", 2, 12);
		createRadioGroup("Ci sono degli ascensori?","Si","No","Un piano!", 3, 13);
		createRadioGroup("Ci sono delle scale mobili?","Si","No","", 4, 14);
		createRadioGroup("C'è un parcheggio per disabili?","Si","No","", 5, 15);
		
		/*createRadioGroup("Can rooms be accessed without stairs?","Yes","No","", 1, 11);
		createRadioGroup("Can a wheelchair easily navigate the space?","Yes","No","", 2, 12);
		createRadioGroup("If multiple floors, is an elevator or escalator available?","Yes","No","", 3, 13);
		createRadioGroup("Are tactile navigation maps available??","Yes","No","", 4, 14);
		createRadioGroup("Is accessible parking available??","Yes","No","", 5, 15);*/
		
		TextView head_comment=new TextView(this);
		//head_comment.setText("Inserisci un commento sul luogo!");
		head_comment.setText("Submit a comment!");
		head_comment.setId(31);
		head_comment.setTextSize(20.0f);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, 15);
		head_comment.setLayoutParams(lp);
		rl.addView(head_comment);
		
		EditText comment=new EditText(this);
		comment.setSingleLine(false);
		comment.setId(51);
		comment.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		comment.setMinLines(3);
		comment.setMaxLines(10);
		comment.setVerticalScrollBarEnabled(true);
		//comment.setPadding(0, 0, 0, 0);
		//comment.setLines(3);
		lp=new RelativeLayout.LayoutParams(300,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, 31);
		comment.setLayoutParams(lp);
		comment.setBackgroundResource(R.drawable.rounded_edittext);
		rl.addView(comment);
		
		Button btn_submit=new Button(this);
		lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.BELOW, 51);
		btn_submit.setLayoutParams(lp);
		btn_submit.setText("Segnala");
		btn_submit.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				
				int quiz1 = getRadioGroupResult(11);
				int quiz2 = getRadioGroupResult(12);
				int quiz3 = getRadioGroupResult(13);
				int quiz4 = getRadioGroupResult(14);
				int quiz5 = getRadioGroupResult(15);
				String squiz1="",squiz2="",squiz3="",squiz4="",squiz5="";
				switch (quiz1)
				{
				   case -1:squiz1=" ";
				   		   break;
				   case 0:squiz1="A";
		   		   		   break;
				   case 1:squiz1="P";
		   		   		   break;
				   case 2:squiz1="N";
		   		   	       break;
				   default:break;
				}
				switch (quiz2)
				{
				   case -1:squiz2=" ";
				   		   break;
				   case 0:squiz2="Yes";
		   		   		   break;
				   case 1:squiz2="No";
				   default:break;
				}
				switch (quiz3)
				{
				   case -1:squiz3=" ";
				   		   break;
				   case 0:squiz3="Yes";
		   		   		   break;
				   case 1:squiz3="No";
		   		   		   break;
				   case 2:squiz3="One floor";
		   		   	       break;
				   default:break;
				}
				switch (quiz4)
				{
				   case -1:squiz4=" ";
				   		   break;
				   case 0:squiz4="Yes";
		   		   		   break;
				   case 1:squiz4="No";
				   default:break;
				}
				switch (quiz5)
				{
				   case -1:squiz5=" ";
				   		   break;
				   case 0:squiz5="Yes";
		   		   		   break;
				   case 1:squiz5="No";
				   default:break;
				}
				
				final String comment_txt=getCommentText(51);
			
				GPSTracker u = new GPSTracker(QuizActivity.this);
				String y = ""+u.getLatitude()+","+u.getLongitude()+"";
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				//System.out.println(); //2014/08/06 15:59:48
				
				ArrayList<String> list_tot= new ArrayList<String>();
				list_tot.add(0,squiz1);//access level
				list_tot.add(1,squiz2);//doorways
				list_tot.add(2,squiz3);//elevator
				list_tot.add(3,squiz4);//escaletor
				list_tot.add(4,squiz5);//parking
				list_tot.add(5,comment_txt);//comment
				list_tot.add(6,y);
				list_tot.add(7, dateFormat.format(date).toString());
		
		//System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
				if(isOnline()||Verify.isLocationEnabled(QuizActivity.this)||Verify.isConnectedToServer()){
				try{
					
					
	        		new TaskHttp(QuizActivity.this,4,list_tot){//4 nuova richiesta da implementare
					
				    public void onPostExecute(ArrayList<String> result) {
				    	
				    	Glob.statusDialog.dismiss();
				    	//Toast.makeText(QuizActivity.this, result.get(0).toString();, Toast.LENGTH_LONG).show();	
				     }
				}.execute("");
	        		}catch(Exception e){
	        		//	Toast.makeText(QuizActivity.this, e.getCause().toString(), Toast.LENGTH_LONG).show();	
	        		}
				}else{
					Toast.makeText(QuizActivity.this, "abilitare il GPS o la connessione internet..", Toast.LENGTH_LONG).show();	
					
				}
			}
		});
		rl.addView(btn_submit);
	}
	public boolean isOnline() {
        ConnectivityManager cm =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}
	
	public void loadResponse(String response, Boolean success)
	{
		spinner.dismiss();
		//Se la segnalazione è stata inviata correttamente alla fusion table, scriviamo il nuovo record nel log
		if (success)
		{
			log=new LogDbManager(this);
			log.openToWrite();
			log.insertEntry(action_for_log, date_for_log);
			log.close();
			user.addReport();
			Toast.makeText(QuizActivity.this, "Segnalazione avvenuta con successo!", Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(QuizActivity.this, "Errore nell'inviare la segnalazione al server. Riprovare più tardi.", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
	private String getCommentText(int id)
	{
		EditText comment=(EditText)findViewById(id);
		return comment.getText().toString();
	}
	
	private int getRadioGroupResult(int rgid)
	{
		RadioGroup rg=(RadioGroup)findViewById(rgid);
		int id=rg.getCheckedRadioButtonId();
		View rb = rg.findViewById(id);
		return(rg.indexOfChild(rb));
	}
	
	private void createRadioGroup(String question, String first, String second, String third, int id, int rgid)
	{
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.quiz_layout);
		TextView tv_question=new TextView(this);
		tv_question.setText(question);
		RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		//lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		if (rgid!=11)
		{
			lp.addRule(RelativeLayout.BELOW , rgid-1);
		}
		else
		{
			lp.addRule(RelativeLayout.BELOW , 41);
		}
		tv_question.setLayoutParams(lp);
		tv_question.setId(id);
		tv_question.setTextSize(20.0f);
		rl.addView(tv_question);
		if (third!="")
		{
			RadioGroup rg=new RadioGroup(this);
			RadioButton rb[]=new RadioButton[3];
			rg.setOrientation(RadioGroup.VERTICAL);
			lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.addRule(RelativeLayout.BELOW, tv_question.getId());
			rg.setPadding(0, 10, 0, 20);
			rg.setLayoutParams(lp);
		    rb[0]  = new RadioButton(this);
		    rg.addView(rb[0]); 
		    rb[0].setText(first);
		    rb[1]  = new RadioButton(this);
		    rg.addView(rb[1]); 
		    rb[1].setText(second);
		    rb[2]  = new RadioButton(this);
		    rg.addView(rb[2]); 
		    rb[2].setText(third);
		    rg.setId(rgid);
			rl.addView(rg);
		}
		else
		{
			RadioGroup rg=new RadioGroup(this);
			RadioButton rb[]=new RadioButton[2];
			rg.setOrientation(RadioGroup.VERTICAL);
			lp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
			//lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.addRule(RelativeLayout.BELOW, tv_question.getId());
			rg.setPadding(0, 10, 0, 20);
			rg.setLayoutParams(lp);
		    rb[0]  = new RadioButton(this);
		    rg.addView(rb[0]); 
		    rb[0].setText(first);
		    rb[1]  = new RadioButton(this);
		    rg.addView(rb[1]); 
		    rb[1].setText(second);
		    rg.setId(rgid);
			rl.addView(rg);
		}
	}
}
