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
import Services.SetHomeAndAwayTeamService;
import static spark.Spark.get;
import spark.servlet.SparkApplication;

/**
 *
 * @author Veiret
 */
public class SparkEndPoint implements SparkApplication {

    @Override
    public void init() {
        get("/Hello", (req, res) -> "Hello World Sebastian Sebastian Sebastian !!!!");
        get("/Sports", (req, res) -> getSports());
        get("/LeaguesFromSport", (req, res) -> getAllLeaguesFromSportId(Long.parseLong(req.queryParams("id"))));
        get("/SeasonsFromLeague" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.queryParams("id"))));
        get("/AddTeam" , (req, res) -> addTeam(req.queryParams("teamName"), (Long.parseLong(req.queryParams("sportId")))));
        get("/AddLeague", (req, res) -> addLeagueToSport(Long.parseLong(req.queryParams("sportId")) , req.queryParams("leagueName")));
        get("/AddSpectatorsAndArena", (req, res) -> addSpectatorsAndArenaToGame(req.queryParams("gameId"), req.queryParams("arenaId"), req.queryParams("spectators")));
        get("/AddResult", (req, res) -> addResultToGame(req.queryParams("homeScore"), req.queryParams("awayScore"), req.queryParams("gameId")));
        get("/AddRound", (req, res) -> addRoundToSeason(req.queryParams("seasonId"), req.queryParams("numberOfRounds")));
        get("/AddSeason", (req, res) -> addSeasonToLeague(req.queryParams("seasonYear"), req.queryParams("LeagueId")));
        get("/AddGame", (req, res) -> addGame(Long.parseLong(req.queryParams("roundId"))));
        get("/AddMetaInfoToGame", (req, res) -> addMetaInfoToGame(Long.parseLong(req.queryParams("gameId")), Long.parseLong(req.queryParams("arenaId")), Integer.parseInt(req.queryParams("spectators"))));
        get("/AddSport", (req, res) -> addSport(req.queryParams("name")));
        get("/ConnectTeamToSeason", (req, res) -> connectTeamToSeason(Long.parseLong(req.queryParams("teamId")), Long.parseLong(req.queryParams("seasonId"))));
        get("/GamesForAwayTeam", (req, res) -> getAllGamesForAwayTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/GamesForHomeTeam", (req, res) -> getAllGamesForHomeTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/GamesForOneTeam", (req, res) -> getAllGamesForOneTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/GamesFromDate", (req, res) -> getAllGamesFromDate(Integer.parseInt(req.queryParams("date"))));
        get("/GamesFromRound", (req, res) -> getAllGamesFromRound(Long.parseLong(req.queryParams("roundId"))));
        get("/GamesFromSeason", (req, res) -> getAllGamesFromSeason(Long.parseLong(req.queryParams("seasonId"))));
        get("/LossesForTeam", (req, res) -> getAllLossesForTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/TiesForTeam", (req, res) -> getAllTiesForTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/WinsForTeam", (req, res) -> getAllWinsForTeam(Long.parseLong(req.queryParams("teamId"))));
        get("/BiggestWinLose", (req, res) -> getBiggestWinLoseForTwoTeams(Long.parseLong(req.queryParams("team1Id")), Long.parseLong(req.queryParams("team2Id"))));
        get("/MatchHistory", (req, res) -> getTeamsMatchHistory(Long.parseLong(req.queryParams("team1Id")), Long.parseLong(req.queryParams("team2Id"))));
        //GetGameResultInfo behöver refaktureras. Om time är null ges nullpointer exception. (Bör även ändras från String till Json.
        get("/GameResultInfo", (req, res) -> getGameResultInfo(Long.parseLong(req.queryParams("gameId"))));
        get("/SetHomeAndAwayTeam", (req, res) -> setHomeAndAwayTeamService(Long.parseLong(req.queryParams("homeTeamId")), Long.parseLong(req.queryParams("awayTeamId")), Long.parseLong(req.queryParams("gameId"))));
        
        
        
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
        
        Long roundIdLong = roundId;
        return runService(new AddGameService(roundIdLong));
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
    private String getAllGamesFromDate(int date){
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
        return new GetGameResultInfoService(gameId).execute();
    }
    private String setHomeAndAwayTeamService(Long homeTeamId, Long awayTeamId, Long gameId){
        return runService(new SetHomeAndAwayTeamService(homeTeamId, awayTeamId, gameId));
    }
    private String runService(Service service) {
        return new ServiceRunner(service).execute();
    }
    
}
