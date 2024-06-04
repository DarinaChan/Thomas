package edu.thomas;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

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
import edu.thomas.service.DatabaseService;
import edu.thomas.service.FirestoreMiguelCallback;
import edu.thomas.users.Train;
import edu.thomas.users.TrainAdapter;
import edu.thomas.users.User;

public class FragmentTrain extends Fragment {
    private final String TAG = "Thomas " + getClass().getSimpleName();
    DatabaseService db = new DatabaseService();
    List<Train> trainsMiguel = new ArrayList<>();
    List<Train> journeyList = new ArrayList<>();
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, container, false);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(FragmentTrain.this);
            navController.navigate(R.id.action_trainFragment_to_searchTrainFragment);
        });
        fetchTrainNames(view);
        return view;
    }



    public void fetchTrainNames(View view) {
        showProgressDialog();
        db.getMiguel(new FirestoreMiguelCallback() {
            @Override
            public void onMiguelCallback(User user) {
                if (user != null) {
                    TrainList.getInstance().addAllTrain(user.getTrains());
                    dismissProgressDialog();
                    updateUiWithTrainNames(view);
                }
            }
        });
    }
    public void updateUiWithTrainNames(View view){
        TrainAdapter adapter = new TrainAdapter(getContext(), TrainList.getInstance().getTrainInfoList());
        ListView listView = view.findViewById(R.id.list_view_train);
        listView.setAdapter(adapter);
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
