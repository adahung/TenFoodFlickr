package com.tenfood.api.service;

import com.tenfood.api.Context;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

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
    private Context context;

    // general
    private static final String API_KEY = "bc97b3f08ea2f8d50edc9ca20daaf1fc";
    private static final String FLICKR_API_SERVER = "http://api.flickr.com/services/rest";

    // api methods
    private static final String PHOTOS_SEARCH_API = "flickr.photos.search";

    public FlickrService(Context context) {
        this.context = context;
    }

    public void search(String[] text) {
        // search photos api http://www.flickr.com/services/api/flickr.photos.search.html
        // example request http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3cab8038b2f5b174960cae51757502c4&text=minion&has_geo=&format=json&nojsoncallback=1&auth_token=72157634822741157-457c6f62545a84a7&api_sig=19ba9a4e090095efeb65640f68afd369 from http://www.flickr.com/services/api/explore/flickr.photos.search

        HttpClient httpclient = new DefaultHttpClient();
        try {
            if (text.length == 0) {
                return;
            }
            HttpGet httpget = new HttpGet(FLICKR_API_SERVER);
            HttpParams httpParams = this.getSearchParams(text[0]);
            httpget.setParams(httpParams);

            logger.log(Level.INFO, "executing request " + httpget.getURI());
            context.addMessage("executing request " + httpget.getURI());

            HttpResponse response = httpclient.execute(httpget);

            logger.log(Level.INFO, response.getStatusLine().toString());
            context.addMessage(response.getStatusLine().toString());
            context.addMessage(EntityUtils.toString(response.getEntity()));

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
            context.addMessage(e.getMessage());
        } finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }
    }

    public HttpParams getSearchParams(String text) {
        HttpParams httpParams = this.getBasicParams();
        httpParams.setParameter("method", PHOTOS_SEARCH_API);
        httpParams.setParameter("text", text);

        return httpParams;
    }

    public HttpParams getBasicParams() {
        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter("api_key", API_KEY);
        httpParams.setParameter("format", "json");

        return httpParams;
    }


}
