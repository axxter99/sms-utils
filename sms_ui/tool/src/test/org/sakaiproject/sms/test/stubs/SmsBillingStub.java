package org.sakaiproject.sms.test.stubs;

import java.util.Date;
import java.util.Set;

import org.sakaiproject.sms.api.SmsBilling;
import org.sakaiproject.sms.hibernate.logic.impl.exception.MoreThanOneAccountFoundException;
import org.sakaiproject.sms.hibernate.model.SmsMessage;
import org.sakaiproject.sms.hibernate.model.SmsTask;

public class SmsBillingStub implements SmsBilling {

	public boolean sufficientCredits = false;

	public void allocateCredits(Long arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public boolean checkSufficientCredits(Long arg0, int arg1) {
		// TODO Auto-generated method stub
		return sufficientCredits;
	}

	public Integer convertAmountToCredits(Float arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Float convertCreditsToAmount(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void debitAccount(Long arg0, Float arg1) {
		// TODO Auto-generated method stub

	}

	public Set getAccTransactions(Long arg0, Date arg1, Date arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getAccountBalance(Long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAccountCredits(Long arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getAccountID(String arg0, String arg1, Integer arg2)
			throws MoreThanOneAccountFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getAllSiteAccounts(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean insertAccount(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Boolean insertTransaction(Long arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void recalculateAccountBalance(Long arg0) {
		// TODO Auto-generated method stub

	}

	public void recalculateAccountBalances() {
		// TODO Auto-generated method stub

	}

	public boolean reserveCredits(SmsTask arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean settleCreditDifference(SmsTask arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean creditLateMessage(SmsMessage smsMessage) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean cancelPendingRequest(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean checkSufficientCredits(SmsTask smsTask) {
		return false;
	}

}