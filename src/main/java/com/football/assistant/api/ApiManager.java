package com.football.assistant.api;

import com.football.assistant.domain.FootballClub;
import com.football.assistant.exception.WebApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

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
        FootballClub resultClub = new FootballClub(foundTeam.getString("strTeam"), foundTeam.getString("strTeamShort"),
                id, Integer.parseInt(foundTeam.getString("intFormedYear")), foundTeam.getString("strStadium"), foundTeam.getString("strWebsite"),
                foundTeam.getString("strDescriptionEN"), foundTeam.getString("strTeamBadge"), new Timestamp(System.currentTimeMillis()));
        return resultClub;
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

    private InputStream getJsonInputStreamFromUrl(String apiUrlSuffix) throws WebApiException, IOException {
        String urlNew = this.apiUrlBase + apiUrlSuffix;
        URL urlObj = new URL(urlNew);
        HttpURLConnection connection = (HttpURLConnection)urlObj.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        if(code == 400 || code == 404)
            throw new WebApiException(connection.getResponseMessage());
        return new URL(urlNew).openStream();
    }

}
