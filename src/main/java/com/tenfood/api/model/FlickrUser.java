package com.tenfood.api.model;

/**
 * Created with IntelliJ IDEA.
 * User: adahung
 * Date: 11/10/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class FlickrUser {
    private String nsid;
    private String username;

    public void setNsid(String nsid) {
        this.nsid = nsid;
    }

    public String getNsid() {
        return nsid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
