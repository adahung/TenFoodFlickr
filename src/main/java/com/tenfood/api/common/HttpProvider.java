package com.tenfood.api.common;

import com.ning.http.client.Response;
import com.tenfood.api.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 8/16/13
 * Time: 11:06 下午
 * To change this template use File | Settings | File Templates.
 */
public class HttpProvider {
    private static Logger logger = Logger.getLogger(HttpProvider.class.getName());

    public HttpResponse doGet(String uri, Context context) throws Exception {
        /*AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> f = asyncHttpClient.prepareGet(uri).execute(new AsyncCompletionHandler() {

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
        return f.get(); */

        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(uri);

            logger.log(Level.INFO, "executing request " + httpget.getURI());
            context.addMessage("executing request " + httpget.getURI());

            return httpclient.execute(httpget);

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            context.addMessage(e.getMessage());
            return null;
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }
}
