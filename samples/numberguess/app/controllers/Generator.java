package controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import play.mvc.Scope.Session;

@ApplicationScoped
public class Generator {

    public static final int MAX_NUMBER = 100;

    private java.util.Random random =
            new java.util.Random(System.currentTimeMillis());
    

    @Inject Session session;

    @Produces @Random
    int next() {
        return random.nextInt(MAX_NUMBER - 1) + 1;
    }

    @Produces @MaxNumber
    int getMaxNumber() {
        return MAX_NUMBER;
    }
}
