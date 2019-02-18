package fr.tennis.kata.model.enums;

/**
 * Created by souid on févr., 2019
 */
public enum GameSteps  {
    DOUCE("DOUCE"), ADVANTAGE("ADVANTAGE") ;

    private String step ;

    private GameSteps(String step) {
        this.step = step ;
    }

    public String getStep() {
        return  this.step ;
    }

}
