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
            	outString = outString.replace("[TAB]", "    ");
            } else {
            	outString = formatInput(input);
            }
            out.print(outString);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
    
    private String formatInput (String input) {
    	String beforeString = null;
    	String codeString = null;
    	String afterString = null;
    	String outString = null;
    	
    	int indexCodeStart = input.indexOf("[code style=");
    	if (input.length() == 0 || indexCodeStart == -1) { // No code tag, simple formatting
    		outString = input.replace("[DB]",  "\"");
        	outString = outString.replace("[NL]", "<br/>");
        	outString = outString.replace("[TAB]", "&nbsp;&nbsp;&nbsp;&nbsp;");
        	return outString;
    	}
    	
    	beforeString = input.substring(0,  indexCodeStart);
    	int indexCodeEnd = input.indexOf("[/code]");
    	if (indexCodeEnd != -1) {
    		indexCodeEnd += 7;
    		codeString = input.substring(indexCodeStart, indexCodeEnd);    		
    		afterString = input.substring(indexCodeEnd);
    	} else {
    		codeString = input.substring(indexCodeStart) + "[NL][/code]";
    		afterString = "";
    	}
    	
    	outString = formatInput(beforeString);
    	
    	codeString = codeString.replace("[DB]",  "\"");
    	codeString = codeString.replace("[NL]", "\r\n");
		codeString = codeString.replace("[TAB]", "&nbsp;&nbsp;&nbsp;&nbsp;");
		codeString = codeString.replace("[code style=java]", "<script type=\"syntaxhighlighter\" class=\"brush:java\">\r\n<![CDATA[");
		codeString = codeString.replace("[/code]", "]]>\r\n</script>");
		outString += codeString;    	
    	
    	outString += formatInput(afterString);
    		
    	return outString;
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
