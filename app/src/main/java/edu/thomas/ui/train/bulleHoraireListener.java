package edu.thomas.ui.train;

import android.content.Context;
import android.widget.TextView;

import edu.thomas.model.train.TrainInfo;

public interface bulleHoraireListener {
        void onClickNom(TrainInfo item, TextView display);
        Context getContext();
    }

