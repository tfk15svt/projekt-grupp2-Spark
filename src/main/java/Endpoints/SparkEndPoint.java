/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endpoints;

import Services.AddTeamToSportService;
import Services.GetAllLeagueFromSportService;
import Services.GetAllSeasonsFromLeagueService;
import Services.GetAllSportService;
import Services.Service;
import Services.ServiceRunner;
import Services.AddGameService;
import Services.AddLeagueToSportService;
import Services.AddMetaInfoToGameService;
import Services.AddPeriodResultsToGameService;
import Services.AddResultToGameService;
import Services.AddRoundToSeasonService;
import Services.AddSeasonToLeagueService;
import Services.AddSportService;
import Services.ConnectTeamToSeasonService;
import Services.GetAllGamesForAwayTeamService;
import Services.GetAllGamesForHomeTeamService;
import Services.GetAllGamesForOneTeamService;
import Services.GetAllGamesFromDateService;
import Services.GetAllGamesFromRoundService;
import Services.GetAllGamesFromSeasonService;
import Services.GetAllLossesForTeamService;
import Services.GetAllTiesForTeamService;
import Services.GetAllWinsForTeamService;
import Services.GetBiggestWinLoseForTwoTeamsService;
import Services.GetGameResultInfoService;
import Services.GetTeamsMatchHistoryService;
import Services.ListTeamsLongestStreaks;
import Services.SetHomeAndAwayTeamService;
import Services.ShowTableWithDynamicFiltersService;
import java.util.ArrayList;
import static spark.Spark.get;
import static spark.Spark.staticFiles;
import spark.servlet.SparkApplication;

/**
 *
 * @author Veiret
 */
