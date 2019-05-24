package test;

import helpers.Channel;
import helpers.EventLoop;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.CountDownLatch;

/**
 * Reactive HTTP server but with logic written in blocking manner. For example database calls are blocking.
 * Architecture somehow similar to what Tomcat with NIO offers.
 *
 * @author Lukasz Frankowski
 */
public class ReactiveWithBlockingDbTest extends BaseReactiveTest {

	@ParameterizedTest
	@ValueSource(ints = {1, 10})
	public void test(int requestsCount) throws Exception {
		CountDownLatch latch = new CountDownLatch(requestsCount);
		EventLoop<Integer> httpProcessor = new EventLoop<>("HTTP-PROCESSOR");

		Flowable.create((FlowableEmitter<Channel<Integer, Integer>> emitter) -> {
			for (int i = 0; i< requestsCount; i++) {
				log("New request connection established: " + i);

				Channel<Integer, Integer> channel =
					new Channel<>(httpProcessor.publisher("Request completed with data", i, REQUEST_DATA_RECEIVE_TIME_MS));

				channel.responseChannel()
					.subscribe(it -> {
						log("Response: " + it + " is ready, sending it to the client");
						latch.countDown();
					});

				emitter.onNext(channel);

				sleep(REQUESTS_DELAY_MS);
			}
			emitter.onComplete();
		}, BackpressureStrategy.ERROR)
			.subscribeOn(Schedulers.from(httpAcceptorPool))
			.subscribe(channel -> {
				log("Beginning request subscription");
				channel.requestChannel()
					.observeOn(Schedulers.from(workersPool))
					.subscribe(it -> {
						log("Received request: " + it);
						log("Doing some CPU-intensive operations");
						sleep(CPU_INTENSIVE_OPERATION_TIME_MS);
						log("Asking the database for data and waiting for it");
						sleep(DATABASE_OPERATION_TIME_MS);
						log("Database data ready for: " + it + " with data: " + it*2);
						Flowable.just(it*2)
							.subscribe(channel.responseChannel());
					});
			});

		latch.await();
	}


}
