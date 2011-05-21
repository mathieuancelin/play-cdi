package play.inject;

import java.lang.annotation.Annotation;

/**
 *
 * @author Mathieu ANCELIN
 */
public interface BeanSource2 {

    public <T> T getBeanOfType(Class<T> clazz, Annotation... qualifiers);

}