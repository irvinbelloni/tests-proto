package com.ossia.test.web.taglib;

public class FormatQuestion {

	public static String formatInput (String input) {
    	String beforeString = null;
    	String codeString = null;
    	String afterString = null;
    	String outString = null;
    	
    	int indexCodeStart = input.indexOf("[code style=");
    	if (input.length() == 0 || indexCodeStart == -1) { // No code tag, simple formatting
    		outString = input.replace("[DB]",  "\"");
        	outString = outString.replace("[NL]", "<br/>");
        	outString = outString.replace("[TAB]", "&nbsp;&nbsp;&nbsp;");
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
		codeString = codeString.replace("[TAB]", "&nbsp;&nbsp;&nbsp;");
		codeString = codeString.replace("[code style=java]", "<script type=\"syntaxhighlighter\" class=\"brush:java\">\r\n<![CDATA[");
		codeString = codeString.replace("[code style=csharp]", "<script type=\"syntaxhighlighter\" class=\"brush:csharp\">\r\n<![CDATA[");
		codeString = codeString.replace("[code style=xml]", "<script type=\"syntaxhighlighter\" class=\"brush:xml\">\r\n<![CDATA[");
		codeString = codeString.replace("[code style=php]", "<script type=\"syntaxhighlighter\" class=\"brush:php\">\r\n<![CDATA[");
		codeString = codeString.replace("[code style=cpp]", "<script type=\"syntaxhighlighter\" class=\"brush:cpp\">\r\n<![CDATA[");
		codeString = codeString.replace("[code style=sql]", "<script type=\"syntaxhighlighter\" class=\"brush:sql\">\r\n<![CDATA[");
		codeString = codeString.replace("[/code]", "]]>\r\n</script>");
		outString += codeString;    	
    	
    	outString += formatInput(afterString);
    		
    	return outString;
    }
}
