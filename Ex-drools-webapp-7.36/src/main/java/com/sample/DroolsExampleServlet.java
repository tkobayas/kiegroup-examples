package com.sample;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(
    urlPatterns = "/hello",
    loadOnStartup = 1
)
public class DroolsExampleServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DroolsExampleServlet.class.getName());

    private static final KieContainer kContainer = KieServices.Factory.get().getKieClasspathContainer();

    @Override
    public void init() throws ServletException {
        LOGGER.info("init() is invoked");
        try {
            // Create and destroy the first KieSession in init() with load-on-startup.
            // This can improve the response time for the first request.
            KieSession kSession = kContainer.newKieSession("ksession-rules");
            if (kSession != null) {
                kSession.dispose();
            }
        } catch(Exception e){
            LOGGER.log(Level.SEVERE, "Exception occurred in init()", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("doGet() is invoked");
        KieSession kSession = null;
        try {
            kSession = kContainer.newKieSession("ksession-rules");

            Message message = new Message();
            message.setMessage("Hello");
            if (request.getParameter("user") != null) {
                message.setUser(request.getParameter("user"));
            } else {
                message.setUser("world");
            }
            message.setStatus(Message.HELLO);
            kSession.insert(message);
            kSession.fireAllRules();

            PrintWriter writer = response.getWriter();
            writer.println("<html>");
            writer.println(" <head>");
            writer.println("  <title>DroolsExampleServlet</title>");
            writer.println(" </head>");
            writer.println(" <body>");
            writer.println(" <h1>");
            writer.println("   message = " + message.getMessage());
            writer.println(" </h1>");
            writer.println(" </body>");
            writer.println("</html>");
            writer.close();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception occurred during the request processing", e);
            throw e;
        } finally {
            if (kSession != null) {
                LOGGER.log(Level.FINE, "kSession.dispose()");
                kSession.dispose();
            }
        }

    }

}
