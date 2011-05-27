package play.modules.cdi.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.util.TypeLiteral;
import javax.inject.Inject;
import javax.inject.Qualifier;
import play.classloading.enhancers.ControllersEnhancer.ControllerSupport;
import play.jobs.Job;
import play.mvc.Mailer;
import play.Play;

/**
 *
 * @author Mathieu ANCELIN
 */
public class Injector2 {
    
    /**
     * For now, inject beans in controllers
     */
    public static void inject(BeanSource2 source) {
        List<Class> classes = Play.classloader.getAssignableClasses(ControllerSupport.class);
        classes.addAll(Play.classloader.getAssignableClasses(Mailer.class));
        classes.addAll(Play.classloader.getAssignableClasses(Job.class));
        for(Class<?> clazz : classes) {
            for(Method method : clazz.getDeclaredMethods()) {
                if(Modifier.isStatic(method.getModifiers()) && method.isAnnotationPresent(Inject.class)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    // TODO : use TypeLiteral when generic params
                    Type[] genericParameterTypes = method.getGenericParameterTypes();
                    Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                    Object[] parameters = new Object[parameterTypes.length];
                    for (int j = 0; j < parameterTypes.length; j++) {
                        Object value = null;
                        if (genericParameterTypes[j] instanceof ParameterizedType) {
                            try {
                                value = source.getBeanOfType(
                                        new ProgrammaticTypeLiteral(genericParameterTypes[j]),
                                        getQualifiers(parameterAnnotations[j]));
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                        if (value == null) {
                            value = source.getBeanOfType(parameterTypes[j],
                                    getQualifiers(parameterAnnotations[j]));
                        }
                        parameters[j] = value;
                    }
                    boolean accessible = method.isAccessible();
                    // set a private method as public method to invoke it
                    if (!accessible) {
                        method.setAccessible(true);
                    }
                    // invocation of the method with rights parameters
                    try {
                        method.invoke(null, parameters);
                    } catch(RuntimeException e) {
                        throw e;
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        // if method was private, then put it private back
                        if (!accessible) {
                            method.setAccessible(accessible);
                        }
                    }
                }
            }
            for(Field field : clazz.getDeclaredFields()) {
                if(Modifier.isStatic(field.getModifiers()) && field.isAnnotationPresent(Inject.class)) {
                    Class<?> type = field.getType();
                    Type genericType = field.getGenericType();
                    Object value = null;
                    if (genericType instanceof ParameterizedType) {
                        try {
                            value = source.getBeanOfType(new ProgrammaticTypeLiteral(genericType),
                                    getQualifiers(field.getAnnotations()));
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                    field.setAccessible(true);
                    try {
                        if (value == null) {
                            value = source.getBeanOfType(type, getQualifiers(field.getAnnotations()));
                        }
                        field.set(null, value);
                    } catch(RuntimeException e) {
                        throw e;
                    } catch(Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private static Annotation[] getQualifiers(Annotation[] annotations) {
        List<Annotation> qualifiers = new ArrayList<Annotation>();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                qualifiers.add(annotation);
            }
        }
        return qualifiers.toArray(new Annotation[qualifiers.size()]);
    }

    private static class ProgrammaticTypeLiteral extends TypeLiteral {

        public ProgrammaticTypeLiteral(Type type) {
            super();
            try {
                Field f = TypeLiteral.class.getDeclaredField("actualType");
                f.setAccessible(true);
                f.set(this, type);
                f.setAccessible(false);
            } catch (Exception ex) {
                throw new RuntimeException("Can't create TypeLiteral for " + type, ex);
            }
        }
    }
}
