package edu.thomas.model.journey;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.thomas.R;

public class JourneyAdapter extends BaseAdapter {
    private Context context;
    private List<Journey> journeys;
    private LayoutInflater inflater;

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

        TextView departureStation = convertView.findViewById(R.id.departureStation);
        TextView arrivalStation = convertView.findViewById(R.id.arrivalStation);
        TextView departureDate = convertView.findViewById(R.id.departureDate);
        TextView arrivalDate = convertView.findViewById(R.id.arrivalDate);
        Button addEvent = convertView.findViewById(R.id.addEvent);

        departureStation.setText(journey.getDepartureStation());
        arrivalStation.setText(journey.getArrivalStation());
        departureDate.setText(journey.getDepartureDate());
        arrivalDate.setText(journey.getArrivalDate());

        addEvent.setOnClickListener(v -> addToCalendar(journey));

        return convertView;
    }

    private void addToCalendar(Journey journey) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, journey.getDepartureStation() + " to " + journey.getArrivalStation());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, convertToMillis(journey.getDepartureDate()));
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, convertToMillis(journey.getArrivalDate()));
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, journey.getDepartureStation() + " - " + journey.getArrivalStation());

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    private long convertToMillis(String dateStr) {
        // Assume the dateStr is in a format like "yyyy-MM-dd HH:mm"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            Date date = format.parse(dateStr);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            return -1;
        }
    }
}
