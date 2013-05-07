package carleton.comp3008.metronomicon;

import java.util.Calendar;

public class Ticker {

	// Measure
	private int measure;

	// Running
	private boolean running;

	// Constructor
	public Ticker(int measure) {
		this.measure = measure;
		running = false;
	}

	// Tick
	public void tick() {
		TapPanel.getInstance().setNextY(TapPanel.getInstance().getHeight() / 4);
		SoundManager.getInstance().playLow();
	}

	// Accent
	public void accent() {
		TapPanel.getInstance().setNextY(TapPanel.getInstance().getHeight() / 9);
		SoundManager.getInstance().playHigh();
	}

	// Sub
	public void sub() {
		TapPanel.getInstance().setNextY(
				TapPanel.getInstance().getHeight() * 7 / 10);
		SoundManager.getInstance().playSub();
	}

	// Start
	public void start() {
		running = true;
		new Thread(new Beat()).start();
	}

	// Stop
	public void stop() {
		running = false;
	}

	// Beat
	private class Beat implements Runnable {
		// Timers
		Calendar previous;
		Calendar current;
		// Vars
		int countIn;
		int count;
		int bpm;
		int delay;
		int elapsed;
		boolean sub3;
		boolean sub2;
		boolean sub1;

		// Run
		public void run() {
			// Init
			previous = Calendar.getInstance();
			count = 1;
			sub1 = sub2 = sub3 = true;
			countIn = -1;
			accent();
			// Thread Loop
			while (running) {
				current = Calendar.getInstance();
				bpm = MainActivity.getInstance().getBPM();
				// Check BPM
				if (bpm == 0) {
					continue;
				}
				delay = 60000 / bpm;
				elapsed = (int) (current.getTimeInMillis() - previous
						.getTimeInMillis());

				// Count In
				if (MainActivity.getInstance().getUseCountIn() && countIn == -1) {
					countIn = MainActivity.getInstance().getCount();
				} else if (!MainActivity.getInstance().getUseCountIn()) {
					countIn = -1;
				}

				// Subdivisions
				if (MainActivity.getInstance().getUseSubdivision()) {
					int subs = MainActivity.getInstance().getDiv();
					int subDelay = delay / (subs + 1);
					// First Sub
					if (subs >= 1 && sub1 && elapsed >= subDelay) {
						sub1 = false;
						sub();
					}
					// Second Sub
					if (subs >= 2 && sub2 && elapsed >= subDelay * 2) {
						sub2 = false;
						sub();
					}
					// Third Sub
					if (subs >= 3 && sub3 && elapsed >= subDelay * 3) {
						sub3 = false;
						sub();
					}
				}

				// Time Elapsed
				if (elapsed >= delay) {
					count = count % measure + 1;
					previous = current;
					// Display Accent
					if (count == 1) {
						accent();
					}
					// Display Tick
					if (count > 1) {
						tick();
					}
					sub1 = true;
					sub2 = true;
					sub3 = true;

					// Count In
					if (count == measure
							&& MainActivity.getInstance().getUseCountIn()) {
						countIn = countIn - 1;
						if (countIn == 0) {
							running = false;
							// Update UP
							MainActivity.getInstance().getHandler()
									.post(new Runnable() {
										public void run() {
											MainActivity.getInstance()
													.getInstance().stop();
										}
									});
						}
					}
				}

			}
		}
	}

}
