package org.sakaiproject.sms.tool.producers;

import org.sakaiproject.sms.logic.hibernate.SmsTaskLogic;
import org.sakaiproject.sms.model.hibernate.SmsTask;
import org.sakaiproject.sms.tool.params.SmsParams;
import org.sakaiproject.sms.tool.renderers.SavedSelectionsRenderer;
import org.sakaiproject.sms.tool.renderers.SmsMessageRenderer;
import org.sakaiproject.sms.tool.renderers.UserNavBarRenderer;
import org.sakaiproject.sms.tool.util.DateUtil;
import org.sakaiproject.sms.tool.util.StatusUtils;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIMessage;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIAlternativeTextDecorator;
import uk.org.ponder.rsf.components.decorators.UIIDStrategyDecorator;
import uk.org.ponder.rsf.request.EarlyRequestParser;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

public class FailedSmsDetailProducer implements ViewComponentProducer, ViewParamsReporter {
	
	public static final String VIEW_ID = "failed-sms-detail";
	
	public String getViewID() {
		return VIEW_ID;
	}
	
	private SmsTaskLogic smsTaskLogic;
	public void setSmsTaskLogic(SmsTaskLogic smsTaskLogic) {
		this.smsTaskLogic = smsTaskLogic;
	}

	private UserNavBarRenderer userNavBarRenderer;
	public void setUserNavBarRenderer(UserNavBarRenderer userNavBarRenderer) {
		this.userNavBarRenderer = userNavBarRenderer;
	}
	
	private DateUtil dateUtil;
	public void setDateUtil(DateUtil dateUtil) {
		this.dateUtil = dateUtil;
	}
	
	private StatusUtils statusUtils;
	public void setStatusUtils(StatusUtils statusUtils) {
		this.statusUtils = statusUtils;
	}

	private SavedSelectionsRenderer savedSelectionsRenderer;
	public void setSavedSelectionsRenderer(
			SavedSelectionsRenderer savedSelectionsRenderer) {
		this.savedSelectionsRenderer = savedSelectionsRenderer;
	}
	
	private SmsMessageRenderer smsMessageRenderer;
	public void setSmsMessageRenderer(SmsMessageRenderer smsMessageRenderer) {
		this.smsMessageRenderer = smsMessageRenderer;
	}

	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {
		
		if ( viewparams != null ){
			SmsParams statusParams = (SmsParams) viewparams;
			if ( statusParams != null && statusParams.id != null){
				
				Long smsId = Long.parseLong(statusParams.id);
				SmsTask smsTask = smsTaskLogic.getSmsTask(smsId);
		
				//Top links
				userNavBarRenderer.makeNavBar(tofill, "navIntraTool:", VIEW_ID);
				
				//Show message
				smsMessageRenderer.renderMessage(smsTask, tofill, "message:");
				
				UIBranchContainer status = UIBranchContainer.make(tofill, "status:");
				String statusCode = smsTask.getStatusCode();
				UILink.make(status, "sms-status", statusUtils.getStatusIcon(statusCode))
					.decorate(new UIAlternativeTextDecorator(statusUtils.getStatusFullName(statusCode)));
				UIOutput.make(status, "sms-status-title", statusUtils.getStatusFullName(statusCode));
				
				UIMessage.make(tofill, "sms-sent", "ui.failed.sms.expired", new Object[] { dateUtil.formatDate(smsTask.getDateProcessed()) });
				
				if ( smsTask.getFailReason() != null && ! "".equals(smsTask.getFailReason()) ){
					UIMessage.make(tofill, "reason", "ui.failed.sms.reason", 
						new Object[] { smsTask.getFailReason() });
				}

				UIMessage.make(tofill, "recipients", "ui.failed.sms.recipients", new Object[] { smsTask.getGroupSizeEstimate() });
				
				//Insert original user selections
				savedSelectionsRenderer.renderSelections(smsTask, tofill, "savedSelections:");
				
				UIForm form = UIForm.make(tofill, "editForm", new SmsParams(SendSMSProducer.VIEW_ID, smsId.toString(), StatusUtils.statusType_REUSE));
				form.type = EarlyRequestParser.RENDER_REQUEST;
				
				//keep the id somewhere that the JS can grab and use it for processing actions edit or delete
				UIInput.make(form, "smsId", null, smsId + "")
					.decorate(new UIIDStrategyDecorator("smsId"));
				/**
				 * These 3 action buttons are handled by JS. RSF is only needed for i18N
				 */
				UICommand.make(form, "edit", UIMessage.make("sms.general.editandsend"))
					.decorate(new UIIDStrategyDecorator("smsEdit"));
				UICommand.make(form, "delete", UIMessage.make("sms.general.delete"))
					.decorate(new UIIDStrategyDecorator("smsDelete"));
				UICommand.make(form, "back", UIMessage.make("sms.general.back"));
				
			}else{
				//TODO: show error message since sms.id() is not specified
			}
		}
		
	
	}

	public ViewParameters getViewParameters() {
		// TODO Auto-generated method stub
		return new SmsParams();
	}

}
