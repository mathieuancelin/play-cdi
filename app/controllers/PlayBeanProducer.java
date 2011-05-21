package controllers;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import play.data.validation.Validation;
import play.db.jpa.JPA;
import play.mvc.Http;
import play.mvc.Scope;

/**
 * Class for Play specific CDI producers
 */
public class PlayBeanProducer {

    @Produces @RequestScoped
    public EntityManager getEm() {
        return JPA.em();
    }

    @Produces @RequestScoped
    public Http.Request request() {
        return Http.Request.current();
    }

    @Produces @RequestScoped
    public Http.Response response() {
        return Http.Response.current();
    }

    @Produces @RequestScoped
    public Scope.Session session() {
        return Scope.Session.current();
    }

    @Produces @RequestScoped
    public Scope.Flash flash() {
        return Scope.Flash.current();
    }

    @Produces @RequestScoped
    public Scope.Params params() {
        return Scope.Params.current();
    }

    @Produces @RequestScoped
    public Scope.RenderArgs renderArgs() {
        return Scope.RenderArgs.current();
    }

    @Produces @RequestScoped
    public Scope.RouteArgs routeArgs() {
        return Scope.RouteArgs.current();
    }

    @Produces @RequestScoped
    public Validation validation() {
        return Validation.current();
    }
}
