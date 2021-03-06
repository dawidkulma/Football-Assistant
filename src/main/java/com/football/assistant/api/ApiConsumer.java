package com.football.assistant.api;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class ApiConsumer {

    protected ApiManager apiManager;
    protected static final int refreshPeriodInDays = 2;

    @Autowired
    public ApiConsumer(ApiManager apiManager) {
        this.apiManager = apiManager;
    }

    public ApiManager getApiManager() {
        return apiManager;
    }

}
