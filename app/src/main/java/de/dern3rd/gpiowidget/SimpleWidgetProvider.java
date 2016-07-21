package de.dern3rd.gpiowidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


public class SimpleWidgetProvider extends AppWidgetProvider {

    private static final String MyOnClick1a = "myOnClickTag1a";
    private static final String MyOnClick1b = "myOnClickTag1b";
    private static final String MyOnClick2a = "myOnClickTag2a";
    private static final String MyOnClick2b = "myOnClickTag2b";
    private static final String MyOnClick3a = "myOnClickTag3a";
    private static final String MyOnClick3b = "myOnClickTag3b";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.w("n3rd","onUpdate called");

        // Get all ids
        ComponentName thisWidget = new ComponentName(context, SimpleWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

        for (int widgetId : allWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.simple_widget);

            remoteViews.setOnClickPendingIntent(R.id.Button1a, getPendingSelfIntent(context, MyOnClick1a));
            remoteViews.setOnClickPendingIntent(R.id.Button1b, getPendingSelfIntent(context, MyOnClick1b));
            remoteViews.setOnClickPendingIntent(R.id.Button2a, getPendingSelfIntent(context, MyOnClick2a));
            remoteViews.setOnClickPendingIntent(R.id.Button2b, getPendingSelfIntent(context, MyOnClick2b));
            remoteViews.setOnClickPendingIntent(R.id.Button3a, getPendingSelfIntent(context, MyOnClick3a));
            remoteViews.setOnClickPendingIntent(R.id.Button3b, getPendingSelfIntent(context, MyOnClick3b));


            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.w("n3rd","onReceive called action: "+intent.getAction());
        if (MyOnClick1a.equals(intent.getAction())) {
            getHttpRequest(1,0, context);
        } else if (MyOnClick1b.equals(intent.getAction())) {
            getHttpRequest(1,1, context);
        } else if (MyOnClick2a.equals(intent.getAction())) {
            getHttpRequest(2,0, context);
        } else if (MyOnClick2b.equals(intent.getAction())) {
            getHttpRequest(2,1, context);
        } else if (MyOnClick3a.equals(intent.getAction())) {
            getHttpRequest(3,0, context);
        } else if (MyOnClick3b.equals(intent.getAction())) {
            getHttpRequest(3,1, context);
        }
    };


    private void getHttpRequest(int geraet, int status, final Context context) {
        AsyncHttpClient asyncClient = new AsyncHttpClient();

        asyncClient.get("http://192.168.2.123/"+geraet+"/"+status, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(context, "Button1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}