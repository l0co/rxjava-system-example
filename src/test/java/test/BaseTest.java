package test;

import helpers.Timer;

/**
 * @author Lukasz Frankowski
 */
public abstract class BaseTest {

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


}
