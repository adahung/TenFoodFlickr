package com.tenfood.api.common;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 8/16/13
 * Time: 11:06 下午
 * To change this template use File | Settings | File Templates.
 */
public class HttpClient {
    public Response doGet(String url) throws Exception {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> f = asyncHttpClient.prepareGet(url).execute(new AsyncCompletionHandler() {

            @Override
            public Response onCompleted(Response response) throws Exception {
                // Do something with the Response
                // ...                                   `
                return response;
            }

            @Override
            public void onThrowable(Throwable t) {
                // Something wrong happened.
            }
        });
        return f.get();
    }
}
