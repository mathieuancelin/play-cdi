package play.modules.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import play.modules.cdi.request.HttpRequestContext;
import play.modules.cdi.request.HttpRequestContextImpl;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PlayExtension implements Extension {

    public static HttpRequestContext requestContext;

    public PlayExtension() {
        requestContext = new HttpRequestContextImpl();
    }

    public void beforBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager manager) {
        event.addScope(RequestScoped.class, true, false);
        event.addAnnotatedType(manager.createAnnotatedType(PlayBeansProducer.class));
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event) {
        event.addContext(requestContext);
    }
}
