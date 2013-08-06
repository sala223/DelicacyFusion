package com.df.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.main);

//        SlidingUpPanelLayout layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
//        layout.setShadowDrawable(getResources().getDrawable(R.drawable.above_shadow));
//        layout.setPanelSlideListener(new PanelSlideListener() {
//
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
//                if (slideOffset < 0.2) {
//                    if (getActionBar().isShowing()) {
//                        getActionBar().hide();
//                    }
//                } else {
//                    if (!getActionBar().isShowing()) {
//                        getActionBar().show();
//                    }
//                }
//            }
//
//            @Override
//            public void onPanelExpanded(View panel) {
//
//
//            }
//
//            @Override
//            public void onPanelCollapsed(View panel) {
//
//
//            }
//        });
//        TextView t = (TextView) findViewById(R.id.brought_by);
//        t.setMovementMethod(LinkMovementMethod.getInstance());
    }
}