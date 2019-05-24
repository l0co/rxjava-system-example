package helpers;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.UnicastProcessor;

/**
 * Represents asynchronous request-response channel. The {@link Flowable} representing request will produce the data when the request
 * will be complete and ready. The {@link Flowable} representing response is subscribed by the facility sending the response back to the
 * client and waits for data from the application logic.
 *
 * @author Lukasz Frankowski
 */
public class Channel<REQ, RESP> {

	private Flowable<REQ> request; // only required to be subscribed
	private FlowableProcessor<RESP> response; // is subscribed by some response processing thread, but also accepts the data published from the application business logic

	public Channel(Flowable<REQ> request) {
		this.request = request;
		this.response = UnicastProcessor.create();
	}

	public Flowable<REQ> requestChannel() {
		return request;
	}

	public FlowableProcessor<RESP> responseChannel() {
		return response;
	}


}
