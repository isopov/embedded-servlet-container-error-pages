package com.sopovs.moradanen.errors;

import java.io.File;
import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.ErrorPage;
import org.apache.catalina.startup.Tomcat;

public class TomcatErrorPageCode {
    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("/", createTempFolder());
        context.setName("error-page");

        Wrapper helloServlet = context.createWrapper();
        helloServlet.setServletClass(HelloServlet.class.getName());
        helloServlet.setName("hello");
        context.addChild(helloServlet);
        context.addServletMapping("/hello", "hello");

        Wrapper bangServlet = context.createWrapper();
        bangServlet.setServletClass(BangServlet.class.getName());
        bangServlet.setName("bang");
        context.addChild(bangServlet);
        context.addServletMapping("/bang", "bang");
        
        
        ErrorPage errorPage = new ErrorPage();
        errorPage.setErrorCode(500);
        errorPage.setLocation("/hello");
        context.addErrorPage(errorPage);
        

        tomcat.start();
        tomcat.getServer().await();

    }

    private static String createTempFolder() throws IOException {

        File tempFolder = File.createTempFile("tomcat", "error-page");
        tempFolder.delete();
        tempFolder.mkdir();
        tempFolder.deleteOnExit();
        return tempFolder.getAbsolutePath();

    }
}
