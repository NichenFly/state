package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import play.Play;
import play.utils.Properties;
import util.CommonUtil;

/**
 * 安全控制
 * @author nichen
 * 
 */
public class Security extends Secure.Security{
	
	static final String SECURE_CONF = CommonUtil.RESOURCE_PATH + "secure.conf";
	
	static boolean authenticate(String username, String password) {
    	if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
    		return false;
    	}
    	
    	Properties properties = new Properties();
    	try {
    		properties.load(new FileInputStream(new File(SECURE_CONF)));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	for (int i = 1; true; i++) {
    		String usernameStored = properties.get("user" + i + ".name");
    		if (usernameStored == null) {
    			break;
    		}
    		if (username.trim().equals(usernameStored.trim())) {
    			String passwdStored = properties.get("user" + i + ".password");
    			if (passwdStored == null) {
    				break;
    			}
    			if (password.trim().equals(passwdStored.trim())) {
    				return true;
    			}
    		}
    	}
    	
        return false;
    }
}
