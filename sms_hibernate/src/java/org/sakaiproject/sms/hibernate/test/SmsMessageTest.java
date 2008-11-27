package org.sakaiproject.sms.hibernate.test;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;

import org.sakaiproject.sms.hibernate.logic.impl.SmsMessageLogicImpl;
import org.sakaiproject.sms.hibernate.logic.impl.SmsTaskLogicImpl;
import org.sakaiproject.sms.hibernate.model.SmsMessage;
import org.sakaiproject.sms.hibernate.model.SmsTask;
import org.sakaiproject.sms.hibernate.model.constants.SmsConst_DeliveryStatus;

/**
 * The Class SmsMessageTest.
 */
public class SmsMessageTest extends TestCase {

	/** The logic. */
	private static SmsMessageLogicImpl logic = null;

	/** The insert task. */
	private static SmsTask insertTask;

	/** The insert message1. */
	private static SmsMessage insertMessage1;

	/** The insert message2. */
	private static SmsMessage insertMessage2;

	/** The task logic. */
	private static SmsTaskLogicImpl taskLogic;
	static {
		logic = new SmsMessageLogicImpl();

		insertTask = new SmsTask();
		insertTask.setSakaiSiteId("sakaiSiteId");
		insertTask.setSmsAccountId(1);
		insertTask.setDateCreated(new Timestamp(System.currentTimeMillis()));
		insertTask.setDateToSend(new Timestamp(System.currentTimeMillis()));
		insertTask.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);
		insertTask.setAttemptCount(2);
		insertTask.setMessageBody("messageBody");
		insertTask.setSenderUserName("senderUserName");
		taskLogic = new SmsTaskLogicImpl();
		// Insert the task so we can play with messages
		taskLogic.persistSmsTask(insertTask);

		insertMessage1 = new SmsMessage();
		insertMessage1.setMobileNumber("0721998919");
		insertMessage1.setSmscMessageId("smscMessageId1");
		insertMessage1.setSakaiUserId("sakaiUserId");
		insertMessage1.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);

		insertMessage2 = new SmsMessage();
		insertMessage2.setMobileNumber("0823450983");
		insertMessage2.setSmscMessageId("smscMessageId2");
		insertMessage2.setSakaiUserId("sakaiUserId");
		insertMessage2.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);
	}

	/**
	 * Instantiates a new sms message test.
	 */
	public SmsMessageTest() {
	}

	/**
	 * Instantiates a new sms message test.
	 * 
	 * @param name
	 *            the name
	 */
	public SmsMessageTest(String name) {
		super(name);
	}

	/**
	 * Test insert sms message.
	 */
	public void testInsertSmsMessage() {
		assertTrue("Task for message not created", insertTask.exists());
		insertMessage1.setSmsTask(insertTask);
		insertMessage2.setSmsTask(insertTask);
		logic.persistSmsMessage(insertMessage1);
		logic.persistSmsMessage(insertMessage2);
		assertTrue("Object not persisted", insertMessage1.exists());
		assertTrue("Object not persisted", insertMessage2.exists());
		insertTask.getSmsMessages().add(insertMessage1);
		insertTask.getSmsMessages().add(insertMessage2);
		taskLogic.persistSmsTask(insertTask);
		assertTrue("", insertTask.getSmsMessages().contains(insertMessage1));
	}

	/**
	 * Test get sms message by id.
	 */
	public void testGetSmsMessageById() {
		SmsMessage getSmsMessage = logic.getSmsMessage(insertMessage1.getId());
		assertTrue("Object not persisted", insertMessage1.exists());
		assertNotNull(getSmsMessage);
		assertEquals(insertMessage1, getSmsMessage);

	}

	/**
	 * Test update sms message.
	 */
	public void testUpdateSmsMessage() {
		SmsMessage smsMessage = logic.getSmsMessage(insertMessage1.getId());
		smsMessage.setSakaiUserId("newSakaiUserId");
		logic.persistSmsMessage(smsMessage);
		smsMessage = logic.getSmsMessage(insertMessage1.getId());
		assertEquals("newSakaiUserId", smsMessage.getSakaiUserId());
	}

	/**
	 * Test get sms messages.
	 */
	public void testGetSmsMessages() {
		List<SmsMessage> messages = logic.getAllSmsMessages();
		assertNotNull("Returnend collection is null", messages);
		assertTrue("No records returned", messages.size() > 0);
	}

	/**
	 * Test get sms message by smsc message id.
	 */
	public void testGetSmsMessageBySmscMessageId() {
		SmsMessage smsMessage = logic
				.getSmsMessageBySmscMessageId(insertMessage2.getSmscMessageId());
		assertSame(smsMessage, insertMessage2);
		assertEquals(smsMessage.getSmscMessageId(), insertMessage2
				.getSmscMessageId());
	}

	/**
	 * Test delete sms message.
	 */
	public void testDeleteSmsMessage() {
		// Delete the associated task too
		taskLogic.deleteSmsTask(insertTask);
		SmsTask getSmsTask = taskLogic.getSmsTask(insertTask.getId());
		assertNull("Object not removed", getSmsTask);
	}
}