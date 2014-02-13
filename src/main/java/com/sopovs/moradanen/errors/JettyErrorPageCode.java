package com.sopovs.moradanen.errors;

import java.net.InetSocketAddress;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class JettyErrorPageCode {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler();

        context.addServlet(HelloServlet.class, "/hello");
        context.addServlet(BangServlet.class, "/bang");

        ErrorPageErrorHandler errorHandler = new ErrorPageErrorHandler();
        errorHandler.addErrorPage(500, "/hello");
        context.setErrorHandler(errorHandler);

        Server server = new Server(new InetSocketAddress("localhost", 8080));
        server.setHandler(context);
        server.start();
        server.join();

    }

}
