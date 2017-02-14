/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.annotations.Singleton;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.interceptor.IInterceptor;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * @author kernelith
 */
public final class EJBContainer 
{
    /**
     * Instance.
     */
    private static EJBContainer instance;
    
    /**
     * Map of all singleton instances instantiated.
     */
    private final Map<Class, Object> singletonInstances;
    
    @Deprecated
    private final Map<Class, Class> associatedTypes;
    
    /**
     * Constructor.
     */
    private EJBContainer()
    {
        singletonInstances = new HashMap<>();
        
        associatedTypes = new HashMap<>();
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
     * @throws TooMuchPreferredClassFound Too much prefered class found while trying to perform injection.
     * @throws TooMuchConcreteClassFound Too much concrete class found while trying to perform injection.
     */
    public void inject(Object o) throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
    {
        Class objectClass = o.getClass();
        
        // For each class field
        for (Field field : objectClass.getDeclaredFields())
        {
            // If field require injection
            if (field.isAnnotationPresent(Inject.class))
            {
                // Search the class that will be instantiate
                Class fieldClass = field.getType();
                Class targetClass = ClassFinder.findClassFor(fieldClass);
                
                // If type found to resolve injection
                if (targetClass != null)
                {
                    // Case of class that is annoted Singleton
                    if (targetClass.isAnnotationPresent(Singleton.class))
                    {
                        // If singleton already instantiated
                        if (singletonInstances.containsKey(targetClass))
                        {
                            setFieldValue(o, field, singletonInstances.get(targetClass));
                        }
                        // No instance for singleton => instantiate it
                        else
                        {
                            Object newInstance = instantiateType(o, field, targetClass);
                            singletonInstances.put(targetClass, newInstance);
                        }
                    }
                    // Normal case
                    else
                    {
                        instantiateType(o, field, targetClass);
                    }
                }
            }
        }
    }
    
    /**
     * Instantiate an object for the given field of given type.
     * @param instance Instance on which setting value.
     * @param field Field concerned for instanciation.
     * @param targetClass Target class to instanciate.
     * @return Instantiated object.
     */
    private Object instantiateType(Object instance, Field field, Class targetClass) throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
    {
        Object instantiatedProxy = null;
        try
        {
            Object instantiatedObject = targetClass.newInstance();
            instantiatedProxy = Proxy.newProxyInstance( targetClass.getClassLoader(),
                                                        targetClass.getInterfaces(),
                                                        new ContainerInvocationHandler(instantiatedObject));

            // Recursive injections
            inject(instantiatedObject);

            setFieldValue(instance, field, instantiatedProxy);
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            Logger.getLogger(EJBContainer.class.getName()).log( Level.SEVERE,
                                                                "Impossible to instantiate type " + targetClass.getName() + " for field " + field.getName() + " for object of type " + instance.getClass().getName(),
                                                                e);
        }
        
        return instantiatedProxy;
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
            Logger.getLogger(EJBContainer.class.getName()).log( Level.SEVERE,
                                                                "Impossible to set field value of field " + field.getName() + " for object of type " + instance.getClass().getName(),
                                                                ex);
        }

        field.setAccessible(accessible);
    }
}
