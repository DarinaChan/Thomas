package edu.thomas.ui.train;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.thomas.CallbackActivity;
import edu.thomas.R;
import edu.thomas.databinding.FragmentTrainBinding;
import edu.thomas.internetRequest.HttpAsyncGet;
import edu.thomas.internetRequest.PostExecuteActivity;
import edu.thomas.model.train.SncfRequest;
import edu.thomas.model.train.TrainList;
import edu.thomas.model.train.TrainStationMap;
import edu.thomas.users.Train;


public class FragmentSearchTrain extends Fragment {

    private FragmentTrainBinding binding;
    private CallbackActivity callbackActivity;
    AutoCompleteTextView editTextDepart;
    AutoCompleteTextView editTextArrive;
    private final String TAG = "THOMAS "+getClass().getSimpleName();



    public FragmentSearchTrain(){};
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
        View root = inflater.inflate(R.layout.fragment_search_train,container,false);
        ArrayList<Train> list = new ArrayList();
        list.add(new Train(new Date(1663981200000L), new Date(1663983300000L), "Paris", "Marseille", "TGV", "123"));
        list.add(new Train(new Date(1671895800000L), new Date(1671897000000L), "Nice", "Aubagne", "TER", "456"));


        SearchTrainAdapter adapter = new SearchTrainAdapter(callbackActivity,list);
        ((ListView)root.findViewById(R.id.listVue)).setAdapter(adapter);
        Button datePicker = root.findViewById(R.id.date_picker);
        Button timePicker = root.findViewById(R.id.time_picker);
        TimeManagement tm = new TimeManagement(getActivity()) {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Les mois commencent à 0 en Java, donc il faut ajouter 1
                month = month + 1;
                // Utiliser String.format pour s'assurer que chaque composant a deux chiffres
                String formattedDay = String.format("%02d", dayOfMonth);
                String formattedMonth = String.format("%02d", month);
                // Mettre à jour le TextView avec la date formatée
                datePicker.setText(formattedDay + "/" + formattedMonth + "/" + year);            }

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Utiliser String.format pour s'assurer que chaque composant a deux chiffres
                String formattedHour = String.format("%02d", hourOfDay);
                String formattedMinute = String.format("%02d", minute);
                // Mettre à jour le TextView avec l'heure formatée
                timePicker.setText(formattedHour + ":" + formattedMinute);

            }

            @Override
            public void onClick(View v) {
                if(v== datePicker) showDatePickerDialog();
                else showTimePickerDialog();
            }
        };
        datePicker.setOnClickListener(tm);
        timePicker.setOnClickListener(tm);

        ImageButton searchButton = root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SncfRequest detail = null;
                try {
                    detail = new SncfRequest(editTextArrive.getText().toString(),editTextDepart.getText().toString(),datePicker.getText().toString()+"-"+timePicker.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                HttpAsyncGet<Train> request = new HttpAsyncGet<>(detail, Train.class, new PostExecuteActivity() {
                    @Override
                    public void onPostExecute(List itemList) {
                        TrainList.getInstance().clearSearchTrainList();
                        TrainList.getInstance().addAllSearchTrain(itemList);
                    }

                    @Override
                    public void runOnUiThread(Runnable runable) {

                    }
                },new ProgressDialog((Context) callbackActivity));

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextDepart = view.findViewById(R.id.editTextDepart);
        editTextArrive  = view.findViewById(R.id.editTextArrivee);

        ArrayAdapter listGare = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line,new ArrayList<>(TrainStationMap.getStationName()));
        editTextArrive.setAdapter(listGare);
        editTextDepart.setAdapter(listGare);
    }

    /*

    @Override
    public void onClickNom(TrainInfo item, TextView display) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Personne");

        builder.setMessage("Vous avez clique sur : " + item.getGareArrivee());
        builder.setNeutralButton("OK",null);
        builder.show();
    }
*/


    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

//todo a rename  aussi verifié que c'est le bon
}