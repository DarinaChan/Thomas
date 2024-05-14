package edu.thomas.model.train;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrainInfoList {
    private static volatile TrainInfoList instance = null;
    private static List<TrainInfo> trainInfoList;

    private TrainInfoList() {
        trainInfoList = new ArrayList<>();
        trainInfoList.add(new TrainInfo("11H41", "15h10", "Antibes", "Monaco", "30min", "TER"));
        trainInfoList.add(new TrainInfo("11H41", "15h10", "Monaco", "Antibes", "30min", "TER", true,
                "Train en retard de 20 min ", "12h00", "16h00"));
    }

    public static TrainInfoList getInstance() {
        if (instance == null) {
            synchronized (TrainInfoList.class) {
                if (instance == null) {
                    instance = new TrainInfoList();
                }
            }
        }
        return instance;
    }

    public  List<TrainInfo> getTrainInfoList() {
        return Collections.unmodifiableList(trainInfoList);
    }

    public void addTrainInfo(TrainInfo trainInfo) {
        trainInfoList.add(trainInfo);
    }
}
