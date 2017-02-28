package fr.isima.dependencyinjector.services.interfaces;

/**
 * Created by kernelith on 07/02/17.
 */
public interface IRequireNewTransactionalService
{
	void methodSucceed();
	void methodMixedSucceed();

	void methodFailed();
	void methodMixedFailed();
}
