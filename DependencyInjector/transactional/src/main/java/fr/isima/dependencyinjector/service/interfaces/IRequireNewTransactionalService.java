package fr.isima.dependencyinjector.service.interfaces;

/**
 * Created by kernelith on 07/02/17.
 */
public interface IRequireNewTransactionalService
{
	void methodSimpleSucceed();
	void methodSucceed();
	void methodMixedSucceed();

	void methodSimpleFailed() throws Exception;
	void methodFailed() throws Exception;
	void methodMixedFailed() throws Exception;
}
