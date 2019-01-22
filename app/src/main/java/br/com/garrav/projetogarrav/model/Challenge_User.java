package br.com.garrav.projetogarrav.model;

import java.util.List;

public class Challenge_User {

    private long id_user;
    private long id_challenge;

    //Actual Check Challenge List
    private static List<Challenge_User> uniqueListCheckChallenge;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_challenge() {
        return id_challenge;
    }

    public void setId_challenge(long id_challenge) {
        this.id_challenge = id_challenge;
    }

    //Actual Check Challenge List
    public static List<Challenge_User> getUniqueListCheckChallenge() {
        return uniqueListCheckChallenge;
    }

    public static void setUniqueListCheckChallenge(List<Challenge_User> uniqueListCheckChallenge) {
        Challenge_User.uniqueListCheckChallenge = uniqueListCheckChallenge;
    }
}
