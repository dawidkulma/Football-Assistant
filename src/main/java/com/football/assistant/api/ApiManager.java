package com.football.assistant.api;

import com.football.assistant.exception.WebApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

@Component
public class ApiManager {

    @Value("${apiManager.apiUrlPrefix}")
    private String apiUrlPrefix;

    @Value("${apiManager.apiKey}")
    private String apiKey;

    private String apiUrlBase;

    public ApiManager() {
        this.apiUrlBase = this.apiUrlPrefix + "/" + this.apiKey;
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
        InputStream stream = new URL(urlNew).openStream();
        return stream;
    }

}
