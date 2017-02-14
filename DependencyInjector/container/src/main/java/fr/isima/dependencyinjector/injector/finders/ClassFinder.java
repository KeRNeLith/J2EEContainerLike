package fr.isima.dependencyinjector.injector.finders;

import fr.isima.dependencyinjector.annotations.Preferred;
import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

	static
	{
		reflectionHelper = new Reflections("fr.isima");
	}

	/**
	 * Find the class that resolve input class.
	 * @param inputClass Input class.
	 * @return Class that resolve input class.
	 */
	public static Class<?> findClassFor(Class<?> inputClass) throws NoConcreteClassFound, TooMuchPreferredClassFound, TooMuchConcreteClassFound
	{
		Class<?> outputClass = null;

		if (inputClass.isInterface())
		{
			Set<?> subTypes = reflectionHelper.getSubTypesOf(inputClass);

			// No Implementations found
			if (subTypes.size() <= 0)
			{
				throw new NoConcreteClassFound();
			}
			// More than one possibility
			else if (subTypes.size() > 1)
			{
				List<Class> preferredClass = new ArrayList<>();
				for (Object subType : subTypes)
				{
					Class curClass = (Class) subType;
					if (curClass.isAnnotationPresent(Preferred.class))
					{
						preferredClass.add(curClass);
					}
				}

				// More than one preferred class
				if (preferredClass.size() > 1)
				{
					throw new TooMuchPreferredClassFound();
				}
				// One preferred class
				else if (preferredClass.size() == 1)
				{
					outputClass = preferredClass.get(0);
				}
				// No Preferred class but too much class found
				else
				{
					throw new TooMuchConcreteClassFound();
				}
			}
			// Only one implementation found
			else
			{
				Iterator it = subTypes.iterator();
				outputClass = (Class) it.next();
			}
		}
		// Already a concrete implementation
		else
		{
			outputClass = inputClass;
		}

		return outputClass;
	}
}
