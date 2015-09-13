package com.manz.drag;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.top).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottom).setOnDragListener(new MyDragListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private final class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements OnDragListener {
        Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    dropAction((LinearLayout) v, event);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    private void dropAction(LinearLayout v, DragEvent event) {
        // Dropped, reassign View to ViewGroup
        View view = (View) event.getLocalState();
        ViewGroup fromContainer = (ViewGroup) view.getParent();
        LinearLayout toContainer = v;
        if(fromContainer == toContainer){
            view .setVisibility(View.VISIBLE);
            fromContainer.invalidate();
            return;
        }
        //fromContainer.removeView(view);
        //container.addView(view);
        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewToAdd = mInflater.inflate(R.layout.component, null, false);

       // TextView viewToAdd = new TextView(MainActivity.this);
       // viewToAdd.setText("Abc");
        toContainer.addView(viewToAdd);
        view.setVisibility(View.VISIBLE);
        viewToAdd.setVisibility(View.VISIBLE);
    }
}