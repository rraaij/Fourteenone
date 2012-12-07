package com.rvr.fourteenone;

import com.rvr.fourteenone.model.GameInfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game);
		
		TextView target = (TextView) findViewById(R.id.textView_targetScore);
		
		Bundle bundle = getIntent().getExtras();
		GameInfo gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
		
		target.setText(Integer.toString(gameinfo.getTarget()));
	}

}
