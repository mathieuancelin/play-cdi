package play.modules.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import play.modules.cdi.request.HttpRequestContext;
import play.modules.cdi.request.HttpRequestContextImpl;

public class PlayExtension implements Extension {

    public static HttpRequestContext requestContext;

    public PlayExtension() {
        requestContext = new HttpRequestContextImpl();
    }

    public void beforBeanDiscovery(@Observes BeforeBeanDiscovery event) {
        event.addScope(RequestScoped.class, true, false);
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event) {
        event.addContext(requestContext);
    }

}
