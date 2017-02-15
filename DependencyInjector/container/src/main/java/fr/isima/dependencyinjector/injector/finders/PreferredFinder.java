package fr.isima.dependencyinjector.injector.finders;

import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Kernelith on 15/02/2017.
 */
public class PreferredFinder
{
    /**
     * Tool to use reflection.
     */
    private static Reflections reflectionHelper;

    static
    {
        reflectionHelper = new Reflections();
    }

    /**
     * Get the preferred class matching input class if there is one.
     * @param inputClass Input class.
     * @return Corresponding class, otherwise null.
     */
    public static Class<?> getPreferredFor(Class<?> inputClass) throws TooMuchPreferredClassFound
    {
        Class<?> outputClass = null;

        Set<Class<?>> subTypes = (Set<Class<?>>) reflectionHelper.getSubTypesOf(inputClass);
        List<Class> preferredClasses = subTypes	.stream()
                                                .filter(clazz -> clazz.isAnnotationPresent(Preferred.class))
                                                .collect(Collectors.toList());

        // More than one preferred class
        if (preferredClasses.size() > 1)
        {
            throw new TooMuchPreferredClassFound();
        }
        // One preferred class
        else if (preferredClasses.size() == 1)
        {
            outputClass = preferredClasses.get(0);
        }

        return outputClass;
    }
}
