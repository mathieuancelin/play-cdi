package play.modules.cdi.discovery;

import java.io.IOException;
import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import play.Play;
import play.classloading.ApplicationClasses.ApplicationClass;

public class PlayBeanDeploymentArchiveFactory {

    // TODO : need to scan lib dir !!!
    public BeanDeploymentArchive scan(Bootstrap bootstrap) {
        List<String> discoveredClasses = new ArrayList<String>();
        List<URL> discoveredBeanXmlUrls = new ArrayList<URL>();
        Enumeration beansXml = null;
        try {
            beansXml = Play.classloader.getResources("conf/beans.xml");
        } catch (IOException ex) {
            beansXml = Collections.enumeration(Collections.emptyList());
        }
        if (beansXml != null) {
            while (beansXml.hasMoreElements()) {
                discoveredBeanXmlUrls.add((URL) beansXml.nextElement());
            }
            for (ApplicationClass clazz : Play.classes.all()) {
                discoveredClasses.add(clazz.name);
            }
        }
        PlayBeanDeploymentArchive archive =
                new PlayBeanDeploymentArchive("play-bean-deployment-archive");
        archive.setBeansXml(bootstrap.parse(discoveredBeanXmlUrls));
        archive.setBeanClasses(discoveredClasses);
        return archive;
    }
}
