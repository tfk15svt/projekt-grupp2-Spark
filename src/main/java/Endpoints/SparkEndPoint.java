/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endpoints;

import Broker.BrokerFactory;
import DB.DbConn;
import Services.AddTeamToSportService;
import Services.GetAllLeagueFromSportService;
import Services.GetAllSeasonsFromLeagueService;
import Services.GetAllSportService;
import Services.Service;
import Services.ServiceRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import Domain.League;
import Domain.Sport;
import Services.AddLeagueToSportService;
import Services.AddMetaInfoToGameService;
import Services.AddResultToGameService;
import Services.AddRoundToSeasonService;
import Services.AddSeasonToLeagueService;
import Services.AddSportService;
import java.util.List;
import static spark.Spark.get;
import spark.servlet.SparkApplication;

/**
 *
 * @author Veiret
 */
public class SparkEndPoint implements SparkApplication {

    @Override
    public void init() {
        get("/hello", (req, res) -> "Hello World Sebastian Sebastian Sebastian !!!!");
        get("/allSports", (req, res) -> getSports());
        get("/allLeaguesFromSport/:id", (req, res) -> getAllLeaguesFromSportId(Long.parseLong(req.params(":id"))));
       // get("/getAllSeasonsFromLeague" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.queryParams("id"))));
        /**get("/AddTeam" , (req, res) -> addTeam(req.queryParams("teamName"), (Long.parseLong(req.queryParams("sportId")))));
        get("/AddLeagueToSport", (req, res) -> addLeagueToSport(Long.parseLong(req.queryParams("sportId")) , req.queryParams("leagueName")));
        get("/AddSpectatorsAndArena", (req, res) -> addSpectatorsAndArenaToGame(req.queryParams("gameId"), req.queryParams("arenaId"), req.queryParams("spectators")));
        get("/AddResultToGame", (req, res) -> addResultToGame(req.queryParams("homeScore"), req.queryParams("awayScore"), req.queryParams("gameId")));
        get("/AddRoundToSeason", (req, res) -> addRoundToSeason(req.queryParams("seasonId"), req.queryParams("numberOfRounds")));
        get("/AddSeasonToLeague", (req, res) -> addSeasonToLeague(req.queryParams("seasonYear"), req.queryParams("LeagueId")));*/
        
    }
    public String getSports() {
        Service service = new GetAllSportService();
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    public String getAllLeaguesFromSportId(Long id) {
        Service service = new GetAllLeagueFromSportService(id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    
    public String addSport(String name){
        Service service = new AddSportService(name);
        new ServiceRunner(service).execute();
        return JsonOutputformat.create(name);
    }
    public String getAllSeasonsFromLeague(Long id){
        Service service = new GetAllSeasonsFromLeagueService(id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    public String addTeam (String teamName, Long sportId){
        Service service = new AddTeamToSportService(teamName, sportId);
        new ServiceRunner(service).execute();
        return teamName + " created";
    }

    private String addLeagueToSport(Long sportId, String leagueName) {
        Service service = new AddLeagueToSportService(sportId, leagueName);
        new ServiceRunner(service).execute();
        return leagueName + " created";
    }

    private String addSpectatorsAndArenaToGame(String gameId, String arenaId, String spectators) {
        Long gameIdLong = Long.parseLong(gameId);
        Long arenaIdLong = Long.parseLong(arenaId);
        int spectatorsInt = Integer.parseInt(spectators);
        Service service = new AddMetaInfoToGameService(gameIdLong, arenaIdLong, spectatorsInt);
        new ServiceRunner(service).execute();
        return "Information added";
    }

    private String addRoundToSeason(String seasonId, String numberOfRounds) {
        Long seasonIdLong = Long.parseLong(seasonId);
        Integer numberOfRoundsInteger = Integer.parseInt(numberOfRounds);
        
        Service service = new AddRoundToSeasonService(seasonIdLong, numberOfRoundsInteger);
        new ServiceRunner(service).execute();
        return "Round added";
    }
    
    private String addSeasonToLeague(String seasonYear, String leagueId){
        int seasonYearInt = Integer.parseInt(seasonYear);
        Long leagueIdLong = Long.parseLong(leagueId);
        Service service = new AddSeasonToLeagueService(seasonYearInt, leagueIdLong);
        new ServiceRunner(service).execute();
        return "Season added";
    }

    private String addResultToGame(String homeScore, String awayScore, String gameId) {
        int homeScoreInt = Integer.parseInt(homeScore);
        int awayScoreInt = Integer.parseInt(awayScore);
        Long gameIdLong = Long.parseLong(gameId);
        Service service = new AddResultToGameService(homeScoreInt, awayScoreInt, gameIdLong);
        return "Result added";
    }
    
}