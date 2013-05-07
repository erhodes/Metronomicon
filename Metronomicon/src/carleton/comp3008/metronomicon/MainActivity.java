package carleton.comp3008.metronomicon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

// Main Activity
public class MainActivity extends Activity {

	// Instance
	private static MainActivity instance;

	// Handler
	private Handler handler;

	// Power Manager
	PowerManager pm;

	// WakeLock
	WakeLock wakeLock;

	// Constants
	public static final int PAN_LEFT = 1;
	public static final int PAN_RIGHT = 2;
	public static final int PAN_BOTH = 3;
	public static final int DIV_EIGHTH = 1;
	public static final int DIV_QUARTER = 2;
	public static final int DIV_SIXTEENTH = 3;
	public static final int SCREEN_OFF = 1;
	public static final int SCREEN_BRIGHT = 3;

	// Settings
	private int pan;
	private int div;
	private int count;
	private int screen;
	private boolean visFeedback;
	private boolean audFeedback;

	// Get Screen
	public int getScreen() {
		return screen;
	}

	// Set Screen
	public void setScreen(int s) {
		this.screen = s;
	}

	// Get visFeedback
	public boolean getVisFeedback() {
		return visFeedback;
	}

	// Set visFeedback
	public void setVisFeedback(boolean vf) {
		visFeedback = vf;
	}

	// Get audFeedback
	public boolean getAudFeedback() {
		return audFeedback;
	}

	// Set audFeedback
	public void setAudFeedback(boolean af) {
		audFeedback = af;
	}

	// Set BPM
	public void setBPM(final int bpm) {
		handler.post(new Runnable() {
			public void run() {
				bpmDisplay.setDisplay(bpm);
			}
		});
	}

	// UI Elements
	private ImageView firstDigit;
	private ImageView secondDigit;
	private ImageView thirdDigit;
	private ImageButton zero;
	private ImageButton one;
	private ImageButton two;
	private ImageButton three;
	private ImageButton four;
	private ImageButton five;
	private ImageButton six;
	private ImageButton seven;
	private ImageButton eight;
	private ImageButton nine;
	private ImageButton left;
	private ImageButton right;
	private ImageButton correct;
	private ImageButton play;
	private ImageButton tap;
	private ImageButton mute;
	private ImageButton subdivision;
	private ImageButton countIn;
	private ImageButton settings;
	private SeekBar volume;
	private TapPanel tapPanel;

	// Listeners
	private OnClickListener zeroL;
	private OnClickListener oneL;
	private OnClickListener twoL;
	private OnClickListener threeL;
	private OnClickListener fourL;
	private OnClickListener fiveL;
	private OnClickListener sixL;
	private OnClickListener sevenL;
	private OnClickListener eightL;
	private OnClickListener nineL;
	private OnClickListener leftL;
	private OnClickListener rightL;
	private OnClickListener correctL;
	private OnClickListener playL;
	private OnClickListener tapL;
	private OnClickListener muteL;
	private OnClickListener subdivisionL;
	private OnClickListener countInL;
	private OnClickListener settingsL;
	private MyRepeatListener leftRL;
	private MyRepeatListener rightRL;
	private OnSeekBarChangeListener volumeCL;

	// Toggles
	private boolean useSound;
	private boolean useSubdivision;
	private boolean useCountIn;
	private boolean isPlaying;

	// Get Use Sub
	public boolean getUseSubdivision() {
		return useSubdivision;
	}

	// Stop
	public void stop() {
		play.setImageResource(R.drawable.play);
		this.isPlaying = false;
	}

	// Get Use Countin
	public boolean getUseCountIn() {
		return useCountIn;
	}

	// BPM Display
	private BPMDisplay bpmDisplay;

	// Sound Manager
	private SoundManager soundManager;

	// Ticker
	private Ticker ticker;

	// On Create
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set Instance
		instance = this;

		// Create Handler
		handler = new Handler();

		// Power Manager
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		// Settings
		pan = 3;
		div = 1;
		count = 2;
		screen = SCREEN_OFF;
		visFeedback = true;
		audFeedback = true;

