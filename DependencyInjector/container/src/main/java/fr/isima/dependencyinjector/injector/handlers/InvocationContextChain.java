package fr.isima.dependencyinjector.injector.handlers;

import fr.isima.dependencyinjector.interceptor.BeanInterceptor;
import fr.isima.dependencyinjector.interceptor.IInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kernelith on 14/02/17.
 */
public class InvocationContextChain
{
	/**
	 * Concerned bean.
	 */
	private Object object;

	/**
	 * List of attached interceptors.
	 */
	private List<IInterceptor> interceptors;
	/**
	 * Method called.
	 */
	private Method method;
	/**
	 * Method arguments.
	 */
	private Object[] args;

	/**
	 * Interceptor index (progression in responsibility chain).
	 */
	private int index;

	/**
	 * Construct a basic responsibility chain that only invoke method.
	 * @param bean Object concerned.
	 * @param method Method called.
	 * @param args Method arguments.
	 */
	public InvocationContextChain(Object bean, Method method, Object... args)
	{
		this.object = bean;

		this.interceptors = new ArrayList<>();
		this.interceptors.add(new BeanInterceptor());

		this.method = method;
		this.args = args;
	}

	/**
	 * Build a responsibility chain based on the given list of interceptor to use.
	 * Automatically add an interceptor in charge of calling embedded method.
	 * @param interceptors List of interceptors.
	 */
	void buildResponsibilityChain(List<IInterceptor> interceptors)
	{
		this.interceptors = interceptors;
		this.interceptors.add(new BeanInterceptor());
	}

	/**
	 * Execute next step of responsibility chain.
	 * @return Next interceptor result.
	 */
	public Object execNextInterceptor()
	{
		return interceptors.get(index++).invoke(this);
	}

	public Object getObject()
	{
		return object;
	}

	public Method getMethod()
	{
		return method;
	}

	public Object[] getArgs()
	{
		return args;
	}
}
