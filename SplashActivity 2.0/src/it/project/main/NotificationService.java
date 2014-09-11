package it.project.main;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationService extends BroadcastReceiver
{
    private Context context;

	@Override
	public void onReceive(Context c, Intent i) 
	{
		context=c;
		
		Intent quizIntent=new Intent(context, QuizActivity.class);

		PendingIntent quizPending=PendingIntent.getActivity(context, 0, quizIntent, 0);
		
		
		
	}
    

    /*@Override
    protected void onHandleIntent(Intent intent) 
    {
    	FsqVenue venue=(FsqVenue)intent.getSerializableExtra("venue");
    	Intent quizIntent=new Intent(this, QuizActivity.class);
		quizIntent.putExtra("venue", venue);
		PendingIntent quizPending=PendingIntent.getActivity(this, 0, quizIntent, 0);
		
		Notification notifica=new Notification.Builder(this)
		.setSmallIcon(R.drawable.logo)
		.setContentTitle("mPASS")
		.setContentText("Fai il quiz!")
		.setContentIntent(quizPending).build();
		notifica.flags|=Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(NOTIF_ID, notifica);
    }*/

}
