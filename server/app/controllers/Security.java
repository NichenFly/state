package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import play.Play;
import util.CommonUtil;

public class Security extends Secure.Security{
	
	static final String secureConf = CommonUtil.resourcesPath + "secure.conf";
	
	static boolean authenticate(String username, String password) {
    	if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
    		return false;
    	}
    	
    	Play.configuration.clear();
    	try {
			Play.configuration.load(new FileInputStream(new File(secureConf)));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	for (int i = 1; true; i++) {
    		String usernameStored = Play.configuration.getProperty("user" + i + ".name");
    		if (usernameStored == null) {
    			break;
    		}
    		if (username.trim().equals(usernameStored.trim())) {
    			String passwdStored = Play.configuration.getProperty("user" + i + ".password");
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
