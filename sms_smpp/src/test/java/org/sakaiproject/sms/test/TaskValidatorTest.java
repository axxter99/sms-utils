package org.sakaiproject.sms.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.sakaiproject.sms.hibernate.logic.impl.HibernateLogicFactory;
import org.sakaiproject.sms.hibernate.model.SmsAccount;
import org.sakaiproject.sms.hibernate.model.SmsMessage;
import org.sakaiproject.sms.hibernate.model.SmsTask;
import org.sakaiproject.sms.hibernate.model.constants.SmsConst_DeliveryStatus;
import org.sakaiproject.sms.hibernate.model.constants.SmsHibernateConstants;
import org.sakaiproject.sms.hibernate.model.constants.ValidationConstants;
import org.sakaiproject.sms.hibernate.util.AbstractBaseTestCase;
import org.sakaiproject.sms.hibernate.util.HibernateUtil;
import org.sakaiproject.sms.impl.validate.TaskValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class SmsMessageValidationTest. Runs tests for {@link SmsMessage}
 * validation that is run by {@link SmsMessageTaskValidator}
 */
public class TaskValidatorTest extends AbstractBaseTestCase {

	/** The sms task. */
	private SmsTask smsTask;

	/** The msg. */
	private SmsMessage msg;

	/** The VALI d_ ms g_ body. */
	private static String VALID_MSG_BODY = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";

	static {
		HibernateUtil.createSchema();
	}

	/** The account. */
	private SmsAccount account;

	ArrayList<String> errors = new ArrayList<String>();

	/**
	 * setUp to run before every test. Create SmsMessage + TaskValidator +
	 * errors
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	public void setUp() {

		account = new SmsAccount();
		account.setSakaiSiteId("sakaiSiteId");
		account.setMessageTypeCode("");
		account.setBalance(10f);
		account.setAccountName("account name");
		account.setStartdate(new Date());
		account.setAccountEnabled(true);
		HibernateLogicFactory.getAccountLogic().persistSmsAccount(account);

		msg = new SmsMessage();
		smsTask = new SmsTask();
		smsTask.setSakaiSiteId("sakaiSiteId");
		smsTask.setSmsAccountId(account.getId().intValue());
		smsTask.setDateCreated(new Timestamp(System.currentTimeMillis()));
		smsTask.setDateToSend(new Timestamp(System.currentTimeMillis()));
		smsTask.setStatusCode(SmsConst_DeliveryStatus.STATUS_PENDING);
		smsTask.setAttemptCount(2);
		smsTask.setMessageBody("messageBody");
		smsTask.setSenderUserName("senderUserName");
		smsTask.setMaxTimeToLive(1);
		smsTask.setDelReportTimeoutDuration(1);
		smsTask.getSmsMessages().add(msg);
		smsTask.setCreditEstimate(5);
		smsTask.setDeliveryGroupId("delGrpId");
		msg.setSmsTask(smsTask);

	}

	/**
	 * Test valid message.
	 */
	public void testValidMessage() {
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() == 0);
	}

	/**
	 * Test empty message body.
	 */
	public void testMessageBody_empty() {
		smsTask.setMessageBody("");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.MESSAGE_BODY_EMPTY));
	}

	/**
	 * Test null message body.
	 */
	public void testMessageBody_null() {
		smsTask.setMessageBody(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.MESSAGE_BODY_EMPTY));
	}

	/**
	 * Test too long message body.
	 */
	public void testMessageBody_tooLong() {
		smsTask.setMessageBody(VALID_MSG_BODY + VALID_MSG_BODY);
		assertTrue(msg.getMessageBody().length() > SmsHibernateConstants.MAX_SMS_LENGTH);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.MESSAGE_BODY_TOO_LONG));
	}

	/**
	 * Test empty message body (with whitespace).
	 */
	public void testMessageBody_whitespace() {
		smsTask.setMessageBody("   ");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.MESSAGE_BODY_EMPTY));
	}

	/**
	 * Test sakai site id.
	 */
	public void testSakaiSiteId() {

		// null
		smsTask.setSakaiSiteId(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SAKAI_SITE_ID_EMPTY));

		// empty String
		smsTask.setSakaiSiteId("");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SAKAI_SITE_ID_EMPTY));

		// Blank space
		smsTask.setSakaiSiteId("   ");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SAKAI_SITE_ID_EMPTY));
	}

	/**
	 * Test account id.
	 */
	public void testAccountId() {

		// account exists
		smsTask.setSmsAccountId(account.getId().intValue());
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() == 0);

		// account does not exist
		smsTask.setSmsAccountId(0);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_ACCOUNT_INVALID));
	}

	/**
	 * Test date created.
	 */
	public void testDateCreated() {

		// null
		smsTask.setDateCreated(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_DATE_CREATED_EMPTY));
	}

	/**
	 * Test date to send.
	 */
	public void testDateToSend() {

		// null
		smsTask.setDateToSend(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_DATE_TO_SEND_EMPTY));
	}

	/**
	 * Test status code.
	 */
	public void testStatusCode() {

		// null
		smsTask.setStatusCode(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_STATUS_CODE_EMPTY));

		// empty String
		smsTask.setStatusCode("");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_STATUS_CODE_EMPTY));

		// Blank space
		smsTask.setStatusCode("   ");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_STATUS_CODE_EMPTY));
	}

	/**
	 * Test message type id.
	 */
	public void testMessageTypeId() {
		// null
		smsTask.setMessageTypeId(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors.contains(ValidationConstants.TASK_MESSAGE_TYPE_EMPTY));
	}

	/**
	 * Test sender user name.
	 */
	public void testSenderUserName() {

		// null
		smsTask.setSenderUserName(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SENDER_USER_NAME_EMPTY));

		// empty String
		smsTask.setSenderUserName("");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SENDER_USER_NAME_EMPTY));

		// Blank space
		smsTask.setSenderUserName("   ");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_SENDER_USER_NAME_EMPTY));
	}

	/**
	 * Test max time to live.
	 */
	public void testMaxTimeToLive() {
		// null
		smsTask.setMaxTimeToLive(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_MAX_TIME_TO_LIVE_INVALID));

		// invalid
		smsTask.setMaxTimeToLive(0);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_MAX_TIME_TO_LIVE_INVALID));
	}

	/**
	 * Test delivery report timeout duration.
	 */
	public void testDelReportTimeoutDuration() {
		// null
		smsTask.setDelReportTimeoutDuration(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_DELIVERY_REPORT_TIMEOUT_INVALID));

		// invalid
		smsTask.setDelReportTimeoutDuration(0);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_DELIVERY_REPORT_TIMEOUT_INVALID));
	}

	/**
	 * Test delivery group id.
	 */
	public void testDeliveryGroupId() {
		// null
		smsTask.setDeliveryGroupId(null);
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_DELIVERY_GROUP_ID_EMPTY));

		// empty String
		smsTask.setDeliveryGroupId("");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_DELIVERY_GROUP_ID_EMPTY));

		// Blank space
		smsTask.setDeliveryGroupId("   ");
		errors = TaskValidator.validateInsertTask(smsTask);
		assertTrue(errors.size() > 0);
		assertTrue(errors
				.contains(ValidationConstants.TASK_DELIVERY_GROUP_ID_EMPTY));
	}

}