package  fr.tennis.kata.utils;


import fr.tennis.kata.model.Player;
import fr.tennis.kata.model.enums.Person;

import java.util.ArrayList;
import java.util.List;

public class NowPlayers {

    private static List<Player> currentPlayers = null;

    public static List<Player> getCurrentPlayers() {
        if (currentPlayers == null) {
            currentPlayers = new ArrayList<Player>() {
                {
                    add(Player.newBuilder()
                            .playerName(Person.ANNABELLE.getPersonName())
                            .score(0)
                            .isGameWinner(false)
                            .isGameWinner(false)
                            .scoreDeuceRule(null)
                            .tieBreakScore(0)
                            .build());
                    add(Player.newBuilder()
                            .playerName(Person.CHARLOTTE.getPersonName())
                            .score(0)
                            .isGameWinner(false)
                            .isGameWinner(false)
                            .scoreDeuceRule(null)
                            .tieBreakScore(0)
                            .build());
                }
            };
        }
        return currentPlayers;
    }
}
