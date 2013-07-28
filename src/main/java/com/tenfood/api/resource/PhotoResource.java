package com.tenfood.api.resource;

import com.tenfood.api.model.Photo;
import com.tenfood.api.Context;
import com.tenfood.api.service.FlickrService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/25/13
 * Time: 9:58 下午
 * To change this template use File | Settings | File Templates.
 */
public class PhotoResource {

    public List<Photo> getPhotos(Map<String, String[]> queryMap, Context context) {
        context.addMessage("In " + this.getClass().getName());
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

        // search photos from flickr
        FlickrService flickrService = new FlickrService(context);
        flickrService.search(text);

        // create response photo list
        List<Photo> photos = new ArrayList<Photo>();
        photos.add(getExamplePhoto());

        return photos;
    }

    public Photo getExamplePhoto() {
        Photo photo = new Photo();

        photo.setUrl("http://farm1.static.flickr.com/2/1418878_1e92283336_m.jpg");

        return photo;
    }
}
