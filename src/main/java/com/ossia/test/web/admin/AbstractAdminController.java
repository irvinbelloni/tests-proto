package com.ossia.test.web.admin;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.ui.ModelMap;

public abstract class AbstractAdminController {
	
	protected final static String SESSION_ADMINISTRATOR_LIST_SORT = "candidate.list.sort";
	protected final static String SESSION_CANDIDATE_LIST_SORT = "candidate.list.sort";	
	
	protected final static String SESSION_LAST_ACTION = "last.action";
	protected final static String SESSION_ERROR_ACTION = "error.action";
	
	protected final static String TAB_HOME = "home";
	protected final static String TAB_ADMINISTRATOR = "administrator";
	protected final static String TAB_CANDIDATE = "candidate";
	
	@Autowired
    private ReloadableResourceBundleMessageSource messageSource;

	/**
	 * Gets the last action String from the session and put in the model map (as well as the error action which specifies an error
	 * @param model
	 * @param request
	 */
	protected void setLastActionInModel(ModelMap model, HttpServletRequest request) {		
		String lastAction = (String)request.getSession().getAttribute(SESSION_LAST_ACTION);
		request.getSession().removeAttribute(SESSION_LAST_ACTION);
		model.put("lastAction",  lastAction);
		
		String errorAction = (String)request.getSession().getAttribute(SESSION_ERROR_ACTION);
		request.getSession().removeAttribute(SESSION_ERROR_ACTION);
		model.put("errorAction",  errorAction);
	}
	
	/**
	 * Builds a string for action notification
	 * 
	 * @param messageCode Message code in the message_fr.properties
	 * @param arguments Arguments of the property string (variable list)
	 * @return The translated message
	 */
	protected String buildNotifyMessage (String messageCode, String ... arguments) {		
		return messageSource.getMessage(messageCode, arguments, new Locale("fr"));
	}
}
