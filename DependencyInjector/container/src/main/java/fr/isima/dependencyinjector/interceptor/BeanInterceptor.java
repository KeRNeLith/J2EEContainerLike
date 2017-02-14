package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;

import java.lang.reflect.Method;

/**
 * Created by kernelith on 14/02/17.
 */
public class BeanInterceptor implements IInterceptor
{
	@Override
	public Object invoke(InvocationContextChain invocation)
	{
		try
		{
			Method method = invocation.getMethod();

			// Call method
			return method.invoke(invocation.getObject(), invocation.getArgs());
		}
		catch (Exception e)
		{
			// TODO
			throw new RuntimeException();
		}
	}
}
