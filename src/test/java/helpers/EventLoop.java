package helpers;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;
import org.reactivestreams.Publisher;
import test.BaseTest;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the emulator for event-looping thread, to which you can publish an event which will be emitted after some delay. This
 * simulates the real event-loop which polls the resources for data in a loop, and when the data is ready it's emits it to the
 * surrounding world.
 *
 * @author Lukasz Frankowski
 */
@SuppressWarnings("unchecked")
public class EventLoop<EVENT> extends Thread {

	protected Map<Long, ConnectableFlowable<EVENT>> resourcesCache = Collections.synchronizedMap(new TreeMap<>());

	public EventLoop(String name) {
		super(name);
		start(); // immediately starts the thread
	}

	@Override
	public void run() {
		BaseTest.log("Starting thread");
		try {
			while (true) {
				synchronized (this) {

					// What basically it does, is to check resourceCache with 100ms interval if there's some event which should be emitted
					// right now. If the event is found, it emits the event by connecting to the ConnectableFlowable representing this
					// event.

					if (!resourcesCache.isEmpty()) {
						Map.Entry<Long, ConnectableFlowable<EVENT>> next = resourcesCache.entrySet().iterator().next();
						if (next.getKey() > System.currentTimeMillis())
							wait(next.getKey() - System.currentTimeMillis());
						resourcesCache.remove(next.getKey());
						ConnectableFlowable<EVENT> emitter = next.getValue();
						emitter.connect();
					}
					wait(100);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param msg The additional message that will be logged onto console on event emitting.
	 * @param value The event to be emitted.
	 * @param delay After what delay the event should be emitted.
	 * @return The {@link Publisher} to which the client can subscribe to get the event after the delay.
	 */
	public Flowable<EVENT> publisher(String msg, EVENT value, long delay) {
		ConnectableFlowable<EVENT> flowable = (ConnectableFlowable) Flowable.create(emitter -> {
			BaseTest.log(msg + ": " + value);
			emitter.onNext(value);
		}, BackpressureStrategy.ERROR)
			// that's imporant, this Publisher won't start emitting events until connect(), what happens in the main thread after requested delay
			.publish();
		resourcesCache.put(System.currentTimeMillis()+delay, flowable);
		return flowable;
	}

}
