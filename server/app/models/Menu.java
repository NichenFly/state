package models;

import java.util.List;

/**
 * 菜单的模型类
 * @author nichen
 *
 */
public class Menu {
	public String menuName;
	public String menuIcon = "";
	public int orderNum;
	public List<SubMenu> subMenus;
}
