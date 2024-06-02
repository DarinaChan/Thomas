package edu.thomas.ui.train;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public  abstract  class TimeManagement implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {
    private Context context;

    public TimeManagement(Context context) {
        // Vérifiez que le contexte est une instance d'Activity
        if (context instanceof Activity) {
            this.context = context;
        } else {
            throw new IllegalArgumentException("Le contexte doit être une instance d'Activity");
        }
    }


    public void showDatePickerDialog() {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    context,
                    this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

            datePickerDialog.show();
        }
    }
    public void showTimePickerDialog() {
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    context,
                    this,
                    hour,
                    minute,
                    true); // true for 24-hour format, false for 12-hour format

            timePickerDialog.show();
        }
    }

    @Override
    public  abstract void onDateSet(DatePicker view, int year, int month, int dayOfMonth);

    @Override
    public abstract void onTimeSet(TimePicker view, int hourOfDay, int minute);

    @Override
    public abstract void onClick(View v);
}
