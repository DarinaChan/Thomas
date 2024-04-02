package edu.thomas.ui.train;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.thomas.databinding.FragmentTrainBinding;


public class TrainFragment extends Fragment {

    private FragmentTrainBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TrainViewModel trainViewModel =
                new ViewModelProvider(this).get(TrainViewModel.class);

        binding = FragmentTrainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTrain;
        trainViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}