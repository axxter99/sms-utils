package org.sakaiproject.sms.model.hibernate.factory;

import org.sakaiproject.sms.logic.hibernate.exception.SmsAccountNotFoundException;
import org.sakaiproject.sms.logic.impl.hibernate.HibernateLogicFactory;
import org.sakaiproject.sms.model.hibernate.SmsAccount;
import org.sakaiproject.sms.model.hibernate.SmsTransaction;
import org.sakaiproject.sms.model.hibernate.constants.SmsConst_Billing;

public class SmsTransactionFactory {

	// Not in use, but there are unit test for it
	private static SmsTransaction createReserveCreditsTask(Long smsTaskId,
			Long smsAccountId, Integer credits)
			throws SmsAccountNotFoundException {

		SmsAccount smsAccount = getSmsAccount(smsAccountId);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSakaiUserId(smsAccount.getSakaiUserId());
		smsTransaction.setTransactionCredits(credits);
		smsTransaction
				.setTransactionTypeCode(SmsConst_Billing.TRANS_RESERVE_CREDITS);
		smsTransaction.setSmsTaskId(smsTaskId);
		smsTransaction.setCredits(smsAccount.getCredits());

		return smsTransaction;
	}

	// Not in use, but there are unit test for it
	public static SmsTransaction createCancelTask(Long smsTaskId,
			Long smsAccountId) throws SmsAccountNotFoundException {
		SmsAccount smsAccount = getSmsAccount(smsAccountId);

		SmsTransaction smsTransaction = new SmsTransaction();
		smsTransaction.setSmsAccount(smsAccount);
		smsTransaction.setSakaiUserId(smsAccount.getSakaiUserId());
		smsTransaction.setTransactionTypeCode(SmsConst_Billing.TRANS_CANCEL);
		smsTransaction.setSmsTaskId(smsTaskId);

		smsTransaction.setTransactionCredits(0);

		smsTransaction.setCredits(0l);

		return smsTransaction;
	}

	private static SmsAccount getSmsAccount(Long smsAccountId)
			throws SmsAccountNotFoundException {
		SmsAccount smsAccount = HibernateLogicFactory.getAccountLogic()
				.getSmsAccount(smsAccountId);

		if (smsAccount == null)
			throw new SmsAccountNotFoundException("Account id " + smsAccountId
					+ " does not exsits");
		return smsAccount;
	}

}
