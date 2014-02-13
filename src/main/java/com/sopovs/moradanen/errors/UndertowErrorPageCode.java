package com.sopovs.moradanen.errors;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import io.undertow.Undertow;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ErrorPage;

import javax.servlet.ServletException;

public class UndertowErrorPageCode {
    public static void main(String[] args) throws ServletException {
        DeploymentInfo servletBuilder = deployment();
        servletBuilder.addServlet(Servlets.servlet("hello", HelloServlet.class).addMapping("/hello"));
        servletBuilder.addServlet(Servlets.servlet("bang", BangServlet.class).addMapping("/bang"));
        servletBuilder.addErrorPage(new ErrorPage("/hello", 500));
        servletBuilder.setDeploymentName("foobar");
        servletBuilder.setContextPath("");
        servletBuilder.setClassLoader(UndertowErrorPageCode.class.getClassLoader());

        DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        Undertow undertow = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(manager.start()).build();
        undertow.start();

    }

}
