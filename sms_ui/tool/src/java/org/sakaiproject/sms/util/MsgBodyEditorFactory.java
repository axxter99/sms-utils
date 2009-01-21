/***********************************************************************************
 * MsgBodyEditorFactory.java
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
package org.sakaiproject.sms.util;

import java.beans.PropertyEditor;

import uk.org.ponder.mapping.PropertyEditorFactory;

public class MsgBodyEditorFactory implements PropertyEditorFactory {
	public PropertyEditor getPropertyEditor() {
		return new MsgBodyEditor();
	}
}

/**
 * Class to edit message body as received from front-end
 */
class MsgBodyEditor extends java.beans.PropertyEditorSupport {

	/**
	 * Replaces all instances of carriage return + new line to only new line
	 */
	@Override
	public void setAsText(String text) {
		String modifiedBody = text.replaceAll("\r\n", "\n");
		setValue(modifiedBody);
	}
}