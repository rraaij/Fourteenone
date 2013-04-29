package com.rvr.fourteenone;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebIconDatabase;
import android.widget.*;
import android.view.View.OnClickListener;

import com.rvr.fourteenone.model.GameInfo;

public class GameActivity extends FragmentActivity {
	//private TextView textview_target = null;
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

        Log.e("GameActivity", "[GameActivity] onCreate");


        setContentView(R.layout.game);

        Bundle bundle = getIntent().getExtras();
        gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Fourteenone - target:" + Integer.toString(gameinfo.getTarget()));

        //textview_target = (TextView) findViewById(R.id.textView_targetScore);
		textview_possiblerun = (TextView) findViewById(R.id.textView_PossibleRun);

		//initiele waarden vullen
		playerAtTable = 1;
		possiblerun = 15;
		
//		textview_target.setText(Integer.toString(gameinfo.getTarget()));
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

    /* Called when activity is no longer visible to user (telefoon gaat uit, wordt uitgezet, een andere activity wordt actief */
    @Override
    protected void onStop() {
        super.onStop();
        Log.e("GameActivity", "[GameActivity] onStop");
    }

    /* Called when activity becomes visible to user */
    @Override
    protected void onStart() {
        super.onStart();
        Log.e("GameActivity", "[GameActivity] onStart");

        if (playerAtTable == 1) {
            scoretable_player1.setPlayerAtTable(true);
            scoretable_player2.setPlayerAtTable(false);
        } else {
            scoretable_player1.setPlayerAtTable(false);
            scoretable_player2.setPlayerAtTable(true);
        }
    }

