package fr.isima.dependencyinjector.injector.factories;

import fr.isima.dependencyinjector.exceptions.bootstrap.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.bootstrap.TooMuchPreferredClassFound;
import fr.isima.dependencyinjector.injector.finders.ClassFinder;
import fr.isima.dependencyinjector.injector.handlers.ContainerInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * Created by kernelith on 14/02/17.
 */
public class ContainerProxyFactory
{
	/**
	 * Instantiate a proxy corresponding to the given interface class.
	 * @param interfaceClass Interface class that proxy should dynamically implements.
	 * @return Instantiated proxy.
	 */
	public static Object createNewProxyFor(Class interfaceClass) throws TooMuchPreferredClassFound, TooMuchConcreteClassFound, NoConcreteClassFound
	{
		Object proxy = null;

		Class concreteClass = ClassFinder.findClassFor(interfaceClass);
		if (concreteClass != null)
		{
			proxy = Proxy.newProxyInstance( concreteClass.getClassLoader(),
											concreteClass.getInterfaces(),
											new ContainerInvocationHandler());
		}

		return proxy;
	}
}
