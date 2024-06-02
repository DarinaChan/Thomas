package edu.thomas.model.train;

import android.content.Context;
import android.content.res.Resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.thomas.R;


public class TrainStationMap {
    private static TrainStationMap instance;
    private HashMap<String, TrainStation> map = new HashMap<>();
    private static final String FILE_NAME = "gare_paca.json";

    private TrainStationMap(Context context) {
        loadStations(context);
    }

    public static synchronized TrainStationMap getInstance(Context context) {
        if (instance == null) {
            instance = new TrainStationMap(context);
        }
        return instance;
    }

    private void loadStations(Context context) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
 /*           InputStream inputStream = context.getResources().openRawResource(
                    context.getResources().getIdentifier(FILE_NAME, "raw", context.getPackageName()));*/
            InputStream inputStream = context.getResources().openRawResource(R.raw.gare_paca);

            List<TrainStation> stations = objectMapper.readValue(inputStream, new TypeReference<List<TrainStation>>() {});
            for (TrainStation station : stations) {
                map.put(station.getNom(), station);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TrainStation getTrainStation(String nom) {
        return instance != null ? instance.map.get(nom) : null;
    }

    public static Set<String> getStationName(){
        return instance.map.keySet();
    }
}
