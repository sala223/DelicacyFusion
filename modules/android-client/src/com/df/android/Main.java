package com.df.android;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.df.android.MenuItem.MenuItemType;

public class Main extends Activity implements OrderChangeListener { 
	private Shop shop;
    @Override  
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);  

        shop = initShop("demo");  
        
        GridView gvMenu = (GridView) findViewById(R.id.gvMenu); 
		final MenuAdapter menuAdapter = new MenuAdapter(this, shop);
		gvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) { 
				MenuItem item = (MenuItem)menuAdapter.getItem(index);
				showMenuItemDetail(view, item);
			} 
			
		});
        gvMenu.setAdapter(menuAdapter);
        
        ListView lstOrder = (ListView)findViewById(R.id.lstOrder);
        OrderAdapter orderAdapter = new OrderAdapter(this);
        Order order = new Order();
        order.registerChangeListener(this);
        orderAdapter.setOrder(order);
        lstOrder.setAdapter(orderAdapter);
        
        findViewById(R.id.openClose).setOnClickListener(new OnClickListener()
        {  
			@Override
			public void onClick(View arg0) {
            	LinearLayout leftPane = (LinearLayout)findViewById(R.id.leftPane);
            	
				if(leftPane.getVisibility() == View.VISIBLE) {
					leftPane.setVisibility(View.GONE);
				}else {
					leftPane.setVisibility(View.VISIBLE);
				}
			}
        });
        
    }
    
    private Shop initShop(String shopId) {
    	Shop shop = new Shop(shopId, shopId);
    	
        String menuMetaFile = "cache/" + shopId + "/menu.xml";
        Menu menu = buildMenuFromMetaFile(menuMetaFile);
	        
        shop.setMenu(menu);
		
		return shop;
    }
    
    private Menu buildMenuFromMetaFile(String metaFile) {
    	Menu menu = new Menu();
    	
    	Log.i(getClass().getName(), "Building menu from " + metaFile);
    	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getAssets().open(metaFile));
	        Element root = dom.getDocumentElement();
            NodeList xmlItems = root.getElementsByTagName("item");

        	Log.d(getClass().getName(), "Found " + xmlItems.getLength() + " items");
            
            for (int i=0; i<xmlItems.getLength(); i++){
            	Element xmlItem = (Element)xmlItems.item(i);
            	String name = xmlItem.getAttribute("name");
            	MenuItemType type = MenuItem.dishTypes[Integer.parseInt(xmlItem.getAttribute("type"))];
            	String image = xmlItem.getAttribute("image");
            	float price = Float.parseFloat(xmlItem.getAttribute("price"));
            	Log.i(getClass().getName(), "Found item: name=" + name + ", type=" + type + ", image=" + image);
            	
            	MenuItem item = new MenuItem(name, type, price, image);
            	menu.addItem(item);
            }
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load menu from meta file due to " + e);
		}
		
		return menu;
    }

    @Override
	public void onMenuItemAdded(MenuItem item) {
    	((OrderAdapter)((ListView)findViewById(R.id.lstOrder)).getAdapter()).notifyDataSetChanged();
    	((TextView)findViewById(R.id.orderCount)).setText("" + Order.currentOrder().getCount());
    } 
    

	@Override
	public void onMenuItemRemoved(MenuItem item) {
    	((OrderAdapter)((ListView)findViewById(R.id.lstOrder)).getAdapter()).notifyDataSetChanged();
    	((TextView)findViewById(R.id.orderCount)).setText("" + Order.currentOrder().getCount());
	}
	
	private Animator mCurrentAnimator;
	private int mShortAnimationDuration = 200;
	private void showMenuItemDetail(final View srcView, final MenuItem item) {
	    // If there's an animation in progress, cancel it
	    // immediately and proceed with this one.
	    if (mCurrentAnimator != null) {
	        mCurrentAnimator.cancel();
	    }
	    
	    ((TextView)findViewById(R.id.tvMenuItemDetailName)).setText(item.getName());
	    ((TextView)findViewById(R.id.tvMenuItemDetailPrice)).setText("" + item.getPrice());
	    final LinearLayout expandedView = (LinearLayout) findViewById(
	            R.id.menuItemDetail);
	    final ImageView expandedImageView = (ImageView) findViewById(
	            R.id.enlargedImage); 
	    String imageFile = "cache/" + shop.getId() + "/" + item.getImage();
        try {
        	expandedImageView.setImageBitmap(BitmapFactory.decodeStream(getAssets().open(imageFile)));
		} catch (IOException e) {
			Log.e(getClass().getName(), "Fail to load image file '" + imageFile + "'");
		} 
	    ((Button)findViewById(R.id.btnMenuItemDetailOrder)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Order.currentOrder().add(item);
			}
	    	
	    });

	    // Calculate the starting and ending bounds for the zoomed-in image.
	    // This step involves lots of math. Yay, math.
	    final Rect startBounds = new Rect();
	    final Rect finalBounds = new Rect();
	    final Point globalOffset = new Point();

	    // The start bounds are the global visible rectangle of the thumbnail,
	    // and the final bounds are the global visible rectangle of the container
	    // view. Also set the container view's offset as the origin for the
	    // bounds, since that's the origin for the positioning animation
	    // properties (X, Y).
	    srcView.getGlobalVisibleRect(startBounds);
	    findViewById(R.id.menuContainer).getGlobalVisibleRect(finalBounds, globalOffset);
	    startBounds.offset(-globalOffset.x, -globalOffset.y);
	    finalBounds.offset(-globalOffset.x, -globalOffset.y);

	    // Adjust the start bounds to be the same aspect ratio as the final
	    // bounds using the "center crop" technique. This prevents undesirable
	    // stretching during the animation. Also calculate the start scaling
	    // factor (the end scaling factor is always 1.0).
	    float startScale;
	    if ((float) finalBounds.width() / finalBounds.height()
	            > (float) startBounds.width() / startBounds.height()) {
	        // Extend start bounds horizontally
	        startScale = (float) startBounds.height() / finalBounds.height();
	        float startWidth = startScale * finalBounds.width();
	        float deltaWidth = (startWidth - startBounds.width()) / 2;
	        startBounds.left -= deltaWidth;
	        startBounds.right += deltaWidth;
	    } else {
	        // Extend start bounds vertically
	        startScale = (float) startBounds.width() / finalBounds.width();
	        float startHeight = startScale * finalBounds.height();
	        float deltaHeight = (startHeight - startBounds.height()) / 2;
	        startBounds.top -= deltaHeight;
	        startBounds.bottom += deltaHeight;
	    } 

	    // Hide the thumbnail and show the zoomed-in view. When the animation
	    // begins, it will position the zoomed-in view in the place of the
	    // thumbnail.
	    srcView.setAlpha(0f);
	    expandedView.setVisibility(View.VISIBLE);

	    // Set the pivot point for SCALE_X and SCALE_Y transformations
	    // to the top-left corner of the zoomed-in view (the default
	    // is the center of the view).
	    expandedView.setPivotX(0f);
	    expandedView.setPivotY(0f);

	    // Construct and run the parallel animation of the four translation and
	    // scale properties (X, Y, SCALE_X, and SCALE_Y).
	    AnimatorSet set = new AnimatorSet();
	    set
	            .play(ObjectAnimator.ofFloat(expandedView, View.X,
	                    startBounds.left, finalBounds.left))
	            .with(ObjectAnimator.ofFloat(expandedView, View.Y,
	                    startBounds.top, finalBounds.top))
	            .with(ObjectAnimator.ofFloat(expandedView, View.SCALE_X,
	            startScale, 1f)).with(ObjectAnimator.ofFloat(expandedView,
	                    View.SCALE_Y, startScale, 1f));
	    set.setDuration(mShortAnimationDuration);
	    set.setInterpolator(new DecelerateInterpolator());
	    set.addListener(new AnimatorListenerAdapter() {
	        @Override
	        public void onAnimationEnd(Animator animation) {
	            mCurrentAnimator = null;
	        }

	        @Override
	        public void onAnimationCancel(Animator animation) {
	            mCurrentAnimator = null;
	        }
	    });
	    set.start();
	    mCurrentAnimator = set;

	    // Upon clicking the zoomed-in image, it should zoom back down
	    // to the original bounds and show the thumbnail instead of
	    // the expanded image.
	    final float startScaleFinal = startScale;
	    expandedView.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View view) {
	            if (mCurrentAnimator != null) {
	                mCurrentAnimator.cancel();
	            }

	            // Animate the four positioning/sizing properties in parallel,
	            // back to their original values.
	            AnimatorSet set = new AnimatorSet();
	            set.play(ObjectAnimator
	                        .ofFloat(expandedView, View.X, startBounds.left))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedView, 
	                                        View.Y,startBounds.top))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedView, 
	                                        View.SCALE_X, startScaleFinal))
	                        .with(ObjectAnimator
	                                .ofFloat(expandedView, 
	                                        View.SCALE_Y, startScaleFinal));
	            set.setDuration(mShortAnimationDuration);
	            set.setInterpolator(new DecelerateInterpolator());
	            set.addListener(new AnimatorListenerAdapter() {
	                @Override
	                public void onAnimationEnd(Animator animation) {
	                    srcView.setAlpha(1f);
	                    expandedView.setVisibility(View.GONE);
	                    mCurrentAnimator = null;
	                }

	                @Override
	                public void onAnimationCancel(Animator animation) {
	                	srcView.setAlpha(1f);
	                    expandedView.setVisibility(View.GONE);
	                    mCurrentAnimator = null;
	                }
	            });
	            set.start();
	            mCurrentAnimator = set;
	        }
	    });
	}
}


