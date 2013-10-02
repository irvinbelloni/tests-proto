package com.ossia.test.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FormatTimeTaglib extends TagSupport {
    
	private static final long serialVersionUID = 6934361314873779925L;
	
	private String input;
     
    @Override
    public int doStartTag() throws JspException {
         
        try {
            //Get the writer object for output.
        	JspWriter out = pageContext.getOut();
        	
        	try {
        		int duration = Integer.parseInt(input);
        		String durationString = "";
        		int nbHours = duration / (60 * 60);
        		if (nbHours >= 10) {
        			durationString += nbHours + ":";
        		} else {
        			durationString += "0" + nbHours + ":";
        		}
        		duration -= nbHours * 60 * 60;
        		int nbMinutes = duration / 60;
        		if (nbMinutes >= 10) {
        			durationString += nbMinutes + ":";
        		} else {
        			durationString += "0" + nbMinutes + ":";
        		}
        		duration -= nbMinutes * 60;
        		if (duration >= 10) {
        			durationString += duration;
        		} else {
        			durationString += "0" + duration;
        		}
        		out.print(durationString);
        	} catch (NumberFormatException nfe) {
        		out.print("");
        	} 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    
    public String getInput() {
        return input;
    }
    
    public void setInput(String input) {
        this.input = input;
    }
}
