package carleton.comp3008.metronomicon;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TapIn extends Activity {
	
	private static Handler handler;
	private static Calendar previous;
	private static ArrayDeque<Integer> differences;
	
	// Touch Event
	public static void touch(){
		
		// Get Current Time
		Calendar current = Calendar.getInstance();
		
		// Check if Previous Touch Exists
		if (previous == null){
			previous = current;
			return;
		}
		
		// Get Difference
		int difference = (int) (current.getTimeInMillis() - previous.getTimeInMillis());
		
		// Set Previous to Current
		previous = current;
		
		// Add difference to list
		differences.addLast(difference);
		
		// Pop Old Elements
		if (differences.size() == 8){
			differences.removeFirst();
		}
		
		// Get Average
		if (differences.size() == 7){
			
			// Iterator
			Iterator it = differences.descendingIterator();
			
			// Sort Differences
			int[] ints = new int[7];
			ints[0] = (Integer) it.next();
			ints[1] = (Integer) it.next();
			ints[2] = (Integer) it.next();
			ints[3] = (Integer) it.next();
			ints[4] = (Integer) it.next();
			ints[5] = (Integer) it.next();
			ints[6] = (Integer) it.next();
			Arrays.sort(ints);
			
			// Get Median
			int avg = ints[3];
			
			// Get BPM
			final int bpm = 60000 / avg;
			
			// Set Visual
			handler.post(new Runnable(){
				public void run() {
					TapIn.this.bpmDisplay.setDisplay(bpm);
					TapIn.this.bpmDisplay.setDisplay(TapIn.this.bpmDisplay.getBPM());
				}
			});
			
		}
		
	}
	
	// UI Elements
	private ImageView firstDigit;
	private ImageView secondDigit;
	private ImageView thirdDigit;
	private ImageButton backButton;
	private ImageButton visualButton;
	private ImageButton audioButton;
	private TapPanel2 tapPanel;
	
	// Feedback
	boolean vis;
	boolean aud;
	
	// BPM Display
	private static BPMDisplay bpmDisplay;

	// On Create
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tap_in);
		
		// Touch Stuff
		handler = new Handler();
		previous = null;
		differences = new ArrayDeque<Integer>();
		
		// Register UI Elements
		firstDigit = (ImageView) findViewById(R.id.firstDigitImageView2);
		secondDigit = (ImageView) findViewById(R.id.secondDigitImageView2);
		thirdDigit = (ImageView) findViewById(R.id.thirdDigitImageView2);
		backButton = (ImageButton) findViewById(R.id.backButton2);
		visualButton = (ImageButton) findViewById(R.id.visualButton);
		audioButton = (ImageButton) findViewById(R.id.audioButton);
		tapPanel = (TapPanel2) findViewById(R.id.tapPanel2); 
		
		// Create BPM Display
		bpmDisplay = new BPMDisplay(firstDigit, secondDigit, thirdDigit, 0);
		
		// Back Button On Click Listener
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TapIn.this.finish();	
			}
		});
		
		// Get Visual Feedback Status
		if(MainActivity.getInstance().getVisFeedback()){
			vis = true;
			visualButton.setImageResource(R.drawable.visual_on);
		}
		else{
			vis = false;
			visualButton.setImageResource(R.drawable.visual_off);
		}
		
		// Get Audio Feedback Status
		if(MainActivity.getInstance().getAudFeedback()){
			aud = true;
			audioButton.setImageResource(R.drawable.sound_on);
		}
		else{
			aud = false;
			audioButton.setImageResource(R.drawable.sound_off);
		}
		
		// Visual Button Listener
		visualButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(vis){
					visualButton.setImageResource(R.drawable.visual_off);
				}
				else{
					visualButton.setImageResource(R.drawable.visual_on);
				}
				vis = ! vis;
				MainActivity.getInstance().setVisFeedback(vis);
			}
		});
		
		// Audio Button Listener
		audioButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				if(aud){
					audioButton.setImageResource(R.drawable.sound_off);
					SoundManager.getInstance().mute();
				}
				else{
					audioButton.setImageResource(R.drawable.sound_on);
					SoundManager.getInstance().unmute();
				}
				aud = !aud;
				MainActivity.getInstance().setAudFeedback(aud);
			}
		});
		
	}

	// On Pause
	public void onPause(){
		super.onPause();
		tapPanel.stop();
		SoundManager.getInstance().unmute();
	}
	
	// On Resume
	public void onResume(){
		super.onResume();
		tapPanel.start();
		if (!aud){
			SoundManager.getInstance().mute();
		}
	}
	
	// On Destroy
	public void onDestroy(){
		// Update Main BPM
		if(bpmDisplay.getBPM() != 0){
			MainActivity.getInstance().setBPM(bpmDisplay.getBPM());
		}
		super.onDestroy();
	}
	
	// Beats Per Minute Display
	private class BPMDisplay {

		final int minBPM = 0;
		final int maxBPM = 240;
		ImageView first, second, third;
		int bpm;

		// Constructor
		public BPMDisplay(ImageView first, ImageView second, ImageView third,
				int bpm) {
			this.first = first;
			this.second = second;
			this.third = third;
			this.bpm = bpm;
			// Update Display
			setDisplay(bpm);
		}

		// Set Display
		private void setDisplay(int bpm) {
			// Set BPM
			this.bpm = bpm;
			// Calculate Digits
			int d1, d2, d3;
			// 3 digits
			if (bpm >= 100) {
				d1 = (bpm / 100) % 10;
				d2 = (bpm / 10) % 10;
				d3 = bpm % 10;
			}
			// 2 digits
			else if (bpm >= 10) {
				d1 = (bpm / 10) % 10;
				d2 = bpm % 10;
				d3 = -1;
			}
			// 1 digit
			else {
				d1 = bpm;
				d2 = -1;
				d3 = -1;
			}
			// Set Digits
			setDigit(first, d1);
			setDigit(second, d2);
			setDigit(third, d3);
		}

		// Set Digit
		private void setDigit(ImageView image, int digit) {
			switch (digit) {
			// Zero Digit
			case 0:
				image.setImageResource(R.drawable.zero);
				break;
			// One Digit
			case 1:
				image.setImageResource(R.drawable.one);
				break;
			// Two Digit
			case 2:
				image.setImageResource(R.drawable.two);
				break;
			// Three Digit
			case 3:
				image.setImageResource(R.drawable.three);
				break;
			// Four Digit
			case 4:
				image.setImageResource(R.drawable.four);
				break;
			// Five Digit
			case 5:
				image.setImageResource(R.drawable.five);
				break;
			// Six Digit
			case 6:
				image.setImageResource(R.drawable.six);
				break;
			// Seven Digit
			case 7:
				image.setImageResource(R.drawable.seven);
				break;
			// Eight Digit
			case 8:
				image.setImageResource(R.drawable.eight);
				break;
			// Nine Digit
			case 9:
				image.setImageResource(R.drawable.nine);
				break;
			// Null Digit
			default:
				image.setImageDrawable(null);
				break;
			}
		}

		// Correct Digit
		public void correctDigit() {
			// Calculate BPM
			if (bpm < 10) {
				bpm = 0;
			} else {
				bpm = bpm / 10;
			}
			// Update Display
			setDisplay(bpm);
		}

		// Add Digit
		public void addDigit(int digit) {
			// Zero First Digit
			if (bpm == 0 && digit == 0) {
				return;
			}
			// Too Many Digits
			if (bpm >= 100) {
				return;
			}
			// BPM > Max
			if ((bpm * 10 + digit) > maxBPM) {
				return;
			}
			// Calculate New BPM
			bpm = bpm * 10 + digit;
			// Update Display
			setDisplay(bpm);
		}

		// Add BPM
		public void addBPM(int b) {
			// Update BPM
			bpm = bpm + b;
			bpm = getBPM();
			// Update Display
			setDisplay(bpm);
		}

		// Get BPM
		public int getBPM() {
			// BPM < Min
			if (bpm < minBPM) {
				return minBPM;
			}
			// BPM > Max
			else if (bpm > maxBPM) {
				return maxBPM;
			} else {
				return bpm;
			}
		}

	}

}
