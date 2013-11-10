package com.tenfood.api.model;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/26/13
 * Time: 12:53 上午
 * To change this template use File | Settings | File Templates.
 */
public class Photo {

    private String url;
    private String id;
    private String secret;
    private String info;
    private FlickrUser owner;
    private String origUrl;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setOwner(FlickrUser owner) {
        this.owner = owner;
    }

    public FlickrUser getOwner() {
        return owner;
    }

    public void setOrigUrl(String origUrl) {
        this.origUrl = origUrl;
    }

    public String getOrigUrl() {
        return origUrl;
    }
}
