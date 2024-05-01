package com.kpsoft.it.ws_POC_Weather_app.dto;

import java.util.List;

import lombok.Data;

@Data
public class CityId {

    List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public CityId(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "CityId [ids=" + ids + "]";
    }

    public CityId() {

    }

}
