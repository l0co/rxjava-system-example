package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lukasz Frankowski
 */
public abstract class BaseReactiveTest extends BaseTest {

	// the pool accepting HTTP connections (single thread for the server socket)
	protected static ExecutorService httpAcceptorPool = Executors.newFixedThreadPool(1, r -> new Thread(r, "HTTP-ACCEPTOR"));

}
