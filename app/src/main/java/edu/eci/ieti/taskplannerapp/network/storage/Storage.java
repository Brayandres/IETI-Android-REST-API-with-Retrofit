package edu.eci.ieti.taskplannerapp.network.storage;

public interface Storage {

    void saveToken(String token);
    String getToken();
}