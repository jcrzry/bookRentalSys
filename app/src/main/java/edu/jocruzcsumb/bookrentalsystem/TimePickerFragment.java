package edu.jocruzcsumb.bookrentalsystem;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.TimePicker;


/**
 * Created by jcrzr on 12/5/2016.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this, hour, min,DateFormat.is24HourFormat(getActivity()));

    }
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
}
