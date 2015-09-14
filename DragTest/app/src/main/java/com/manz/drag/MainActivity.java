package com.manz.drag;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manz.util.AppUtils;

public class MainActivity extends Activity {

    private Context ctx;
    private View topContainer;
    private View bottomContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_main);
        topContainer = findViewById(R.id.scrollViewContainer);
        bottomContainer = findViewById(R.id.bottom);
        findViewById(R.id.activity1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.activity2).setOnTouchListener(new MyTouchListener());

        topContainer.setOnDragListener(new MyDragListener());
        bottomContainer.setOnDragListener(new MyDragListener());
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
                    dropAction(v, event);

                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.d("", "Place view - Drag location - " + event.getX() + " " + event.getY());
                    xDelta = (int)event.getX();
                    yDelta = (int)event.getY();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            return true;
        }
    }

    private void dropAction(View v, DragEvent event) {
        View draggedView = (View) event.getLocalState();
        ViewGroup fromContainer = (ViewGroup) draggedView.getParent().getParent();
        View toContainer = v;
        if(fromContainer == toContainer){
            draggedView .setVisibility(View.VISIBLE);
            fromContainer.invalidate();
            return;
        }
        //fromContainer.removeView(draggedView);
        //container.addView(draggedView);
        String activityTag = (String) draggedView.getTag();
        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewToAdd = mInflater.inflate(AppUtils.getLayout(activityTag), null, false);
        initNewView(viewToAdd, activityTag);

       // TextView viewToAdd = new TextView(MainActivity.this);
       // viewToAdd.setText("Abc");
        if(toContainer instanceof  LinearLayout) {
            ((LinearLayout)toContainer).addView(viewToAdd);
        }
        else if(toContainer instanceof RelativeLayout) {
            ((RelativeLayout)toContainer).addView(viewToAdd);
        }
        draggedView.setVisibility(View.VISIBLE);
        viewToAdd.setVisibility(View.VISIBLE);
        placeView(viewToAdd, event);
    }

    private void placeView(View viewToAdd, DragEvent event) {
//        final int X = (int)bottomContainer.getX();
//        final int Y = (int)bottomContainer.getY();
//        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) viewToAdd.getLayoutParams();
//        xDelta = lastDragX;
//        yDelta = lastDragY;
//        Log.d("", "Place view - " + X + " " + Y + " " + lParams.leftMargin + " " + lParams.topMargin);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewToAdd
                .getLayoutParams();
        layoutParams.leftMargin = xDelta;
        layoutParams.topMargin = yDelta;
        //layoutParams.rightMargin = -250;
        //layoutParams.bottomMargin = -250;
        viewToAdd.setLayoutParams(layoutParams);
        viewToAdd.setVisibility(View.VISIBLE);
        bottomContainer.invalidate();
    }

    private void initNewView(final View viewToAdd, final String activityTag) {
        if(isPlainActivity(activityTag)){
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            viewToAdd.setLayoutParams(params);
            View closeButton = viewToAdd.findViewById(R.id.closeButton);
            Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
            //RelativeLayout.LayoutParams l1 = (RelativeLayout.LayoutParams) closeButton.getLayoutParams();
            //l1.addRule(RelativeLayout.ALIGN_PARENT_TOP | RelativeLayout.ALIGN_PARENT_RIGHT);
            //closeButton.setLayoutParams(l1);
            ((TextView)closeButton).setTypeface(font);
            // Add click listener
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ctx, "Remove event from - " + activityTag, Toast.LENGTH_SHORT).show();
                    ViewGroup vg = (ViewGroup) viewToAdd.getParent();
                    vg.removeView(viewToAdd);
                }
            });

            //Add drag listener
            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
            //viewToAdd.setLayoutParams(layoutParams);
            OnTouchListener touchListener = new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    final int X = (int) event.getRawX();
                    final int Y = (int) event.getRawY();
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) viewToAdd.getLayoutParams();
                            xDelta = X - lParams.leftMargin;
                            yDelta = Y - lParams.topMargin;
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            break;
                        case MotionEvent.ACTION_POINTER_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewToAdd
                                    .getLayoutParams();
                            layoutParams.leftMargin = X - xDelta;
                            layoutParams.topMargin = Y - yDelta;
                            layoutParams.rightMargin = -250;
                            layoutParams.bottomMargin = -250;
                            viewToAdd.setLayoutParams(layoutParams);
                            break;
                    }
                    bottomContainer.invalidate();
                    return true;
                }
            };
            viewToAdd.setOnTouchListener(touchListener);
            viewToAdd.findViewById(R.id.mainButton).setOnTouchListener(touchListener);
        }
    }

    private int xDelta;
    private int yDelta;

    private boolean isPlainActivity(String activityTag) {
        if(activityTag.equalsIgnoreCase("Activity1")
                || activityTag.equalsIgnoreCase("Activity2")){
            return true;
        }
        return false;
    }
}