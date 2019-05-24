package helpers;

public class Timer {

	protected static long startTime;

	private Timer() {
		start();
	}

	public static void start() {
		startTime = System.currentTimeMillis();
	}

	public static long elapsed() {
		return System.currentTimeMillis() - startTime;
	}


	
}
