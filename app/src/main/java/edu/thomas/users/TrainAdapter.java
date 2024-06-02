package edu.thomas.users;

import static edu.thomas.users.Train.formatDateAndTime;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.thomas.R;

public class TrainAdapter extends BaseAdapter {
    private final Context context;
    private final List<Train> trains;
    private final LayoutInflater inflater;

    public TrainAdapter(Context context, List<Train> trains) {
        this.context = context;
        this.trains = trains;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return trains.size();
    }

    @Override
    public Object getItem(int position) {
        return trains.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_train, parent, false);
        }

        Train train = trains.get(position);

        TextView tvDate = convertView.findViewById(R.id.date);
        TextView tvDepartureStation = convertView.findViewById(R.id.departureStation);
        TextView tvArrivalStation = convertView.findViewById(R.id.arrivalStation);
        TextView tvDeparture = convertView.findViewById(R.id.departure);
        TextView tvArrival = convertView.findViewById(R.id.arrival);
        ImageButton btnAddToCalendar = convertView.findViewById(R.id.addEvent);

        tvDate.setText(formatDateAndTime(train.getDepartureAt())[0]);
        tvDepartureStation.setText(train.getDepartureWhere());
        tvArrivalStation.setText(train.getArrivalWhere());
        tvDeparture.setText(formatDateAndTime(train.getDepartureAt())[1]);
        tvArrival.setText(formatDateAndTime(train.getArrivalAt())[1]);

        btnAddToCalendar.setOnClickListener(v -> {
            Log.d("TrainAdapter", "Button clicked for train: " + train.getDepartureWhere() + " to " + train.getArrivalWhere());
            addToCalendar(train);
        });

        return convertView;
    }

    private void addToCalendar(Train train) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Calendar permission is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, train.getDepartureWhere() + " à " + train.getArrivalWhere())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, train.getDepartureAt())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, train.getArrivalAt())
                .putExtra(CalendarContract.Events.EVENT_LOCATION, train.getDepartureWhere() + " - " + train.getArrivalWhere())
                .putExtra(CalendarContract.Events.DESCRIPTION, "Voyage de " + train.getArrivalWhere() + " à " + train.getArrivalWhere());

        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            Log.d("TrainAdapter", "Starting activity to add to calendar");
            context.startActivity(intent);
        } else {
            Log.e("TrainAdapter", "No calendar app found");
            Toast.makeText(context, "No calendar app found", Toast.LENGTH_SHORT).show();
        }
    }

    private long convertToMillis(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH'h'mm", Locale.getDefault());
        String dateTimeStr = dateStr;
        try {
            Date date = format.parse(dateTimeStr);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            Log.e("TrainAdapter", "Date parsing error", e);
            return 0;
        }
    }
}
