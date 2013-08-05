package signaturesdk.acquisition;

import java.util.LinkedList;

import signaturesdk.beans.AcquisitionSignWord;
import signaturesdk.beans.SignatureData;
import signaturesdk.features.utils.Copier;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * Drawable View for Android
 * @author Luciano Giuseppe - 05/2013
 * 
 *
 */
public class SignatureAndroidView extends View {
	private boolean inMotion = false;
	
	public SignatureAndroidView(Context context) {
		super(context);

		paint = createPaint(Color.BLACK, 4);
		
		this.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if(!inMotion) 
					clear();
				
				return true;
			}
			
		});
	}

	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		this.setBackgroundColor(Color.WHITE);
		
		for(AcquisitionSignWord w : signature) {
			LinkedList<Double> x = w.getX();
			LinkedList<Double> y = w.getY();
			
			//draw by line
			for(int i = 0; i < x.size()-1; i++) {
				c.drawLine(x.get(i).floatValue(), y.get(i).floatValue(), x.get(i+1).floatValue(), y.get(i+1).floatValue(), paint);			
			}

		}
	}

	final Handler h = new Handler();
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		super.onTouchEvent(event);
		if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			inMotion = false;
			
			word = new AcquisitionSignWord();
			xr = word.getX();
			yr = word.getY();
			tr = word.getTime();
			press = word.getPressure();
			signature.add(word);
			
			xr.add((double) event.getX());
			yr.add((double) event.getY());
			tr.add(System.nanoTime());
			press.add((double) event.getPressure());
			
			invalidate();
		}
		
		if(event.getActionMasked() == MotionEvent.ACTION_MOVE){
			inMotion = true;
			
			xr.add((double) event.getX());
			yr.add((double) event.getY());
			tr.add(System.nanoTime());
			press.add((double) event.getPressure());
			
			invalidate();
		}
		
		if(event.getActionMasked() == MotionEvent.ACTION_UP){
		    h.postDelayed(new Runnable()
		    {
		        @Override
		        public void run()
		        {
		        	if(authListener != null)
		        		authListener.onAuthentication();
		        }
		    }, 1000); 
		}

		return true;
	}
	
	private AuthenticationListener authListener = null;
	public void registerAuthencationListener(AuthenticationListener listener) {
		authListener = listener;
	}
	
	public void clear() {
		this.signature.clear();
		invalidate();
		
	}
	
	public SignatureData getSignature() {
		return new SignatureData(Copier.acquisitionSignature(signature));
	}


	private Paint createPaint(int color, float width){
		Paint temp = new Paint();
		temp.setStyle(Paint.Style.STROKE);
		temp.setAntiAlias(true);
		temp.setColor(color);
		temp.setStrokeWidth(width);
		temp.setStrokeCap(Paint.Cap.ROUND);

		return temp;
	}

	private Paint paint;
	private LinkedList<Double> xr, yr, press;
	private LinkedList<Long> tr;
	private LinkedList<AcquisitionSignWord> signature = new LinkedList<AcquisitionSignWord>();
	private AcquisitionSignWord word;

}