		// WakeLock
		switch (screen) {
		case SCREEN_OFF:
			wakeLock = null;
			break;
		case SCREEN_BRIGHT:
			wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
					"My Tag");
			wakeLock.acquire();
			break;
		}

		// Set Toggles
		useSound = true;
		useSubdivision = false;
		useCountIn = false;
		isPlaying = false;

		// Register UI Elements
		firstDigit = (ImageView) findViewById(R.id.firstDigitImageView);
		secondDigit = (ImageView) findViewById(R.id.secondDigitImageView);
		thirdDigit = (ImageView) findViewById(R.id.thirdDigitImageView);
		zero = (ImageButton) findViewById(R.id.zeroButton);
		one = (ImageButton) findViewById(R.id.oneButton);
		two = (ImageButton) findViewById(R.id.twoButton);
		three = (ImageButton) findViewById(R.id.threeButton);
		four = (ImageButton) findViewById(R.id.fourButton);
		five = (ImageButton) findViewById(R.id.fiveButton);
		six = (ImageButton) findViewById(R.id.sixButton);
		seven = (ImageButton) findViewById(R.id.sevenButton);
		eight = (ImageButton) findViewById(R.id.eightButton);
		nine = (ImageButton) findViewById(R.id.nineButton);
		left = (ImageButton) findViewById(R.id.leftButton);
		right = (ImageButton) findViewById(R.id.rightButton);
		correct = (ImageButton) findViewById(R.id.correctButton);
		play = (ImageButton) findViewById(R.id.playButton);
		tap = (ImageButton) findViewById(R.id.tapButton);
		mute = (ImageButton) findViewById(R.id.muteButton);
		subdivision = (ImageButton) findViewById(R.id.subdivisionButton);
		countIn = (ImageButton) findViewById(R.id.countinButton);
		settings = (ImageButton) findViewById(R.id.settingsButton);
		volume = (SeekBar) findViewById(R.id.volumeBar);
		tapPanel = (TapPanel) findViewById(R.id.tapPanel);

		// Create BPM Display
		bpmDisplay = new BPMDisplay(firstDigit, secondDigit, thirdDigit, 120);

		// Create Sound Manager
		soundManager = new SoundManager(this);

		// Create Ticker
		ticker = new Ticker(4);

		// Zero Button Listener
		zeroL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 0
				bpmDisplay.addDigit(0);
			}
		};

		// One Button Listener
		oneL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 1
				bpmDisplay.addDigit(1);
			}
		};

		// Two Button Listener
		twoL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 2
				bpmDisplay.addDigit(2);
			}
		};

		// Three Button Listener
		threeL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 3
				bpmDisplay.addDigit(3);
			}
		};

		// Four Button Listener
		fourL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 4
				bpmDisplay.addDigit(4);
			}
		};

		// Five Button Listener
		fiveL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 5
				bpmDisplay.addDigit(5);
			}
		};

		// Six Button Listener
		sixL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 6
				bpmDisplay.addDigit(6);
			}
		};

		// Seven Button Listener
		sevenL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 7
				bpmDisplay.addDigit(7);
			}
		};

		// Eight Button Listener
		eightL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 8
				bpmDisplay.addDigit(8);
			}
		};

		// Nine Button Listener
		nineL = new View.OnClickListener() {
			public void onClick(View v) {
				// Add Digit 9
				bpmDisplay.addDigit(9);
			}
		};

		// Left Button Listener
		leftL = new View.OnClickListener() {
			// Minus One
			public void onClick(View v) {
				bpmDisplay.addBPM(-1);
			}
		};

		// Left Button Repeat Listener
		leftRL = new MyRepeatListener(MyRepeatListener.LEFT, leftL);

		// Right Button Listener
		rightL = new View.OnClickListener() {
			// Minus One
			public void onClick(View v) {
				bpmDisplay.addBPM(+1);
			}
		};

		// Right Button Repeat Listener
		rightRL = new MyRepeatListener(MyRepeatListener.RIGHT, rightL);

		// Correct Button Listener
		correctL = new View.OnClickListener() {
			public void onClick(View v) {
				// Correct BPM Display
				bpmDisplay.correctDigit();
			}
		};

		// Play Button Listener
		playL = new View.OnClickListener() {
			public void onClick(View v) {
				// Play
				if (!isPlaying) {
					// Set Stop Image
					play.setImageResource(R.drawable.stop);
					// Start Ticker
					ticker.start();
				}
				// Stop
				else {
					// Set Play Image
					play.setImageResource(R.drawable.play);
					// Stop Ticker
					ticker.stop();
				}
				isPlaying = !isPlaying;
			}
		};

		// Tap Button Listener
		tapL = new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TapIn.class);
				MainActivity.this.startActivity(intent);
			}
		};

		// Mute Button Listener
		muteL = new View.OnClickListener() {
			public void onClick(View v) {
				// Mute
				if (useSound) {
					soundManager.mute();
					mute.setImageResource(R.drawable.sound_off);
				}
				// Unmute
				else {
					soundManager.unmute();
					mute.setImageResource(R.drawable.sound_on);
				}
				useSound = !useSound;
			}
		};

		// Subdivision Button Listener
		subdivisionL = new View.OnClickListener() {
			public void onClick(View v) {
				// Turn Subdivision Off
				if (useSubdivision) {
					subdivision.setImageResource(R.drawable.subdivision_off);
				}
				// Turn Subdivision On
				else {
					subdivision.setImageResource(R.drawable.subdivision_on);
				}
				useSubdivision = !useSubdivision;
			}
		};

		// CountIn Button Listener
		countInL = new View.OnClickListener() {
			public void onClick(View v) {
				// Turn CountIn Off
				if (useCountIn) {
					countIn.setImageResource(R.drawable.countin_off);
				}
				// Turn Subdivision On
				else {
					countIn.setImageResource(R.drawable.countin_on);
				}
				useCountIn = !useCountIn;
			}
		};

		// Settings Button Listener
		settingsL = new View.OnClickListener() {
			public void onClick(View v) {
				// Start Activity
				Intent intent = new Intent(MainActivity.this, Settings.class);
				MainActivity.this.startActivity(intent);
			}
		};

		// Set Volume Seek Bar Values
		volume.setMax(soundManager.getMaxVolume());
		volume.setProgress(soundManager.getCurrentVolume());

		// Volume Listener
		volumeCL = new SeekBar.OnSeekBarChangeListener() {
			// Stop Tracking
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			// Start Tracking
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			// Progress Changed
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				soundManager.setCurrentVolume(progress);
			}
		};

		// Register Listeners
		zero.setOnClickListener(zeroL);
		one.setOnClickListener(oneL);
		two.setOnClickListener(twoL);
		three.setOnClickListener(threeL);
		four.setOnClickListener(fourL);
		five.setOnClickListener(fiveL);
		six.setOnClickListener(sixL);
		seven.setOnClickListener(sevenL);
		eight.setOnClickListener(eightL);
		nine.setOnClickListener(nineL);
		left.setOnTouchListener(leftRL);
		right.setOnTouchListener(rightRL);
		correct.setOnClickListener(correctL);
		play.setOnClickListener(playL);
		tap.setOnClickListener(tapL);
		mute.setOnClickListener(muteL);
		subdivision.setOnClickListener(subdivisionL);
		countIn.setOnClickListener(countInL);
		settings.setOnClickListener(settingsL);
		volume.setOnSeekBarChangeListener(volumeCL);

	}

	// On Pause
	public void onPause() {
		super.onPause();
		// Stop Tap Panel
		tapPanel.stop();
		// Stop Ticker
		ticker.stop();

		if (wakeLock != null) {
			wakeLock.release();
		}

		play.setImageResource(R.drawable.play);
		isPlaying = false;
		soundManager.unmute();
	}

	// On Resume
	public void onResume() {
		super.onResume();
		// Start Tap Penel
		tapPanel.start();

		// WakeLock
		switch (screen) {
		case SCREEN_OFF:
			wakeLock = null;
			break;
		case SCREEN_BRIGHT:
			wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
					"My Tag");
			wakeLock.acquire();
			break;
		}

		// Mute
		if (!useSound) {
			soundManager.mute();
		}
	}

	// Get Instance
	public static MainActivity getInstance() {
		return instance;
	}

	// Get Handler
	public Handler getHandler() {
		return handler;
	}

	// Get Pan
	public int getPan() {
		return pan;
	}

	// Set Pan
	public void setPan(int pan) {
		this.pan = pan;
		this.soundManager.setPanValue(pan);
	}

	// Get Div
	public int getDiv() {
		return div;
	}

	// Set Div
	public void setDiv(int div) {
		this.div = div;
	}

	// Get Count
	public int getCount() {
		return count;
	}

	// Set Count
	public void setCount(int count) {
		this.count = count;
	}

	// Get BPM
	public int getBPM() {
		return bpmDisplay.getBPM();
	}

	// Disable Keypad
	public void disableKeypad() {
		zero.setOnClickListener(null);
		one.setOnClickListener(null);
		two.setOnClickListener(null);
		three.setOnClickListener(null);
		four.setOnClickListener(null);
		five.setOnClickListener(null);
		six.setOnClickListener(null);
		seven.setOnClickListener(null);
		eight.setOnClickListener(null);
		nine.setOnClickListener(null);
		correct.setOnClickListener(null);
		tap.setOnClickListener(null);
	}

	// Disable Keypad
	public void enableKeypad() {
		zero.setOnClickListener(zeroL);
		one.setOnClickListener(oneL);
		two.setOnClickListener(twoL);
		three.setOnClickListener(threeL);
		four.setOnClickListener(fourL);
		five.setOnClickListener(fiveL);
		six.setOnClickListener(sixL);
		seven.setOnClickListener(sevenL);
		eight.setOnClickListener(eightL);
		nine.setOnClickListener(nineL);
		correct.setOnClickListener(correctL);
		tap.setOnClickListener(tapL);
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

	// My Repeat Listener
	private class MyRepeatListener extends RepeatListener {

		// Constants
		public static final int LEFT = 0;
		public static final int RIGHT = 1;

		// Variables
		int direction;

		// Constructor
		public MyRepeatListener(int direction, OnClickListener listener) {
			super(500, 100, listener);
			this.direction = direction;
		}

		// On Touch
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			// Disable Keypad When Pushed
			case MotionEvent.ACTION_DOWN:
				MainActivity.this.disableKeypad();
				if (direction == LEFT) {
					right.setOnTouchListener(null);
				} else {
					left.setOnTouchListener(null);
				}
				break;
			// Enable Keypad When Released
			case MotionEvent.ACTION_UP:
				MainActivity.this.enableKeypad();
				right.setOnTouchListener(rightRL);
				left.setOnTouchListener(leftRL);
				break;
			}
			return super.onTouch(v, event);
		}

	}

}
