package edu.thomas.model.journey;

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

public class JourneyAdapter extends BaseAdapter {
    private final Context context;
    private final List<Journey> journeys;
    private final LayoutInflater inflater;

    public JourneyAdapter(Context context, List<Journey> journeys) {
        this.context = context;
        this.journeys = journeys;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return journeys.size();
    }

    @Override
    public Object getItem(int position) {
        return journeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_journey, parent, false);
        }

        Journey journey = journeys.get(position);

        TextView tvDepartureStation = convertView.findViewById(R.id.departureStation);
        TextView tvArrivalStation = convertView.findViewById(R.id.arrivalStation);
        TextView tvDepartureDate = convertView.findViewById(R.id.departureDate);
        TextView tvArrivalDate = convertView.findViewById(R.id.arrivalDate);
        ImageButton btnAddToCalendar = convertView.findViewById(R.id.addEvent);

        tvDepartureStation.setText(journey.getDepartureStation());
        tvArrivalStation.setText(journey.getArrivalStation());
        tvDepartureDate.setText(journey.getDepartureDate());
        tvArrivalDate.setText(journey.getArrivalDate());

        btnAddToCalendar.setOnClickListener(v -> {
            Log.d("JourneyAdapter", "Button clicked for journey: " + journey.getDepartureStation() + " to " + journey.getArrivalStation());
            addToCalendar(journey);
        });

        return convertView;
    }

    private void addToCalendar(Journey journey) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Calendar permission is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, journey.getDepartureStation() + " to " + journey.getArrivalStation())
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertToMillis(journey.getDepartureDate()))
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, convertToMillis(journey.getArrivalDate()))
                .putExtra(CalendarContract.Events.EVENT_LOCATION, journey.getDepartureStation() + " - " + journey.getArrivalStation())
                .putExtra(CalendarContract.Events.DESCRIPTION, "Journey from " + journey.getDepartureStation() + " to " + journey.getArrivalStation());

        PackageManager packageManager = context.getPackageManager();
        if (intent.resolveActivity(packageManager) != null) {
            Log.d("JourneyAdapter", "Starting activity to add to calendar");
            context.startActivity(intent);
        } else {
            Log.e("JourneyAdapter", "No calendar app found");
            Toast.makeText(context, "No calendar app found", Toast.LENGTH_SHORT).show();
        }
    }

    private long convertToMillis(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.getDefault());
        try {
            Date date = format.parse(dateStr);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            Log.e("JourneyAdapter", "Date parsing error", e);
            return 0;
        }
    }
}
