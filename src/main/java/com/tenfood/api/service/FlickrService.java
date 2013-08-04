package com.tenfood.api.service;

import com.tenfood.api.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/25/13
 * Time: 9:38 下午
 * To change this template use File | Settings | File Templates.
 */
public class FlickrService {

    private static Logger logger = Logger.getLogger(FlickrService.class.getName());

    // general
    private static final String API_KEY = "bc97b3f08ea2f8d50edc9ca20daaf1fc";
    private static final String FLICKR_API_SERVER = "api.flickr.com";
    private static final String FLICKR_API_PATH = "/services/rest/";

    // api methods
    private static final String PHOTOS_SEARCH_API = "flickr.photos.search";

    public JSONObject search(String text, Context context) {
        // search photos api http://www.flickr.com/services/api/flickr.photos.search.html
        // example request http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3cab8038b2f5b174960cae51757502c4&text=minion&has_geo=&format=json&nojsoncallback=1&auth_token=72157634822741157-457c6f62545a84a7&api_sig=19ba9a4e090095efeb65640f68afd369 from http://www.flickr.com/services/api/explore/flickr.photos.search

        HttpClient httpclient = new DefaultHttpClient();
        try {
            if (text.isEmpty()) {
                return null;
            }

            String uri = getSearchRequestURI(text);
            HttpGet httpget = new HttpGet(uri);

            logger.log(Level.INFO, "executing request " + httpget.getURI());
            context.addMessage("executing request " + httpget.getURI());

            HttpResponse response = httpclient.execute(httpget);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.log(Level.WARNING, response.getStatusLine().toString());
                context.addMessage(response.getStatusLine().toString());
                return null;
            }

            // parse json response
            return new JSONObject(EntityUtils.toString(response.getEntity()));

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            context.addMessage(e.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }

        return null;
    }

    public String getSearchRequestURI(String text) {
        URIBuilder uri = new URIBuilder();
        getBasicURI(uri);
        uri.setParameter("method", PHOTOS_SEARCH_API);
        uri.setParameter("text", text);

        return uri.toString();
    }

    private void getBasicURI(URIBuilder uri) {
        uri.setScheme("http");
        uri.setHost(FLICKR_API_SERVER);
        uri.setPath(FLICKR_API_PATH);
        uri.setParameter("api_key", API_KEY);
        uri.setParameter("format", "json");
        uri.setParameter("nojsoncallback", "1");
    }
}
