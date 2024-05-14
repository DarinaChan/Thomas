package edu.thomas.ui.train;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.thomas.CallbackActivity;
import edu.thomas.R;
import edu.thomas.model.train.TrainInfo;
import edu.thomas.model.train.TrainInfoList;

public class bulleHoraireAdapter extends BaseAdapter {
    private final String TAG = "THOMAS "+getClass().getSimpleName();

    private final CallbackActivity activity;
    private final LayoutInflater inflater;
    List<TrainInfo> train;

    public bulleHoraireAdapter(CallbackActivity Callback){
        this.activity = Callback;
        inflater = LayoutInflater.from((Context) activity);
        train = TrainInfoList.getInstance().getTrainInfoList();
    }
    public bulleHoraireAdapter(CallbackActivity Callback,List<TrainInfo> trainList){
        this.activity = Callback;
        inflater = LayoutInflater.from((Context) activity);
        train = trainList;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d(TAG, "getView: view");
        View layoutItem;
        TrainInfo tr = train.get(position);
        Boolean incident= tr.getIncidents();
       /* if(view == null){
            if(incident){
                layoutItem= inflater.inflate(R.layout.bulle_horaire_pb_layout, parent, false);
            }else {
                layoutItem= inflater.inflate(R.layout.bulle_horaire_layout, parent, false);
            }
        }else {
            layoutItem=view;
        }
           */
        if(view == null) {
            layoutItem = inflater.inflate(R.layout.bulle_horaire_layout, parent, false);
        }else{
            layoutItem=view;
        }

        TextView heureDepart = layoutItem.findViewById(R.id.heureDepart);
        TextView heureArrive = layoutItem.findViewById(R.id.heureArrivee);
        TextView gareDepart = layoutItem.findViewById(R.id.gareDepart);
        TextView gareArrive = layoutItem.findViewById(R.id.gareArrivé);
        TextView dureeTrajet =layoutItem.findViewById(R.id.dureeTrajet);
        ImageView imageTrain = layoutItem.findViewById(R.id.imgTrain);
       /* if(incident){
            TextView nouvelleHeureDepart = layoutItem.findViewById(R.id.nouvelleHeureDepart);
            TextView nouvelleheureArrive = layoutItem.findViewById(R.id.nouvelleHeureArrivee);
            TextView texteIncident=  layoutItem.findViewById(R.id.textRetard);

            if(tr.getNouvelleHoraireArrivee() != null){
                nouvelleheureArrive.setText(tr.getNouvelleHoraireArrivee());
            }
            if(tr.getNouvelleHoraireDepart() != null){
                nouvelleHeureDepart.setText(tr.getNouvelleHoraireDepart());
            }
            texteIncident.setText(tr.getDescIncidents());
        }*/

        heureDepart.setText(tr.getHoraireDepart());
        heureArrive.setText(tr.getHoraireArrivee());
        gareDepart.setText(tr.getGareDepart());
        gareArrive.setText(tr.getGareArrivee());
        dureeTrajet.setText(tr.getDureeTrajet());
        imageTrain.setImageResource(tr.getImage());


        return layoutItem;
    }

}
