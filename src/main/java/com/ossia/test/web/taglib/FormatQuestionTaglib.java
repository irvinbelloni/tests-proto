package com.ossia.test.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FormatQuestionTaglib extends TagSupport {
    
	private static final long serialVersionUID = 6978082314873779925L;
	
	@SuppressWarnings("unused")
	private final static String MODE_DISPLAY = "display";
	private final static String MODE_INPUT = "input";
	
	private String input;
	
	private String mode;
     
    @Override
    public int doStartTag() throws JspException {
         
        try {
            //Get the writer object for output.
        	JspWriter out = pageContext.getOut();
            
        	String outString = null;
            if (mode.equals(MODE_INPUT)) {
            	outString = input.replace("[DB]",  "\"");
            	outString = outString.replace("[NL]", "\r\n");
            	outString = outString.replace("[TAB]", "   ");
            } else {
            	outString = FormatQuestion.formatInput(input);
            }
            out.print(outString);
 
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
