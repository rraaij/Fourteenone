package com.rvr.fourteenone;

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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ScoretableP1Fragment extends Fragment {
	
	public static int PLAYERNUMBER = 1;
	
	private TableLayout table;
	private int turn = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
		return inflater.inflate(R.layout.scoretable_p1, container, false);		
	}

	@Override
    public void onStart() {
        super.onStart();
        
        Bundle bundle = getActivity().getIntent().getExtras();
        GameInfo gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
                
        table = (TableLayout) getActivity().findViewById(R.id.tableLayout_player1);

        table.setStretchAllColumns(true);  
        table.setShrinkAllColumns(true);  
        TableRow playername = new TableRow(getActivity());  
        playername.setGravity(Gravity.CENTER_HORIZONTAL);

        // title column/row  
        TextView name = new TextView(getActivity());  
        name.setWidth(160);
        name.setText(gameinfo.getPlayer1());  
        name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);  
        name.setGravity(Gravity.CENTER);  
        name.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);  
        playername.addView(name);  
        
        TableRow header = createTableRow("#", "Run", "Foul", "Score");
        
        table.addView(playername);
        table.addView(header);
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

	public void addRow(int vRun, int vFoul) {
		// get last score
		if (table.getChildCount() == 2 && turn == 0) {
			// it's the first score to be entered
			table.addView(createTableRow(Integer.toString(turn+1), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(vRun-vFoul)));
		} else {
			TableRow child = (TableRow) table.getChildAt(table.getChildCount()-1);
			int iNr = Integer.parseInt((String) ((TextView) child.getChildAt(0)).getText());
			iNr++;

			// opeenvolgende fouls
			int iFoul = Integer.parseInt((String) ((TextView) child.getChildAt(2)).getText());

			int iScore = Integer.parseInt((String) ((TextView) child.getChildAt(3)).getText());
			iScore += vRun - vFoul;
			
			table.addView(createTableRow(Integer.toString(iNr), Integer.toString(vRun), Integer.toString(vFoul), Integer.toString(iScore)));
		}
	}
	
	public void setPlayerAtTable(boolean active) {
		if (active) {
			table.setBackgroundColor(Color.WHITE);
		} else {
			table.setBackgroundColor(Color.parseColor("#33b5e5"));			
		}
	}
}
