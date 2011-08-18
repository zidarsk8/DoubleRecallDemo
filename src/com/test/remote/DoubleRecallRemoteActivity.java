package com.test.remote;

import com.doublerecall.DoubleRecall;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DoubleRecallRemoteActivity extends Activity {
	
	private static final int DR_REQUEST_CODE = 0;
	private DoubleRecall mDoubleRecall;
	private TextView mResultText;
	private TextView mStatsText;
	private Button mButton;
	private int mCaceled = 0;
	private int mRetries = 0;
	private int mNothing = 0;
	private int mCorrect = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mDoubleRecall = new DoubleRecall(this, "local_test_key");
		
		init();
	}
	
	private void init(){
		mResultText = (TextView) findViewById(R.id.resultText);
		mButton = ((Button) findViewById(R.id.showRecallButton));
		mStatsText = ((TextView) findViewById(R.id.statsText));
		((TextView) findViewById(R.id.versionText)).setText(DoubleRecall.VERSION);
		
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mDoubleRecall.showRecall(DR_REQUEST_CODE);
			}
		});

		mResultText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCounters();
			}
		});

		mStatsText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showCounters();
			}
		});
		
		showCounters();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == DR_REQUEST_CODE){
			switch (resultCode) {
				case DoubleRecall.RESPONSE_CANCELED:
					mResultText.setText("Y U NO RETYPE");
					mCaceled++;
					break;
				case DoubleRecall.RESPONSE_MAX_RETRIES:
					mResultText.setText("¯\\_(ツ)_/¯");
					mRetries++;
					break;
				case DoubleRecall.RESPONSE_NO_MESSAGES:
					mResultText.setText("Nothing to see here,\nmove along.");
					mNothing++;
					break;
				case DoubleRecall.RESPONSE_OK:
					mResultText.setText("Finally!");
					mCorrect++;
					break;
				default:
					mResultText.setText("WAIT WOOT!!");
					break;
			}
		}
		showCounters();
	}
	
	private void showCounters(){
		int count = mDoubleRecall.count();
		mStatsText.setText(
				"Canceled:     " +mCaceled+ "\n" +
				"Max retries:  " +mRetries+ "\n" +
				"No recalls:   " +mNothing+ "\n" +
				"Correct:      " +mCorrect+ "\n" +
				"Count:        " +count);
		
		if (count > 0){
			mButton.setVisibility(Button.VISIBLE);
		}else {
			mButton.setVisibility(Button.INVISIBLE);
		}
		
	}
	
}