package com.example.dialog;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 1;
    LinearLayout view;
    String strEmail;
    String strPass;
    EditText emailsend;
    EditText passsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button send = findViewById(R.id.send);
        Button remove = findViewById(R.id.remove);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSendDialog();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveDialog();
            }
        });
    }

    private void showSendDialog(){
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        view =(LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog_signin, null);
        builder.setView(view);
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "EDIT LISTING",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        emailsend = (EditText) builder.findViewById(R.id.usernamesend);
                        strEmail = emailsend.getText().toString();
                        passsend = (EditText)builder.findViewById(R.id.passwordesend);
                        strPass = passsend.getText().toString();
                        sendNotification();
                    }
                });
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "NEVERMIND",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
    private void showRemoveDialog(){
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        view =(LinearLayout) getLayoutInflater()
                .inflate(R.layout.dialog_remove, null);
        builder.setView(view);
        builder.setButton(AlertDialog.BUTTON_POSITIVE, "REMOVE BIKE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.setButton(AlertDialog.BUTTON_NEGATIVE, "NEVERMIND",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void sendNotification(){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_chanel_id_01";

        //check anroid version
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID , "My Notifications" , NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel descriptio!");//описание канала
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this , NOTIFICATION_CHANNEL_ID)
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setTicker("Hearty365")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(strEmail)
                .setContentText(strPass + "\n Этот аккаунт завтра будет взломан. Удачи =)")
                .setContentInfo("Info");

        notificationManager.notify(NOTIFICATION_ID, notificationbuilder.build());
 
    }
}
