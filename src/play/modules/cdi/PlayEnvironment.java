package play.modules.cdi;

import org.jboss.weld.bootstrap.api.Environment;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ScheduledExecutorServiceFactory;

import java.util.HashSet;
import java.util.Set;

public class PlayEnvironment implements Environment {

    @Override
    public Set<Class<? extends Service>> getRequiredDeploymentServices() {
        HashSet set = new HashSet();
        set.add(ScheduledExecutorServiceFactory.class);
        return set;
    }

    @Override
    public Set<Class<? extends Service>> getRequiredBeanDeploymentArchiveServices() {
        HashSet set = new HashSet();
        set.add(ResourceLoader.class);
        return set;
    }
}