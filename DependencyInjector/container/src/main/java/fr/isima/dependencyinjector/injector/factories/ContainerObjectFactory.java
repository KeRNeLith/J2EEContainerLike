package fr.isima.dependencyinjector.injector.factories;

import fr.isima.dependencyinjector.annotations.Singleton;
import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;

/**
 * Created by kernelith on 14/02/17.
 */
public class ContainerObjectFactory
{
	public static Object createNewInstanceFor(Class targetClass) throws TooMuchPreferredClassFound, TooMuchConcreteClassFound, NoConcreteClassFound, IllegalAccessException, InstantiationException
	{
		Object instance = null;

		// Delegate Singleton handling
		if (targetClass.isAnnotationPresent(Singleton.class))
		{
			instance = SingletonFactory.getInstanceFor(targetClass);
		}
		else
		{
			instance = targetClass.newInstance();
			EJBContainer.getInjector().inject(instance);
		}

		return instance;
	}
}
