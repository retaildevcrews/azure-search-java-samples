package com.microsoft.azure.app.hotel;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    @JsonProperty(value = "HotelId")
    private String hotelId;

    @JsonProperty(value = "Tags")
    private List<String> tags;

    public Hotel() {
        this.tags = new ArrayList<>();
    }

    public String hotelId() {
        return this.hotelId;
    }

    public Hotel hotelId(String hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public List<String> tags() {
        return this.tags;
    }

    public Hotel tags(List<String> tags) {
        this.tags = tags;
        return this;
    }
}