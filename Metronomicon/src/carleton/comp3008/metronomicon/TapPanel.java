package carleton.comp3008.metronomicon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class TapPanel extends SurfaceView implements SurfaceHolder.Callback {

	// Instance
	private static TapPanel instance;
	
	// GetInstance
	public static TapPanel getInstance(){
		return instance;
	}
	
	// Thread
	TapThread thread;
	
	// Start
	public void start(){
		thread = new TapThread(getHolder(), this);
		thread.setRunning(true);
		thread.start();
	}
	
	// Stop
	public void stop(){
		thread.setRunning(false);
	}
	
	private static final String TAG = TapPanel.class.getSimpleName();
	
	int timer, yBuffer;
	float[] coordinates;
	static int DEFAULT_X = 0;
	int DEFAULT_Y;
	int LINE_LENGTH;
	
	static int NUM_LINES = 30;
	static int NUM_POINTS = 2 * NUM_LINES;
	static int NUM_COORDINATES = 2 * NUM_POINTS;
	static int UPDATE_DELAY = 1;
	
	private long prevTime;
	private long prevTime2;

	public TapPanel(Context context, AttributeSet set) {
		super(context, set);
		
		// Instance
		instance = this;

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		timer = 0;
		yBuffer = 0;
		coordinates = new float[NUM_COORDINATES];

		prevTime = System.currentTimeMillis();
		prevTime2 = System.currentTimeMillis();
		

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		/* Empty */
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		DEFAULT_Y = this.getHeight()/2;
		LINE_LENGTH = this.getWidth()/NUM_LINES;
		
		for (int i = 0; i < NUM_COORDINATES; i += 4) {
			coordinates[i] = DEFAULT_X + ((i / 4) * LINE_LENGTH);
			coordinates[i + 1] = DEFAULT_Y;
			coordinates[i + 2] = coordinates[i] + LINE_LENGTH;
			coordinates[i + 3] = DEFAULT_Y;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			//manager.playSound();
			//setNextY(50);
		}
		return true;
	}

	protected void render(Canvas canvas) {
		// fills the canvas with black
		canvas.drawColor(Color.BLACK);

		// draw visual here
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(10);
		

		// canvas.drawLine(x, y, x + 20, y + 20, paint);
		canvas.drawLines(coordinates, paint);

	}

	public void setNextY(int y) {
		yBuffer = y;
		update();
	}

	public void update() {
		DEFAULT_Y = this.getHeight()/2;
		LINE_LENGTH = this.getWidth()/NUM_LINES;
		
		// visual update here
		timer++;
		if (timer % UPDATE_DELAY == 0) {
			// shift the previous points over
			for (int i = 1; i < NUM_COORDINATES - 4; i += 2) {
				coordinates[i] = coordinates[i + 4];
			}
			coordinates[NUM_COORDINATES - 3] = coordinates[NUM_COORDINATES - 1];
			coordinates[NUM_COORDINATES - 1] = yBuffer;
			yBuffer = DEFAULT_Y;
		}
		if (timer > NUM_POINTS * 10) {
			// RESET THE TIMER
			timer = 0;
		}
	
	}
}
