package app.controllers;

import app.entities.Subscriber;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.NewsletterMapper;
import app.persistence.SubscriberMapper;
import io.javalin.http.Context;
import java.util.List;


public class SubscriberController {
    private static ConnectionPool connectionPool;

    public SubscriberController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void subscribe(Context ctx) throws DatabaseException {
        String email = ctx.formParam("email");
        String message = "";
        if (email != null) {
            int result = SubscriberMapper.subscribe(email, connectionPool );
            if (result == 1) {
                message = "Du er nu tilmeldt nyhedsbrevet";
            } else if (result == 0) {
                message = "Mailadressen er allerede tilmeldt";
            }
            ctx.attribute("message", message);
            ctx.render("index.html");
        }
    }
}
