package edu.thomas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.thomas.model.train.TrainList;
import edu.thomas.users.Train;
import edu.thomas.users.TrainAdapter;

public class FragmentTrain extends Fragment {
    private final String TAG = "Thomas " + getClass().getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, container, false);
        ListView listView = view.findViewById(R.id.list_view_train);

        TrainAdapter adapter = new TrainAdapter(getContext(), TrainList.getInstance().getTrainInfoList());
        listView.setAdapter(adapter);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(FragmentTrain.this);
            navController.navigate(R.id.action_trainFragment_to_searchTrainFragment);
        });

        return view;
    }
}
