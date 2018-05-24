/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Endpoints;

import Services.Add.AddGameService;
import Services.Add.AddLeagueToSportService;
import Services.Add.AddMetaInfoToGameService;
import Services.Add.AddPeriodResultsToGameService;
import Services.Add.AddResultToGameService;
import Services.Add.AddRoundToSeasonService;
import Services.Add.AddSeasonToLeagueService;
import Services.Add.AddSportService;
import Services.Add.AddTeamToSportService;
import Services.ConnectTeamToSeasonService;
import Services.Get.GetAllGamesForAwayTeamService;
import Services.Get.GetAllGamesForHomeTeamService;
import Services.Get.GetAllGamesForOneTeamService;
import Services.Get.GetAllGamesFromDateService;
import Services.Get.GetAllGamesFromRoundService;
import Services.Get.GetAllGamesFromSeasonService;
import Services.Get.GetAllLeagueFromSportService;
import Services.Get.GetAllLossesForTeamService;
import Services.Get.GetAllSeasonsFromLeagueService;
import Services.Get.GetAllSportService;
import Services.Get.GetAllTiesForTeamService;
import Services.Get.GetAllWinsForTeamService;
import Services.Get.GetBiggestWinLoseForTwoTeamsService;
import Services.Get.GetGameResultInfoService;
import Services.Get.GetTeamsMatchHistoryService;
import Services.Get.GetBiggestLossForOneTeamService;
import Services.Get.GetBiggestWinForOneTeamService;
import Services.ListTeamsLongestStreaks;
import Services.ServiceRunner;
import Services.SetHomeAndAwayTeamService;
import Services.Show.ShowTableWithDynamicFiltersService;
import java.util.ArrayList;
import Services.Service;
import Services.Show.ShowArenaPercentAndTeamGoalsOnDateInterval;
import Services.Show.ShowGoalsOnPeriodFilter;
import Services.Show.ShowSpectatorsAndTeamGoalsOnDateInterval;
import Services.Show.ShowTeamWinLossStatistics;
import java.util.List;
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
        //get("/GetBiggestLossForOneTeam/:teamId/:startDate/:endDate", (req, res) -> getBiggestLossForOneTeam(Long.parseLong(req.params(":teamId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        //get("/GetBiggestWinForOneTeam/:teamId/:startDate/:endDate", (req, res) -> getBiggestWinForOneTeam(Long.parseLong(req.params(":teamId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        get("/BiggestWinLose/:team1Id/:team2Id", (req, res) -> getBiggestWinLoseForTwoTeams(Long.parseLong(req.params(":team1Id")), Long.parseLong(req.params(":team2Id"))));
        get("/MatchHistory/:team1Id/:team2Id", (req, res) -> getTeamsMatchHistory(Long.parseLong(req.params(":team1Id")), Long.parseLong(req.params(":team2Id"))));
        get("/GameResultInfo/:gameId", (req, res) -> getGameResultInfo(Long.parseLong(req.params(":gameId"))));
        get("/ShowGoalsOnPeriodFilter/:seasonId/:startDate/:endDate", (req, res) -> showGoalsOnPeriodFilter(Long.parseLong(req.params(":seasonId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        get("/showSpectatorsAndTeamGoalsOnDateInterval/:leagueId/:startDate/:endDate", (req, res) -> showSpectatorsAndTeamGoalsOnDateInterval(Long.parseLong(req.params(":leagueId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        get("/showArenaPercent/:arenaId/:seasonId/:startDate/:endDate", (req, res) -> showArenaPercentAndTeamGoalsOnDateInterval(Long.parseLong(req.params(":arenaId")), Long.parseLong(req.params(":seasonId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        get("/SetHomeAndAwayTeam/:homeTeamId/:awayTeamId/:gameId", (req, res) -> setHomeAndAwayTeamService(Long.parseLong(req.params(":homeTeamId")), Long.parseLong(req.params(":awayTeamId")), Long.parseLong(req.params(":gameId"))));
        get("/AddPeriodResultsToGameService/:homeTeamScores/:awayTeamScores/:gameId", (req, res) -> addPeriodResults(req.params(":homeTeamScores"), req.params(":awayTeamScores"), Long.parseLong(req.params(":gameId"))));
        //get("/GetTableFromSeasons/:seasonIds/:filter/:tDatefRound/:HAConditions/:Date/:Round", (req, res) -> getTableFromSeasons(req.params(":seasonIds"), null, false, null, null, null));
        get("/GetTableFromSeasons/:seasonIds/:filter/:tDatefRound/:HAConditions/:Date/:Round", (req, res) -> getTableFromSeasons(req.params(":seasonIds"), req.params(":filter"), Boolean.parseBoolean(req.params(":tDatefRound")), req.params(":HAConditions"), req.params(":Date"), req.params(":Round")));
        get("/GetTableFromLeague/:seasonIds/:filter/:tDatefRound/:HAConditions/:Date/:Round", (req, res) -> getTableFromLeague(Long.parseLong(req.params(":seasonIds")), req.params(":filter"), Boolean.parseBoolean(req.params(":tDatefRound")), req.params(":HAConditions"), req.params(":Date"), req.params(":Round")));
        get("/Streak/:teamId/:startDate/:endDate", (req, res) -> listTeamsLongestStreaks(Long.parseLong(req.params(":leagueId")), Integer.parseInt(req.params(":startDate")), Integer.parseInt(req.params(":endDate"))));
        
        
    }
    public String addPeriodResults(String homeTeamScores, String awayTeamScores, Long gameId){
        ArrayList<Integer> homeScores = getIntegerListFromString(homeTeamScores);
        ArrayList<Integer> awayScores = getIntegerListFromString(awayTeamScores);

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
    private String getBiggestLossForOneTeam(Long teamId, Integer startDate, Integer endDate){
        return runService(new GetBiggestLossForOneTeamService(teamId, startDate, endDate));
    }
    private String getBiggestWinForOneTeam(Long teamId, Integer startDate, Integer endDate){
        return runService(new GetBiggestWinForOneTeamService(teamId, startDate, endDate));
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
    private String showArenaPercentAndTeamGoalsOnDateInterval(Long arenaId, Long seasonId, Integer startDate, Integer endDate){
        return runService(new ShowArenaPercentAndTeamGoalsOnDateInterval(arenaId, seasonId, startDate, endDate));
    }
    private String showGoalsOnPeriodFilter(Long seasonId, Integer startDate, Integer endDate){
        return runService(new ShowGoalsOnPeriodFilter(seasonId, startDate, endDate));
    }
    private String showSpectatorsAndTeamGoalsOnDateInterval(Long leagueId, Integer startDate, Integer endDate){
        return runService(new ShowSpectatorsAndTeamGoalsOnDateInterval(leagueId, startDate, endDate));
    }
    //ShowTeamWinLossStatistics(List<Long> seasonIds, Boolean[] firstLastGoal, Boolean[] fullOvertime, Integer wonPeriods, Boolean[] homeAway)
   /* private String showTeamWinLossStatistics(String seasonIds, String firstLastGoal, String fullOvertime, Integer wonPeriods, String homeAway){
        List<Long> seasonIDs = getLongListFromString(seasonIds);
        Boolean[] firstLastGoals = getBooleanArrayFromString(firstLastGoal);
        Boolean[] fullOvertimes = getBooleanArrayFromString(fullOvertime);
        Boolean[] homeAways = getBooleanArrayFromString(homeAway);
        return runService(new ShowTeamWinLossStatistics(seasonIDs, firstLastGoals, fullOvertimes, wonPeriods, homeAways));
    }*/
    private String getTableFromLeague(Long leagueId, String filter, boolean trueDateFalseRound, String homeAwayConditions, String startEndDate, String startEndRound){
         if (!"0".equals(filter)){
             int[] filters = getIntArrayFromString(filter);
             if (!"0".equals(homeAwayConditions)){
                 boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
                 return runService(new ShowTableWithDynamicFiltersService(leagueId, filters, trueDateFalseRound, homeAwayConditionsArray));
             } else
                 return runService(new ShowTableWithDynamicFiltersService(leagueId, filters, trueDateFalseRound));
         }
         else if (!"0".equals(startEndDate) && !"0".equals(startEndRound)){
            int[] startEndDateArray = getIntArrayFromString(startEndDate);
            int[] startEndRoundArray = getIntArrayFromString(startEndRound);
            if(!"0".equals(homeAwayConditions)){
                boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
                return runService(new ShowTableWithDynamicFiltersService(leagueId, startEndDateArray, startEndRoundArray, homeAwayConditionsArray));
            } else
                return runService(new ShowTableWithDynamicFiltersService(leagueId, startEndDateArray, startEndRoundArray));
         } else if (!"0".equals(homeAwayConditions)){
            boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
            return runService(new ShowTableWithDynamicFiltersService(leagueId, homeAwayConditionsArray));
        } else
            return runService(new ShowTableWithDynamicFiltersService(leagueId));
    }
    private String getTableFromSeasons(String seasonInput, String filter, boolean trueDateFalseRound, String homeAwayConditions, String startEndDate, String startEndRound){
        ArrayList<Long> seasonIDs = getLongListFromString(seasonInput);

        if (!"0".equals(filter)){
            int[] filters = getIntArrayFromString(filter);
            if (!"0".equals(homeAwayConditions)){
                boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
                return runService(new ShowTableWithDynamicFiltersService(seasonIDs, filters, trueDateFalseRound, homeAwayConditionsArray));
            } else{
                return runService(new ShowTableWithDynamicFiltersService(seasonIDs, filters, trueDateFalseRound));
            }
        }
        else if (!"0".equals(startEndDate) && !"0".equals(startEndRound)){
            int[] startEndDateArray = getIntArrayFromString(startEndDate);
            int[] startEndRoundArray = getIntArrayFromString(startEndRound);
            if(!"0".equals(homeAwayConditions)){
                boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
                return runService(new ShowTableWithDynamicFiltersService(seasonIDs, startEndDateArray, startEndRoundArray, homeAwayConditionsArray));
            } else
                return runService(new ShowTableWithDynamicFiltersService(seasonIDs, startEndDateArray, startEndRoundArray));
        } else if (!"0".equals(homeAwayConditions)){
            boolean[] homeAwayConditionsArray = getPrimBooleanArrayFromString(homeAwayConditions);
            return runService(new ShowTableWithDynamicFiltersService(seasonIDs, homeAwayConditionsArray));
        } else
            return runService(new ShowTableWithDynamicFiltersService(seasonIDs));
    }
    private String getCustomTable(Long leagueId){
        return runService(new ShowTableWithDynamicFiltersService(leagueId));
    }
    private String listTeamsLongestStreaks(Long teamId, Integer startDate, Integer endDate){
        return runService(new ListTeamsLongestStreaks(teamId, startDate, endDate));
    }
    
    private Boolean[] getBooleanArrayFromString(String input){
        String[] inputArray = input.split("-");
        Boolean[] data = new Boolean[inputArray.length];
        
        for (int x = 0; x < inputArray.length; x++)
            data[x] = Boolean.parseBoolean(inputArray[x]);
        return data;
    }
    
    private boolean[] getPrimBooleanArrayFromString(String input){
        String[] inputArray = input.split("-");
        boolean[] data = new boolean[inputArray.length];
        
        for (int x = 0; x < inputArray.length; x++)
            data[x] = Boolean.parseBoolean(inputArray[x]);
        return data;
    }
    private int[] getIntArrayFromString(String input){
        String[] inputArray = input.split("-");
        int[] data = new int[inputArray.length];
        
        for (int x = 0; x < inputArray.length; x++)
            data[x] = Integer.parseInt(inputArray[x]);
        return data;
    }

    private ArrayList<Long> getLongListFromString(String input){
        ArrayList<Long> data = new ArrayList<>();
        String[] inputArray = input.split("-");
        
        for (String x: inputArray)
            data.add(Long.parseLong(x));
        return data;
    }
    private ArrayList<Integer> getIntegerListFromString(String input){
        ArrayList<Integer> data = new ArrayList<>();
        String[] inputArray = input.split("-");
        
        for (String x: inputArray)
            data.add(Integer.parseInt(x));
        return data;
    }
    
    
    private String runService(Service service) {
        return new ServiceRunner(service).execute();
    }
    
}
