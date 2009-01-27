/***********************************************************************************
 * SearchCriteriaRenderer.java
 * Copyright (c) 2008 Sakai Project/Sakai Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 * 
 *      http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 **********************************************************************************/
package org.sakaiproject.sms.tool.renderers;

import org.sakaiproject.sms.model.hibernate.constants.SmsConst_DeliveryStatus;
import org.sakaiproject.sms.tool.beans.ActionResults;
import org.springframework.util.Assert;

import uk.org.ponder.beanutil.BeanGetter;
import uk.org.ponder.rsf.components.ELReference;
import uk.org.ponder.rsf.components.UIBoundList;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIDeletionBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.evolvers.FormatAwareDateInputEvolver;

public class SearchCriteriaRenderer {

	private String labelID;
	private String labelDropDown;
	private String searchBeanName;
	private FormatAwareDateInputEvolver dateEvolver;
	private BeanGetter ELEvaluator;

	public void setDateEvolver(FormatAwareDateInputEvolver dateEvolver) {
		this.dateEvolver = dateEvolver;
	}

	public void setELEvaluator(BeanGetter ELEvaluator) {
		this.ELEvaluator = ELEvaluator;
	}

	public void setSearchBeanName(String searchBeanName) {
		this.searchBeanName = searchBeanName;
	}

	public void setLabelID(String labelID) {
		this.labelID = labelID;
	}

	public void setLabelDropDown(String labelDropDown) {
		this.labelDropDown = labelDropDown;
	}

	public void init() {
		Assert.notNull(labelID);
		Assert.notNull(labelDropDown);
		Assert.notNull(searchBeanName);
		dateEvolver.setStyle(FormatAwareDateInputEvolver.DATE_INPUT);
	}

	public void createSearchCriteria(UIContainer tofill, String divID,
			String viewID) {

		init();

		UIJointContainer searchCriteria = new UIJointContainer(tofill, divID,
				"search-component:");

		UIForm searchForm = UIForm.make(searchCriteria, "search-criteria");

		// No drop down for Transaction log
		// TODO: refactor by splitting into 3 different classes when design is
		// known
		if (labelDropDown.indexOf("Task") != -1)
			createTaskDropDown(searchForm);
		if (labelDropDown.indexOf("Message") != -1)
			createMessageDropDown(searchForm);

		UIInput dateFrom = UIInput.make(searchForm, "date-from:",
				searchBeanName + "." + "dateFrom");
		dateEvolver.evolveDateInput(dateFrom);

		UIInput dateTo = UIInput.make(searchForm, "date-to:", searchBeanName
				+ "." + "dateTo");
		dateEvolver.evolveDateInput(dateTo);

		// No Id field for Task
		if (labelDropDown.indexOf("Task") == -1)
			UIOutput.make(searchForm, "label-id", labelID);

		if (labelDropDown.indexOf("Task") == -1)
			UIInput.make(searchForm, "id", createSearchELString("number"));

		// No Tool name search criteria
		if (labelDropDown.indexOf("Type") == -1) {
			UIOutput.make(searchForm, "tool-name-label", "Tool Name:");
			UIInput.make(searchForm, "tool-name",
					createSearchELString("toolName"));
		}

		// No sender Transaction log
		if ((labelDropDown.indexOf("Task") != -1)
				|| (labelDropDown.indexOf("Message") != -1))
			createSender(searchForm);

		UICommand
				.make(searchForm, "search", createSearchELString("fireAction"));
		UICommand command = UICommand.make(searchForm, "reset",
				"sms.general.reset").setReturn(ActionResults.RESET);
		command.addParameter(new UIDeletionBinding(
				"#{destroyScope.searchScope}"));

		clearDates(tofill);

	}

	/**
	 * Fixup to clear date input for null dates
	 */
	private void clearDates(UIContainer tofill) {
		if (!(ELEvaluator.getBean(searchBeanName) == null)) {

			if (ELEvaluator.getBean(searchBeanName + ".dateFrom") == null) {
				UIInitBlock
						.make(
								tofill,
								"init-clear-from-date-input",
								"initClearDateInput",
								new Object[] { "searchCriteria::date-from:1:date-field" });
			}
			if (ELEvaluator.getBean(searchBeanName + ".dateTo") == null) {
				UIInitBlock
						.make(
								tofill,
								"init-clear-end-date-input",
								"initClearDateInput",
								new Object[] { "searchCriteria::date-to:1:date-field" });
			}
		}

	}

	private void createSender(UIForm searchForm) {

		UIOutput.make(searchForm, "sender-label", "Sender:");
		UIInput.make(searchForm, "sender", createSearchELString("sender"));
	}

	private void createTaskDropDown(UIForm searchForm) {
		UIOutput.make(searchForm, "label-dropdown", labelDropDown);

		UISelect combo = UISelect.make(searchForm, "task-status");
		combo.selection = new UIInput();
		combo.selection.valuebinding = new ELReference(
				createSearchELString("status"));
		UIBoundList comboValues = new UIBoundList();
		comboValues.setValue(new String[] { "",
				SmsConst_DeliveryStatus.STATUS_RETRY,
				SmsConst_DeliveryStatus.STATUS_SENT,
				SmsConst_DeliveryStatus.STATUS_BUSY,
				SmsConst_DeliveryStatus.STATUS_PENDING,
				SmsConst_DeliveryStatus.STATUS_INCOMPLETE,
				SmsConst_DeliveryStatus.STATUS_FAIL,
				SmsConst_DeliveryStatus.STATUS_TASK_COMPLETED });
		combo.optionlist = comboValues;
		UIBoundList comboNames = new UIBoundList();
		comboNames.setValue(new String[] { "All", "Retry", "Sent", "Busy",
				"Pending", "Incomplete", "Failed", "Complete" });
		combo.optionnames = comboNames;
	}

	private void createMessageDropDown(UIForm searchForm) {
		UIOutput.make(searchForm, "label-dropdown", labelDropDown);

		UISelect combo = UISelect.make(searchForm, "task-status");
		combo.selection = new UIInput();
		combo.selection.valuebinding = new ELReference(
				createSearchELString("status"));
		UIBoundList comboValues = new UIBoundList();
		comboValues.setValue(new String[] { "",
				SmsConst_DeliveryStatus.STATUS_SENT,
				SmsConst_DeliveryStatus.STATUS_PENDING,
				SmsConst_DeliveryStatus.STATUS_FAIL,
				SmsConst_DeliveryStatus.STATUS_DELIVERED });
		combo.optionlist = comboValues;
		UIBoundList comboNames = new UIBoundList();
		comboNames.setValue(new String[] { "All", "Sent", "Pending", "Failed",
				"Delivered" });
		combo.optionnames = comboNames;
	}

	private String createSearchELString(String field) {
		return "#{" + searchBeanName + "." + field + "}";
	}

}