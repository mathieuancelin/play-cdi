package play.modules.cdi;

import play.PlayPlugin;
import play.inject.BeanSource;
import play.inject.Injector;

public class CDIPlugin extends PlayPlugin implements BeanSource {

    public static final Weld weld = new Weld();

    public static boolean started = false;

    @Override
    public void onApplicationStart() {
        started = weld.initialize();
        if (started) {
            Injector.inject(this);
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
    public <T> T getBeanOfType(Class<T> clazz) {
        if (started) {
            return weld.getInstance().select(clazz).get();
        } else {
            throw new IllegalStateException("CDI container not started");
        }
    }
}
