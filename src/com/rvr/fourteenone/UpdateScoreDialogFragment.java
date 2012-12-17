package com.rvr.fourteenone;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.rvr.fourteenone.model.GameInfo;

public class UpdateScoreDialogFragment extends DialogFragment {
	
	private NumberPicker ballsontable;
	private boolean foul = false;
	
	
	public interface UpdateScoreDialogListener {
        void onFinishUpdateScoreDialog(int ballsontable, boolean foul);
    }

    public UpdateScoreDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updatescore, container);
        
        Bundle bundle = getActivity().getIntent().getExtras();
        GameInfo gameinfo = bundle.getParcelable("com.rvr.fourteenone.model.GameInfo");
        
        getDialog().setTitle("Update Score voor " + gameinfo.getPlayer1());
        
        ballsontable = (NumberPicker) view.findViewById(R.id.numberPicker_ballsontable);
        ballsontable.setMaxValue(15);
        ballsontable.setMinValue(1);
        
      //set up foul button
        Button button_foul = (Button) view.findViewById(R.id.button_foul);
        button_foul.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				foul = true;
			}
		});

        Button button_cancel = (Button) view.findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//view.dismiss();
			}
		});
        
        
        Button button_ok = (Button) view.findViewById(R.id.button_ok);
        button_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//view.dismiss();
			}
		});
        
        return view;
    }
    
    public void onClickFoul(View view) {
    	Toast.makeText(getActivity(), "FOUL", Toast.LENGTH_SHORT).show();
    }
}
