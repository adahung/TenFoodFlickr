package com.tenfood.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/26/13
 * Time: 12:58 上午
 * To change this template use File | Settings | File Templates.
 */
public class Context {
    private List<String> _messages;
    private Map<String, String[]> _queryMap;

    public Context() {
        _messages = new ArrayList<String>();
    }

    public Map<String, String[]> getQueryMap() {
        return _queryMap;
    }

    public void setQueryMap(Map<String, String[]> map) {
        _queryMap = map;
    }

    public void addMessage(String msg) {
        _messages.add(msg);
    }

    public List<String> getMessages() {
        return _messages;
    }

    public void addError(int errorCode, String errorMsg) {

    }
}
