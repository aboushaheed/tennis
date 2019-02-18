package  fr.tennis.kata.controller;

 import com.fasterxml.jackson.core.JsonProcessingException;
 import com.fasterxml.jackson.databind.ObjectMapper;
 import fr.tennis.kata.model.Player;
 import fr.tennis.kata.service.GameService;
 import io.swagger.annotations.*;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @author souidi
 */
@RestController
@RequestMapping(path="/tennis", produces="application/json", method=RequestMethod.GET)
@Api(value="Tennis Game", description="implementation of a tennis game ")
public class TennisGameController {

    @Autowired
    GameService gameService;

   static final ObjectMapper objectMapper = new ObjectMapper();

    @ApiOperation(value="Make Player win a point 15 - 30 ...",
            nickname="winPoint",
            notes="this service alows a plyer to make a point")

    @RequestMapping(path = "/win/{player}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "player", value = "player you want to add point", required = true, dataType = "string", paramType = "path", allowableValues="Annabelle, Charlotte"),
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity winPoint(@PathVariable String player) {
        gameService.winPoint(player);
        return new ResponseEntity<Player>(gameService.getPlyerByName(player), HttpStatus.OK);

    }


    @ApiOperation(value="Play Random",
            nickname="play",
            notes="this service alows a plyer to make a point")

    @RequestMapping(path = "/play/{type}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "type of the game", required = true, dataType = "string", paramType = "path", allowableValues="Game,Match"),


    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity play(@PathVariable String type) throws JsonProcessingException {

        if(type.equals("Game")) {
            gameService.playGame();
        }
        if(type.equals("Match")) {
            gameService.playMatch();
        }
        if(gameService.getWinner() == null) {
            return new ResponseEntity<String>(objectMapper.writeValueAsString("Game in progress !! please make points for Annabelle or Charlotte"), HttpStatus.IM_USED);
        }else{
            return new ResponseEntity<String>(objectMapper.writeValueAsString(gameService.getWinner()), HttpStatus.OK);
        }


    }

    @ApiOperation(value="Get score of given player",
            nickname="getScore",
            notes="this service show the score of the passed player")



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(path = "/game-score/{player}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "player", value = "player you want his score", required = true, dataType = "string", paramType = "path", allowableValues="Annabelle, Charlotte"),
     })
    public ResponseEntity getScore(@PathVariable String player) {

        return new ResponseEntity<Integer>(gameService.getScore(player), HttpStatus.OK);
     }



    @ApiOperation(value="Get the winner",
            nickname="getWinner",
            notes="this service show the winner, or show game in progress if no winner ")



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/winner")
    public ResponseEntity getWinner() throws JsonProcessingException {

        if(gameService.getWinner() == null) {
            return new ResponseEntity<String>(objectMapper.writeValueAsString("Game in progress !! please make points for Annabelle or Charlotte"), HttpStatus.IM_USED);
        }else{
            return new ResponseEntity<String>(objectMapper.writeValueAsString(gameService.getWinner()), HttpStatus.OK);
        }
    }

    @ApiOperation(value="Get All players",
            nickname="getPlayers",
            notes="this service show players of the game")



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/players")
    public List<String> getPlayers() {
        return gameService.getPlayerNames();
    }

    @ApiOperation(value="Init game",
            nickname="initGame",
            notes="this service show init the game")



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/init")
    public ResponseEntity initGame() throws JsonProcessingException {
        gameService.initGame();
        return new ResponseEntity<String>(objectMapper.writeValueAsString("Game initialized !"), HttpStatus.OK);


    }

    @ApiOperation(value="Match score",
            nickname="matchScore",
            notes="this service show the match score af all games")



    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @RequestMapping(value = "/match-score")
    public ResponseEntity matchScore() throws JsonProcessingException {
        return new ResponseEntity<String>(objectMapper.writeValueAsString(gameService.getMatchScore()), HttpStatus.OK);

    }
}


