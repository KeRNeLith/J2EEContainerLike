package fr.isima.dependencyinjector.injector.finders;

import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by kernelith on 14/02/17.
 */
public class ClassFinder
{
	/**
	 * Tool to use reflection.
	 */
	private static Reflections reflectionHelper;

	/**
	 * Cache map of already treated classes.
	 */
	private static Map<Class, Class> cacheClassAssociations;

	static
	{
		reflectionHelper = new Reflections();

		cacheClassAssociations = new HashMap<>();
	}

	/**
	 * Find the class that resolve input class.
	 * @param inputClass Input class.
	 * @return Class that resolve input class.
	 */
	public static Class<?> findClassFor(Class<?> inputClass) throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
	{
		Class<?> outputClass = null;

		// Class already cached
		if (cacheClassAssociations.containsKey(inputClass))
		{
			outputClass = cacheClassAssociations.get(inputClass);
		}
		else
		{
			outputClass = findClass(inputClass);
			cacheClassAssociations.put(inputClass, outputClass);
		}

		return outputClass;
	}

	/**
	 * Find the corresponding class matching input class.
	 * @param inputClass Input class.
	 * @return Corresponding class.
	 */
	private static Class<?> findClass(Class<?> inputClass) throws TooMuchPreferredClassFound, TooMuchConcreteClassFound, NoConcreteClassFound
	{
		Class<?> outputClass = null;

		if (inputClass.isInterface())
		{
			outputClass = PreferredFinder.getPreferredFor(inputClass);

			// No preferred class found
			if (outputClass == null)
			{
				outputClass = findConcreteClassFor(inputClass);
			}
		}
		// Already a concrete implementation
		else
		{
			outputClass = inputClass;
		}

		return outputClass;
	}

	/**
	 * Get concrete class matching input class.
	 * @param inputClass Input class.
	 * @return Concrete class corresponding to input class.
	 */
	private static Class<?> findConcreteClassFor(Class<?> inputClass) throws NoConcreteClassFound, TooMuchConcreteClassFound
	{
		Class<?> outputClass = null;

		Set<Class<?>> subTypes = (Set<Class<?>>) reflectionHelper.getSubTypesOf(inputClass);

		// No Implementations found
		if (subTypes.isEmpty())
		{
			throw new NoConcreteClassFound();
		}
		// More than one possibility
		else if (subTypes.size() > 1)
		{
			throw new TooMuchConcreteClassFound();
		}
		// Only one implementation found
		else
		{
			Iterator it = subTypes.iterator();
			outputClass = (Class) it.next();
		}

		return outputClass;
	}
}
