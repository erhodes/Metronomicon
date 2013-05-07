package carleton.comp3008.metronomicon;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

//Repeat Listener
public class RepeatListener implements OnTouchListener {

	// Variables
	private Handler handler;
	private OnClickListener listener;
	private View downView;
	private int initialInterval;
	private final int normalInterval;

	// Constructor
	public RepeatListener(int initialInterval, int normalInterval,
			OnClickListener listener) {
		handler = new Handler();
		this.listener = listener;
		this.initialInterval = initialInterval;
		this.normalInterval = normalInterval;
	}

	// Runnable
	private Runnable handlerRunnable = new Runnable() {
		@Override
		public void run() {
			handler.postDelayed(this, normalInterval);
			listener.onClick(downView);
		}
	};

	// On Touch
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Play Feedback Sound
			v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
			// Disable Keypad
			handler.removeCallbacks(handlerRunnable);
			handler.postDelayed(handlerRunnable, initialInterval);
			downView = v;
			listener.onClick(v);
			break;
		case MotionEvent.ACTION_UP:
			// Enable Keypad
			handler.removeCallbacks(handlerRunnable);
			downView = null;
			break;
		}
		return false;
	}

}