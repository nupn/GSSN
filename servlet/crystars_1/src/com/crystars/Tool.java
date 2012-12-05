package com.crystars;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Tool {
	
    static public String decode(String str){
    	try{
    		return URLDecoder.decode(str,"utf-8");
    	}catch(UnsupportedEncodingException e)
    	{
    		
    	}
    	return "";
    }

}
