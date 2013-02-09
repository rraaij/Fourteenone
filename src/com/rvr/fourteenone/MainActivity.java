package com.rvr.fourteenone;

import com.rvr.fourteenone.model.GameInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private GameInfo gameinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
	}

	public void onClickStartGame(View view) {
		
		EditText txt_player1 = (EditText) findViewById(R.id.editText_pl1name);
		EditText txt_player2 = (EditText) findViewById(R.id.editText_pl2name);

		/* STUB DATA */
		txt_player1.setText("Shane");
		txt_player2.setText("Earl");
		((EditText) findViewById(R.id.editText_target)).setText("100");

        gameinfo = new GameInfo();
        gameinfo.setTarget(Integer.parseInt(((EditText) findViewById(R.id.editText_target)).getText().toString()));

        // Select the player who starts the game
        SelectPlayerDialogFragment selectPlayer = SelectPlayerDialogFragment.newInstance(
                "Select the player who starts the game",
                txt_player1.getText().toString(),
                txt_player2.getText().toString());
        selectPlayer.show(getFragmentManager(), "dialog");
	}

    public void doPlayer1Click() {
        gameinfo.setPlayer1(((EditText) findViewById(R.id.editText_pl1name)).getText().toString());
        gameinfo.setPlayer2(((EditText) findViewById(R.id.editText_pl2name)).getText().toString());

        startGame();
    }

    public void doPlayer2Click() {
        gameinfo.setPlayer1(((EditText) findViewById(R.id.editText_pl2name)).getText().toString());
        gameinfo.setPlayer2(((EditText) findViewById(R.id.editText_pl1name)).getText().toString());

        startGame();
    }

    private void startGame() {
        Intent gameIntent = new Intent("com.rvr.fourteenone.GameActivity");
        gameIntent.putExtra("com.rvr.fourteenone.model.GameInfo", gameinfo);

        startActivity(gameIntent);
    }
}
