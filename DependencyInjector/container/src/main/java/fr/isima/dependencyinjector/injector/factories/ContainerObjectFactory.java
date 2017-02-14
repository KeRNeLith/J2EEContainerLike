package fr.isima.dependencyinjector.injector.factories;

import fr.isima.dependencyinjector.annotations.Singleton;
import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.EJBContainer;

/**
 * Created by kernelith on 14/02/17.
 */
public class ContainerObjectFactory
{
	/**
	 * Instantiate an object of type targetClass.
	 * @param targetClass Class wanted for the instantiated object.
	 * @return Instantiated object.
	 */
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
