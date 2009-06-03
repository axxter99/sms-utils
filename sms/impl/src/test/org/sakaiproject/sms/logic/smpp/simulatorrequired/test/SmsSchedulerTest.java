package org.sakaiproject.sms.logic.smpp.simulatorrequired.test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.sakaiproject.sms.logic.external.ExternalLogic;
import org.sakaiproject.sms.logic.impl.hibernate.SmsConfigLogicImpl;
import org.sakaiproject.sms.logic.smpp.SmsTaskValidationException;
import org.sakaiproject.sms.logic.smpp.exception.SmsSendDeniedException;
import org.sakaiproject.sms.logic.smpp.exception.SmsSendDisabledException;
import org.sakaiproject.sms.logic.smpp.impl.SmsBillingImpl;
import org.sakaiproject.sms.logic.smpp.impl.SmsCoreImpl;
import org.sakaiproject.sms.logic.smpp.impl.SmsSchedulerImpl;
import org.sakaiproject.sms.logic.smpp.impl.SmsSmppImpl;
import org.sakaiproject.sms.logic.smpp.validate.SmsTaskValidatorImpl;
import org.sakaiproject.sms.logic.stubs.ExternalLogicStub;
import org.sakaiproject.sms.model.hibernate.SmsAccount;
import org.sakaiproject.sms.model.hibernate.SmsConfig;
import org.sakaiproject.sms.model.hibernate.SmsTask;
import org.sakaiproject.sms.model.hibernate.constants.SmsConstants;
import org.sakaiproject.sms.util.AbstractBaseTestCase;

/**
 * SmsScheduler Junit.This class will test various scheduling related scenarios.
 * 
 * @author Etienne@psybergate.co.za
 * 
 */
public class SmsSchedulerTest extends AbstractBaseTestCase {

	private static SmsCoreImpl smsCoreImpl = null;
	private static SmsBillingImpl smsBilling = null;
	private static SmsSchedulerImpl smsSchedulerImpl = null;
	private static SmsSmppImpl smsSmppImpl = null;
	private static SmsConfigLogicImpl smsConfigLogic = null;
	private final static ExternalLogic EXTERNAL_LOGIC = new ExternalLogicStub();

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(SmsCoreTest.class);

	/**
	 * This tests will insert 3 tasks to to processed.The test succeeds if no
	 * tasks remain after 1 min.
	 */
	public void testTaskProcessing() {
		if (!SmsConstants.isDbSchemaCreated) {
			smsDao.createSchema();
		}

		smsConfigLogic = new SmsConfigLogicImpl();
		smsBilling = new SmsBillingImpl();
		smsBilling.setHibernateLogicLocator(hibernateLogicLocator);

		smsConfigLogic.setSmsDao(smsDao);
		smsSchedulerImpl = new SmsSchedulerImpl();
		smsSchedulerImpl.setHibernateLogicLocator(hibernateLogicLocator);
		smsCoreImpl = new SmsCoreImpl();
		smsCoreImpl.setHibernateLogicLocator(hibernateLogicLocator);
		SmsTaskValidatorImpl smsTaskValidatorImpl = new SmsTaskValidatorImpl();
		smsTaskValidatorImpl.setSmsBilling(smsBilling);

		smsCoreImpl.setSmsTaskValidator(smsTaskValidatorImpl);
		smsCoreImpl.setSmsBilling(smsBilling);
		smsSmppImpl = new SmsSmppImpl();

		smsSmppImpl.setHibernateLogicLocator(hibernateLogicLocator);

		smsSmppImpl.init();
		smsCoreImpl.setSmsSmpp(smsSmppImpl);

		smsSchedulerImpl.setSmsCore(smsCoreImpl);

		LOG.setLevel(Level.WARN);
		SmsConfig config = smsConfigLogic
				.getOrCreateSmsConfigBySakaiSiteId(EXTERNAL_LOGIC
						.getCurrentSiteId());
		config.setSendSmsEnabled(true);
		smsConfigLogic.persistSmsConfig(config);
		smsSchedulerImpl.init();
		List<SmsTask> smsTasks = smsCoreImpl.getHibernateLogicLocator()
				.getSmsTaskLogic().getAllSmsTask();

		for (SmsTask smsTask : smsTasks) {
			smsCoreImpl.getHibernateLogicLocator().getSmsTaskLogic()
					.deleteSmsTask(smsTask);
		}
		SmsAccount smsAccount = new SmsAccount();
		smsAccount.setSakaiUserId(EXTERNAL_LOGIC.getCurrentUserId()
				+ Math.random());
		smsAccount.setSakaiSiteId(EXTERNAL_LOGIC.getCurrentSiteId()
				+ Math.random());
		smsAccount.setMessageTypeCode("3");
		smsAccount.setOverdraftLimit(1000L);
		smsAccount.setCredits(1000L);
		smsAccount.setAccountName("accountname" + Math.random());
		smsAccount.setAccountEnabled(true);
		hibernateLogicLocator.getSmsAccountLogic()
				.persistSmsAccount(smsAccount);

		Calendar now = Calendar.getInstance();
		SmsTask smsTask3 = smsCoreImpl.getPreliminaryTask("smsTask3", new Date(
				now.getTimeInMillis()), "smsTask3",
				smsAccount.getSakaiSiteId(), null, smsAccount.getSakaiUserId());

		smsTask3.setSmsAccountId(smsAccount.getId());
		smsCoreImpl.calculateEstimatedGroupSize(smsTask3);
		try {
			smsCoreImpl.insertTask(smsTask3);
		} catch (SmsTaskValidationException e1) {
			fail(e1.getMessage());
		} catch (SmsSendDeniedException e) {
			fail("SmsSendDeniedException caught");
		} catch (SmsSendDisabledException sd) {
			fail("SmsSendDisabledException caught");
		}

		now.add(Calendar.MINUTE, -1);
		SmsTask smsTask2 = smsCoreImpl.getPreliminaryTask("smsTask2", new Date(
				now.getTimeInMillis()), "smsTask2MessageBody", smsAccount
				.getSakaiSiteId(), null, smsAccount.getSakaiUserId());
		smsTask2.setSmsAccountId(smsAccount.getId());
		smsCoreImpl.calculateEstimatedGroupSize(smsTask2);
		try {
			smsCoreImpl.insertTask(smsTask2);
		} catch (SmsTaskValidationException e1) {
			fail(e1.getMessage());
		} catch (SmsSendDeniedException e) {
			fail("SmsSendDeniedException caught");
		} catch (SmsSendDisabledException sd) {
			fail("SmsSendDisabledException caught");
		}

		now.add(Calendar.MINUTE, -3);
		SmsTask smsTask1 = smsCoreImpl.getPreliminaryTask("smsTask1", new Date(
				now.getTimeInMillis()), "smsTask1MessageBody", smsAccount
				.getSakaiSiteId(), null, smsAccount.getSakaiUserId());
		smsTask1.setSmsAccountId(smsAccount.getId());
		smsCoreImpl.calculateEstimatedGroupSize(smsTask1);
		try {
			smsCoreImpl.insertTask(smsTask1);
		} catch (SmsTaskValidationException e1) {
			fail(e1.getMessage());
		} catch (SmsSendDeniedException e) {
			fail("SmsSendDeniedException caught");
		} catch (SmsSendDisabledException sd) {
			fail("SmsSendDisabledException caught");
		}

		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		assertTrue(smsCoreImpl.getNextSmsTask() == null);
	}

	@Override
	protected void tearDown() throws Exception {
		smsSchedulerImpl.stopSmsScheduler();
		smsCoreImpl.smsSmpp.disconnectGateWay();
	}
}