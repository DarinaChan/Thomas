package edu.thomas.ui.train;


import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import edu.thomas.CallbackActivity;
import edu.thomas.R;
import edu.thomas.model.train.TrainList;
import edu.thomas.users.Train;

public class bulleHoraireAdapter extends BaseAdapter  implements bulleHoraireListener{
    private final String TAG = "THOMAS "+getClass().getSimpleName();

    private final CallbackActivity activity;
    private final LayoutInflater inflater;
    List<Train> train;

    public bulleHoraireAdapter(CallbackActivity Callback){
        this.activity = Callback;
        inflater = LayoutInflater.from((Context) activity);
        train = TrainList.getInstance().getTrainInfoList();
    }


    @Override
    public int getCount() {
        return train.size();
    }

    @Override
    public Object getItem(int position) {
        return train.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d(TAG, "getView: view");
        View layoutItem;
        Train tr = train.get(position);
        layoutItem= inflater.inflate(R.layout.bulle_horaire_layout, parent, false);


        layoutItem.setOnClickListener(v -> onClickNom(tr, (TextView) layoutItem.findViewById(R.id.heureDepart)));
        TextView heureDepart = layoutItem.findViewById(R.id.heureDepart);
        TextView heureArrive = layoutItem.findViewById(R.id.heureArrivee);
        TextView gareDepart = layoutItem.findViewById(R.id.gareDepart);
        TextView gareArrive = layoutItem.findViewById(R.id.gareArrivé);
        TextView dureeTrajet =layoutItem.findViewById(R.id.dureeTrajet);
        ImageView imageTrain = layoutItem.findViewById(R.id.imgTrain);
        Calendar calendarDeparture = Calendar.getInstance();
        calendarDeparture.setTime(tr.getDepartureAt());
        String depart = String.format(Locale.FRANCE, "%02d:%02d",
                calendarDeparture.get(Calendar.HOUR_OF_DAY),
                calendarDeparture.get(Calendar.MINUTE));

        Calendar calendarArrival = Calendar.getInstance();
        calendarArrival.setTime(tr.getArrivalAt());
        String arrive = String.format(Locale.FRANCE, "%02d:%02d",
                calendarArrival.get(Calendar.HOUR_OF_DAY),
                calendarArrival.get(Calendar.MINUTE));

        Calendar calendarDuree = Calendar.getInstance();
        calendarDuree.setTime(tr.getTravelTime());
        String dureeTempsTrajet = String.format(Locale.FRANCE, "%02d:%02d",
                calendarArrival.get(Calendar.HOUR_OF_DAY),
                calendarArrival.get(Calendar.MINUTE));
        heureDepart.setText(depart);
        heureArrive.setText(arrive);
        gareDepart.setText(tr.getDepartureWhere());
        gareArrive.setText(tr.getArrivalWhere());
        dureeTrajet.setText(dureeTempsTrajet);
        imageTrain.setImageResource(tr.getImage());


        return layoutItem;
    }

    @Override
    public void onClickNom(Train item, TextView display) {
        TrainList.getInstance().addTrain(item);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Train ajouté");
        alertDialog.setMessage("Le train "+item.getDepartureWhere()+"-"+item.getArrivalWhere()+" a été ajouté à votre liste de train");
        alertDialog.show();

    }

    @Override
    public Context getContext() {
        return inflater.getContext();
    }
}




