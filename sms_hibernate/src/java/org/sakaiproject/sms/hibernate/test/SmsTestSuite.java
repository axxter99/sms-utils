package org.sakaiproject.sms.hibernate.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Run all tests.
 */
public class SmsTestSuite {

	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for org.sakaiproject.sms.hibernate.test");
		// $JUnit-BEGIN$
		suite.addTestSuite(SmsTransactionTest.class);
		suite.addTestSuite(SmsTaskTest.class);
		suite.addTestSuite(SmsConfigTest.class);
		suite.addTestSuite(SmsMessageTest.class);
		suite.addTestSuite(SmsAccountTest.class);
		suite.addTestSuite(SmsHibernateStressTest.class);
		// $JUnit-END$
		return suite;
	}

}
