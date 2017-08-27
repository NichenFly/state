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
import util.CommonUtil;

public class MySQLController extends Controller {
    
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
