/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.annotations.Log;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.annotations.Behaviour;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.annotations.Prefered;
import fr.isima.dependencyinjector.injector.annotations.Singleton;
import fr.isima.dependencyinjector.injector.interceptor.IInterceptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

// TODO list
// @Inject
// @Singleton
// classloader il se debrouille et utilise le bon en regardant ce qui est dispo => gestion des erreurs
// @Preferred
// Cascade
// @Log ajouter un log en début et fin d'exec d'une méthode annotée
// @Transactional   -Required
//                  -Require_new

/**
 * Handle the mechanism of dependency injection.
 * @author alraberin1
 */
public final class EJBContainer 
{
    /**
     * Instance.
     */
    private static EJBContainer instance;
    
    /**
     * Map of all singleton instances instaciated.
     */
    private final Map<Class, Object> singletonInstances;
    
    /**
     * Map of all interceptors.
     */
    private final Map<Class, IInterceptor> interceptors;
    
    /**
     * Tool to use reflection.
     */
    private Reflections reflectionHelper;
    
    @Deprecated
    private final Map<Class, Class> associatedTypes;
    
    /**
     * Constructor.
     */
    private EJBContainer()
    {
        singletonInstances = new HashMap();
        interceptors = new HashMap();
        
        reflectionHelper = new Reflections("");
        
        // Instanciate all interceptors that are usable
        Set< Class<? extends IInterceptor> > interceptorsClasses = reflectionHelper.getSubTypesOf(IInterceptor.class);
        for (Class<? extends IInterceptor> interceptorClass : interceptorsClasses)
        {
            try 
            {
                IInterceptor interceptor = interceptorClass.newInstance();
                try 
                {
                    inject(interceptor);
                    interceptors.put(interceptorClass, interceptor);
                } 
                catch ( NoConcreteClassFound 
                        | TooMuchPreferedClassFound 
                        | TooMuchConcreteClassFound ex) 
                {
                    // Problem impossible to inject interceptors dependencies
                    Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
            catch (InstantiationException | IllegalAccessException ex) 
            {
                // Problem impossible to instanciate interceptors
                Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        associatedTypes = new HashMap();
    }
    
    /**
     * Get Container instance.
     * @return Instance.
     */
    public static EJBContainer getInjector()
    {
        if (instance == null)
            instance = new EJBContainer();
        
        return instance;
    }
    
    // LEGACY code for hard coded tests
    @Deprecated
    public void registerType(Class sourceType, Class typeToInject)
    {
        associatedTypes.put(sourceType, typeToInject);
    }
    
    // LEGACY code for hard coded tests
    @Deprecated
    public <T> Class<T> resolveType(Class<T> type)
    {
        return associatedTypes.get(type);
    }
    
    /**
     * Handle mechanism of dependancy injection on the given object.
     * @param o Object that will have dependency injections.
     * @throws NoConcreteClassFound No concrete class found while trying to perform injection.
     * @throws TooMuchPreferedClassFound Too much prefered class found while trying to perform injection.
     * @throws TooMuchConcreteClassFound Too much concrete class found while trying to perform injection.
     */
    public void inject(Object o) throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound
    {
        Class objectClass = o.getClass();
        
        // For each class field
        for (Field field : objectClass.getDeclaredFields())
        {
            // If field require injection
            if (field.isAnnotationPresent(Inject.class))
            {
                // Search the class that will be instanciate
                Class targetClass = null;
                Class fieldClass = field.getType();
                
                Set< Class<?> > subTypes = reflectionHelper.getSubTypesOf(fieldClass);
                
                // No Implementations found
                if (subTypes.size() <= 0)
                {
                    throw new NoConcreteClassFound();
                }
                // More than one possibility
                else if (subTypes.size() > 1)
                {
                    List<Class> preferedClass = new ArrayList();
                    for (Class<?> c : subTypes)
                    {
                        if (c.isAnnotationPresent(Prefered.class))
                        {
                            preferedClass.add(c);
                        }
                    }
                    
                    // More than one prefered class
                    if (preferedClass.size() > 1)
                    {
                        throw new TooMuchPreferedClassFound();
                    }
                    // One prefered class
                    else if (preferedClass.size() == 1)
                    {
                        targetClass = preferedClass.get(0);
                    }
                    // No Prefered class but too much class found
                    else
                    {
                        throw new TooMuchConcreteClassFound();
                    }
                }
                // Only one implementation found
                else
                {
                    Iterator it = subTypes.iterator();
                    targetClass = (Class) it.next();
                }
                
                // If type found to resolve injection
                if (targetClass != null)
                {
                    // Case of class that is annoted Singleton
                    if (targetClass.isAnnotationPresent(Singleton.class))
                    {
                        // If singleton already instanciated
                        if (singletonInstances.containsKey(targetClass))
                        {
                            setFieldValue(o, field, singletonInstances.get(targetClass));
                        }
                        // No instance for singleton => instanciate it
                        else
                        {
                            Object newInstance = instanciateType(o, field, targetClass);
                            singletonInstances.put(targetClass, newInstance);
                        }
                    }
                    // Normal case
                    else
                    {
                        instanciateType(o, field, targetClass);
                    }
                }
            }
        }
    }
    
    /**
     * Instanciate an object for the given field of given type.
     * @param <T> Type of instanciated object.
     * @param instance Instance on which setting value.
     * @param field Field concerned for instanciation.
     * @param targetClass Target class to instanciate.
     * @return Instanciated object.
     */
    private <T> T instanciateType(Object instance, Field field, Class<T> targetClass) throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound
    {
        T instanciatedObject = null;
        
        // Try getting default constructor
        try
        {
            // Class has methods annoted with @Log
            Set<Method> logMethods = ReflectionUtils.getAllMethods(targetClass, ReflectionUtils.withAnnotation(Log.class));
            
            // If there is at least one method annoted => require proxy handling
            if (logMethods.size() > 0)
            {
                // Create proxy factory
                ProxyFactory factory = new ProxyFactory();
                factory.setSuperclass(targetClass);
                // Filter only annoted methods
                factory.setFilter(new MethodFilter() {
                    @Override
                    public boolean isHandled(Method m) 
                    {
                        return m.isAnnotationPresent(Log.class);
                    }
                });
                
                // Get interceptor to use
                Behaviour behaviourAnnotation = Log.class.getAnnotation(Behaviour.class);
                Class<? extends IInterceptor> interceptorClass = behaviourAnnotation.interceptor();
                
                // Interceptor class found
                if (interceptors.containsKey(interceptorClass))
                {
                    IInterceptor interceptor = interceptors.get(interceptorClass);
                    ContainerMethodHandler containerMethodHandler = new ContainerMethodHandler(interceptor);
                    
                    // Create proxy with default constructor
                    instanciatedObject = (T) factory.create(new Class<?>[0], new Object[0], containerMethodHandler);
                }
                // Else cannot set interceptor...
                else
                {
                    Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, "Cannot set interceptor of type {0}", interceptorClass.getName());
                }
            }
            // Classic instantiation with default constructor
            else
            {
                Constructor defaultConstructor = targetClass.getConstructor();
                if (defaultConstructor != null)
                {
                    // If possible instantiate type
                    instanciatedObject = (T) defaultConstructor.newInstance();
                }
            }
            
            // Recursive injections
            inject(instanciatedObject);

            setFieldValue(instance, field, instanciatedObject);
        } 
        catch ( IllegalAccessException | IllegalArgumentException 
                | InstantiationException | NoSuchMethodException 
                | SecurityException | InvocationTargetException ex) 
        {
            Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return instanciatedObject;
    }
    
    /**
     * Set an instance into field.
     * @param <T> Field value type.
     * @param instance Instance on which setting value.
     * @param field Field that wil receive value for the given instance.
     * @param value Value.
     */
    private <T> void setFieldValue(Object instance, Field field, T value)
    {
        boolean accessible = field.isAccessible();
        if (!accessible)
        {
            field.setAccessible(true);
        }

        try 
        {
            field.set(instance, value);
        } 
        catch (IllegalArgumentException | IllegalAccessException ex) 
        {
            Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, null, ex);
        }

        field.setAccessible(accessible);
    }
}
