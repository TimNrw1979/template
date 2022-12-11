package de.tauiotamy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo class.
 */
public class FooBar {

	/**
	 * Logger.
	 */
	private static Logger LOG = LoggerFactory.getLogger(FooBar.class);

	/**
	 * Dummy method.
	 * 
	 * @return 0.
	 */
	public int dummy() {
		LOG.error("Dummy");
		return 0;
	}

	public static void main(String[] args) {
		LOG.info("Test");
		System.out.println(new FooBar().dummy());
	}

}
