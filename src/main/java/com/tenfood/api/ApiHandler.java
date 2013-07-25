package com.tenfood.api;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

public class ApiHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getRequestURI();
        Map<String, Object> queryMap = req.getParameterMap();

        resp.getWriter().print("Hello from Java!\n");
        resp.getWriter().print("Path: " + path + "\n");
        resp.getWriter().print("Query params count: " + queryMap.size());
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
