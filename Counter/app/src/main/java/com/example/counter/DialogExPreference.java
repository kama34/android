package com.example.counter;

import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import com.example.counter.MainActivity.*;

public class DialogExPreference extends DialogPreference implements DialogInterface.OnClickListener
{
    public DialogExPreference(Context oContext, AttributeSet attrs)
    {
        super(oContext, attrs);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        super.onClick(dialog, which);
        if (which == -1) {
            Log.d("myLog", "" + which);
            MainActivity.delete("Counter", "0");
        }
    }
}
