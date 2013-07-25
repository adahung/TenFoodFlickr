package com.tenfood.api.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/26/13
 * Time: 12:58 上午
 * To change this template use File | Settings | File Templates.
 */
public class Context {
    List<String> messages;

    public Context() {
        messages = new ArrayList<String>();
    }

    public void addMessage(String msg) {
        this.messages.add(msg);
    }

    public List<String> getMessages() {
        return this.messages;
    }
}
