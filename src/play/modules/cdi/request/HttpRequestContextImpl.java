package play.modules.cdi.request;

import java.lang.annotation.Annotation;

import javax.enterprise.context.RequestScoped;

import org.jboss.weld.context.AbstractBoundContext;
import org.jboss.weld.context.beanstore.SimpleNamingScheme;
import org.jboss.weld.context.beanstore.NamingScheme;
import play.mvc.Http.Request;

/**
 *
 * @author Mathieu ANCELIN
 */
public class HttpRequestContextImpl extends AbstractBoundContext<Request> implements HttpRequestContext {

    private static final String IDENTIFIER = HttpRequestContextImpl.class.getName();
    private final NamingScheme namingScheme;

    /**
     * Constructor
     */
    public HttpRequestContextImpl() {
        super(false);
        this.namingScheme = new SimpleNamingScheme(HttpRequestContextImpl.class.getName());
    }

    @Override
    public boolean associate(Request request) {
        if (request.args.get(IDENTIFIER) == null) {
            request.args.put(IDENTIFIER, IDENTIFIER);
            setBeanStore(new RequestBeanStore(request, namingScheme));
            getBeanStore().attach();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean dissociate(Request request) {
        if (request.args.get(IDENTIFIER) != null) {
            try {
                setBeanStore(null);
                request.args.remove(IDENTIFIER);
                return true;
            } finally {
                cleanup();
            }
        } else {
            return false;
        }

    }

    @Override
    public Class<? extends Annotation> getScope() {
        return RequestScoped.class;
    }
}
