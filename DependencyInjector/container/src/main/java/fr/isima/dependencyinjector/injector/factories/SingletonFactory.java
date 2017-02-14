package fr.isima.dependencyinjector.injector.factories;

import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;

import java.util.HashMap;

/**
 * Created by kernelith on 14/02/17.
 */
public class SingletonFactory
{
	/**
	 * Map of all singleton instances instantiated.
	 */
	private static HashMap<Class, Object> singletonsInstances;

	static
	{
		singletonsInstances = new HashMap<>();
	}

	public static Object getInstanceFor(Class targetClass) throws IllegalAccessException, InstantiationException, TooMuchPreferredClassFound, TooMuchConcreteClassFound, NoConcreteClassFound
	{
		Object instance = null;

		if (!singletonsInstances.containsKey(targetClass))
		{
			instance = targetClass.newInstance();
			EJBContainer.getInjector().inject(instance);

			// Save instance
			singletonsInstances.put(targetClass, instance);
		}
		else
		{
			instance = singletonsInstances.get(targetClass);
		}

		return instance;
	}
}
