package com.tenfood.api.service;

import com.tenfood.api.Context;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: hungadai
 * Date: 7/28/13
 * Time: 10:03 下午
 * To change this template use File | Settings | File Templates.
 */
public class FlickrServiceTest {
    @Test
    public void testGetSearchRequestURI() throws Exception {
        FlickrService service = new FlickrService();
        Context context = new Context();
        context.setQueryMap(new HashMap<String, String[]>());
        String uriString = service.getSearchRequestURI("test", 1, context);

        assertTrue(uriString.contains("method=flickr.photos.search"));
        assertTrue(uriString.contains("text=test"));
        assertTrue(uriString.contains("api_key="));
        assertTrue(uriString.contains("per_page=1"));
    }

    @Test
    public void testGetSearchRequestParamURI() throws Exception {
        FlickrService service = new FlickrService();
        Context context = new Context();
        Map<String, String[]> queryMap = new HashMap<String, String[]>();
        queryMap.put("sort", new String[] { "aaa" });
        context.setQueryMap(queryMap);
        String uriString = service.getSearchRequestURI("test", 1, context);

        assertTrue(uriString.contains("method=flickr.photos.search"));
        assertTrue(uriString.contains("text=test"));
        assertTrue(uriString.contains("api_key="));
        assertTrue(uriString.contains("per_page=1"));
        assertTrue(uriString.contains("sort=aaa"));
    }

}
