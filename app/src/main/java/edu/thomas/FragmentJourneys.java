package edu.thomas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import edu.thomas.model.journey.Journey;
import edu.thomas.model.journey.JourneyAdapter;

public class FragmentJourneys extends Fragment {
    private final String TAG = "Thomas " + getClass().getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journeys, container, false);
        ListView listView = view.findViewById(R.id.list_view_journeys);

        List<Journey> journeyList = new ArrayList<>();
        journeyList.add(new Journey("Antibes", "Nice", "10h30", "10h50"));
        journeyList.add(new Journey("Nice", "Monaco", "14h20", "14h40"));

        JourneyAdapter adapter = new JourneyAdapter(getContext(), journeyList);
        listView.setAdapter(adapter);

        return view;
    }
}
