package edu.thomas.ui.train;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import edu.thomas.CallbackActivity;
import edu.thomas.R;
import edu.thomas.databinding.FragmentTrainBinding;
import edu.thomas.model.train.TrainInfo;


public class TrainFragment extends Fragment implements bulleHoraireListener   {

    private FragmentTrainBinding binding;
    private CallbackActivity callbackActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CallbackActivity) {
            callbackActivity = (CallbackActivity) context;
        }
        else {
            throw new RuntimeException(callbackActivity  + " must implement CallbackActivity");
        }
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ArrayList<TrainInfo> trainInfos;
        trainInfos = new ArrayList<>();
        trainInfos.add(new TrainInfo("11H41", "15h10", "Antibes", "Monaco", "30min", "TER"));
        trainInfos.add(new TrainInfo("11H41", "15h10", "Monaco", "Antibes", "30min", "TER", true,
                "Train en retard de 20 min ", "12h00", "16h00"));
        bulleHoraireAdapter bl = new bulleHoraireAdapter(callbackActivity,trainInfos);
        ListView lv = view.findViewById(R.id.listVue);
        lv.setAdapter(bl);
    }

    @Override
    public void onClickNom(TrainInfo item, TextView display) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Personne");

        builder.setMessage("Vous avez clique sur : " + item.getGareArrivee());
        builder.setNeutralButton("OK",null);
        builder.show();
    }

    @Override
    public Context getContext() {
        return requireActivity().getApplicationContext();
    }


}