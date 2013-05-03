package com.rvr.fourteenone;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: ramonvanraaij
 * Date: 29-04-13
 * Time: 20:37
 * To change this template use File | Settings | File Templates.
 */
public class EndGameFragment extends Fragment {

    static EndGameFragment newInstance(String winner, int highrun_p1, int highrun_p2, int avgrun_p1, int avgrun_p2, int fouls_p1, int fouls_p2) {
        EndGameFragment endgame = new EndGameFragment();
        Bundle args = new Bundle();
        args.putString("winner", winner);
        args.putInt("highrun_p1", highrun_p1);
        args.putInt("highrun_p2", highrun_p2);
        args.putInt("avgrun_p1", avgrun_p1);
        args.putInt("avgrun_p2", avgrun_p2);
        args.putInt("fouls_p1", fouls_p1);
        args.putInt("fouls_p2", fouls_p2);
        endgame.setArguments(args);
        return endgame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View view = inflater.inflate(R.layout.endgame, container, false);

        TextView endofgame = (TextView) view.findViewById(R.id.textView_endofgame);
        endofgame.setShadowLayer(2f, 2f, 2f, Color.WHITE);
        endofgame.setText("Game winner: " + getArguments().getString("winner"));

        TextView highrun_p1 = (TextView) view.findViewById(R.id.textView_highrun_p1);
        highrun_p1.setText(String.valueOf(getArguments().getInt("highrun_p1",0)));
        TextView highrun_p2 = (TextView) view.findViewById(R.id.textView_highrun_p2);
        highrun_p2.setText(String.valueOf(getArguments().getInt("highrun_p2",0)));
        TextView avgrun_p1 = (TextView) view.findViewById(R.id.textView_avgrun_p1);
        avgrun_p1.setText(String.valueOf(getArguments().getInt("avgrun_p1",0)));
        TextView avgrun_p2 = (TextView) view.findViewById(R.id.textView_avgrun_p2);
        avgrun_p2.setText(String.valueOf(getArguments().getInt("avgrun_p2",0)));
        TextView fouls_p1 = (TextView) view.findViewById(R.id.textView_fouls_p1);
        fouls_p1.setText(String.valueOf(getArguments().getInt("fouls_p1",0)));
        TextView fouls_p2 = (TextView) view.findViewById(R.id.textView_fouls_p2);
        fouls_p2.setText(String.valueOf(getArguments().getInt("fouls_p2",0)));

        Button startnewgame = ((Button) view.findViewById(R.id.button_startnewgame));
        final Intent i = new Intent(getActivity(), MainActivity.class);
        startnewgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        return view;
    }
}
