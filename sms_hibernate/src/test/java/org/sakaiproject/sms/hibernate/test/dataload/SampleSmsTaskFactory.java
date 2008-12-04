package org.sakaiproject.sms.hibernate.test.dataload;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.sms.hibernate.model.SmsTask;

public class SampleSmsTaskFactory implements Listable{

	List<SmsTask> smsTasks;
	RandomUtils randomUtils = new RandomUtils();
	
	public SampleSmsTaskFactory() {
		createSampleSmsTasks(); 
	}
		
	public Object getElementAt(int i) {
		return getTestSmsTask(i);
	}
	
	public void refreshList() {
		createSampleSmsTasks();
		
	}

	private void createSampleSmsTasks() {
		smsTasks = new ArrayList<SmsTask>();
		
		SmsTask task1 = new SmsTask("3", "SC", "CHEM100-05", 123456, "Test date moved form 12 Jan to 15 Jan");
		task1.setDateCreated(randomUtils.getBoundRandomDate(2008));
		task1.setDateToSend(randomUtils.getBoundRandomDate(2008));
		task1.setSenderUserName("Prof Blue");
		smsTasks.add(task1);
		
		SmsTask task2 = new SmsTask("56", "GM", "EEE475-05", 123457, "Matlab tutorial move to Science labs D");
		task2.setDateCreated(randomUtils.getBoundRandomDate(2008));
		task2.setDateToSend(randomUtils.getBoundRandomDate(2008));
		task2.setSenderUserName("Prof Green");
		smsTasks.add(task2);

		
		SmsTask task3 = new SmsTask("32", "RD", "MAM100-05", 123458, "Location of tut changed to Science Block");
		task3.setDateCreated(randomUtils.getBoundRandomDate(2008));
		task3.setDateToSend(randomUtils.getBoundRandomDate(2008));
		task3.setSenderUserName("Prof Red");
		smsTasks.add(task3);

		
		SmsTask task4 = new SmsTask("67", "EO", "PHY131-05", 123459, "Problem set to be handed in by 15 Jan");
		task4.setDateCreated(randomUtils.getBoundRandomDate(2008));
		task4.setDateToSend(randomUtils.getBoundRandomDate(2008));
		task4.setSenderUserName("Prof Lime");
		smsTasks.add(task4);
		
		SmsTask task5 = new SmsTask("42", "FQ", "BUS100-05", 123460, "No tutorial required this month");
		task5.setDateCreated(randomUtils.getBoundRandomDate(2008));
		task5.setDateToSend(randomUtils.getBoundRandomDate(2008));
		task5.setSenderUserName("Prof Orange");
		smsTasks.add(task5);
		
	}
	
	public List<SmsTask> getAllTestSmsTasks(){
		return smsTasks;
	}

	public SmsTask getTestSmsTask(int index){
		
		if(index >= smsTasks.size())
			throw new RuntimeException("The specified index is too high");
			
		return smsTasks.get(index);	
	}
	
	public int getTotalSmsTasks(){
		return smsTasks.size();
	}

	
}
