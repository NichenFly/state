package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import models.Result;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.mvc.*;

public class MySQLController extends Controller {
	
	static final String resourcesPath = Play.applicationPath + "/conf/resources/"; 
	static final String mysqlConf = resourcesPath + "mysql.conf";
	static final String redisConf = resourcesPath + "redis.conf";

    public static void index() {
    	try {
    		InputStream inStream = new FileInputStream(new File(mysqlConf));
			Play.configuration.load(inStream);
			String user = Play.configuration.getProperty("mysql.host1.user");
			Logger.info("user: %s", user);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void getBases() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
    	result.data = new ArrayList<Object>();
    	renderJSON(result);
    }
    
    public static void getCaches() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
    	result.data = new ArrayList<Object>();
    	renderJSON(result);
    }
    
    public static void getReplications() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
    	result.data = new ArrayList<Object>();
    	renderJSON(result);
    }

}
