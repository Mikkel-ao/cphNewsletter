package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.NewsletterController;
import app.controllers.SubscriberController;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cphNewsletter";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static final NewsletterController newsletterController = new NewsletterController(connectionPool);
    private static final SubscriberController subscriberController = new SubscriberController(connectionPool);


    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx ->  ctx.render("index.html"));
        app.post("/cphNewsletters/subscribe", ctx -> subscriberController.subscribe(ctx));
        app.get("/cphNewsletters/addNewsletter", ctx -> ctx.render("add_newsletter.html"));
        app.post("/cphNewsletter/Upload", ctx -> newsletterController.addNewsletter(ctx));
    }

}