package edu.thomas.model.train;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.thomas.users.Train;

public class TrainList {
    private static volatile TrainList instance = null;
    private static List<Train> trainList;

    private TrainList() {
        trainList = new ArrayList<>();
    }

    public static TrainList getInstance() {
        if (instance == null) {
            synchronized (TrainList.class) {
                if (instance == null) {
                    instance = new TrainList();
                }
            }
        }
        return instance;
    }

    public  List<Train> getTrainInfoList() {
        return Collections.unmodifiableList(trainList);
    }

    public void addTrain(Train trainInfo) {
        trainList.add(trainInfo);
    }

    public void addAllTrain(List<Train> trainInfoList) {
        trainList.addAll(trainInfoList);
    }
}
