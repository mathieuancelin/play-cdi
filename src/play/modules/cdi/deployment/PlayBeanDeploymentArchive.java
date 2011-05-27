package play.modules.cdi.deployment;

import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.bootstrap.api.helpers.SimpleServiceRegistry;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.ejb.spi.EjbDescriptor;

import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PlayBeanDeploymentArchive implements BeanDeploymentArchive {

    private String id;
    private Collection<String> beanClasses;
    private BeansXml beansXml;
    private ServiceRegistry serviceRegistry;

    public PlayBeanDeploymentArchive(String id) {
        this.id = id;
        this.serviceRegistry = new SimpleServiceRegistry();
    }

    @Override
    public Collection<String> getBeanClasses() {
        return beanClasses;
    }

    @Override
    public Collection<BeanDeploymentArchive> getBeanDeploymentArchives() {
        return Collections.emptyList();
    }

    @Override
    public BeansXml getBeansXml() {
        return beansXml;
    }

    @Override
    public Collection<EjbDescriptor<?>> getEjbs() {
        return Collections.emptyList();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ServiceRegistry getServices() {
        return serviceRegistry;
    }

    public void setBeanClasses(Collection<String> classes) {
        this.beanClasses = Collections.unmodifiableCollection(classes);
    }

    public void setBeansXml(BeansXml beansXml) {
        this.beansXml = beansXml;
    }
}

