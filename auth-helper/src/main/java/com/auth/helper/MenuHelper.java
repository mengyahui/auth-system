package com.auth.helper;

import com.auth.model.entity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:15
 */
public class MenuHelper {

    public static List<Menu> buildTree(List<Menu> menuList) {
        List<Menu> trees = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == 0) {
                trees.add(findChildren(menu,menuList));
            }
        }
        return trees;
    }

    public static Menu findChildren(Menu menu, List<Menu> treeNodes) {
        menu.setChildren(new ArrayList<>());
        for (Menu it : treeNodes) {
            if(menu.getId().toString().equals(it.getParentId().toString())) {
                if (menu.getChildren() == null) {
                    menu.setChildren(new ArrayList<>());
                }
                menu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return menu;
    }
}
