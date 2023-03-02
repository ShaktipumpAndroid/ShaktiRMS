package com.shaktipumps.shakti_rms.aryabata.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class User_auth_response {

    List<User_auth> users;

    public User_auth_response() {
        users = new ArrayList<>();
    }

    public static User_auth[] parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, User_auth[].class);
    }

    public static User_auth parseJSONForSingle(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, User_auth.class);
    }

}
