package fr.isima.dependencyinjector.interceptor;

import fr.isima.dependencyinjector.exceptions.runtime.InvocationException;
import fr.isima.dependencyinjector.injector.handlers.InvocationContextChain;

import java.lang.reflect.Method;

/**
 * Created by kernelith on 14/02/17.
 */
public class BeanInterceptor implements IInterceptor
{
	/**
	 * Handle interceptor logic that simply run a bean method.
	 * @param invocation Context of invocation.
	 * @return Bean method result.
	 */
	@Override
	public Object invoke(InvocationContextChain invocation)
	{
		Method method = invocation.getMethod();

		// Call method
		try
		{
			return method.invoke(invocation.getObject(), invocation.getArgs());
		}
		catch (Exception e)
		{
			throw new InvocationException(e.getMessage());
		}
	}
}
