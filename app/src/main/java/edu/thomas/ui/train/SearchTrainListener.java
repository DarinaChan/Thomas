package edu.thomas.ui.train;

import android.content.Context;
import android.widget.TextView;

import edu.thomas.users.Train;

public interface SearchTrainListener {
        void onClickNom(Train item, TextView display);
        Context getContext();
    }

