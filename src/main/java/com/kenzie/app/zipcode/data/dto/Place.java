package com.kenzie.app.zipcode.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {

    @JsonProperty(value = "place name")
    private String placeName;

    @JsonProperty(value = "post code")
    private String postCode;

    //methods - getters/setters
    public String getPlaceName() {
        return placeName;
    }

    public void setPlace_name(String place_name) {
        this.placeName = place_name;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
