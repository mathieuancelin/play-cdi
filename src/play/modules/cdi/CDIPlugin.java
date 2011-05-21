package play.modules.cdi;

import java.lang.annotation.Annotation;
import play.PlayPlugin;
import play.inject.BeanSource2;
import play.inject.Injector2;
import play.mvc.Http;

public class CDIPlugin extends PlayPlugin implements BeanSource2 {

    public static final Weld weld = new Weld();
    public static boolean started = false;

    @Override
    public void onApplicationStart() {
        started = weld.initialize();
        if (started) {
            Injector2.inject(this);
        } else {
            throw new IllegalStateException("CDI container can't start");
        }
    }

    @Override
    public void onApplicationStop() {
        if (started) {
            weld.shutdown();
        }
    }

    @Override
    public <T> T getBeanOfType(Class<T> clazz, Annotation... qualifiers) {
        if (started) {
            return weld.getInstance().select(clazz, qualifiers).get();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }

    @Override
    public void beforeInvocation() {
        PlayExtension.requestContext.associate(Http.Request.current.get());
        PlayExtension.requestContext.activate();
    }

    @Override
    public void afterInvocation() {
        try {
            PlayExtension.requestContext.invalidate();
            PlayExtension.requestContext.deactivate();
        } finally {
            PlayExtension.requestContext.dissociate(Http.Request.current.get());
        }
    }
}
