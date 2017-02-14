package fr.isima.dependencyinjector.injector.finders;

import fr.isima.dependencyinjector.annotations.Behaviour;
import fr.isima.dependencyinjector.interceptor.IInterceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kernelith on 14/02/17.
 */
public class BehaviourFinder
{
	/**
	 * Get a list of interceptors class that are attached to the given method.
	 * @param method Method to check.
	 * @return List of interceptor's classes.
	 */
	public static List<Class> getInterceptorsFor(Method method)
	{
		List<Class> interceptorsClasses = new ArrayList<>();
		for (Annotation annotation : method.getAnnotations())
		{
			// Check annotations of current annotation => search a behaviour annotation
			Class<? extends Annotation> annotationType = annotation.annotationType();
			if (annotationType.isAnnotationPresent(Behaviour.class))
			{
				Behaviour behaviourAnnotation = annotationType.getAnnotation(Behaviour.class);
				Class<? extends IInterceptor> interceptorClass = behaviourAnnotation.interceptor();

				interceptorsClasses.add(interceptorClass);
			}
		}

		return interceptorsClasses;
	}
}
