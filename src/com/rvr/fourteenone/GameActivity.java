package com.rvr.fourteenone;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;

import com.rvr.fourteenone.UpdateScoreDialogFragment.UpdateScoreDialogListener;
import com.rvr.fourteenone.model.GameInfo;

public class GameActivity extends FragmentActivity implements UpdateScoreDialogListener {
	private TextView textview_target = null;
	private TextView textview_possiblerun = null;
	private GameInfo gameinfo = null;
	private boolean foul = false;
	//private ScoreUpdate scoreupdate = null;
	private ScoretableP1Fragment scoretable_player1;
	private ScoretableP2Fragment scoretable_player2;
	private int playerAtTable;
	private int possiblerun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.game);
		
		textview_target = (TextView) findViewById(R.id.textView_targetScore);
		textview_possiblerun = (TextView) findViewById(R.id.textView_PossibleRun);
		
		Bundle bundle = getIntent().getExtras();
		gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
		
		//initiele waarden vullen
		playerAtTable = 1;
		possiblerun = 15;
		textview_target.setText(Integer.toString(gameinfo.getTarget()));
		textview_possiblerun.setText(Integer.toString(possiblerun));
		
		// scoretabellen voor de spelers aanmaken...
		scoretable_player1 = new ScoretableP1Fragment();
		scoretable_player2 = new ScoretableP2Fragment();

		// ... en toevoegen aan de layout
		getSupportFragmentManager().beginTransaction()
			.add(R.id.scoretables, scoretable_player1)
			.add(R.id.scoretables, scoretable_player2)
			.commit();
		
	}
	
	public void onClickRerack(View view) {
		possiblerun = 14 + Integer.parseInt(textview_possiblerun.getText().toString());
		textview_possiblerun.setText(String.valueOf(possiblerun));
	}
	
	
    @Override
    public void onFinishUpdateScoreDialog(int ballsontable, boolean foul) {
        Toast.makeText(this, "ballsontable: " + ballsontable + ", foul: " + foul, Toast.LENGTH_SHORT).show();
    }
	
	
	public void onClickUpdateScore(View view) {
		
		//scoreupdate = new ScoreUpdate(0, false);
		
		//set up dialog
        final Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.updatescore);
        dialog.setTitle("Update Score voor " + gameinfo.getPlayer1());
        dialog.setCancelable(true);

        //set up numberpicker
        NumberPicker ballsontable = (NumberPicker) dialog.findViewById(R.id.numberPicker_ballsontable);
        ballsontable.setMaxValue(possiblerun>15?15:possiblerun);
        ballsontable.setMinValue(1);
        
        //set up foul button
        final ToggleButton button_foul = (ToggleButton) dialog.findViewById(R.id.button_foul);
        button_foul.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foul = button_foul.isChecked();
			}
		});

        Button button_cancel = (Button) dialog.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
        
        
        Button button_ok = (Button) dialog.findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				NumberPicker picker = (NumberPicker) dialog.findViewById(R.id.numberPicker_ballsontable);
				//scoreupdate.setBallsontable(picker.getValue());
				
				switch (playerAtTable) {
					case 1:
						scoretable_player1.addRow(possiblerun - Math.abs(picker.getValue()), foul?1:0);
						possiblerun = Math.abs(picker.getValue());
						textview_possiblerun.setText(String.valueOf(possiblerun));
						playerAtTable = 2;
						scoretable_player1.setPlayerAtTable(false);
						scoretable_player2.setPlayerAtTable(true);
						break;
					case 2:
						scoretable_player2.addRow(possiblerun - Math.abs(picker.getValue()), foul?1:0);
						possiblerun = Math.abs(picker.getValue());
						textview_possiblerun.setText(String.valueOf(possiblerun));
						playerAtTable = 1;
						scoretable_player1.setPlayerAtTable(true);
						scoretable_player2.setPlayerAtTable(false);
						break;
					default:
						break;
				}
				
				dialog.dismiss();
			}
		});
        
        //now that the dialog is set up, it's time to show it    
        dialog.show();
		
	}

}
