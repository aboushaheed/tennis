package fr.tennis.kata;


import fr.tennis.kata.model.Player;

import fr.tennis.kata.model.enums.GameSteps;
import fr.tennis.kata.service.GameService;
import fr.tennis.kata.utils.GameScores;
import fr.tennis.kata.utils.NowPlayers;
import fr.tennis.kata.model.enums.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = TennisGameBootApplication.class)
public class GameServiceTest {

    @Autowired
    GameService gameService;

    @Before
    public void initGame() {
        gameService.initGame();
    }

    // story 1
    @Test
    public void testWinPoints() {
        //given

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);

        //when annabelle wins 3 points
        gameService.winSomePointTo(annabelle,3);
        //when charlotte wins 2 points
        gameService.winSomePointTo(charlotte,2);
        //then
        Assert.assertEquals(40, annabelle.getScore());
        Assert.assertEquals(30, charlotte.getScore());
    }

    @Test
    public void testAnnabelleWins40AndScoreIsBackTo0() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        //when  annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then score is intialized
        Assert.assertEquals(0, annabelle.getScore());
    }

    @Test
    public void testCharlotteWinsTheGame() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);

        //when annabelle has 2 points
        gameService.winSomePointTo(annabelle,2);

        //when charlotte has 3 points
        gameService.winSomePointTo(charlotte,4);

        //then charlotte wins the game
        Assert.assertEquals(0, charlotte.getScore());
        Assert.assertTrue(charlotte.isGameWinner());
    }

    // story 2
    @Test
    public void testDouceActivationAndAdvantageWins() {
        //given
        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);
        //when annabelle has 40 ( 3 points )
        gameService.winSomePointTo(annabelle,3);
        // when chalotte has 40 ( 3 points )
        gameService.winSomePointTo(charlotte,3); // Douce
        // give advantage to charlotte
        gameService.winPoint(Person.CHARLOTTE.getPersonName());
        //then
        Assert.assertEquals(40, annabelle.getScore());
        Assert.assertEquals(GameSteps.ADVANTAGE, charlotte.getScoreDeuceRule());

        // annabelle wins a point
        gameService.winPoint(Person.ANNABELLE.getPersonName());
        // the they are douce
        Assert.assertEquals(GameSteps.DOUCE, annabelle.getScoreDeuceRule());
        Assert.assertEquals(GameSteps.DOUCE, charlotte.getScoreDeuceRule());

        // give other advantage to annabelle
        gameService.winPoint(Person.ANNABELLE.getPersonName());
        Assert.assertEquals(GameSteps.ADVANTAGE, annabelle.getScoreDeuceRule());

        // annabelle wins her last points and wins the game
        gameService.winPoint(Person.ANNABELLE.getPersonName());
        Assert.assertEquals(0, annabelle.getScore());
        Assert.assertEquals(0, charlotte.getScore());
        Assert.assertTrue(annabelle.isGameWinner());
    }

    //  story 2-1

    @Test
    public void testGameScores() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        //when
        // annabelle win points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(1, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());
    }

    @Test
    public void testMatchWinner() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);
        //when annabelle wins 4  points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the 1st game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(1, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

        //when annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the 2nd game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(2, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

        //when annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the 3td game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(3, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

        //when annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the 4th game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(4, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());


        //when annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the 5th game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(5, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(0, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

        ////when charlotte wins 4 points
        gameService.winSomePointTo(charlotte,4);
        //then she wins the 6th game
        Assert.assertTrue(charlotte.isGameWinner());
        Assert.assertEquals(5, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(1, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

         //when annabelle wins 4 points
        gameService.winSomePointTo(annabelle,4);
        //then she wins the last game
        Assert.assertTrue(annabelle.isGameWinner());
        Assert.assertEquals(6, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(1, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());
        Assert.assertTrue(annabelle.isMatchWinner());

        Assert.assertFalse(charlotte.isMatchWinner());

    }

    // story 2-2
    @Test
    public void testTieBreakAndGameScores() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);

        // init for tieBreak
        GameScores.getGameScores().put(Person.ANNABELLE.getPersonName(), 6);
        GameScores.getGameScores().put(Person.CHARLOTTE.getPersonName(), 6);

        gameService.winSomePointTo (annabelle, 4) ; // give 4 points to annabelle
        Assert.assertFalse(annabelle.isMatchWinner());

        gameService.winSomePointTo (annabelle, 3); // annabelle has 7 points now

        Assert.assertTrue(annabelle.isMatchWinner());
        Assert.assertFalse(charlotte.isMatchWinner());
        Assert.assertEquals(7, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
    }


    @Test
    public void testTieBreakActivation() {

        Player annabelle = NowPlayers.getCurrentPlayers().get(0);
        Player charlotte = NowPlayers.getCurrentPlayers().get(1);

        GameScores.getGameScores().put(Person.ANNABELLE.getPersonName(), 6);
        GameScores.getGameScores().put(Person.CHARLOTTE.getPersonName(), 6);

        gameService.winSomePointTo(charlotte,6); // charlotte win 6 points
        gameService.winSomePointTo(annabelle,7);
        Assert.assertFalse(annabelle.isMatchWinner());
        Assert.assertFalse(charlotte.isMatchWinner());
        gameService.winSomePointTo(annabelle,1); // annabelle has now 8 point

        Assert.assertTrue(annabelle.isMatchWinner());

        Assert.assertEquals(7, GameScores.getGameScores().get(Person.ANNABELLE.getPersonName()).intValue());
        Assert.assertEquals(6, GameScores.getGameScores().get(Person.CHARLOTTE.getPersonName()).intValue());

    }


}
