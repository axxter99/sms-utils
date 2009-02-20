package org.sakaiproject.sms.logic;

import org.hibernate.HibernateException;
import org.sakaiproject.sms.dao.SmsDao;
import org.sakaiproject.sms.model.hibernate.BaseModel;

/**
 * Base logic to retrieve from db
 */
abstract public class BaseLogic {
	
	protected SmsDao smsDao;
	
	public void setSmsDao(SmsDao smsDao) {
		this.smsDao = smsDao;
	}
	
	/**
	 * Persists the given instance to the database. The save or update operation
	 * is done over a transaction. In case of any errors the transaction is
	 * rolled back.
	 * 
	 * @param object
	 *            object instance to be persisted
	 * @exception HibernateException
	 *                if any error occurs while saving or updating data in the
	 *                database
	 */
	protected void persist(BaseModel object) throws HibernateException {
		smsDao.save(object);
	}

	/**
	 * Deletes the given instance from the database.
	 * 
	 * @exception HibernateException
	 *                if any error occurs while saving or updating data in the
	 *                database
	 */
	protected void delete(Object object) throws HibernateException {
		smsDao.delete(object);
	}

	/**
	 * Find by id.
	 * 
	 * @param className
	 *            the class name
	 * @param id
	 *            the id
	 * 
	 * @return the object
	 */
	protected Object findById(Class className, Long id) {
		return smsDao.findById(className, id);
	}
	
	
}