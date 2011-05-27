package play.modules.cdi.inject;

import java.lang.annotation.Annotation;
import javax.enterprise.util.TypeLiteral;

/**
 *
 * @author Mathieu ANCELIN
 */
public interface BeanSource2 {

    public <T> T getBeanOfType(Class<T> clazz, Annotation... qualifiers);

    public <T> T getBeanOfType(TypeLiteral<T> type, Annotation... qualifiers);

}