package myappwidget.com.myappwidget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.StackView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;


public class MyActivity extends Activity implements GestureDetector.OnGestureListener {

    public static final String TAG = "MyActivity";
    private StackView stackView;
    private AdapterViewFlipper adapterViewFlipper;
    private ViewFlipper flipper;

    private float y = 0;

    int counter = 0;
    int[] images = {android.R.drawable.ic_dialog_alert, android.R.drawable.ic_dialog_email, android.R.drawable.ic_dialog_info};
    // 监听AdapterViewFlipper
    private View.OnClickListener imageViewListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "image", 200).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initStackView();
//        initAdapterViewFlipper();
        initFlipper();
    }


    private void initFlipper() {
        flipper = (ViewFlipper) findViewById(R.id.flipper);
//        flipper.startFlipping();
        flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        float end = event.getX();
                        if (end-y >100) {
                            flipper.showNext();
                        }
                        if (y-end > 100) {
                            flipper.showPrevious();
                        }
                        Log.i(TAG, "ACTION_UP:"+(end));
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i(TAG, "ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        y = event.getX();
                        Log.i(TAG, "ACTION_DOWN:"+y);
                        break;
                }
                return true;
            }
        });
    }

    private void initAdapterViewFlipper() {
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapter_flipper);
        adapterViewFlipper.setAdapter(new FlipperAdapter(getApplicationContext(), images,
                imageViewListener));
        // 开始滑动
        adapterViewFlipper.startFlipping();
        adapterViewFlipper.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void initStackView() {
        stackView = (StackView) findViewById(R.id.stack_view);
        ArrayList<String> lists = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            lists.add("stack_" + i);
        }

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item, R.id.text1, lists);
        stackView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e2.getX() - e1.getX() > 100) {
            flipper.showPrevious();
        } else if (e2.getX() - e1.getX() < 100) {
            flipper.showNext();
        }
        return false;
    }
}
