package org.sakaiproject.sms.producers;

import org.sakaiproject.sms.util.MessageFixupHelper;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;

public class DebitAccountProducer implements ViewComponentProducer{

	public static final String VIEW_ID = "debit_account";
	
	private MessageFixupHelper messageFixupHelper;

	public void setMessageFixupHelper(MessageFixupHelper messageFixupHelper) {
		this.messageFixupHelper = messageFixupHelper;
	}

	public void init() {
		messageFixupHelper.fixupMessages("account-id-input",
				"account-amount-input");
	}


	public String getViewID() {
		return VIEW_ID;
	}

	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		
		init();
		
		UIMessage.make(tofill, "page-title", "sms.debit.account.title");
		UIMessage.make(tofill, "sms-debit-account-heading", "sms.debit.account.title");
		
		UIForm form = UIForm.make(tofill, "debit-account-form");
		
		UIMessage.make(form, "account-id-label", "sms.debit.account.id");
		UIInput.make(form, "account-id-input", "#{debitAccountBean.accountId}");
		
		UIMessage.make(form, "account-amount-label", "sms.debit.account.amount");
		
		UIInput.make(form, "account-amount-input", "#{debitAccountBean.amountToDebit}");
		UICommand.make(form, "save-btn", "#{debitAccountActionBean.debitAccount}");
	}
}