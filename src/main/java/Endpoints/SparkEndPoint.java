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
        get("/hello", (req, res) -> "Hello World Sebastian Sebastian Sebastian !!!!");
        get("/allSports", (req, res) -> getSports());
        get("/allLeaguesFromSport", (req, res) -> getAllLeaguesFromSportId(Long.parseLong(req.queryParams("id"))));
        get("/getAllSeasonsFromLeague" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.queryParams("id"))));
        get("/AddTeam" , (req, res) -> addTeam(req.queryParams("teamName"), (Long.parseLong(req.queryParams("sportId")))));
        get("/AddLeagueToSport", (req, res) -> addLeagueToSport(Long.parseLong(req.queryParams("sportId")) , req.queryParams("leagueName")));
        get("/AddSpectatorsAndArena", (req, res) -> addSpectatorsAndArenaToGame(req.queryParams("gameId"), req.queryParams("arenaId"), req.queryParams("spectators")));
        get("/AddResultToGame", (req, res) -> addResultToGame(req.queryParams("homeScore"), req.queryParams("awayScore"), req.queryParams("gameId")));
        get("/AddRoundToSeason", (req, res) -> addRoundToSeason(req.queryParams("seasonId"), req.queryParams("numberOfRounds")));
        get("/AddSeasonToLeague", (req, res) -> addSeasonToLeague(req.queryParams("seasonYear"), req.queryParams("LeagueId")));
        get("/AddRoundToSeason", (req, res) -> addRoundToSeason(req.queryParams("seasonId"), req.queryParams("numberOfGames")));
        get("/AddSeasonToLeague", (req, res) -> addSeasonToLeague(req.queryParams("seasonYear"), req.queryParams("leagueId")));
        get("/AddGame", (req, res) -> addGame(Long.parseLong(req.queryParams("roundId"))));
        get("/AddMetaInfoToGame", (req, res) -> addMetaInfoToGame(Long.parseLong(req.queryParams("gameId")), Long.parseLong(req.queryParams("arenaId")), Integer.parseInt(req.queryParams("spectators"))));
        
        //Många "500 Internal server error" här under, min gissning är att dessa beror på att det saknar @JsonIgnore i domänklass(er)
        
        //---Verkar fungera---
        get("/AddSport", (req, res) -> addSport(req.queryParams("name")));
        //---Verkar fungera---
        get("/ConnectTeamToSeason", (req, res) -> connectTeamToSeason(Long.parseLong(req.queryParams("teamId")), Long.parseLong(req.queryParams("seasonId"))));
        //Ger "500 Internal server error" på teamId som har bortamatcher, ger "[]" på teamId som inte har några bortamatcher
        get("/GetAllGamesForAwayTeam", (req, res) -> getAllGamesForAwayTeam(Long.parseLong(req.queryParams("teamId"))));
        //Ger "500 Internal server error" på teamId som har hemmamatcher, ger "[]" på teamId som inte har några hemmamatcher
        get("/GetAllGamesForHomeTeam", (req, res) -> getAllGamesForHomeTeam(Long.parseLong(req.queryParams("teamId"))));
        //Ger "500 Internal server error" på teamId som har matcher
        get("/GetAllGamesForOneTeam", (req, res) -> getAllGamesForOneTeam(Long.parseLong(req.queryParams("teamId"))));
        //Ger "500 Internal server error" på date=1, det finns ett Game med date satt till 1 i DB
        get("/GetAllGamesFromDate", (req, res) -> getAllGamesFromDate(Long.parseLong(req.queryParams("date"))));
        //Ger "500 Internal server error" på roundId=0, det finns ett Game med round satt till 0 i DB
        get("/GetAllGamesFromRound", (req, res) -> getAllGamesFromRound(Long.parseLong(req.queryParams("roundId"))));
        //Ger "500 Internal server error" på seasonId=0, det finns ett Game med round satt till 0 i DB, round 0 är i sin tur kopplad till season 0
        get("/GetAllGamesFromSeason", (req, res) -> getAllGamesFromSeason(Long.parseLong(req.queryParams("seasonId"))));
        //---Verkar fungera---
        get("/GetAllLeaguesFromSport", (req, res) -> getAllLeaguesFromSport(Long.parseLong(req.queryParams("sportId"))));
        //Ger "500 Internal server error" på teamId som har förluster, ger "[]" på teamId som inte har några förluster
        get("/GetAllLossesForTeam", (req, res) -> getAllLossesForTeam(Long.parseLong(req.queryParams("teamId"))));
        //Ger "[]" på teamId som inte har några oavgjorda matcher, ger -TROLIGTVIS- "500 Internal server error" på teamId som har oavgjorda matcher
        get("/GetAllTiesForTeam", (req, res) -> getAllTiesForTeam(Long.parseLong(req.queryParams("teamId"))));
        //Ger "500 Internal server error" på teamId som har vinster, ger "[]" på teamId som inte har några vinster
        get("/GetAllWinsForTeam", (req, res) -> getAllWinsForTeam(Long.parseLong(req.queryParams("teamId"))));
        //---Verkar fungera---
        get("/GetBiggestWinLoseForTwoTeams", (req, res) -> getBiggestWinLoseForTwoTeams(Long.parseLong(req.queryParams("team1Id")), Long.parseLong(req.queryParams("team2Id"))));
        //Ger "500 Internal server error" med team1Id=0 & team2Id=1, vilka har en gemensam match
        get("/GetTeamsMatchHistory", (req, res) -> getTeamsMatchHistory(Long.parseLong(req.queryParams("team1Id")), Long.parseLong(req.queryParams("team2Id"))));
        //Ger "500 Internal server error" med gameId=0 vilket har ett result, ger "no result for this game" för gameId=1 vilket är ett game utan kopplat resultat
        get("/GetGameResultInfo", (req, res) -> getGameResultInfo(Long.parseLong(req.queryParams("gameId"))));
        //Ger "Team 0 and team 1 was added to new game." med homeTeamId=0 & awayTeamId=1
        get("/SetHomeAndAwayTeamService", (req, res) -> setHomeAndAwayTeamService(Long.parseLong(req.queryParams("homeTeamId")), Long.parseLong(req.queryParams("awayTeamId"))));
        
        
        
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
        System.out.println("Hej");
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
        return "Arena " + arenaId + " and " + spectators + " added to game with ID " + gameId;
    }

    private String addRoundToSeason(String seasonId, String numberOfGames) {
        Long seasonIdLong = Long.parseLong(seasonId);
        Integer numberOfGamesInteger = Integer.parseInt(numberOfGames);
        
        Service service = new AddRoundToSeasonService(seasonIdLong, numberOfGamesInteger);
        new ServiceRunner(service).execute();
        return "Round with "+ numberOfGames + " games added to season " + seasonId;
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
        new ServiceRunner(service).execute();
        return "Result added";
    }
    
    private String addGame(Long roundId) {
        
        Long roundIdLong = roundId;
        Service service = new AddGameService(roundIdLong);
        new ServiceRunner(service).execute();
        return "Game added";
    }
    private String addMetaInfoToGame(Long gameId, Long arenaId, int spectators){
        Service service = new AddMetaInfoToGameService(gameId, arenaId, spectators);
        new ServiceRunner(service).execute();
        return "Meta info added to game " + gameId;
    }
    private String connectTeamToSeason(Long teamId, Long seasonId){
        Service service = new ConnectTeamToSeasonService(teamId, seasonId);
        new ServiceRunner(service).execute();
        return "Team " + teamId + " added to season " + seasonId;
    }
    private String getAllGamesForAwayTeam(Long teamId){
        Service service = new GetAllGamesForAwayTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllGamesForHomeTeam(Long teamId){
        Service service = new GetAllGamesForHomeTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllGamesForOneTeam(Long teamId){
        Service service = new GetAllGamesForOneTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllGamesFromDate(Long date){
        Service service = new GetAllGamesFromDateService(date);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllGamesFromRound(Long roundId){
        Service service = new GetAllGamesFromRoundService(roundId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllGamesFromSeason(Long seasonId){
        Service service = new GetAllGamesFromSeasonService(seasonId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllLeaguesFromSport(Long sportId){
        Service service = new GetAllLeagueFromSportService(sportId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllLossesForTeam(Long teamId){
        Service service = new GetAllLossesForTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllTiesForTeam(Long teamId){
        Service service = new GetAllTiesForTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getAllWinsForTeam(Long teamId){
        Service service = new GetAllWinsForTeamService(teamId);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getBiggestWinLoseForTwoTeams(Long team1Id, Long team2Id){
        Service service = new GetBiggestWinLoseForTwoTeamsService(team1Id, team2Id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getTeamsMatchHistory(Long team1Id, Long team2Id){
        Service service = new GetTeamsMatchHistoryService(team1Id, team2Id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    private String getGameResultInfo(Long gameId){
        Service service = new GetGameResultInfoService(gameId);
        return (String) new ServiceRunner(service).execute();
    }
    private String setHomeAndAwayTeamService(Long homeTeamId, Long awayTeamId){
        Service service = new SetHomeAndAwayTeamService(homeTeamId, awayTeamId);
        new ServiceRunner(service).execute();
        return ("Team " + homeTeamId + " and team " + awayTeamId + " was added to new game.");
    }
    
    
    
}
