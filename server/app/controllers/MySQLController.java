package controllers;

import java.util.ArrayList;

import models.Result;
import play.mvc.*;

public class MySQLController extends Controller {

    public static void index() {
        render();
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
