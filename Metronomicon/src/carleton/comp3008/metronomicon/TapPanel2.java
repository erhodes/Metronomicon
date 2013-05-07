package carleton.comp3008.metronomicon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TapPanel2 extends SurfaceView implements SurfaceHolder.Callback {
	
	private TapThread2 thread;
	
	// Start
	public void start(){
		thread = new TapThread2(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}
	
	// Stop
	public void stop(){
		thread.setRunning(false);
	}
	
	
	Bitmap circle;
	private float radius;
	private Paint paint;
	private float x, y;
	private int delay;
	
	public TapPanel2(Context context, AttributeSet set) {
		super(context, set);
		
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
		
		x = 0.0f;
		y = 0.0f;
		paint = new Paint();
		radius = 100.0f;
		delay = 0;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		/* Empty */
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}
	
	@SuppressWarnings("deprecation")
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			x = event.getX();
			y = event.getY();
			delay = 100;
			////////////////////////////////////////////////// 
			// On Touch                                    //
			/////////////////////////////////////////////////
			
			SoundManager.getInstance().playLow();
			TapIn.touch();
			
		}
		return true;
	}
	
	protected void render(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.BLACK);
		
		// Check if visual is set
		if(MainActivity.getInstance().getVisFeedback()){
			paint.setARGB(delay, 255, 255, 255);
			canvas.drawCircle(x, y, radius, paint);
		}
		
		if (delay > 0) delay -= 10;
		//canvas.drawBitmap(circle, x - (circle.getWidth()/2), y - (circle.getHeight()/2), null);
		
	}
	
}
