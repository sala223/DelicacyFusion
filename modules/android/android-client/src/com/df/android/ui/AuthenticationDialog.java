package com.df.android.ui;

import java.util.LinkedList;
import java.util.Vector;

import com.df.android.R;

import signaturesdk.acquisition.AuthenticationListener;
import signaturesdk.acquisition.SignatureAndroidView;
import signaturesdk.beans.AcquisitionSignWord;
import signaturesdk.beans.SignWord;
import signaturesdk.features.Normalize;
import signaturesdk.features.Sample;
import signaturesdk.verification.Verification;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class AuthenticationDialog extends Dialog {

    Context context;
    LayoutParams p ;
  
    public AuthenticationDialog(Context context) {
        super(context);
        this.context = context;
    }
  
    static final int BACKGROUND_COLOR = Color.WHITE;
  
    static final int BRUSH_COLOR = Color.BLACK;
  
    // PaintView mView;
    SignatureAndroidView mView;
  
    /** The index of the current color to use. */
    int mColorIndex;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.auth_dialog);
          
        p = getWindow().getAttributes();  //获取对话框当前的参数值   
        p.height = 320;//(int) (d.getHeight() * 0.4);   //高度设置为屏幕的0.4 
        p.width = 480;//(int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.6           
        getWindow().setAttributes(p);     //设置生效
          
          
        mView = new SignatureAndroidView(context);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
        frameLayout.addView(mView);
        mView.registerAuthencationListener(new AuthenticationListener() {

			@Override
			public void onAuthentication() {
				verifySignature();
			}
        	
        });
        mView.requestFocus();
          
        Button btnCancel = (Button)findViewById(R.id.tablet_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
              
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

    }
    
    private void verifySignature() {
    	boolean match = true;
    	
		Toast.makeText(getContext(), "Verifying signature ...", Toast.LENGTH_SHORT).show();
		
		Vector<Double> 	ret;
		
		LinkedList<AcquisitionSignWord> sign1 = mView.getSignature().getSignature();
		LinkedList<AcquisitionSignWord> sign2 = mView.getSignature().getSignature();
		if(sign1.size() == 0) {
			Toast.makeText(getContext(), "No signature", Toast.LENGTH_SHORT).show();
			return;
		}
		
		LinkedList<SignWord> f1 = (new Normalize(sign1)).size();
		LinkedList<SignWord> f2 = (new Normalize(sign2)).size();
//		int npS1 = Extract.pointsNumber(f1);
//		int npS2 = Extract.pointsNumber(f2);
		
		Sample s = new Sample();
		s.sample(f1, f2);
		ret = Verification.coordsER2(s.getSignature1(), s.getSignature2());
		for(double v: ret) {
			if(v*100 < 75)
				match = false;
		}
		
		if(match)
			Toast.makeText(getContext(), "Signature match", Toast.LENGTH_SHORT).show();
    }
}