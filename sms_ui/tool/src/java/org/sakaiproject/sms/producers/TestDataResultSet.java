package org.sakaiproject.sms.producers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.sakaiproject.sms.constants.SortDirection;

/**
 *	Temp object to test table generation
 */
public class TestDataResultSet {

	
	public static ArrayList<TestDataRow> testDataSet() {

		TestDataRow row1 = new TestDataRow("Bob", new Integer(12), "Abbey");
		TestDataRow row2 = new TestDataRow("Ann", new Integer(18), "Won");
		TestDataRow row3 = new TestDataRow("Mark", new Integer(12), "Get");
		TestDataRow row4 = new TestDataRow("John", new Integer(77), "JL");
		TestDataRow row5 = new TestDataRow("Sam", new Integer(12), "Jord");

		
		ArrayList<TestDataRow> resultSet = new ArrayList<TestDataRow>();
		resultSet.add(row1);
		resultSet.add(row2);
		resultSet.add(row3);
		resultSet.add(row4);
		resultSet.add(row5);

		
		return resultSet;
	}
	
	public static ArrayList<TestDataRow> sortByName(ArrayList<TestDataRow> list, final SortDirection sortDirection){
		
		Collections.sort(list, new Comparator<TestDataRow>(){

			public int compare(TestDataRow o1, TestDataRow o2) {
				if(sortDirection == SortDirection.ASC)
					return o1.getName().compareTo(o2.getName());
				else
					return o2.getName().compareTo(o1.getName());
			}
		});
		
		return list;
	}
	
	public static class TestDataRow {
		
		private String name;
		private Integer house;
		private String street;
		

		public TestDataRow() {
			super();
		}

		public TestDataRow(String name, Integer house, String street) {
			super();
			this.name = name;
			this.house = house;
			this.street = street;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getHouse() {
			return house;
		}

		public void setHouse(Integer house) {
			this.house = house;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}
	}
}
