package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Parameter;
import javax.persistence.Query;

import models.Result;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.mvc.*;
import util.CommonUtil;
import util.MySqlDBUtil;

public class MySQLController extends Controller {
    
    public static void getBases() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
    	Map<String, String> infos = MySqlDBUtil.getBases("10.18.13.34", "3306", "root", "Root1234@");
    	result.data = infos;
    	renderJSON(result);
    }
    
    public static void getCaches() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
    	Connection con = MySqlDBUtil.getMysqlConnection("10.18.13.34", "3306", "root", "Root1234@");
    	result.data = MySqlDBUtil.getCaches(con);
    	renderJSON(result);
    }
    
    public static void getReplications() {
    	Result result = new Result();
    	result.code = 200;
    	result.msg = "成功";
//    	Connection con = MySqlDBUtil.getMysqlConnection("10.18.13.34", "3306", "root", "Root1234@");
    	result.data = MySqlDBUtil.getReplications("10.18.13.35", "3306", "root", "Root1234@");
    	renderJSON(result);
    }

}
