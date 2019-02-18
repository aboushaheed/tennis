package  fr.tennis.kata.utils;

import fr.tennis.kata.model.enums.Person;

import java.util.HashMap;
import java.util.Map;

public class GameScores {

    private static HashMap gameScores;

    public static Map<String, Integer> getGameScores() {
        if (gameScores == null) {
            gameScores = new HashMap<String, Integer>() {
                {
                    put(Person.CHARLOTTE.getPersonName(), 0);
                    put(Person.ANNABELLE.getPersonName(), 0);
                }
            };

        }
        return gameScores;
    }
}
