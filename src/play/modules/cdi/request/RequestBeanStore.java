package play.modules.cdi.request;

import java.util.Collection;
import java.util.Enumeration;

import org.jboss.weld.context.beanstore.AttributeBeanStore;
import org.jboss.weld.context.beanstore.NamingScheme;
import org.jboss.weld.util.collections.EnumerationList;
import org.jboss.weld.util.reflection.Reflections;
import play.mvc.Http.Request;

public class RequestBeanStore extends AttributeBeanStore {

    private final Request request;

    public RequestBeanStore(Request request, NamingScheme namingScheme) {
        super(namingScheme);
        this.request = request;
    }

    @Override
    protected Object getAttribute(String key) {
        return request.args.get(key);
    }

    @Override
    protected void removeAttribute(String key) {
        request.args.remove(key);
    }

    @Override
    protected Collection<String> getAttributeNames() {
        return new EnumerationList<String>(Reflections.<Enumeration<String>>cast(request.args.keySet()));
    }

    @Override
    protected void setAttribute(String key, Object instance) {
        request.args.put(key, instance);
    }

    @Override
    public boolean attach() {
        // Doesn't support detachment
        return false;
    }

    @Override
    public boolean detach() {
        return false;
    }

    @Override
    public boolean isAttached() {
        // Doesn't support detachment
        return true;
    }
}
