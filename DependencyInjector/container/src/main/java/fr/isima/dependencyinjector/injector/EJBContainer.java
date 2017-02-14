/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.annotations.Inject;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.factories.ContainerProxyFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
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

    @Deprecated
    private final Map<Class, Class> associatedTypes;
    
    /**
     * Constructor.
     */
    private EJBContainer()
    {
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

                // Create a proxy in place of object
                Object proxy = ContainerProxyFactory.createNewProxyFor(fieldClass);

                // Set field value
                setFieldValue(o, field, proxy);
            }
        }
    }
    
    /**
     * Set an instance into field.
     * @param instance Instance on which setting value.
     * @param field Field that wil receive value for the given instance.
     * @param value Value.
     */
    private void setFieldValue(Object instance, Field field, Object value)
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
