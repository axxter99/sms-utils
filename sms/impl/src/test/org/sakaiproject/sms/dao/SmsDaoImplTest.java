/******************************************************************************
 * SmsDaoImplTest.java - created by Sakai App Builder -AZ
 * 
 * Copyright (c) 2008 Sakai Project/Sakai Foundation
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 * 
 *****************************************************************************/

package org.sakaiproject.sms.dao;

import java.util.Date;

import junit.framework.Assert;

import org.sakaiproject.sms.dao.SmsDao;
import org.sakaiproject.sms.logic.FakeDataPreload;
import org.sakaiproject.sms.model.SmsItem;
import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * Testing for the specialized DAO methods (do not test the Generic Dao methods)
 * @author Sakai App Builder -AZ
 */
public class SmsDaoImplTest extends AbstractTransactionalSpringContextTests {

   protected SmsDao dao;
   private FakeDataPreload tdp;

   private SmsItem item;

   private final static String ITEM_TITLE = "New Title";
   private final static String ITEM_OWNER = "11111111";
   private final static String ITEM_SITE = "22222222";
   private final static Boolean ITEM_HIDDEN = Boolean.FALSE;


   protected String[] getConfigLocations() {
      // point to the needed spring config files, must be on the classpath
      // (add component/src/webapp/WEB-INF to the build path in Eclipse),
      // they also need to be referenced in the project.xml file
      return new String[] {"hibernate-test.xml", "spring-hibernate.xml"};
   }

   // run this before each test starts
   protected void onSetUpBeforeTransaction() throws Exception {
      // create test objects
      item = new SmsItem(ITEM_TITLE, ITEM_OWNER, ITEM_SITE, ITEM_HIDDEN, new Date());
   }

   // run this before each test starts and as part of the transaction
   protected void onSetUpInTransaction() {
      // load the spring created dao class bean from the Spring Application Context
      dao = (SmsDao) applicationContext.
         getBean("org.sakaiproject.sms.dao.SmsDao");
      if (dao == null) {
         throw new NullPointerException("DAO could not be retrieved from spring context");
      }

      // load up the test data preloader from spring
      tdp = (FakeDataPreload) applicationContext.
         getBean("org.sakaiproject.sms.logic.test.FakeDataPreload");
      if (tdp == null) {
         throw new NullPointerException("FakeDataPreload could not be retrieved from spring context");
      }

      // init the class if needed

      // check the preloaded data
      Assert.assertTrue("Error preloading data", dao.countAll(SmsItem.class) > 0);

      // preload data if desired
      dao.save(item);
   }


   /**
    * ADD unit tests below here, use testMethod as the name of the unit test,
    * Note that if a method is overloaded you should include the arguments in the
    * test name like so: testMethodClassInt (for method(Class, int);
    */


   // THESE ARE SAMPLE UNIT TESTS WHICH SHOULD BE REMOVED LATER -AZ
   /**
    * TODO - Remove this sample unit test
    * Test method for {@link org.sakaiproject.sms.dao.impl.GenericHibernateDao#save(java.lang.Object)}.
    */
   public void testSave() {
      SmsItem item1 = new SmsItem("New item1", ITEM_OWNER, ITEM_SITE, ITEM_HIDDEN, new Date());
      dao.save(item1);
      Long itemId = item1.getId();
      Assert.assertNotNull(itemId);
      Assert.assertEquals(7, dao.countAll(SmsItem.class));
   }

   /**
    * TODO - Remove this sample unit test
    * Test method for {@link org.sakaiproject.sms.dao.impl.GenericHibernateDao#delete(java.lang.Object)}.
    */
   public void testDelete() {
      Assert.assertEquals(dao.countAll(SmsItem.class), 6);
      dao.delete(item);
      Assert.assertEquals(dao.countAll(SmsItem.class), 5);
   }

   /**
    * TODO - Remove this sample unit test
    * Test method for {@link org.sakaiproject.sms.dao.impl.GenericHibernateDao#findById(java.lang.Class, java.io.Serializable)}.
    */
   public void testFindById() {
      Long id = item.getId();
      Assert.assertNotNull(id);
      SmsItem item1 = (SmsItem) dao.findById(SmsItem.class, id);
      Assert.assertNotNull(item1);
      Assert.assertEquals(item, item1);
   }

   /**
    * Add anything that supports the unit tests below here
    */
}
