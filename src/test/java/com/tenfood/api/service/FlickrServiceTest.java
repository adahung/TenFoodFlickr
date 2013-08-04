package com.tenfood.api.service;

import com.tenfood.api.Context;
import org.testng.annotations.Test;

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
        String uriString = service.getSearchRequestURI("test");

        assertTrue(uriString.contains("method=flickr.photos.search"));
        assertTrue(uriString.contains("text=test"));
        assertTrue(uriString.contains("api_key="));
    }
}