public class SparkEndPoint implements SparkApplication {

    
    @Override
    public void init() {
        staticFiles.location("/public");
        get("/", (req, res) -> {
            res.redirect("start.html");
            return null;
        });
        get("/Hello", (req, res) -> "Hello World Sebastian Sebastian Sebastian !!!!");
        get("/Sports", (req, res) -> getSports());
        get("/LeaguesFromSport/:id", (req, res) -> getAllLeaguesFromSportId(Long.parseLong(req.params(":id"))));
        get("/SeasonsFromLeague/:id" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.params(":id"))));
        get("/AddTeam/:teamName/:sportId" , (req, res) -> addTeam(req.params(":teamName"), (Long.parseLong(req.params(":sportId")))));
        get("/AddLeague/:sportId/:leagueName", (req, res) -> addLeagueToSport(Long.parseLong(req.params(":sportId")) , req.params(":leagueName")));
        get("/AddSpectatorsAndArena/:gameId/:arenId/:spec", (req, res) -> addSpectatorsAndArenaToGame(req.params(":gameId"), req.params(":arenaId"), req.params(":spec")));
        get("/AddResult/:homeScore/:awayScore/:gameId", (req, res) -> addResultToGame(req.params(":homeScore"), req.params(":awayScore"), req.params(":gameId")));
        get("/AddRound/:seasonId/:numRounds", (req, res) -> addRoundToSeason(req.params("seasonId"), req.params(":numRounds")));
        get("/AddSeason/:seasonYear/:leagueId", (req, res) -> addSeasonToLeague(req.params("seasonYear"), req.params(":leagueId")));
        get("/AddGame/:roundId", (req, res) -> addGame(Long.parseLong(req.params(":roundId"))));
        get("/AddGame/:homeTeamId/:awayTeamId/:roundId", (req, res) -> addGameWithTeams(Long.parseLong(req.params(":homeTeamId")), Long.parseLong(req.params(":awayTeamId")), Long.parseLong(req.params(":roundId"))));
        get("/AddMetaInfoToGame/:gameId/:spec", (req, res) -> addMetaInfoToGame(Long.parseLong(req.params(":gameId")), Long.parseLong(req.params("arenaId")), Integer.parseInt(req.params(":spec"))));
        get("/AddSport/:name", (req, res) -> addSport(req.params(":name")));
        get("/ConnectTeamToSeason/:teamId/:seasonId", (req, res) -> connectTeamToSeason(Long.parseLong(req.params(":teamId")), Long.parseLong(req.params(":seasonId"))));
        get("/GamesForAwayTeam/:teamId", (req, res) -> getAllGamesForAwayTeam(Long.parseLong(req.params(":teamId"))));
        get("/GamesForHomeTeam/:teamId", (req, res) -> getAllGamesForHomeTeam(Long.parseLong(req.params(":teamId"))));
        get("/GamesForOneTeam/:teamId", (req, res) -> getAllGamesForOneTeam(Long.parseLong(req.params(":teamId"))));
        get("/GamesFromDate/:date", (req, res) -> getAllGamesFromDate(Integer.parseInt(req.params(":date"))));
        get("/GamesFromRound/:roundId", (req, res) -> getAllGamesFromRound(Long.parseLong(req.params("roundId"))));
        get("/GamesFromSeason/:seasonId", (req, res) -> getAllGamesFromSeason(Long.parseLong(req.params("seasonId"))));
        get("/LossesForTeam/:teamId", (req, res) -> getAllLossesForTeam(Long.parseLong(req.params(":teamId"))));
        get("/TiesForTeam/:teamId", (req, res) -> getAllTiesForTeam(Long.parseLong(req.params(":teamId"))));
        get("/WinsForTeam/:teamId", (req, res) -> getAllWinsForTeam(Long.parseLong(req.params(":teamId"))));
        get("/BiggestWinLose/:team1Id/:team2Id", (req, res) -> getBiggestWinLoseForTwoTeams(Long.parseLong(req.params(":team1Id")), Long.parseLong(req.params(":team2Id"))));
        get("/MatchHistory/:team1Id/:team2Id", (req, res) -> getTeamsMatchHistory(Long.parseLong(req.params(":team1Id")), Long.parseLong(req.params(":team2Id"))));
        get("/GameResultInfo/:gameId", (req, res) -> getGameResultInfo(Long.parseLong(req.params(":gameId"))));
        
        get("/SetHomeAndAwayTeam/:homeTeamId/:awayTeamId/:gameId", (req, res) -> setHomeAndAwayTeamService(Long.parseLong(req.params(":homeTeamId")), Long.parseLong(req.params(":awayTeamId")), Long.parseLong(req.params(":gameId"))));
        get("/AddPeriodResultsToGameService/:homeTeamScores/:awayTeamScores/:gameId", (req, res) -> addPeriodResults(req.params(":homeTeamScores"), req.params(":awayTeamScores"), Long.parseLong(req.params(":gameId"))));
        get("/Test/:leagueId", (req, res) -> test(Long.parseLong(req.params(":leagueId"))));
        get("/Streak/:teamId/:startDate/:endDate", (req, res) -> listTeamsLongestStreaks(Long.parseLong(req.params(":leagueId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        
        
    }
    public String addPeriodResults(String homeTeamScores, String awayTeamScores, Long gameId){
        ArrayList<Integer> homeScores = new ArrayList<Integer>();
        ArrayList<Integer> awayScores = new ArrayList<Integer>();

        //Parsar hemmapoäng till arraylist
        String tempCharString = "";
        int x = 0;
        while(x < homeTeamScores.length()) {
            if (homeTeamScores.charAt(x) != ':'){
                tempCharString += homeTeamScores.charAt(x);
            }
            if ((homeTeamScores.charAt(x) == ':') || homeTeamScores.length() == (x+1))
            {
                homeScores.add(Integer.parseInt(tempCharString));
                tempCharString = "";
            }
            x++;
        }
        
        //Parsar bortapoäng till arraylist
        x = 0;
        while(x < awayTeamScores.length()) {
            if (awayTeamScores.charAt(x) != ':'){
                tempCharString += awayTeamScores.charAt(x);
            }
            if ((awayTeamScores.charAt(x) == ':') || awayTeamScores.length() == (x+1))
            {
                awayScores.add(Integer.parseInt(tempCharString));
                tempCharString = "";
            }
            x++;
        }
        Integer[] homeScoresArray = homeScores.toArray(new Integer[homeScores.size()]);
        Integer[] awayScoresArray = awayScores.toArray(new Integer[awayScores.size()]);
        return runService(new AddPeriodResultsToGameService(homeScoresArray, awayScoresArray, gameId));
    }
    
    public String getSports() {
        return runService(new GetAllSportService());
    }
    public String getAllLeaguesFromSportId(Long id) {
        return runService(new GetAllLeagueFromSportService(id));
    }
    
    public String addSport(String name){
        return runService(new AddSportService(name));
    }
    public String getAllSeasonsFromLeague(Long id){
        return runService(new GetAllSeasonsFromLeagueService(id));
    }
    public String addTeam (String teamName, Long sportId){
        return runService(new AddTeamToSportService(teamName, sportId));
    }

    private String addLeagueToSport(Long sportId, String leagueName) {
        return runService(new AddLeagueToSportService(sportId, leagueName));
    }

    private String addSpectatorsAndArenaToGame(String gameId, String arenaId, String spectators) {
        Long gameIdLong = Long.parseLong(gameId);
        Long arenaIdLong = Long.parseLong(arenaId);
        int spectatorsInt = Integer.parseInt(spectators);
        return runService(new AddMetaInfoToGameService(gameIdLong, arenaIdLong, spectatorsInt));
    }

    private String addRoundToSeason(String seasonId, String numberOfGames) {
        Long seasonIdLong = Long.parseLong(seasonId);
        Integer numberOfGamesInteger = Integer.parseInt(numberOfGames);
        
        return runService(new AddRoundToSeasonService(seasonIdLong, numberOfGamesInteger));
    }
    
    private String addSeasonToLeague(String seasonYear, String leagueId){
        int seasonYearInt = Integer.parseInt(seasonYear);
        Long leagueIdLong = Long.parseLong(leagueId);
        return runService(new AddSeasonToLeagueService(seasonYearInt, leagueIdLong));
    }

    private String addResultToGame(String homeScore, String awayScore, String gameId) {
        int homeScoreInt = Integer.parseInt(homeScore);
        int awayScoreInt = Integer.parseInt(awayScore);
        Long gameIdLong = Long.parseLong(gameId);
        return runService(new AddResultToGameService(homeScoreInt, awayScoreInt, gameIdLong));
    }
    
    private String addGame(Long roundId) {
        return runService(new AddGameService(roundId));
    }
    private String addGameWithTeams(Long homeTeamId, Long awayTeamId, Long roundId) {
        return runService(new AddGameService(roundId, homeTeamId, awayTeamId));
    }
    private String addMetaInfoToGame(Long gameId, Long arenaId, int spectators){
        return runService(new AddMetaInfoToGameService(gameId, arenaId, spectators));
    }
    private String connectTeamToSeason(Long teamId, Long seasonId){
        return runService(new ConnectTeamToSeasonService(teamId, seasonId));
    }
    private String getAllGamesForAwayTeam(Long teamId){
        return runService(new GetAllGamesForAwayTeamService(teamId)); 
    }
    private String getAllGamesForHomeTeam(Long teamId){
        return runService(new GetAllGamesForHomeTeamService(teamId));
    }
    private String getAllGamesForOneTeam(Long teamId){
        return runService(new GetAllGamesForOneTeamService(teamId));
    }
    private String getAllGamesFromDate(Integer date){
        return runService(new GetAllGamesFromDateService(date));
    }
    private String getAllGamesFromRound(Long roundId){
        return runService(new GetAllGamesFromRoundService(roundId));
    }
    private String getAllGamesFromSeason(Long seasonId){
        return runService(new GetAllGamesFromSeasonService(seasonId));
    }
    private String getAllLeaguesFromSport(Long sportId){
        return runService(new GetAllLeagueFromSportService(sportId));
    }
    private String getAllLossesForTeam(Long teamId){
        return runService(new GetAllLossesForTeamService(teamId));
    }
    private String getAllTiesForTeam(Long teamId){
        return runService(new GetAllTiesForTeamService(teamId));
    }
    private String getAllWinsForTeam(Long teamId){
        return runService(new GetAllWinsForTeamService(teamId));
    }
    private String getBiggestWinLoseForTwoTeams(Long team1Id, Long team2Id){
        return runService(new GetBiggestWinLoseForTwoTeamsService(team1Id, team2Id));
    }
    private String getTeamsMatchHistory(Long team1Id, Long team2Id){
        return runService(new GetTeamsMatchHistoryService(team1Id, team2Id));
    }
    private String getGameResultInfo(Long gameId){
        return runService(new GetGameResultInfoService(gameId));
    }
    private String setHomeAndAwayTeamService(Long homeTeamId, Long awayTeamId, Long gameId){
        return runService(new SetHomeAndAwayTeamService(homeTeamId, awayTeamId, gameId));
    }
    private String test(Long leagueId){
        return runService(new ShowTableWithDynamicFiltersService(leagueId));
    }
    private String listTeamsLongestStreaks(Long teamId, Integer startDate, Integer endDate){
        return runService(new ListTeamsLongestStreaks(teamId, startDate, endDate));
    }
    
    
    
    
    
    private String runService(Service service) {
        return new ServiceRunner(service).execute();
    }
    
}
