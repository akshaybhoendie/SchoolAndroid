package sr.unasat.financialapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import sr.unasat.financialapp.activities.main.MainActivity;
import sr.unasat.financialapp.activities.main.fragments.BalanceFragment;

public class TransacttionReminderBroadcastReceiver extends BroadcastReceiver {
    public TransacttionReminderBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, MainActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("daily transacction reminder")
                .setContentText("dont forget to add your daily transactions")
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());
        Bundle args = new Bundle();
        args.putString("daily transaction","daily transaction");
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
