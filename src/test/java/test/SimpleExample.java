package test;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

/**
 * The very basic introduction to RxJava.
 *
 * @author Lukasz Frankowski
 */
@SuppressWarnings("Convert2MethodRef")
public class SimpleExample extends BaseTest {

	@Test
	public void testA0() {
		int i = 1;
		log(i);
	}

	@Test
	public void testA1() {
		Flowable<Integer> publisher = Flowable.just(1);
		publisher.subscribe(i -> log(i));
		log("bye bye");
	}

	@Test
	public void testA2() {
		Flowable<Integer> publisher = Flowable.just(1)
			.subscribeOn(Schedulers.newThread());
		publisher.subscribe(i -> log(i));
		log("bye bye");
		sleep(1000);
	}

	@Test
	public void testB0() {
		int i = 1;
		i++;
		i++;
		log(i);
	}

	@Test
	public void testB1() {
		Flowable<Integer> publisher = Flowable.just(1)
			.map(this::addOne)
			.map(this::addOne);
		publisher.subscribe(i -> log(i));
		log("bye bye");
	}

	@Test
	public void testB2() {
		Flowable<Integer> publisher = Flowable.just(1)
			.subscribeOn(Schedulers.newThread())
			.map(this::addOne)
			.map(this::addOne);
		publisher.subscribe(i -> log(i));
		log("bye bye");
		sleep(1000);
	}

	@Test
	public void testB3() {
		Flowable<Integer> publisher = Flowable.just(1)
			.subscribeOn(Schedulers.newThread())
			.observeOn(Schedulers.newThread())
			.map(this::addOne)
			.observeOn(Schedulers.newThread())
			.map(this::addOne);
		publisher.subscribe(i -> log(i));
		log("bye bye");
		sleep(1000);
	}

	public Integer addOne(Integer i) {
		log(String.format("adding 1 to %s what makes: %d", i, i+1));
		return i+1;
	}

}
