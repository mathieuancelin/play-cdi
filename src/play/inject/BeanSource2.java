package play.inject;

import java.lang.annotation.Annotation;

public interface BeanSource2 {

    public <T> T getBeanOfType(Class<T> clazz, Annotation... qualifiers);

}