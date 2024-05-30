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
    private List<Journey> journeyList;
    private ListView listView;
    private JourneyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journeys, container, false);
        listView = view.findViewById(R.id.list_view_journeys);

        // Sample data
        journeyList = new ArrayList<>();
        journeyList.add(new Journey("Paris", "London", "2024-06-01 08:00", "2024-06-01 10:00"));
        journeyList.add(new Journey("Berlin", "Munich", "2024-06-02 09:00", "2024-06-02 11:00"));
        // Add more journeys as needed

        adapter = new JourneyAdapter(getContext(), journeyList);
        listView.setAdapter(adapter);

        return view;
    }
}
