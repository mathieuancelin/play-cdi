package play.modules.cdi;

import org.jboss.weld.bootstrap.WeldBootstrap;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.manager.api.WeldManager;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.BeanManager;
import java.util.Collection;
import play.modules.cdi.discovery.PlayBeanDeploymentArchiveFactory;
import play.modules.cdi.discovery.PlayDeployment;

/**
 *
 * @author Mathieu ANCELIN
 */
public class Weld {

    private PlayDeployment deployment;
    private boolean started = false;
    private Bootstrap bootstrap;
    private boolean hasShutdownBeenCalled = false;
    private PlayBeanDeploymentArchiveFactory factory;
    private WeldManager manager;
    private Collection<String> beanClasses;

    public Weld() {
        factory = new PlayBeanDeploymentArchiveFactory();
    }

    public boolean isStarted() {
        return started;
    }

    public boolean initialize() {
        started = false;
        // hack to make jboss interceptors works.
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        // -------------
        try {
            bootstrap = new WeldBootstrap();
            deployment = createDeployment(bootstrap);
            // Set up the container
            bootstrap.startContainer(new PlayEnvironment(), deployment);
            // Start the container
            bootstrap.startInitialization();
            bootstrap.deployBeans();
            bootstrap.validateBeans();
            bootstrap.endInitialization();
            // Get this Bundle BeanManager
            manager = bootstrap.getManager(deployment.getBeanDeploymentArchive());
            beanClasses = deployment.getBeanDeploymentArchive().getBeanClasses();
            started = true;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            Thread.currentThread().setContextClassLoader(old);
        }
        return started;
    }

    private PlayDeployment createDeployment(Bootstrap bootstrap) {
        return new PlayDeployment(bootstrap, factory);
    }

    public boolean shutdown() {
        if (started) {
            synchronized (this) {
                if (!hasShutdownBeenCalled) {
                    hasShutdownBeenCalled = true;
                    try {
                        bootstrap.shutdown();
                    } catch (Throwable t) {
                    }
                    started = false;
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public Event getEvent() {
        return manager.instance().select(Event.class).get();
    }

    public BeanManager getBeanManager() {
        return manager;
    }

    public Instance<Object> getInstance() {
        return manager.instance();
    }

    public Collection<String> getBeanClasses() {
        return beanClasses;
    }
}
