/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.annotations.Behaviour;
import fr.isima.dependencyinjector.interceptor.IInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kernelith
 */
public class ContainerInvocationHandler implements InvocationHandler
{
    private Object m_object;

    public ContainerInvocationHandler(Object object)
    {
        m_object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
    {
        Object ret = null;

        // Get method interceptors
        Class objectClass = m_object.getClass();
        // Get corresponding method called in real object
        Method calledMethod = objectClass.getMethod(method.getName(), method.getParameterTypes());

        List<IInterceptor> interceptors = getMethodInterceptors(calledMethod);
        try
        {
            for (IInterceptor i : interceptors)
                i.before(proxy, method, args);

            // Real method call
            ret = method.invoke(m_object, args);

            for (IInterceptor i : interceptors)
                i.after(proxy, method, args);
        }
        catch (Exception e)
        {
            for (IInterceptor i : interceptors)
                i.onError(proxy, method, args);
        }
        
        return ret;
    }

    private List<IInterceptor> getMethodInterceptors(Method method)
    {
        List<IInterceptor> interceptors = new ArrayList<>();
        for (Annotation annotation : method.getAnnotations())
        {
            // Check annotations of current annotation => search a behaviour annotation
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Behaviour.class))
            {
                Behaviour behaviourAnnotation = annotationType.getAnnotation(Behaviour.class);
                Class<? extends IInterceptor> interceptorClass = behaviourAnnotation.interceptor();

                try
                {
                    IInterceptor interceptor = interceptorClass.newInstance();
                    try
                    {
                        EJBContainer.getInjector().inject(interceptor);
                        interceptors.add(interceptor);
                    }
                    catch ( TooMuchPreferredClassFound
                            | TooMuchConcreteClassFound e)
                    {
                        // Problem impossible to inject interceptors dependencies
                        Logger.getLogger(EJBContainer.class.getName()).log( Level.SEVERE,
                                                                            "Container could not determine type while performing injection for " + interceptor.getClass().getName(),
                                                                            e);
                    }
                    catch (NoConcreteClassFound e)
                    {
                        // Problem impossible to inject interceptors dependencies
                        Logger.getLogger(EJBContainer.class.getName()).log( Level.SEVERE,
                                                                            "Container could not find type to use while performing injection for " + interceptorClass.getName(),
                                                                            e);
                    }

                }
                catch (InstantiationException | IllegalAccessException e)
                {
                    // Problem impossible to instantiate interceptors
                    Logger.getLogger(EJBContainer.class.getName()).log( Level.SEVERE,
                                                                        "Impossible to instantiate interceptor of type " + interceptorClass.getName(),
                                                                        e);
                }
            }
        }

        return interceptors;
    }

    public Object getObject()
    {
        return m_object;
    }
}
