package com.tenfood.api.resource;

import com.tenfood.api.model.Photo;
import com.tenfood.api.Context;
import com.tenfood.api.service.FlickrService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/25/13
 * Time: 9:58 下午
 * To change this template use File | Settings | File Templates.
 */
public class PhotoResource {
    private static Logger logger = Logger.getLogger(PhotoResource.class.getName());
    private FlickrService _flickrService;

    public PhotoResource(FlickrService flickrService) {
        this._flickrService = flickrService;
    }

    public List<Photo> getPhotos(Context context) {
        context.addMessage("In " + this.getClass().getName());
        Map<String, String[]> queryMap = context.getQueryMap();
        if (queryMap == null)
            return null;

        if (!queryMap.containsKey("text"))
            return null;

        context.addMessage("text is found in query");

        // get text from queryMap
        String[] text = queryMap.get("text");
        if (text.length == 0) {
            context.addMessage("text is empty array");
            return null;
        }
        context.addMessage("search for " + text[0]);
        logger.info("search for " + text[0]);

        // search photos from flickr
        JSONObject srchRslt = _flickrService.search(text[0], context);
        if (srchRslt == null) {
            logger.info("search result is null");
            return null;
        }

        // generate photos from search results
        List<Photo> photos = null;
        try {
            photos = generatePhotosFromSearchResult(srchRslt.getJSONObject("photos"));
        } catch (JSONException e) {
            context.addMessage(e.getMessage());
            logger.warning(e.getMessage());
        }

        return photos;
    }

    private List<Photo> generatePhotosFromSearchResult(JSONObject srchRslt) throws JSONException {
        JSONArray photoArray = srchRslt.getJSONArray("photo");
        List<Photo> photoList = new ArrayList<Photo>();
        for (int i = 0; i < photoArray.length(); i++ ) {
            Photo photo = new Photo();
            JSONObject obj = photoArray.getJSONObject(i);
            photo.setId(obj.getString("id"));
            photo.setSecret(obj.getString("secret"));
            photo.setUrl(generateFlickrUrl( obj.getString("farm"),
                                            obj.getString("server"),
                                            obj.getString("id"),
                                            obj.getString("secret")
                                           ));
            photoList.add(photo);
            //logger.info(photo.getUrl());
        }

        return photoList;
    }


    private String generateFlickrUrl(String farmId, String serverId, String id, String secret) {
        // http://www.flickr.com/services/api/misc.urls.html
        // http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
        // or
        // http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
        // or
        // http://farm{farm-id}.staticflickr.com/{server-id}/{id}_{o-secret}_o.(jpg|gif|png)
        return String.format( "http://farm%s.staticflickr.com/%s/%s_%s.jpg",
                              farmId, serverId, id, secret);
    }

    public Photo getExamplePhoto() {
        Photo photo = new Photo();

        photo.setUrl("http://farm1.static.flickr.com/2/1418878_1e92283336_m.jpg");

        return photo;
    }
}
