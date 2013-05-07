package carleton.comp3008.metronomicon;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Settings extends Activity {

	// UI Elements
	TextView settingsTextView;
	TextView subdevisionsTextView;
	TextView panTextView;
	TextView countInTextView;
	TextView wakeLockTextView;
	ImageButton backButton;
	ImageButton panLeft;
	ImageButton panCenter;
	ImageButton panRight;
	ImageButton quarterButton;
	ImageButton eighthButton;
	ImageButton sixteenthButton;
	ImageButton countOne;
	ImageButton countTwo;
	ImageButton countThree;
	ImageButton countFour;
	ImageButton countFive;
	ImageButton countSix;
	ImageButton screenBright;
	ImageButton screenOff;

	// Listener
	OnClickListener backButtonL;
	OnTouchListener panLeftL;
	OnTouchListener panCenterL;
	OnTouchListener panRightL;
	OnTouchListener quarterButtonL;
	OnTouchListener eighthButtonL;
	OnTouchListener sixteenthButtonL;
	OnTouchListener countOneL;
	OnTouchListener countTwoL;
	OnTouchListener countThreeL;
	OnTouchListener countFourL;
	OnTouchListener countFiveL;
	OnTouchListener countSixL;
	OnTouchListener screenBrightL;
	OnTouchListener screenDimL;
	OnTouchListener screenOffL;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// Register UI Elemetns
		settingsTextView = (TextView) findViewById(R.id.settingsTextView);
		subdevisionsTextView = (TextView) findViewById(R.id.subDivisionsTextView);
		panTextView = (TextView) findViewById(R.id.panTextView);
		countInTextView = (TextView) findViewById(R.id.countInTextView);
		wakeLockTextView = (TextView) findViewById(R.id.wakeLockTextView);
		backButton = (ImageButton) findViewById(R.id.backButton);
		panLeft = (ImageButton) findViewById(R.id.panLeftButton);
		panCenter = (ImageButton) findViewById(R.id.panCenterButton);
		panRight = (ImageButton) findViewById(R.id.panRightButton);
		quarterButton = (ImageButton) findViewById(R.id.quarterButton);
		eighthButton = (ImageButton) findViewById(R.id.eighthButton);
		sixteenthButton = (ImageButton) findViewById(R.id.sixteenthButton);
		countOne = (ImageButton) findViewById(R.id.countOneButton);
		countTwo = (ImageButton) findViewById(R.id.countTwoButton);
		countThree = (ImageButton) findViewById(R.id.countThreeButton);
		countFour = (ImageButton) findViewById(R.id.countFourButton);
		countFive = (ImageButton) findViewById(R.id.countFiveButton);
		countSix = (ImageButton) findViewById(R.id.countSixButton);
		screenOff = (ImageButton) findViewById(R.id.screenOffButton);
		screenBright = (ImageButton) findViewById(R.id.screenBrightButton);
		
		// Create and Apply Font
		Typeface font = Typeface.createFromAsset(getAssets(), "OCR_A_Std.ttf");
		settingsTextView.setTypeface(font);
		subdevisionsTextView.setTypeface(font);
		panTextView.setTypeface(font);
		countInTextView.setTypeface(font);
		wakeLockTextView.setTypeface(font);

		// Back Button On Click Listener
		backButtonL = new View.OnClickListener() {
			public void onClick(View v) {
				Settings.this.finish();
			}
		};

		// Set Back Button On Click Listener
		backButton.setOnClickListener(backButtonL);

		// Pan Left On Touch Listener
		panLeftL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					panCenter.setPressed(false);
					panRight.setPressed(false);
					panCenter.setOnTouchListener(null);
					panRight.setOnTouchListener(null);
					panCenter.setClickable(false);
					panRight.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					panCenter.setClickable(true);
					panRight.setClickable(true);
					panCenter.setOnTouchListener(panCenterL);
					panRight.setOnTouchListener(panRightL);
					panLeft.setPressed(true);
					MainActivity.getInstance().setPan(MainActivity.PAN_LEFT);
				}
				return true;
			}
		};

		// Set Left On Touch Listener
		panLeft.setOnTouchListener(panLeftL);

		// Pan Center On Touch Listener
		panCenterL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					panLeft.setPressed(false);
					panRight.setPressed(false);
					panLeft.setOnTouchListener(null);
					panRight.setOnTouchListener(null);
					panLeft.setClickable(false);
					panRight.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					panLeft.setClickable(true);
					panRight.setClickable(true);
					panLeft.setOnTouchListener(panLeftL);
					panRight.setOnTouchListener(panRightL);
					panCenter.setPressed(true);
					MainActivity.getInstance().setPan(MainActivity.PAN_BOTH);
				}
				return true;
			}
		};

		// Set Center On Touch Listener
		panCenter.setOnTouchListener(panCenterL);

		// Pan Right On Touch Listener
		panRightL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					panLeft.setPressed(false);
					panCenter.setPressed(false);
					panLeft.setOnTouchListener(null);
					panCenter.setOnTouchListener(null);
					panLeft.setClickable(false);
					panCenter.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					panLeft.setClickable(true);
					panCenter.setClickable(true);
					panLeft.setOnTouchListener(panLeftL);
					panCenter.setOnTouchListener(panCenterL);
					panRight.setPressed(true);
					MainActivity.getInstance().setPan(MainActivity.PAN_RIGHT);
				}
				return true;
			}
		};

		// Set Right On Touch Listener
		panRight.setOnTouchListener(panRightL);

		// Quarter Button On Touch Listener
		quarterButtonL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					eighthButton.setPressed(false);
					sixteenthButton.setPressed(false);
					eighthButton.setOnTouchListener(null);
					sixteenthButton.setOnTouchListener(null);
					eighthButton.setClickable(false);
					sixteenthButton.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					eighthButton.setClickable(true);
					sixteenthButton.setClickable(true);
					eighthButton.setOnTouchListener(eighthButtonL);
					sixteenthButton.setOnTouchListener(sixteenthButtonL);
					quarterButton.setPressed(true);
					MainActivity.getInstance().setDiv(MainActivity.DIV_QUARTER);
				}
				return true;
			}
		};

		// Set Quarter Button On Touch Listener
		quarterButton.setOnTouchListener(quarterButtonL);

		// Eighth Button On Touch Listener
		eighthButtonL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					quarterButton.setPressed(false);
					sixteenthButton.setPressed(false);
					quarterButton.setOnTouchListener(null);
					sixteenthButton.setOnTouchListener(null);
					quarterButton.setClickable(false);
					sixteenthButton.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					quarterButton.setClickable(true);
					sixteenthButton.setClickable(true);
					quarterButton.setOnTouchListener(quarterButtonL);
					sixteenthButton.setOnTouchListener(sixteenthButtonL);
					eighthButton.setPressed(true);
					MainActivity.getInstance().setDiv(MainActivity.DIV_EIGHTH);
				}
				return true;
			}
		};

		// Set Eighth Button On Touch Listener
		eighthButton.setOnTouchListener(eighthButtonL);

		// Sixteenth Button On Touch Listener
		sixteenthButtonL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					quarterButton.setPressed(false);
					eighthButton.setPressed(false);
					quarterButton.setOnTouchListener(null);
					eighthButton.setOnTouchListener(null);
					quarterButton.setClickable(false);
					eighthButton.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					quarterButton.setClickable(true);
					eighthButton.setClickable(true);
					quarterButton.setOnTouchListener(quarterButtonL);
					eighthButton.setOnTouchListener(eighthButtonL);
					sixteenthButton.setPressed(true);
					MainActivity.getInstance().setDiv(
							MainActivity.DIV_SIXTEENTH);
				}
				return true;
			}
		};

		// Set Sixteenth Button On Touch Listener
		sixteenthButton.setOnTouchListener(sixteenthButtonL);

		// Count-One On Touch Listener
		countOneL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countTwo.setPressed(false);
					countThree.setPressed(false);
					countFour.setPressed(false);
					countFive.setPressed(false);
					countSix.setPressed(false);
					countTwo.setOnTouchListener(null);
					countThree.setOnTouchListener(null);
					countFour.setOnTouchListener(null);
					countFive.setOnTouchListener(null);
					countSix.setOnTouchListener(null);
					countTwo.setClickable(false);
					countThree.setClickable(false);
					countFour.setClickable(false);
					countFive.setClickable(false);
					countSix.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countTwo.setClickable(true);
					countThree.setClickable(true);
					countFour.setClickable(true);
					countFive.setClickable(true);
					countSix.setClickable(true);
					countTwo.setOnTouchListener(countTwoL);
					countThree.setOnTouchListener(countThreeL);
					countFour.setOnTouchListener(countFourL);
					countFive.setOnTouchListener(countFiveL);
					countSix.setOnTouchListener(countSixL);
					countOne.setPressed(true);
					MainActivity.getInstance().setCount(1);
				}
				return true;
			}
		};

		// Set Count-One On Touch Listener
		countOne.setOnTouchListener(countOneL);

		// Count-Two On Touch Listener
		countTwoL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countOne.setPressed(false);
					countThree.setPressed(false);
					countFour.setPressed(false);
					countFive.setPressed(false);
					countSix.setPressed(false);
					countOne.setOnTouchListener(null);
					countThree.setOnTouchListener(null);
					countFour.setOnTouchListener(null);
					countFive.setOnTouchListener(null);
					countSix.setOnTouchListener(null);
					countOne.setClickable(false);
					countThree.setClickable(false);
					countFour.setClickable(false);
					countFive.setClickable(false);
					countSix.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countOne.setClickable(true);
					countThree.setClickable(true);
					countFour.setClickable(true);
					countFive.setClickable(true);
					countSix.setClickable(true);
					countOne.setOnTouchListener(countOneL);
					countThree.setOnTouchListener(countThreeL);
					countFour.setOnTouchListener(countFourL);
					countFive.setOnTouchListener(countFiveL);
					countSix.setOnTouchListener(countSixL);
					countTwo.setPressed(true);
					MainActivity.getInstance().setCount(2);
				}
				return true;
			}
		};

		// Set Count-Two On Touch Listener
		countTwo.setOnTouchListener(countTwoL);

		// Count-Three On Touch Listener
		countThreeL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countOne.setPressed(false);
					countTwo.setPressed(false);
					countFour.setPressed(false);
					countFive.setPressed(false);
					countSix.setPressed(false);
					countOne.setOnTouchListener(null);
					countTwo.setOnTouchListener(null);
					countFour.setOnTouchListener(null);
					countFive.setOnTouchListener(null);
					countSix.setOnTouchListener(null);
					countOne.setClickable(false);
					countTwo.setClickable(false);
					countFour.setClickable(false);
					countFive.setClickable(false);
					countSix.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countOne.setClickable(true);
					countTwo.setClickable(true);
					countFour.setClickable(true);
					countFive.setClickable(true);
					countSix.setClickable(true);
					countOne.setOnTouchListener(countOneL);
					countTwo.setOnTouchListener(countTwoL);
					countFour.setOnTouchListener(countFourL);
					countFive.setOnTouchListener(countFiveL);
					countSix.setOnTouchListener(countSixL);
					countThree.setPressed(true);
					MainActivity.getInstance().setCount(3);
				}
				return true;
			}
		};

		// Set Count-Three On Touch Listener
		countThree.setOnTouchListener(countThreeL);

		// Count-Four On Touch Listener
		countFourL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countOne.setPressed(false);
					countTwo.setPressed(false);
					countThree.setPressed(false);
					countFive.setPressed(false);
					countSix.setPressed(false);
					countOne.setOnTouchListener(null);
					countTwo.setOnTouchListener(null);
					countThree.setOnTouchListener(null);
					countFive.setOnTouchListener(null);
					countSix.setOnTouchListener(null);
					countOne.setClickable(false);
					countTwo.setClickable(false);
					countThree.setClickable(false);
					countFive.setClickable(false);
					countSix.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countOne.setClickable(true);
					countTwo.setClickable(true);
					countThree.setClickable(true);
					countFive.setClickable(true);
					countSix.setClickable(true);
					countOne.setOnTouchListener(countOneL);
					countTwo.setOnTouchListener(countTwoL);
					countThree.setOnTouchListener(countThreeL);
					countFive.setOnTouchListener(countFiveL);
					countSix.setOnTouchListener(countSixL);
					countFour.setPressed(true);
					MainActivity.getInstance().setCount(4);
				}
				return true;
			}
		};

		// Set Count-Four On Touch Listener
		countFour.setOnTouchListener(countFourL);

		// Count-Five On Touch Listener
		countFiveL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countOne.setPressed(false);
					countTwo.setPressed(false);
					countThree.setPressed(false);
					countFour.setPressed(false);
					countSix.setPressed(false);
					countOne.setOnTouchListener(null);
					countTwo.setOnTouchListener(null);
					countThree.setOnTouchListener(null);
					countFour.setOnTouchListener(null);
					countSix.setOnTouchListener(null);
					countOne.setClickable(false);
					countTwo.setClickable(false);
					countThree.setClickable(false);
					countFour.setClickable(false);
					countSix.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countOne.setClickable(true);
					countTwo.setClickable(true);
					countThree.setClickable(true);
					countFour.setClickable(true);
					countSix.setClickable(true);
					countOne.setOnTouchListener(countOneL);
					countTwo.setOnTouchListener(countTwoL);
					countThree.setOnTouchListener(countThreeL);
					countFour.setOnTouchListener(countFourL);
					countSix.setOnTouchListener(countSixL);
					countFive.setPressed(true);
					MainActivity.getInstance().setCount(5);
				}
				return true;
			}
		};

		// Set Count-Six On Touch Listener
		countSix.setOnTouchListener(countFiveL);

		// Count-Six On Touch Listener
		countSixL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					countOne.setPressed(false);
					countTwo.setPressed(false);
					countThree.setPressed(false);
					countFour.setPressed(false);
					countFive.setPressed(false);
					countOne.setOnTouchListener(null);
					countTwo.setOnTouchListener(null);
					countThree.setOnTouchListener(null);
					countFour.setOnTouchListener(null);
					countFive.setOnTouchListener(null);
					countOne.setClickable(false);
					countTwo.setClickable(false);
					countThree.setClickable(false);
					countFour.setClickable(false);
					countFive.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					countOne.setClickable(true);
					countTwo.setClickable(true);
					countThree.setClickable(true);
					countFour.setClickable(true);
					countFive.setClickable(true);
					countOne.setOnTouchListener(countOneL);
					countTwo.setOnTouchListener(countTwoL);
					countThree.setOnTouchListener(countThreeL);
					countFour.setOnTouchListener(countFourL);
					countFive.setOnTouchListener(countFiveL);
					countSix.setPressed(true);
					MainActivity.getInstance().setCount(6);
				}
				return true;
			}
		};

		// Set Count-Six On Touch Listener
		countSix.setOnTouchListener(countSixL);
		
		// Screen Off On Touch Listener
		screenOffL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					screenBright.setPressed(false);
					screenBright.setOnTouchListener(null);
					screenBright.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					screenBright.setClickable(true);
					screenBright.setOnTouchListener(screenBrightL);
					screenOff.setPressed(true);
					MainActivity.getInstance().setScreen(MainActivity.SCREEN_OFF);
				}
				return true;
			}
		};
		
		// Set Screen Off On Touch Listener
		screenOff.setOnTouchListener(screenOffL);

		// Screen Bright On Touch Listener
		screenBrightL = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Button Down
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					screenOff.setPressed(false);
					screenOff.setOnTouchListener(null);
					screenOff.setClickable(false);
					return false;
				}
				// Button Up
				else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.playSoundEffect(android.view.SoundEffectConstants.CLICK);
					screenOff.setClickable(true);
					screenOff.setOnTouchListener(screenOffL);
					screenBright.setPressed(true);
					MainActivity.getInstance().setScreen(MainActivity.SCREEN_BRIGHT);
				}
				return true;
			}
		};

		// Set Screen Dim On Touch Listener
		screenBright.setOnTouchListener(screenBrightL);

	}

	// On Resume
	public void onResume() {
		super.onResume();
		
		// Set Pan Default
		int pan = MainActivity.getInstance().getPan();
		switch (pan) {
		case MainActivity.PAN_LEFT:
			this.panLeft.setPressed(true);
			break;
		case MainActivity.PAN_RIGHT:
			this.panRight.setPressed(true);
			break;
		case MainActivity.PAN_BOTH:
			this.panCenter.setPressed(true);
			break;
		}

		// Set Default Div
		int div = MainActivity.getInstance().getDiv();
		switch (div) {
		case MainActivity.DIV_QUARTER:
			this.quarterButton.setPressed(true);
			break;
		case MainActivity.DIV_EIGHTH:
			this.eighthButton.setPressed(true);
			break;
		case MainActivity.DIV_SIXTEENTH:
			this.sixteenthButton.setPressed(true);
			break;
		}
		
		// Set Default Count In
		int count = MainActivity.getInstance().getCount();
		switch (count) {
		case 1:
			this.countOne.setPressed(true);
			break;
		case 2:
			this.countTwo.setPressed(true);
			break;
		case 3:
			this.countThree.setPressed(true);
			break;
		case 4:
			this.countFour.setPressed(true);
			break;
		case 5:
			this.countFive.setPressed(true);
			break;
		case 6:
			this.countSix.setPressed(true);
			break;
		}
		
		// Set Default Screen
		int screen = MainActivity.getInstance().getScreen();
		switch (screen) {
		case MainActivity.SCREEN_OFF:
			this.screenOff.setPressed(true);
			break;
		case MainActivity.SCREEN_BRIGHT:
			this.screenBright.setPressed(true);
			break;
		}	

	}

}
