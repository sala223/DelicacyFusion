package com.df.android;

import java.util.LinkedList;
import java.util.Vector;

import signaturesdk.acquisition.AuthenticationListener;
import signaturesdk.acquisition.SignatureAndroidView;
import signaturesdk.beans.AcquisitionSignWord;
import signaturesdk.beans.SignWord;
import signaturesdk.features.Normalize;
import signaturesdk.features.Sample;
import signaturesdk.verification.Verification;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.df.android.entity.Store;
import com.df.android.utils.CacheMgr;
import com.df.android.utils.ResourceUtils;
import com.df.android.utils.WebTask;
import com.df.client.http.DFClient;
import com.df.client.http.LoginContext;
import com.df.client.rs.resource.StoreResource;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {

    LayoutParams p ;
  
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
        setContentView(R.layout.login);

		GlobalSettings.newInstance(this);
		CacheMgr.newInstance(this);
		
		String tenantCode = GlobalSettings.instance().getCurrentTenantCode();
		String storeCode = GlobalSettings.instance().getCurrentStoreCode();
		Store store = CacheMgr.instance().loadStore(tenantCode, storeCode);
		if(store != null) {
			onStoreLoaded(store);
			
			return;
		}
        
        initView();

    }

	private void initView() {
		p = getWindow().getAttributes();  //��ȡ�Ի���ǰ�Ĳ���ֵ   
        p.height = 320;//(int) (d.getHeight() * 0.4);   //�߶�����Ϊ��Ļ��0.4 
        p.width = 480;//(int) (d.getWidth() * 0.6);    //�������Ϊ��Ļ��0.6           
        getWindow().setAttributes(p);     //������Ч
          
          
        mView = new SignatureAndroidView(this);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);
        frameLayout.addView(mView);
        mView.registerAuthencationListener(new AuthenticationListener() {

			@Override
			public void onAuthentication() {
				login();
			}
        	
        });
        mView.requestFocus();
          
        findViewById(R.id.btnDemo).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                demo();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                finish();
            }
        });
	}
    
    private void demo() {
		GlobalSettings.instance().setCurrentTenantCode("demo");
		GlobalSettings.instance().setCurrentStoreCode("demo1");
    	
		login("demo", "demo");
    }
    
    private void login() {
		GlobalSettings.instance().setCurrentTenantCode("test");
		GlobalSettings.instance().setCurrentStoreCode("S1");

		login("testuser", "testpassword");
    }
    
    private boolean verifySignature() {
    	boolean match = true;
    	
		Toast.makeText(this, "Verifying signature ...", Toast.LENGTH_SHORT).show();
		
		Vector<Double> 	ret;
		
		LinkedList<AcquisitionSignWord> sign1 = mView.getSignature().getSignature();
		LinkedList<AcquisitionSignWord> sign2 = mView.getSignature().getSignature();
		if(sign1.size() == 0) {
			Toast.makeText(this, "No signature", Toast.LENGTH_SHORT).show();
			return false;
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
		
		return match;
    }

	private void login(final String userName, final String pwd) {
		if(!verifySignature()) {
			Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Store store = CacheMgr.instance().loadStore(GlobalSettings.instance().getCurrentTenantCode(), GlobalSettings.instance().getCurrentStoreCode());
		if(store != null) {
			onStoreLoaded(store);
			return;
		}

		new WebTask<String[]>() {
			@Override
			protected String[] doInBackground(String... params) {
				String[] roles = {};
				try {
					GlobalSettings.instance().setUserCode(userName);
					LoginContext lc = new LoginContext(GlobalSettings.instance().getCurrentTenantCode(), userName, pwd);
					GlobalSettings.instance().setClient(new DFClient(lc,
							GlobalSettings.instance().getServerUrl()));
					GlobalSettings.instance().getClient().login();
				} catch (Exception e) {
					Log.e(getClass().getName(), "Fail to login due to " + e);
				}

				return roles;
			}

			@Override
			protected void onPostExecute(String[] roles) {
//				if (roles == null || roles.length <= 0)
//					onLoginFail();
//				else
					onLoginSucceed(roles);
			}
		}.execute();
	}

	private void onLoginFail() {
		Toast.makeText(this, ResourceUtils.getStringByKey(this, "login_fail"),
				Toast.LENGTH_LONG).show();
		finish();
	}

	private void onLoginSucceed(String[] roles) {
		new WebTask<com.df.client.rs.model.Store[]>() {
			@Override
			protected com.df.client.rs.model.Store[] doInBackground(String... params) {
				com.df.client.rs.model.Store[] stores = {new com.df.client.rs.model.Store()};
				try {
					StoreResource res = GlobalSettings.instance().getClient().getResource(
							StoreResource.class);
//					stores = res.getStores();
					stores[0].setCode("S1");
					stores[0].setName("Store_1");
				} catch (Exception e) {
					Log.e(getClass().getName(),
							"Fail to retrieve store due to " + e);
				}

				return stores;
			}

			@Override
			protected void onPostExecute(com.df.client.rs.model.Store[] stores) {
				Log.d(getClass().getName(), "stores: " + stores);
				if (stores == null || stores.length <= 0)
					onStoreNotFound();
				else {
					Store cStore = new Store(
							stores[0].getCode(), stores[0].getName());
					GlobalSettings.instance().setCurrentStoreCode(cStore.getCode());
					GlobalSettings.instance().getClient().setStore(cStore.getCode());
					
					CacheMgr.instance().saveStore(GlobalSettings.instance().getCurrentTenantCode(), cStore);
					
					onStoreLoaded(cStore);
				}
			}
		}.execute();
	}

	private void onStoreNotFound() {
		Toast.makeText(this,
				ResourceUtils.getStringByKey(this, "no_store_found"),
				Toast.LENGTH_LONG).show();
	}

	private void onStoreLoaded(Store store) {
		GlobalSettings.instance().setCurrentStore(store);
		startActivity(new Intent(this, Main.class));
	}
    
}
