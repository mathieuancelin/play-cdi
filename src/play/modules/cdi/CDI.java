package play.modules.cdi;

import java.lang.annotation.Annotation;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;

public class CDI {
    
    public static <T> T getBeanOfType(Class<T> type, Annotation... qualifiers) {
        if (CDIPlugin.started) {
            return CDIPlugin.weld.getInstance().select(type, qualifiers).get();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }

    public Event getEvent() {
        if (CDIPlugin.started) {
            return CDIPlugin.weld.getEvent();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }

    public BeanManager getBeanManager() {
        if (CDIPlugin.started) {
            return CDIPlugin.weld.getBeanManager();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }

    public Instance<Object> getInstance() {
        if (CDIPlugin.started) {
            return CDIPlugin.weld.getInstance();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }
}
