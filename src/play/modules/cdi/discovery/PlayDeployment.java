package play.modules.cdi.discovery;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.resources.spi.ResourceLoader;

import java.util.Collections;
import java.util.List;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.bootstrap.spi.Metadata;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PlayDeployment implements Deployment {

    private final BeanDeploymentArchive beanDeploymentArchive;
    private final ServiceRegistry serviceRegistry;
    private final Iterable<Metadata<Extension>> extensions;

    public PlayDeployment(Bootstrap bootstrap, PlayBeanDeploymentArchiveFactory factory) {
        this.serviceRegistry = new SimpleServiceRegistry();
        this.extensions = bootstrap.loadExtensions(getClass().getClassLoader());
        this.beanDeploymentArchive = factory.scan(bootstrap);
        this.beanDeploymentArchive.getServices().add(ResourceLoader.class, new PlayResourceLoader());
    }

    @Override
    public ServiceRegistry getServices() {
        return serviceRegistry;
    }

    @Override
    public Iterable<Metadata<Extension>> getExtensions() {
        return extensions;
    }

    @Override
    public List<BeanDeploymentArchive> getBeanDeploymentArchives() {
        return Collections.singletonList(beanDeploymentArchive);
    }

    @Override
    public BeanDeploymentArchive loadBeanDeploymentArchive(Class<?> beanClass) {
        return beanDeploymentArchive;
    }

    public BeanDeploymentArchive getBeanDeploymentArchive() {
        return beanDeploymentArchive;
    }
}
