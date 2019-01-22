package br.com.garrav.projetogarrav.model;

import java.util.List;

public class Challenge {

    private long id;
    private String name;

    //Unique List
    private static List<Challenge> uniqueListChallenge;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Unique Get & Set
    public static List<Challenge> getUniqueListChallenge() {
        return uniqueListChallenge;
    }

    public static void setUniqueListChallenge(List<Challenge> uniqueListChallenge) {
        Challenge.uniqueListChallenge = uniqueListChallenge;
    }
}
