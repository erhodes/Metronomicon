package carleton.comp3008.metronomicon;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class TapThread extends Thread {

	private SurfaceHolder surfaceHolder;
	private TapPanel tapPanel;
	private boolean running;
	
	
	public TapThread(SurfaceHolder sh, TapPanel tp) {
		super();
		surfaceHolder = sh;
		tapPanel = tp;
		running = false;
	}
	
	public void setRunning(boolean run) { running = run; }
	
	@Override
	public void run() {
		Canvas canvas;
		
		while(running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized(surfaceHolder) {
					this.tapPanel.update();
					// draw the canvas on the panel
					this.tapPanel.render(canvas);
				}
			} 
			catch(Exception e){
				
			}
			finally {
				// in case of an exception the surface is not left in an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
