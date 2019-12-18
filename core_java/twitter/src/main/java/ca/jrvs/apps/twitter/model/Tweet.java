package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favourited",
        "retweeted"
})
public class Tweet {

    @JsonProperty("created_at")
    private String created_at;
    @JsonProperty("id")
    private long id;
    @JsonProperty("id_str")
    private String id_str;
    @JsonProperty("text")
    private String text;
    @JsonProperty("entites")
    private Entities entities;
    @JsonProperty("coordinates")
    private Cooridinates cooridinates;
    @JsonProperty("retweet_count")
    private Long retweet_count;
    @JsonProperty("favourite_count")
    private Long favourite_count;
    @JsonProperty("favourited")
    private Boolean favourited;
    @JsonProperty("retweeted")
    private Boolean retweeted;

    @JsonProperty("created_at")
    public String getCreated_at() {
        return created_at;
    }

    @JsonProperty("created_at")
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("id_str")
    public String getId_str() {
        return id_str;
    }

    @JsonProperty("id_str")
    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("entities")
    public Entities getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(Entities entities) {
        this.entities = entities;
    }

    @JsonProperty("coordinates")
    public Cooridinates getCooridinates() {
        return cooridinates;
    }

    @JsonProperty("coordinates")
    public void setCooridinates(Cooridinates cooridinates) {
        this.cooridinates = cooridinates;
    }

    @JsonProperty("retweet_count")
    public Long getRetweet_count() {
        return retweet_count;
    }

    @JsonProperty("retweet_count")
    public void setRetweet_count(Long retweet_count) {
        this.retweet_count = retweet_count;
    }

    @JsonProperty("favourite_count")
    public Long getFavourite_count() {
        return favourite_count;
    }

    @JsonProperty("favorite_count")
    public void setFavourite_count(Long favourite_count) {
        this.favourite_count = favourite_count;
    }

    @JsonProperty("favourited")
    public Boolean getFavourited() {
        return favourited;
    }

    @JsonProperty("favourited")
    public void setFavourited(Boolean favourited) {
        this.favourited = favourited;
    }

    @JsonProperty("retweeted")
    public Boolean getRetweeted() {
        return retweeted;
    }

    @JsonProperty("retweeted")
    public void setRetweeted(Boolean retweeted) {
        this.retweeted = retweeted;
    }
}
