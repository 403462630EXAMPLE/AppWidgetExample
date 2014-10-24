package myappwidget.com.myappwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rjhy on 14-10-14.
 */
public class MyRemoteViewService extends RemoteViewsService {
    private static final String TAG = "MyRemoteViewService";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyRemoteViewFactory(getApplicationContext(), intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(MyActivity.TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return super.onBind(intent);
    }

    private class MyRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private List<String> datas = new ArrayList<String>();

        private int appWidgetId;
        private Context context;

        public MyRemoteViewFactory(Context context, Intent intent) {
            Log.i(MyActivity.TAG, "MyRemoteViewFactory");
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            this.context = context;
        }

        @Override
        public void onCreate() {
            Log.i(TAG, "onCreate");
            for (int i=0; i<10; i++) {
                datas.add("position_"+i);
            }
        }

        @Override
        public void onDataSetChanged() {
            Log.i(TAG, "onDataSetChanged");
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "onDestroy");
        }

        @Override
        public int getCount() {
            Log.i(TAG, "getCount");
            return datas.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i(TAG, "getViewAt--position:"+position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item);
            remoteViews.setTextViewText(R.id.text1, datas.get(position));

            Intent intent = new Intent();
            intent.putExtra("position", position);
            remoteViews.setOnClickFillInIntent(R.id.item, intent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            Log.i(TAG, "getLoadingView");
            return null;
        }

        @Override
        public int getViewTypeCount() {
            Log.i(TAG, "getViewTypeCount");
            return 1;
        }

        @Override
        public long getItemId(int position) {
            Log.i(TAG, "getItemId");
            return position;
        }

        @Override
        public boolean hasStableIds() {
            Log.i(TAG, "hasStableIds");
            return true;
        }
    }
}
