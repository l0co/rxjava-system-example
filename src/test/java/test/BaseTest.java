package test;

import helpers.Timer;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lukasz Frankowski
 */
public abstract class BaseTest {

	public static final int REQUESTS_DELAY_MS = 10; // the delay between requests
	public static final int REQUEST_DATA_RECEIVE_TIME_MS = 2000; // how long do we receive data from the client
	public static final int DATABASE_OPERATION_TIME_MS = 2000; // how long do we get data from db
	public static final int CPU_INTENSIVE_OPERATION_TIME_MS = 500; // how long the CPU-intensive operation lasts

	private static int threadNum = 0;

	// our workers pool which executed the business logic
	protected static ExecutorService workersPool = Executors.newFixedThreadPool(3, r -> new Thread(r, "WORKER-" + (++threadNum)));

	public static void log(Object o) {
		System.out.println(String.format(Timer.elapsed() + " [%s] %s", Thread.currentThread().getName(), o));
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	public void resetTime() {
		Timer.start();
	}

}
