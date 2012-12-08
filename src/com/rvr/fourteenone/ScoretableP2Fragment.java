package com.rvr.fourteenone;

import com.rvr.fourteenone.model.GameInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoretableP2Fragment extends Fragment {
	
	public static int PLAYERNUMBER = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
		return inflater.inflate(R.layout.scoretable_p2, container, false);		
	}

	@Override
    public void onStart() {
        super.onStart();
        
        Bundle bundle = getActivity().getIntent().getExtras();
        GameInfo gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
                
        TextView player2 = (TextView) getActivity().findViewById(R.id.textView_player2);

        player2.setText(gameinfo.getPlayer2());

	}
}