    /* this activity is being paused, previous activity is being resumed */
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("GameActivity", "[GameActivity] onPause");
    }

    /* Called when activity has been stopped (onStop() ) and is restarting again */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("GameActivity", "[GameActivity] onRestart");
    }

    /* called when activity starts interacting with user */
    @Override
    protected void onResume() {
        super.onResume();
        Log.e("GameActivity", "[GameActivity] onResume");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   // the < in the icon  in taskbar is pressed
            case R.id.menu_quitgame:
                quitGame();
                return true;
            case R.id.menu_scorecorrection:
                onClickScoreCorrection();
                return true;
            case R.id.menu_undolastscore:
                onClickUndoLastScore();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void quitGame() {
        // Are you sure Y/N
        final Intent i = new Intent(this, MainActivity.class);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
            .setTitle("Quit Game")
            .setMessage("Are you sure you want to quit this game?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener)
            .show();
    }

    /* the back button below the screen is pressed; implementing this method prevents the game from ending.  */
    public void onBackPressed() {
        Log.e("GameActivity", "[GameActivity] onBackPressed");
        quitGame();
    }

    public void onRerack(View view) {
        int iRun = Integer.parseInt(textview_possiblerun.getText().toString());
		possiblerun = 14 + iRun;
		textview_possiblerun.setText(String.valueOf(possiblerun));
        switch (playerAtTable) {
            case 1:
                if (iRun <= 15) {
                    scoretable_player1.setProvisionalTotalScore(iRun);
                } else {
                    scoretable_player1.setProvisionalTotalScore(15);
                }
                break;
            case 2:
                if (iRun <= 15) {
                    scoretable_player2.setProvisionalTotalScore(iRun);
                } else {
                    scoretable_player2.setProvisionalTotalScore(15);
                }
                break;
            default:
                break;
        }

        ((Button) findViewById(R.id.button_NoScore)).setEnabled(false);
        gameinfo.setLastAction(GameInfo.LAST_ACTION_RERACK);
	}

    public void onEndOfGame() {
        ((Button) findViewById(R.id.button_UpdateScore)).setEnabled(false);
        ((Button) findViewById(R.id.button_NoScore)).setEnabled(false);
        ((Button) findViewById(R.id.button_Rerack)).setEnabled(false);

        ((Button) findViewById(R.id.button_UpdateScore)).setVisibility(View.INVISIBLE);
        ((Button) findViewById(R.id.button_NoScore)).setVisibility(View.INVISIBLE);
        ((Button) findViewById(R.id.button_Rerack)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView_PossibleRunText)).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.textView_PossibleRun)).setVisibility(View.INVISIBLE);

        ((LinearLayout) findViewById(R.id.gameLayout)).removeView((LinearLayout) findViewById(R.id.linearLayout_ScoreButtons));
        ((LinearLayout) findViewById(R.id.linearLayout_NewGameButton)).setVisibility(View.VISIBLE);

        TextView endofgame = (TextView) findViewById(R.id.textView_endofgame);
        endofgame.setVisibility(1);
        endofgame.setShadowLayer(2f, 2f, 2f, Color.WHITE);
        endofgame.setText("Game winner!");

        Button startnewgame = ((Button) findViewById(R.id.button_startnewgame));
        startnewgame.setVisibility(1);
        final Intent i = new Intent(this, MainActivity.class);
        startnewgame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    public void onClickNoScore(View view) {
        switch (playerAtTable) {
            case 1:
                playerAtTable = 2;
                scoretable_player1.setPlayerAtTable(false);
                scoretable_player2.setPlayerAtTable(true);
                break;
            case 2:
                playerAtTable = 1;
                scoretable_player1.setPlayerAtTable(true);
                scoretable_player2.setPlayerAtTable(false);
                break;
            default:
                break;
        }
    }
	
	public void onClickUpdateScore(View view) {
		
		//set up dialog
        final Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.updatescore);
        dialog.setTitle("Update score for player " + (playerAtTable == 1?gameinfo.getPlayer1():gameinfo.getPlayer2()));
        dialog.setCancelable(true);

        //set up numberpicker
        NumberPicker ballsontable = (NumberPicker) dialog.findViewById(R.id.numberPicker_ballsontable);
        ballsontable.setMaxValue(possiblerun>15?15:possiblerun);
        ballsontable.setMinValue(2);
        ballsontable.setValue(possiblerun>15?15:possiblerun);
        
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

				switch (playerAtTable) {
					case 1:
						scoretable_player1.addRow(possiblerun - Math.abs(picker.getValue()), foul?1:0);
                        gameinfo.setLastAction(GameInfo.LAST_ACTION_SCORE_PLAYER_1);

						playerAtTable = 2;
						scoretable_player1.setPlayerAtTable(false);
						scoretable_player2.setPlayerAtTable(true);
						break;
					case 2:
						scoretable_player2.addRow(possiblerun - Math.abs(picker.getValue()), foul?1:0);
                        gameinfo.setLastAction(GameInfo.LAST_ACTION_SCORE_PLAYER_2);

                        playerAtTable = 1;
						scoretable_player1.setPlayerAtTable(true);
						scoretable_player2.setPlayerAtTable(false);
						break;
					default:
						break;
				}
                possiblerun = Math.abs(picker.getValue());
                textview_possiblerun.setText(String.valueOf(possiblerun));
                foul = false;
                if (findViewById(R.id.button_NoScore) != null) {
                    ((Button) findViewById(R.id.button_NoScore)).setEnabled(true);
                }
				dialog.dismiss();
			}
		});

        dialog.show();
		
	}

    public void onClickScoreCorrection() {
        //set up dialog
        final Dialog dialog = new Dialog(GameActivity.this);
        dialog.setContentView(R.layout.scorecorrection);
        dialog.setTitle("Score Correction");
        dialog.setCancelable(true);

        final RadioButton radio_p1 = (RadioButton) dialog.findViewById(R.id.radioButton_player1);
        final RadioButton radio_p2 = (RadioButton) dialog.findViewById(R.id.radioButton_player2);

        radio_p1.setText(gameinfo.getPlayer1());
        radio_p1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_p2.setChecked(false);
            }
        });
        radio_p2.setText(gameinfo.getPlayer2());
        radio_p2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_p1.setChecked(false);
            }
        });


        final ToggleButton button_add = (ToggleButton) dialog.findViewById(R.id.toggleButton_add);
        final ToggleButton button_subtract = (ToggleButton) dialog.findViewById(R.id.toggleButton_subtract);
        button_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                button_subtract.setChecked(false);
            }
        });
        button_subtract.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                button_add.setChecked(false);
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
                TextView errortext = (TextView) dialog.findViewById(R.id.textView_error);

                if (!radio_p1.isChecked() && !radio_p2.isChecked()) {
                    errortext.setText("Please select a player.");
                } else if (!button_add.isChecked() && !button_subtract.isChecked()) {
                    errortext.setText("Please select add or subtract.");
                } else if (((EditText) dialog.findViewById(R.id.editText_points)).getText().toString().equals("")
                        || ((EditText) dialog.findViewById(R.id.editText_points)).getText().toString().equals("0") ) {
                    errortext.setText("Correction must be more than 0.");
                } else {
                    int correction = Integer.parseInt(((EditText) dialog.findViewById(R.id.editText_points)).getText().toString());

                    if (button_subtract.isChecked()) {
                        correction = 0 - correction;
                    }

                    if (radio_p1.isChecked()) {
                        scoretable_player1.addRow(correction, 0);
                        gameinfo.setLastAction(GameInfo.LAST_ACTION_SCORE_PLAYER_1);
                    } else {
                        scoretable_player2.addRow(correction, 0);
                        gameinfo.setLastAction(GameInfo.LAST_ACTION_SCORE_PLAYER_2);
                    }

                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    /* 1. de laatste regel in een van de twee tabellen;
     * 2. een scorecorrectie in een van de twee tabellen;
     * 3. een onterechte rerack */
    public void onClickUndoLastScore() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        int score;
                        switch (gameinfo.getLastAction()) {
                            case GameInfo.LAST_ACTION_NONE:
                                break;
                            case GameInfo.LAST_ACTION_SCORE_PLAYER_1:
                                score = scoretable_player1.removeLastRow();
                                possiblerun = score + Integer.parseInt(textview_possiblerun.getText().toString());
                                textview_possiblerun.setText(String.valueOf(possiblerun));
                                break;
                            case GameInfo.LAST_ACTION_SCORE_PLAYER_2:
                                score = scoretable_player2.removeLastRow();
                                possiblerun = score + Integer.parseInt(textview_possiblerun.getText().toString());
                                textview_possiblerun.setText(String.valueOf(possiblerun));
                                break;
                            case GameInfo.LAST_ACTION_RERACK:
                                possiblerun = Integer.parseInt(textview_possiblerun.getText().toString()) - 14;
                                textview_possiblerun.setText(String.valueOf(possiblerun));
                                break;
                        }
                        gameinfo.setLastAction(GameInfo.LAST_ACTION_NONE);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Undo Last Score")
               .setMessage("The last score entry will be deleted. Are you sure?")
               .setPositiveButton("Yes", dialogClickListener)
               .setNegativeButton("No", dialogClickListener)
               .show();
    }

}
