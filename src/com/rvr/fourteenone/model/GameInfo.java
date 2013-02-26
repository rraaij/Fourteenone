package com.rvr.fourteenone.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GameInfo implements Parcelable {

    public static final int LAST_ACTION_NONE = 0;
    public static final int LAST_ACTION_SCORE_PLAYER_1 = 1;
    public static final int LAST_ACTION_SCORE_PLAYER_2 = 2;
    public static final int LAST_ACTION_RERACK = 3;

	private String player1;
	private String player2;
	private int target;
    private int lastAction;

	/**
	 * Standard basic constructor for non-parcel
	 * object creation
	 */
	public GameInfo() {
       setLastAction(0);
    }
 
	/**
	 *
	 * Constructor to use when re-constructing object
	 * from a parcel
	 *
	 * @param in a parcel from which to read this object
	 */
	public GameInfo(Parcel in) {
        setLastAction(0);
        readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
 
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		// will come back in the same order
		dest.writeString(player1);
		dest.writeString(player2);
		dest.writeInt(target);
	}
 
	/**
	 *
	 * Called from the constructor to create this
	 * object from a parcel.
	 *
	 * @param in parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		player1 = in.readString();
		player2 = in.readString();
		target = in.readInt();
	}
 
    /**
     *
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays.
     *
     * This also means that you can use use the default
     * constructor to create the object and use another
     * method to hyrdate it as necessary.
     *
     * I just find it easier to use the constructor.
     * It makes sense for the way my brain thinks ;-)
     *
     */
    public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public GameInfo createFromParcel(Parcel in) {
                return new GameInfo(in);
            }
 
            public GameInfo[] newArray(int size) {
                return new GameInfo[size];
            }
        };
 

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

    public int getLastAction() {
        return lastAction;
    }

    public void setLastAction(int lastAction) {
        this.lastAction = lastAction;
    }


}
