package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Menu;
import models.Result;
import models.SubMenu;
import play.mvc.*;

public class MenuController extends Controller {

    public static void getMenus() {
    	List<Menu> menus = new ArrayList<Menu>();
    	Menu menu = new Menu();
    	menu.menuName = "MySQL类别";
    	menu.orderNum = 1;
    	
    	List<SubMenu> subMenus = new ArrayList<SubMenu>();
    	SubMenu subMenu = new SubMenu();
    	subMenu.name = "base";
    	subMenu.orderNum = 11;
    	subMenu.display = "基本状态";
    	subMenu.path = "/bases";
    	subMenu.desc = "显示数据库的基本状态";
    	subMenus.add(subMenu);
    	
    	subMenu = new SubMenu();
    	subMenu.name = "replication";
    	subMenu.orderNum = 13;
    	subMenu.display = "复制状态";
    	subMenu.path = "/replications";
    	subMenu.desc = "显示数据库的主从复制状态";
    	subMenus.add(subMenu);
    	
    	menu.subMenus = subMenus;
    	
    	menus.add(menu);
    	
    	Result result = new Result();
    	result.setCode(Result.OK);
    	result.setMsg(Result.OK_MSG);
    	result.setData(menus);
    	
        renderJSON(result);
    }

}
