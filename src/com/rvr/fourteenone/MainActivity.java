package com.rvr.fourteenone;

import com.rvr.fourteenone.model.GameInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickStartGame(View view) {
		
		EditText txt_player1 = (EditText) findViewById(R.id.editText_pl1name);
		EditText txt_player2 = (EditText) findViewById(R.id.editText_pl2name);
		EditText txt_target = (EditText) findViewById(R.id.editText_target);
		
		GameInfo gameinfo = new GameInfo();
		gameinfo.setPlayer1(txt_player1.getText().toString());
		gameinfo.setPlayer2(txt_player2.getText().toString());
		gameinfo.setTarget(Integer.parseInt(txt_target.getText().toString()));
		
		Intent gameIntent = new Intent("com.rvr.fourteenone.GameActivity");
		gameIntent.putExtra("com.rvr.fourteenone.model.GameInfo", gameinfo);
		
		startActivity(gameIntent);
	}

}
