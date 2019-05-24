package test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CountDownLatch;

/**
 * Shows how the usual blocking HTTP server works.
 *
 * @author Lukasz Frankowski
 */
public class ThreadPerRequestTest extends BaseTest {

	@ParameterizedTest
	@ValueSource(ints = {1, 10})
	public void requestPerThread(int requestsCount) throws Exception {
		CountDownLatch latch = new CountDownLatch(requestsCount);

		for (int i = 0; i< requestsCount; i++) {
			log("New request connection established: " + i);

			final int data = i;
			workersPool.submit(() -> {
				log("New request connection established: " + data);
				sleep(REQUEST_DATA_RECEIVE_TIME_MS);
				log("Received request: " + data);
				log("Doing some CPU-intensive operations");
				sleep(CPU_INTENSIVE_OPERATION_TIME_MS);
				log("Asking the database for data and exiting the worker thread");
				sleep(DATABASE_OPERATION_TIME_MS);
				log("Database data ready for: " + data + " with data: " + data*2);
				log("Response: " + data*2 + " is ready, sending it to the client");
				latch.countDown();
			});

			sleep(REQUESTS_DELAY_MS);
		}

		latch.await();
	}


}
