package com.tenfood.api;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

import com.tenfood.api.resource.PhotoResource;

public class ApiHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Context context = new Context();
        String path = req.getRequestURI();
        Map<String, String[]> queryMap = req.getParameterMap();
        Object result = null;

        if ( path.equals("/photo") ) {
            PhotoResource resource = new PhotoResource();
            result = resource.getPhotos(queryMap, context);
        }

        resp.getWriter().println("Hello from TenFood!");
        resp.getWriter().println("Path: " + path);
        resp.getWriter().println("Query params count: " + queryMap.size());
        resp.getWriter().println(new ObjectMapper().writeValueAsString(result));
        resp.getWriter().println(new ObjectMapper().writeValueAsString(context.getMessages()));
    }

    public static void main(String[] args) throws Exception{
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new ApiHandler()),"/*");
        server.start();
        server.join();   
    }
}
