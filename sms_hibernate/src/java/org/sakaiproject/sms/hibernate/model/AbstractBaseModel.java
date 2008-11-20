/***********************************************************************************
 * AbstractBaseModel.java
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

package org.sakaiproject.sms.hibernate.model;

// TODO: Auto-generated Javadoc
/**
 * This is the base model class from which all model class should extend.
 * 
 * It also holds the id field that should be used as the unique identifier for
 * all the model classes.
 * 
 * @author Julian Wyngaard
 * @version 1.0
 * @created 19-Nov-2008
 */
public abstract class AbstractBaseModel {

	/**
	 * Represent the unique id for the model object.
	 * */
	private Integer id;

	/**
	 * Exists.
	 * 
	 * @return true if this entity already exists in the persistent store - we
	 *         determine this if id is allocated.
	 */
	public boolean exists() {
		if (getId() != null)
			return true;
		return false;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}