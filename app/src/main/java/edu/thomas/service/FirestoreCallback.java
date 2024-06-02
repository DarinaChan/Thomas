package edu.thomas.service;

import java.util.List;

import edu.thomas.users.User;

public interface FirestoreCallback {
    void onUserCallback(List<User> userList);
}
