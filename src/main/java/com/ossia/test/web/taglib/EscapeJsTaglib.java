package com.ossia.test.web.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public final class EscapeJsTaglib extends TagSupport {
    
	private static final long serialVersionUID = 6978082314873779925L;
	
	private String input;
	
	private boolean lines;
     
    @Override
    public int doStartTag() throws JspException {
         
        try {
            //Get the writer object for output.
            JspWriter out = pageContext.getOut();
            String outString = input.replace("'",  "\\\'");            
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

	public boolean getLines() {
		return lines;
	}

	public void setLines(boolean lines) {
		this.lines = lines;
	}
}
