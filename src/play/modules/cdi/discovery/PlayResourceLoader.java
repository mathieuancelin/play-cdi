package play.modules.cdi.discovery;

import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.collections.EnumerationList;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import play.Play;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PlayResourceLoader implements ResourceLoader {

    @Override
    public Class<?> classForName(String name) {
        try {
            Class<?> clazz = Play.classloader.loadClass(name);
            Class<?> obj = clazz;
            while (obj != null && obj != Object.class) {
                obj.getDeclaredConstructors();
                obj.getDeclaredFields();
                obj.getDeclaredMethods();
                obj = obj.getSuperclass();
            }
            return clazz;
        } catch (ClassNotFoundException e) {
            throw new ResourceLoadingException(e);
        } catch (NoClassDefFoundError e) {
            throw new ResourceLoadingException(e);
        }
    }

    @Override
    public URL getResource(String name) {
        return Play.classloader.getResource(name);
    }

    @Override
    public Collection<URL> getResources(String name) {
        try {
            return new EnumerationList<URL>(Play.classloader.getResources(name));
        } catch (IOException e) {
            throw new ResourceLoadingException(e);
        }
    }

    @Override
    public void cleanup() {
        // nothing to do
    }
}

