package play.modules.cdi.request;

import org.jboss.weld.context.BoundContext;
import org.jboss.weld.context.RequestContext;
import play.mvc.Http.Request;

/**
 *
 * @author Mathieu ANCELIN
 */
public interface HttpRequestContext extends BoundContext<Request>, RequestContext {
    
}
