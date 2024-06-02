package edu.thomas.users;

import java.util.List;

public interface OnGetTrainsListener {
    void onSuccess(List<Train> trains);
    void onFailure(Exception e);
}

