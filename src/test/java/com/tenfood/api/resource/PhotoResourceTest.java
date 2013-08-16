package com.tenfood.api.resource;

import com.tenfood.api.Context;
import com.tenfood.api.model.Photo;
import com.tenfood.api.service.FlickrService;
import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.Mockito;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/25/13
 * Time: 10:26 下午
 * To change this template use File | Settings | File Templates.
 */
public class PhotoResourceTest {

    private Context context;
    private FlickrService flickrService;
    private PhotoResource resource;

    @BeforeTest
    public void setUp() throws JSONException {
        context = new Context();
        Map<String, String[]> queryMap = new HashMap<String, String[]>();
        context.setQueryMap(queryMap);

        flickrService = Mockito.mock(FlickrService.class);
        // return object
        String srchRsltString = "{ \"photos\": { \"page\": 1, \"pages\": \"3185967\", \"perpage\": 1, \"total\": \"3185967\", \n" +
                "    \"photo\": [\n" +
                "      { \"id\": \"9432874385\", \"owner\": \"94937606@N02\", \"secret\": \"1b3ed99eb5\", \"server\": \"7377\", \"farm\": 8, \"title\": \"Love Tatoo ! #tatouage #bracelet #plume #mylittlebox\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 }\n" +
                "    ] }, \"stat\": \"ok\" }";
        JSONObject flickrSrchObj = new JSONObject(srchRsltString);

        Mockito.when(flickrService.search("test", 10, context)).thenReturn(flickrSrchObj);
        Mockito.when(flickrService.search("", context)).thenReturn(null);

        resource = new PhotoResource(flickrService);

    }

    @Test
    public void testGetEmptyPhotos() throws Exception {
        Context context = new Context();
        assertNull(resource.getPhotos(context));
    }

    @Test
    public void testGetPhotosEmptyText() throws Exception {
        context.getQueryMap().put("text", (new String[0]));
        assertNull(resource.getPhotos(context));

        String[] text = new String[1];
        text[0] = "";
        context.getQueryMap().clear();
        context.getQueryMap().put("text", text);
        assertNull(resource.getPhotos(context));
    }

    @Test
    public void testGetPhotosWithOtherParams() throws Exception {
        String[] text = new String[1];
        text[0] = "";
        context.getQueryMap().clear();
        context.getQueryMap().put("a", text);
        assertNull(resource.getPhotos(context));
    }

    @Test
    public void testGetPhotosWithMock() throws Exception {
        String[] text = new String[1];
        text[0] = "test";
        context.getQueryMap().clear();
        context.getQueryMap().put("text", text);
        List<Photo> photoList = resource.getPhotos(context);

        assertNotNull(photoList);
        assertEquals(1, photoList.size());
        assertEquals( "http://farm8.staticflickr.com/7377/9432874385_1b3ed99eb5.jpg",
                        photoList.get(0).getUrl());
    }

}