package fr.isima.dependencyinjector.injector;

import fr.isima.dependencyinjector.exceptions.NoConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchConcreteClassFound;
import fr.isima.dependencyinjector.exceptions.TooMuchPreferredClassFound;

import java.lang.reflect.Proxy;

/**
 * Created by kernelith on 14/02/17.
 */
public class ContainerProxyFactory
{
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
