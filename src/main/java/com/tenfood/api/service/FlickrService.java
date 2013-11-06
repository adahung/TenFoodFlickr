package com.tenfood.api.service;

import com.tenfood.api.Context;
import com.tenfood.api.common.HttpProvider;
import org.apache.http.HttpResponse;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    private static final String PHOTOS_INFO_API = "flickr.photos.getInfo";

    // search api params
    private static final Set<String> PHOTOS_SEARCH_API_PARAMS = new HashSet<String>();
    static {
        PHOTOS_SEARCH_API_PARAMS.add("sort");
    };

    public JSONObject search(String text, Context context) {
        return this.search(text, 1, context);
    }

    public JSONObject search(String text, int count, Context context) {
        // search photos api http://www.flickr.com/services/api/flickr.photos.search.html
        // example request http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3cab8038b2f5b174960cae51757502c4&text=minion&has_geo=&format=json&nojsoncallback=1&auth_token=72157634822741157-457c6f62545a84a7&api_sig=19ba9a4e090095efeb65640f68afd369 from http://www.flickr.com/services/api/explore/flickr.photos.search

        if (text.isEmpty()) {
            return null;
        }

        String uri = getSearchRequestURI(text, count, context);
        try {

            HttpProvider httpProvider = new HttpProvider();

            logger.log(Level.INFO, "executing request " + uri);
            context.addMessage("executing request " + uri);

            HttpResponse response = httpProvider.doGet(uri, context);

            if (response.getStatusLine().getStatusCode() != 200)
            {
                logger.log(Level.WARNING, response.getStatusLine().toString());
                context.addMessage("Not 200:" + response.getStatusLine().toString());
                return null;
            }

            // parse json response
            return new JSONObject(EntityUtils.toString(response.getEntity()));

        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            context.addMessage(e.getMessage());
        }

        return null;
    }

    public JSONObject getInfo(String photo_id, String secret, Context context) {
        String uri  = getPhotoInfoURI(photo_id, secret);
        try {
            HttpProvider httpClient = new HttpProvider();
            HttpResponse resp = httpClient.doGet(uri, context);

            if (resp.getStatusLine().getStatusCode() == 200) {
                return new JSONObject(EntityUtils.toString(resp.getEntity()));
            }

        } catch (Exception e) {
            context.addMessage(e.getMessage());

        }

        return null;
    }

    public String getSearchRequestURI(String text, int count, Context context) {
        URIBuilder uri = new URIBuilder();
        getBasicURI(uri);
        uri.setParameter("method", PHOTOS_SEARCH_API);
        uri.setParameter("text", text);
        uri.setParameter("per_page", String.valueOf(count));

        // add additional search api params
        Map<String, String[]> queryMap = context.getQueryMap();
        for (String key: queryMap.keySet()) {
             if (PHOTOS_SEARCH_API_PARAMS.contains(key) && queryMap.get(key).length > 0) {
                 uri.setParameter(key, queryMap.get(key)[0]);
             }
        }

        return uri.toString();
    }

    public String getPhotoInfoURI(String photo_id, String secret) {
        //  http://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=70abf45679a23b4fe604527e65aee2b0&photo_id=9524594318&format=json&nojsoncallback=1
        URIBuilder uri = new URIBuilder();
        getBasicURI(uri);
        uri.setParameter("method", PHOTOS_INFO_API);
        uri.setParameter("photo_id", photo_id);
        uri.setParameter("secret", secret);

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
