package edu.thomas.internetRequest;

import android.app.ProgressDialog;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import edu.thomas.model.train.SncfRequest;
import edu.thomas.users.Train;

/**
 * construit un Objet T depuis un fichier json dont l'adress URL est passé en paramètre
 * Tache asynchrone
 * @author Frédéric RALLO - March 2023
 */
public class HttpAsyncGet<T>{
    private static final String TAG = "thomas " + HttpAsyncGet.class.getSimpleName();    //Pour affichage en cas d'erreur
    private final Class<T> clazz;
    private List<T> itemList;
    private final HttpHandler webService;

    public HttpAsyncGet(SncfRequest request, Class<T> clazz, PostExecuteActivity postExecuteActivity, ProgressDialog progressDialog) {
        super();
        webService = new HttpHandler();
        this.clazz = clazz;
        if(progressDialog != null) onPreExecute( progressDialog );
        Runnable runnable = ()->{
            doInBackGround(request);
            postExecuteActivity.runOnUiThread( ()-> {
                if(progressDialog != null) progressDialog.dismiss();
                postExecuteActivity.onPostExecute(getItemResult());
            } );
        };
        Executors.newSingleThreadExecutor().execute( runnable );
    }

    public void doInBackGround(SncfRequest request){
        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(request);
        itemList = (List<T>) extractTrainJourneys(jsonStr);
    }

    public List<T> getItemResult() {
        return itemList;
    }

    public void onPreExecute( ProgressDialog progressDialog ) {
        progressDialog.setMessage("Connexion en cours...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    // Méthode pour extraire les informations spécifiques des trajets en train
    private List<Train> extractTrainJourneys(String jsonStr) {
        ObjectMapper mapper = new ObjectMapper();
        List<Train> trainJourneys = new ArrayList<>();

        try {
            JsonNode root = mapper.readTree(jsonStr);
            JsonNode journeys = root.path("journeys");

            for (JsonNode journey : journeys) {
                Train trainJourney = new Train();

                trainJourney.setzzz(journey.path("departure_date_time").asText());
                trainJourney.setzz((journey.path("arrival_date_time").asText()));

                JsonNode sections = journey.path("sections");
                for (JsonNode section : sections) {
                    if (section.path("mode").asText().equals("train")) {
                        trainJourney.setDepartureWhere(section.path("from").path("name").asText());
                        trainJourney.setArrivalWhere(section.path("to").path("name").asText());

                        JsonNode displayInformations = section.path("display_informations");
                        trainJourney.setTrainId(displayInformations.path("trip_short_name").asText());
                        trainJourney.setTrainType(displayInformations.path("commercial_mode").asText());
                        trainJourney.setTrainName(displayInformations.path("headsign").asText());

                        break; // Exit the loop since we only need the train section
                    }
                }
                trainJourneys.add(trainJourney);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return trainJourneys;
    }

    static class HttpHandler { //innerClass

        public String makeServiceCall(SncfRequest request ) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(request.getURL()).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization",request.getToken());
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {

                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG,line);
                }
            }
            catch (IOException e) {
                Log.d(TAG, "convertStreamToString: ici");
                e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}
