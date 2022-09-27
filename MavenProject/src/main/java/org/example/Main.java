package org.example;
import org.tinylog.Logger;

import static spark.Spark.*;

public class Main {
    private static boolean authentication = false;

    public static void main(String[] args) {
        // Set up the port
        port(8080);

        after("/*", (req, res) -> Logger.info("[{}] {} {} - {}", req.ip(), req.requestMethod(), req.pathInfo(), res.status()));
        get("/hello", (req, res) -> {
            Logger.info("User entered {0}", req.ip());
            return "CIAO!";
        });

        before("/protected", (request, response) -> {
            // check if user is "authenticated"
            if (!authentication) {
                Logger.warn("User {} is not authenticated", request.ip());
                halt(401, "You are not welcome here...");
            }

        });
    }
}