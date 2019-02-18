package  fr.tennis.kata.service;


import fr.tennis.kata.model.Player;

import fr.tennis.kata.model.enums.GameSteps;
import fr.tennis.kata.model.enums.Person;
import fr.tennis.kata.utils.GameScores;
import fr.tennis.kata.utils.NowPlayers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * service
 * @author souidi
 */
@Service
public class GameService {


        Logger logger = LoggerFactory.getLogger(GameService.class);



    /**
     *
     * @return List of names
     */
    public List<String> getPlayerNames() {
        return NowPlayers.getCurrentPlayers().stream()
                .map(player -> player.getPlayerName())
                .collect(Collectors.toList());
    }


    /**
     *
     * @return get plyer by name
     */
    public Player getPlyerByName( final String playerName) {

        Optional<Player> result =  NowPlayers.getCurrentPlayers().stream()
                .filter((p) ->  p.getPlayerName().equals(playerName))
                .collect(toSingleton());

        if(result.isPresent()) return  result.get();
        else return null;
    }
    public static <T> Collector<T, ?, Optional<T>> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> list.size() == 1 ? Optional.of(list.get(0)) : Optional.empty()
        );
    }
    /**
     *
     * @return name
     */
    public String getWinner() {
        for (Player p : NowPlayers.getCurrentPlayers()) {
            if (p.isGameWinner()) {
                logger.info("Winner is {}",p.getPlayerName());
                return p.getPlayerName();
            }
        }
        return null;
    }

    /**
     * init the game
     */
    public void initGame() {
        NowPlayers.getCurrentPlayers().forEach(p -> {
                    p.setGameWinner(false);
                    p.setScore(0);
                    p.setScoreDeuceRule(null);
                    p.setMatchWinner(false);
                    p.setTieBreakScore(0);
                }
        );

        GameScores.getGameScores().replaceAll((k, v) -> v = 0);
    }

    /**
     *
     * @return
     */
    public Map<String, Integer> getMatchScore() {
        return GameScores.getGameScores();
    }

    public void winPoint(final String player) {
         Optional<Player> winPointPlayerOpt = NowPlayers.getCurrentPlayers().stream()
                .filter(p -> p.getPlayerName().equals(player))
                .findFirst();
        Optional<Player> opposedPlayerOpt = NowPlayers.getCurrentPlayers().stream()
                .filter(p -> !p.getPlayerName().equals(player))
                .findFirst();


        if (winPointPlayerOpt.isPresent() && opposedPlayerOpt.isPresent()) {
            updateAllScores(winPointPlayerOpt.get(), opposedPlayerOpt.get());
        }

    }

    /**
     *
     * @param winner
     * @param loser
     */
    private void checkWinner(final Player winner, final Player loser) {
        for (Player p : NowPlayers.getCurrentPlayers()) {
            winner.setScore(0);
            if (p.getPlayerName().equals(winner.getPlayerName())) {
                p.setGameWinner(true); // annabelle
                // todo: set calcule
                setScoreCalculator(winner, loser);
                break;
            }

        }
    }


    public void winSomePointTo(final Player player, int point) {


        for (int i=1; i<=point; i++) {
            winPoint(player.getPlayerName());
            logger.debug("the player  : {}", player, "Win {} points", i);
        }
        // IntStream.range(1,point).forEach(i -> winPoint(player));

    }

    /**
     *
     * @param playerName
     * @return int score
     */
    public int getScore(final String playerName) {
        return NowPlayers.getCurrentPlayers().stream()
                .filter(player -> player.getPlayerName().equals(playerName))
                .mapToInt(player -> player.getScore()).sum();

    }
    /**
     *
     * @param winner
     * @param loser
     */
    private void updateAllScores(final Player winner, final Player loser) {
        if (winner.getTieBreakScore() == 0
                && loser.getTieBreakScore() == 0
                && !isTieBreak(winner, loser)) {

            if (winner.getScore() == 40 && !isDeuceActivated()) {
                checkWinner(winner, loser);
            } else {
                scoreUpdater(winner, loser);
            }

        } else {
            tieBreakScoreUpdater(winner, loser);
        }
    }

    /**
     *
     * @param winnerPointPlayer
     * @param opposedPlayer
     */
    private void scoreUpdater(final Player winnerPointPlayer, final Player opposedPlayer) {
        if (!isDeuceActivated()) {
            scoreMatchUpdater(winnerPointPlayer, opposedPlayer);
        } else {
            douceScoreUpdater(winnerPointPlayer, opposedPlayer);
        }
    }

    /**
     *
     * @param winner
     * @param loser
     */
    private void scoreMatchUpdater(final Player winner, final Player loser) {
        int scoring = winner.getScore();
        if (scoring == 30) {
            scoring += 10;
        } else {
            scoring += 15;
        }

        for (Player p : NowPlayers.getCurrentPlayers()) {
            if (scoring == 40 && loser.getScore() == 40) {
                p.setScoreDeuceRule(GameSteps.DOUCE);
            }
            if (p.getPlayerName().equals(winner.getPlayerName())) {
                p.setScore(scoring);

            }
        }
    }

    /**
     *
     * @return boolean
     */
    private boolean isDeuceActivated() {
        boolean isDeuceActivated = false;
        for (Player p : NowPlayers.getCurrentPlayers()) {
            if (!StringUtils.isEmpty(p.getScoreDeuceRule())) {
                isDeuceActivated = true;
                logger.info("Deuce Rule is Activated" );
            }
        }
        return isDeuceActivated;
    }

    /**
     *
     * @param winner
     * @param loser
     */
    private void setScoreCalculator(final Player winner, final Player loser) {

        int winnerScore = GameScores.getGameScores().get(winner.getPlayerName());
        int loserScore = GameScores.getGameScores().get(loser.getPlayerName());

        int setScore = winnerScore + 1;
        // todo : mise a jour des scores
        GameScores.getGameScores().put(winner.getPlayerName(),setScore);
        if ((setScore == 6 && loserScore < 5) || setScore == 7) {
            for (Player p : NowPlayers.getCurrentPlayers()) {
                if (p.getPlayerName().equals(winner.getPlayerName())) {
                    p.setMatchWinner(true);
                    break;
                }
            }
        }
    }

    /**
     *
     * @param winner
     * @param loser
     * @return
     */
    private boolean isTieBreak(final Player winner, final Player loser) {
        if (GameScores.getGameScores().get(winner.getPlayerName()) == 6
                && GameScores.getGameScores().get(loser.getPlayerName()) == 6) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param winner
     * @param loser
     */
    private void douceScoreUpdater(final Player winner, final Player loser) {

        if (GameSteps.DOUCE.equals(winner.getScoreDeuceRule())) { // we have a douce here
            winner.setScoreDeuceRule(GameSteps.ADVANTAGE);
            loser.setScoreDeuceRule(null);
        } else if (GameSteps.ADVANTAGE.equals(winner.getScoreDeuceRule())) {
            winner.setGameWinner(true);
            // set to 0 all players scores
            for (Player p : NowPlayers.getCurrentPlayers()) { p.setScore(0); }
            // and claculate the set score
            setScoreCalculator(winner, loser);

        } else {
            winner.setScoreDeuceRule(GameSteps.DOUCE);
            loser.setScoreDeuceRule(GameSteps.DOUCE);
            logger.info("Game Step : {}", GameSteps.DOUCE );
            // debugging
        }
    }

    /**
     *
     * @param winner
     * @param loser
     */
    private void tieBreakScoreUpdater(final Player winner, final Player loser) {
        int score = winner.getTieBreakScore() + 1;
        int scoreDiff = score - loser.getTieBreakScore();

        for (Player p : NowPlayers.getCurrentPlayers()) {
            if (p.getPlayerName().equals(winner.getPlayerName())) {
                p.setTieBreakScore(score);
                if (score >= 7 && scoreDiff >= 2) {
                    GameScores.getGameScores().put(winner.getPlayerName(),
                            GameScores.getGameScores().get(winner.getPlayerName()).intValue() + 1);
                    p.setMatchWinner(true);
                }
                break;
            }
        }

    }

    /**
     * play a simple game
     */
    public void playGame() {
        initGame();
        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);
        int pointsAnnabelle = (int)(Math.random() * ((4 - 1) + 1)) + 1;
        int pointsCharlotte = (int)(Math.random() * ((4 - 1) + 1)) + 1;
         winSomePointTo(annabelle,pointsAnnabelle);
         winSomePointTo(charlotte,pointsCharlotte);


    }

    /**
     * play a full match
     */
    public void playMatch() {

        playGame();
        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);
        GameScores.getGameScores().put(Person.ANNABELLE.getPersonName(), 6);
        GameScores.getGameScores().put(Person.CHARLOTTE.getPersonName(), 6);

        int pointsAnnabelle = (int)(Math.random() * ((4 - 1) + 1)) + 1;
        int pointsCharlotte = (int)(Math.random() * ((4 - 1) + 1)) + 1;
        winSomePointTo(annabelle,pointsAnnabelle);
        winSomePointTo(charlotte,pointsCharlotte);


    }
}
