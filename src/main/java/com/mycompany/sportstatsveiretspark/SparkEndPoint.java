/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sportstatsveiretspark;

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
        get("/allLeaguesFromSport", (req, res) -> getAllLeaguesFromSportId(Long.parseLong(req.queryParams("id"))));
        get("/getAllSeasonsFromLeague" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.queryParams("id"))));
        get("/AddTeam" , (req, res) -> getAllSeasonsFromLeague(Long.parseLong(req.queryParams("id"))));
        get("/param", (req, res) -> Long.parseLong(req.queryParams("id")));
        get("/twoparam", (req, res) -> req.queryParams("a")+req.queryParams("b"));
    }
    // team/:name/:id
    // /namnet/4
    public String getSports() {
        Service service = new GetAllSportService();
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    public String getAllLeaguesFromSportId(Long id) {
        Service service = new GetAllLeagueFromSportService(id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    public void addSport(String name, Long sportId){
        Service service = new AddTeamToSportService(name, sportId);
        new ServiceRunner(service).execute();
    }
    public String getAllSeasonsFromLeague(Long id){
        Service service = new GetAllSeasonsFromLeagueService(id);
        return JsonOutputformat.create(new ServiceRunner(service).execute());
    }
    public void AddTeam (String name, Long sportId){
        Service service = new AddTeamToSportService(name, sportId);
        new ServiceRunner(service).execute();
    }
}