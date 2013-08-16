package com.tenfood.api.common;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import com.tenfood.api.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 8/16/13
 * Time: 11:06 下午
 * To change this template use File | Settings | File Templates.
 */
public class HttpClient {
    public Response doGet(String url) throws IOException {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler(){

            @Override
            public Response onCompleted(Response response) throws Exception{
                // Do something with the Response
                // ...                                   `
                return response;
            }

            @Override
            public void onThrowable(Throwable t){
                // Something wrong happened.
            }
        });
        return null;
    }
}
