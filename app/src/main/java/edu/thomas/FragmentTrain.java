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
import java.util.Date;
import java.util.List;

import edu.thomas.users.Train;
import edu.thomas.users.TrainAdapter;

public class FragmentTrain extends Fragment {
    private final String TAG = "Thomas " + getClass().getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, container, false);
        ListView listView = view.findViewById(R.id.list_view_train);

        List<Train> journeyList = new ArrayList<>();
        journeyList.add(new Train(new Date(1663981200000L), new Date(1663983300000L), "Antibes", "Nice", "TGV", "123"));
        journeyList.add(new Train(new Date(1671895800000L), new Date(1671897000000L), "Nice", "Monaco", "TER", "456"));

        TrainAdapter adapter = new TrainAdapter(getContext(), journeyList);
        listView.setAdapter(adapter);

        return view;
    }
}
