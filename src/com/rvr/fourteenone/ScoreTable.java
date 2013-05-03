package com.rvr.fourteenone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.*;
import com.rvr.fourteenone.model.GameInfo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoreTable extends Fragment {
	
	private GameInfo gameinfo = null;
    private int avg;
    private int fouls;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle, int layout) {
		return inflater.inflate(layout, container, false);		
	}
	
	public LinearLayout onStart(LinearLayout linearlayout, int layout, int player) {
        super.onStart();

        Log.e("ScoreTable", "[ScoreTable] onStart");

        Bundle bundle = getActivity().getIntent().getExtras();
        gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");

        linearlayout = (LinearLayout) getActivity().findViewById(layout);

        TableLayout scoretable = (TableLayout) linearlayout.findViewById(R.id.scoretable);

        scoretable.setStretchAllColumns(true);
        scoretable.setShrinkAllColumns(true);

        // title column/row  
        TextView name = (TextView) linearlayout.findViewById(R.id.playername);
        name.setWidth(160);
        name.setText(player == 1?gameinfo.getPlayer1():gameinfo.getPlayer2());
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

        // totalscore column/row
        TextView totalscore = (TextView) linearlayout.findViewById(R.id.totalscore);
        totalscore.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 38);
        totalscore.setShadowLayer(2f, 2f, 2f, 0xFF000000);

        // bij allereerste schermopbouw...
        if (scoretable.getChildCount() == 0) {
            scoretable.addView(createTableRow("#", "Run", "Foul", "Score"));
            totalscore.setText("0");
        } else if (scoretable.getChildCount() > 1) {
            // bij terugkomst nadat scherm uitgezet was
            TableRow child = (TableRow) scoretable.getChildAt(scoretable.getChildCount()-1);
            int iScore = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
            totalscore.setText(Integer.toString(iScore));
        }

        return linearlayout;
	}

    /* Called when activity is no longer visible to user (telefoon gaat uit, wordt uitgezet, een andere activity wordt actief */
    @Override
    public void onStop() {
        super.onStop();
        Log.e("ScoreTable", "[ScoreTable] onStop");
    }

    /* this activity is being paused, previous activity is being resumed */
    @Override
    public void onPause() {
        super.onPause();
        Log.e("ScoreTable", "[ScoreTable] onPause");
    }

    /* called when activity starts interacting with user */
    @Override
    public void onResume() {
        super.onResume();
        Log.e("ScoreTable", "[ScoreTable] onResume");
    }

    private TableRow createTableRow(String vNr, String vRun, String vFoul, String vScore) {
		TableRow row = new TableRow(getActivity());
        TextView nr = new TextView(getActivity());
        nr.setText(vNr);
        nr.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        nr.setGravity(Gravity.CENTER);
        nr.setTypeface(Typeface.DEFAULT_BOLD);
        TextView run = new TextView(getActivity());
        run.setText(vRun);
        run.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        run.setGravity(Gravity.CENTER);
        run.setTypeface(Typeface.DEFAULT_BOLD);
        TextView foul = new TextView(getActivity());
        foul.setText(vFoul);
        foul.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        foul.setGravity(Gravity.CENTER);
        foul.setTypeface(Typeface.DEFAULT_BOLD);
        TextView score = new TextView(getActivity());
        score.setText(vScore);
        score.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        score.setGravity(Gravity.CENTER);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        row.addView(nr);
        row.addView(run);
        row.addView(foul);
        row.addView(score);
        
        return row;
	}

	protected void addRow(LinearLayout linearlayout, int turn, int vRun, int vFoul) {
		int iScore;

        TableLayout scoretable = (TableLayout) linearlayout.findViewById(R.id.scoretable);

        // get last score
		if (scoretable.getChildCount() == 1 && turn == 0) {
			// it's the first score to be entered
			iScore = vRun-vFoul;
            scoretable.addView(createTableRow(Integer.toString(turn+1), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(iScore)));
		} else {
			TableRow child = (TableRow) scoretable.getChildAt(scoretable.getChildCount()-1);
			int iNr = Integer.parseInt((String) ((TextView) child.getChildAt(0)).getText());
			iNr++;

			// opeenvolgende fouls
			int iFoul = Integer.parseInt((String) ((TextView) child.getChildAt(2)).getText());

			iScore = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
			iScore += vRun - vFoul;

            scoretable.addView(createTableRow(Integer.toString(iNr), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(iScore)));
		}

        // totalscore column/row
        TextView totalscore = (TextView) linearlayout.findViewById(R.id.totalscore);
        if (totalscore != null) {
            totalscore.setText(Integer.toString(iScore));
            totalscore.setTextColor(Color.parseColor("#ffffff")); //wit
            totalscore.setShadowLayer(2f, 2f, 2f, 0xFF000000);
            ((TextView) linearlayout.findViewById(R.id.textView_ProvisionalText)).setVisibility(View.INVISIBLE);
        }

        if (iScore >= gameinfo.getTarget()) {
            // Winnaar is bekend!
            String winner = ((TextView) linearlayout.findViewById(R.id.playername)).getText().toString();
            ((GameActivity)getActivity()).onEndOfGame(winner);
        }
	}

    protected void setProvisionalTotalScore(LinearLayout linearlayout, int vRun) {
        TextView totalscore = (TextView) linearlayout.findViewById(R.id.totalscore);
        int iScore = Integer.parseInt((String) totalscore.getText()) + vRun - 1;
        totalscore.setText(Integer.toString(iScore));
        totalscore.setTextColor(Color.parseColor("#33b5e5")); //
        totalscore.setShadowLayer(0,0,0,0);

        ((TextView) linearlayout.findViewById(R.id.textView_ProvisionalText)).setVisibility(View.VISIBLE);
    }

    protected int removeLastRow(LinearLayout linearlayout) {
        TableLayout scoretable = (TableLayout) linearlayout.findViewById(R.id.scoretable);

        int score = 0;

        // get last score
        if (scoretable.getChildCount() > 1) {
            // terug te geven score teruggeven, om op te tellen bij de possible run.
            TableRow child = (TableRow) scoretable.getChildAt(scoretable.getChildCount()-1);
            score = Integer.parseInt((String) ((TextView) child.getChildAt(1)).getText());

            // regel verwijderen uit tabel
            scoretable.removeViewAt(scoretable.getChildCount()-1);

            TextView totalscore = (TextView) linearlayout.findViewById(R.id.totalscore);
            child = (TableRow) scoretable.getChildAt(scoretable.getChildCount()-1);
            if (scoretable.getChildCount() > 1) {
                int iScore = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
                totalscore.setText(Integer.toString(iScore));
            } else {
                totalscore.setText("0");
            }
        }
        return score;
    }
	
	protected void setPlayerAtTable(LinearLayout linearlayout, boolean active) {

        ScrollView scrollview = (ScrollView) linearlayout.findViewById(R.id.scrollview);
        TableLayout scoretable = (TableLayout) linearlayout.findViewById(R.id.scoretable);
        TextView name = (TextView) linearlayout.findViewById(R.id.playername);

        if (active) {
            scrollview.setBackgroundColor(Color.WHITE);
            scoretable.setBackgroundColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            name.setShadowLayer(2f, 2f, 2f, 0xFF000000);
		} else {
            scrollview.setBackgroundColor(Color.parseColor("#33b5e5"));
            scoretable.setBackgroundColor(Color.parseColor("#33b5e5"));
            name.setTextColor(Color.parseColor("#33b5e5"));
            name.setShadowLayer(0,0,0,0);
        }
	}

    public int getHighRun(LinearLayout linearlayout) {

        int highrun = 0;
        int total = 0;
        fouls = 0;
        int nr = 0;

        TableLayout scoretable = (TableLayout) linearlayout.findViewById(R.id.scoretable);

        for (int i = 1; i<scoretable.getChildCount();i++) {
            TableRow child = (TableRow) scoretable.getChildAt(i);

            nr = Integer.parseInt((String) ((TextView) child.getChildAt(0)).getText());
            int run = Integer.parseInt((String) ((TextView) child.getChildAt(1)).getText());
            if (run > highrun) {
                highrun = run;
            }

            fouls += Integer.parseInt((String) ((TextView) child.getChildAt(2)).getText());
            total = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
        }

        if (nr > 0) {
            avg = Math.round(total/nr);
        } else {
            avg = 0;
        }

        return highrun;
    }

    public int getAvgRun() {
        return avg;
    }

    public int getFouls() {
        return fouls;
    }

}
