package myappwidget.com.myappwidget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FlipperAdapter extends BaseAdapter {
    private int[] images;
    private Context context;
    private OnClickListener listener;
    Animation animation;

    public FlipperAdapter(Context context, int[] images,
                          OnClickListener listener) {
        this.images = images;
        this.context = context;
        this.listener = listener;

    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.FIT_XY);
        imageView
                .setLayoutParams(new AdapterViewFlipper.LayoutParams(480, 800));
        imageView.setImageResource(images[position]);
        // 设置显示的动画效果
//        animation = AnimationUtils.loadAnimation(context, R.anim.alpha1);
        imageView.setAnimation(animation);

        imageView.setOnClickListener(listener);
        return imageView;
    }
}