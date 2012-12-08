package com.rvr.fourteenone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.rvr.fourteenone.model.GameInfo;

public class GameActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game);
		
		TextView target = (TextView) findViewById(R.id.textView_targetScore);
		
		Bundle bundle = getIntent().getExtras();
		GameInfo gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
		
		//targetscore invullen
		target.setText(Integer.toString(gameinfo.getTarget()));
		
		// scoretabellen voor de spelers aanmaken...
		ScoretableP1Fragment scoretable_player1 = new ScoretableP1Fragment();

		ScoretableP2Fragment scoretable_player2 = new ScoretableP2Fragment();

		// ... en toevoegen aan de layout
		getSupportFragmentManager().beginTransaction()
			.add(R.id.scoretables, scoretable_player1)
			.add(R.id.scoretables, scoretable_player2)
			.commit();
		
	}

}
