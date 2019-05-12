package com.football.assistant.api;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.domain.League;
import com.football.assistant.domain.Player;
import com.football.assistant.exception.WebApiException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.json.*;

@Component
public class ApiManager {

    private String apiUrlPrefix = "https://www.thesportsdb.com/api/v1/json";

    private String apiKey = "1";

    private String apiUrlBase;

    public ApiManager() {
        this.apiUrlBase = this.apiUrlPrefix + "/" + this.apiKey;
    }

    public FootballClub fetchFootballClubById(Integer id) {
        String fetchUrlSuffix = "/lookupteam.php?id=" + id.toString();
        JsonObject fetchResult = null;
        try {
            fetchResult = getJsonObjFromURL(fetchUrlSuffix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (fetchResult == null) return null;
        JsonArray arrayOfFoundTeams = null;
        try {
            arrayOfFoundTeams = fetchResult.getJsonArray("teams");
        } catch (Exception e) {
            return null;
        }
        if(arrayOfFoundTeams == null) return null;
        if (fetchResult.size() < 1) return null;
        JsonObject foundTeam = arrayOfFoundTeams.getJsonObject(0);
        FootballClub resultClub = null;
        try {
            resultClub = new FootballClub(foundTeam.getString("strTeam"),
                    id, Integer.parseInt(foundTeam.getString("intFormedYear")), foundTeam.getString("strStadium"), foundTeam.getString("strWebsite"),
                    foundTeam.getString("strDescriptionEN"), foundTeam.getString("strTeamBadge"), new Timestamp(System.currentTimeMillis()));
        } catch (Exception e) {
            return null;
        }
        return resultClub;
    }

    public Player fetchPlayerById(Integer id) {
        String fetchUrlSuffix = "/lookupplayer.php?id=" + id.toString();
        JsonObject fetchResult = null;
        try {
            fetchResult = getJsonObjFromURL(fetchUrlSuffix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (fetchResult == null) return null;
        JsonArray arrayOfFoundPlayers = null;
        try {
            arrayOfFoundPlayers = fetchResult.getJsonArray("players");
        } catch (Exception e) {
            return null;
        }
        if(arrayOfFoundPlayers == null) return null;
        if (fetchResult.size() < 1) return null;
        JsonObject foundPlayer = arrayOfFoundPlayers.getJsonObject(0);
        Player player = null;
        try {
            player = new Player(id, new Timestamp(System.currentTimeMillis()), foundPlayer.getString("strNationality"),
                    foundPlayer.getString("strPlayer"), foundPlayer.getString("dateBorn"), foundPlayer.getString("strWage"),
                    foundPlayer.getString("strBirthLocation"), foundPlayer.getString("strDescriptionEN"), foundPlayer.getString("strPosition"),
                    Double.parseDouble(foundPlayer.getString("strHeight")), Double.parseDouble(foundPlayer.getString("strWeight")),
                    foundPlayer.getString("strThumb"));
        } catch (Exception e) {
            return null;
        }
        return player;
    }

    public League fetchLeagueById(Integer id) {
        String fetchUrlSuffix = "/lookupleague.php?id=" + id.toString();
        JsonObject fetchResult = null;
        try {
            fetchResult = getJsonObjFromURL(fetchUrlSuffix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (fetchResult == null) return null;
        JsonArray arrayOfFoundLeagues = null;
        try {
            arrayOfFoundLeagues = fetchResult.getJsonArray("leagues");
        } catch (Exception e) {
            return null;
        }
        if(arrayOfFoundLeagues == null) return null;
        if (arrayOfFoundLeagues.size() < 1) return null;
        JsonObject foundLeague = arrayOfFoundLeagues.getJsonObject(0);
        League league = null;
        try {
            league = new League(id, new Timestamp(System.currentTimeMillis()), foundLeague.getString("strLeague"),
                    Integer.parseInt(foundLeague.getString("intFormedYear")), foundLeague.getString("strWebsite"),
                    foundLeague.getString("strDescriptionEN"), foundLeague.getString("strBadge"));
        } catch (Exception e) {
            return null;
        }
        return league;
    }

    public List<FootballClub> fetchAllClubsInLeague(Integer id) {
        String fetchUrlSuffix = "/lookup_all_teams.php?id=" + id.toString();
        JsonObject fetchResult = null;
        try {
            fetchResult = getJsonObjFromURL(fetchUrlSuffix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (fetchResult == null) return null;
        JsonArray arrayOfFoundClubs = null;
        try {
            arrayOfFoundClubs = fetchResult.getJsonArray("teams");
        } catch (Exception e) {
            return null;
        }
        if(arrayOfFoundClubs == null) return null;
        if (arrayOfFoundClubs.size() < 1) return null;
        List<FootballClub> result = new LinkedList<>();
        for(int i = 0; i < arrayOfFoundClubs.size(); i++) {
            JsonObject foundTeam = arrayOfFoundClubs.getJsonObject(i);
            FootballClub resultClub = null;
            try {
                resultClub = new FootballClub(foundTeam.getString("strTeam"),
                        Integer.parseInt(foundTeam.getString("idTeam")), Integer.parseInt(foundTeam.getString("intFormedYear")),
                        foundTeam.getString("strStadium"), foundTeam.getString("strWebsite"),
                        foundTeam.getString("strDescriptionEN"), foundTeam.getString("strTeamBadge"),
                        new Timestamp(System.currentTimeMillis()));
            } catch (Exception e) {
                continue;
            }
            result.add(resultClub);
        }
        return result;
    }

    public List<Player> fetchAllPlayersInClub(Integer id) {
        String fetchUrlSuffix = "/lookup_all_players.php?id=" + id.toString();
        JsonObject fetchResult = null;
        try {
            fetchResult = getJsonObjFromURL(fetchUrlSuffix);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (fetchResult == null) return null;
        JsonArray arrayOfFoundPlayers = null;
        try {
            arrayOfFoundPlayers = fetchResult.getJsonArray("player");
        } catch (Exception e) {
            return null;
        }
        if(arrayOfFoundPlayers == null) return null;
        if (arrayOfFoundPlayers.size() < 1) return null;
        List<Player> result = new LinkedList<>();
        for(int i = 0; i < arrayOfFoundPlayers.size(); i++) {
            JsonObject foundPlayer = arrayOfFoundPlayers.getJsonObject(i);
            Player resultPlayer = null;
            try {
                resultPlayer = new Player(Integer.parseInt(foundPlayer.getString("idPlayer")), new Timestamp(System.currentTimeMillis()),
                        foundPlayer.getString("strNationality"), foundPlayer.getString("strPlayer"), foundPlayer.getString("dateBorn"),
                        foundPlayer.getString("strWage"), foundPlayer.getString("strBirthLocation"), foundPlayer.getString("strDescriptionEN"),
                        foundPlayer.getString("strPosition"), Double.parseDouble(foundPlayer.getString("strHeight")),
                        Double.parseDouble(foundPlayer.getString("strWeight")), foundPlayer.getString("strThumb"));
            } catch (Exception e) {
                continue;
            }
            result.add(resultPlayer);
        }
        return result;
    }

    public String getApiUrlPrefix() {
        return apiUrlPrefix;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrlBase() {
        return apiUrlBase;
    }

    private JsonObject getJsonObjFromURL(String apiUrlSuffix) throws WebApiException, IOException {
        InputStream stream = this.getJsonInputStreamFromUrl(apiUrlSuffix);
        JsonReader reader = Json.createReader(stream);
        JsonObject object = reader.readObject();
        reader.close();
        stream.close();
        return object;
    }

    private JsonArray getJsonArrFromURL(String apiUrlSuffix) throws WebApiException, IOException {
        InputStream stream = this.getJsonInputStreamFromUrl(apiUrlSuffix);
        JsonReader reader = Json.createReader(stream);
        JsonArray array = reader.readArray();
        reader.close();
        stream.close();
        return array;
    }

    private InputStream getJsonInputStreamFromUrl(String apiUrlSuffix) throws IOException {
        String urlNew = this.apiUrlBase + apiUrlSuffix;
        return new URL(urlNew).openStream();
    }

}
