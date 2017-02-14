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
	private Object object;

	private List<IInterceptor> interceptors;
	private Method method;
	private Object[] args;

	private int index;

	public InvocationContextChain(Object bean, Method method, Object... args)
	{
		this.object = bean;

		this.interceptors = new ArrayList<>();
		this.interceptors.add(new BeanInterceptor());

		this.method = method;
		this.args = args;
	}

	void buildResponsibilityChain(List<IInterceptor> interceptors)
	{
		this.interceptors = interceptors;
		this.interceptors.add(new BeanInterceptor());
	}

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
