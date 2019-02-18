package  fr.tennis.kata.model;

import fr.tennis.kata.model.enums.GameSteps;

/**
 *
 * @author souidi
 */
public class Player  {

    private String playerName;
    private int score;
    private boolean isGameWinner;
    private boolean isMatchWinner;
    private GameSteps scoreDeuceRule;
    private int tieBreakScore;

    private Player(final Builder builder) {
        setPlayerName(builder.playerName);
        setScore(builder.score);
        setGameWinner(builder.isGameWinner);
        setMatchWinner(builder.isMatchWinner);
        setScoreDeuceRule(builder.scoreDeuceRule);
        setTieBreakScore(builder.tieBreakScore);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(final Player copy) {
        Builder builder = new Builder();
        builder.playerName = copy.getPlayerName();
        builder.score = copy.getScore();
        builder.isGameWinner = copy.isGameWinner();
        builder.isMatchWinner = copy.isMatchWinner();
        builder.scoreDeuceRule = copy.getScoreDeuceRule();
        builder.tieBreakScore = copy.getTieBreakScore();
        return builder;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isGameWinner() {
        return isGameWinner;
    }

    public void setGameWinner(boolean gameWinner) {
        isGameWinner = gameWinner;
    }

    public GameSteps getScoreDeuceRule() {
        return scoreDeuceRule;
    }

    public void setScoreDeuceRule(GameSteps scoreDeuceRule) {
        this.scoreDeuceRule = scoreDeuceRule;
    }

    public boolean isMatchWinner() {
        return isMatchWinner;
    }

    public void setMatchWinner(boolean matchWinner) {
        isMatchWinner = matchWinner;
    }

    public int getTieBreakScore() {
        return tieBreakScore;
    }

    public void setTieBreakScore(int tieBreakScore) {
        this.tieBreakScore = tieBreakScore;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                ", isGameWinner=" + isGameWinner +
                ", isMatchWinner=" + isMatchWinner +
                ", scoreDeuceRule='" + scoreDeuceRule + '\'' +
                ", tieBreakScore=" + tieBreakScore +
                '}';
    }


    /**
     * {@code Player} builder static inner class.
     */
    public static final class Builder {
        private String playerName;
        private int score;
        private boolean isGameWinner;
        private boolean isMatchWinner;
        private GameSteps scoreDeuceRule;
        private int tieBreakScore;

        private Builder() {
        }

        /**
         * Sets the {@code playerName} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code playerName} to set
         * @return a reference to this Builder
         */
        public Builder playerName(final String val) {
            playerName = val;
            return this;
        }

        /**
         * Sets the {@code score} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code score} to set
         * @return a reference to this Builder
         */
        public Builder score(final int val) {
            score = val;
            return this;
        }

        /**
         * Sets the {@code isGameWinner} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code isGameWinner} to set
         * @return a reference to this Builder
         */
        public Builder isGameWinner(final boolean val) {
            isGameWinner = val;
            return this;
        }

        /**
         * Sets the {@code isMatchWinner} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code isMatchWinner} to set
         * @return a reference to this Builder
         */
        public Builder isMatchWinner(final boolean val) {
            isMatchWinner = val;
            return this;
        }

        /**
         * Sets the {@code scoreDeuceRule} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code scoreDeuceRule} to set
         * @return a reference to this Builder
         */
        public Builder scoreDeuceRule(final GameSteps val) {
            scoreDeuceRule = val;
            return this;
        }

        /**
         * Sets the {@code tieBreakScore} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code tieBreakScore} to set
         * @return a reference to this Builder
         */
        public Builder tieBreakScore(final int val) {
            tieBreakScore = val;
            return this;
        }

        /**
         * Returns a {@code Player} built from the parameters previously set.
         *
         * @return a {@code Player} built with parameters of this {@code Player.Builder}
         */
        public Player build() {
            return new Player(this);
        }
    }
}
