package myappwidget.com.myappwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by rjhy on 14-10-13.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String GRID_VIEW_CLICK_ACTION = "myappwidget.com.myappwidget.MyAppWidgetProvider";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(MyActivity.TAG, "onReceive");
        if (intent.getAction().equals(GRID_VIEW_CLICK_ACTION)) {
            int position = intent.getIntExtra("position", -1);
            Toast.makeText(context, "position_"+position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i(MyActivity.TAG, "onUpdate");

        for (int i=0; i<appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, MyActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.appwidget_provider);
            remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);

            Intent serviceIntent = new Intent(context, MyRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            remoteViews.setRemoteAdapter(R.id.grid_view1, serviceIntent);

            Intent templateIntent = new Intent();
            templateIntent.setAction(GRID_VIEW_CLICK_ACTION);
            templateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent templatePendingIntent = PendingIntent.getBroadcast(context, 0, templateIntent, 0);
            remoteViews.setPendingIntentTemplate(R.id.grid_view1, templatePendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.i(MyActivity.TAG, "onAppWidgetOptionsChanged");
        Bundle bundle = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int i = bundle.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY);
        Log.i(MyActivity.TAG, (AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD == i)+"");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i(MyActivity.TAG, "onDeleted");
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i(MyActivity.TAG, "onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.i(MyActivity.TAG, "onDisabled");
    }
}
