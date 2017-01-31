/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferedClassFound;
import fr.isima.dependencyinjector.injector.annotations.Inject;
import fr.isima.dependencyinjector.injector.annotations.Prefered;
import fr.isima.dependencyinjector.injector.annotations.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

// 3 projets
// tests
// conteneur
// plugins

/**
 *
 * @author alraberin1
 */
public class EJBContainer 
{
    private static EJBContainer instance;
    
    private final Map<Class, Object> singletonInstances;
    private final Map<Class, Class> associatedTypes;
    
    private EJBContainer()
    {
        singletonInstances = new HashMap();
        associatedTypes = new HashMap();
    }
    
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
                
                Reflections reflections = new Reflections("fr.isima");
                Set< Class<?> > subTypes = reflections.getSubTypesOf(fieldClass);
                
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
    
    private <T> T instanciateType(Object instance, Field field, Class<T> targetClass) throws NoConcreteClassFound, TooMuchPreferedClassFound, TooMuchConcreteClassFound
    {
        T instanciatedObject = null;
        
        // Try getting default constructor
        try
        {
            Constructor defaultConstructor = targetClass.getConstructor();
            if (defaultConstructor != null)
            {
                // If possible instanciate type
                instanciatedObject = (T) defaultConstructor.newInstance();
                
                // Recursive injections
                inject(instanciatedObject);
                
                setFieldValue(instance, field, instanciatedObject);
            }
        } 
        catch ( IllegalAccessException | IllegalArgumentException 
                | InstantiationException | NoSuchMethodException 
                | SecurityException | InvocationTargetException ex) 
        {
            Logger.getLogger(EJBContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return instanciatedObject;
    }
    
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
