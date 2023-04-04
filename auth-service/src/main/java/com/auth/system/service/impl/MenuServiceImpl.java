package com.auth.system.service.impl;


import com.auth.helper.MenuHelper;
import com.auth.helper.RouterHelper;
import com.auth.model.entity.Menu;
import com.auth.model.entity.RoleMenu;
import com.auth.model.vo.RouterVo;
import com.auth.system.mapper.MenuMapper;
import com.auth.system.service.MenuService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:53
 */
@Repository
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;


    @Override
    public List<RouterVo> findUserMenuList(Long id) {
        // admin 为超级管理员 id：1
        List<Menu> menuList = null;
        if (id == 1L) {
            // 查询所有菜单
            menuList = menuMapper.selectMenuList();
        } else {
            //根据角色 id 查询菜单列表
            menuList = menuMapper.selectMenuByUserId(id);
        }

        // 构建菜单树
        List<Menu> menuTree = MenuHelper.buildTree(menuList);
        // 构建路由
        return RouterHelper.buildRouters(menuTree);
    }

    @Override
    public List<String> findUserPermsList(Long id) {

        List<Menu> menuList = null;
        if (id == 1L) {
            menuList = menuMapper.selectMenuList();
        } else {
            menuList = menuMapper.selectMenuByUserId(id);
        }

        List<String> permissionList = new ArrayList<>();
        for (Menu menu : menuList) {
            if(menu.getType() == 2){
                permissionList.add(menu.getPerms());
            }
        }
        return permissionList;
    }

    /**
     * 查询所有的菜单
     *
     * @return 菜单列表树
     */
    @Override
    public List<Menu> findAllMenu(Long id) {
        // 查询所有菜单
        List<Menu> menus = menuMapper.selectMenuList();
        // 根据角色id查询其所具有的菜单
        List<Menu> menuList = menuMapper.findMenusByRoleId(id);
        List<Long> menuIds = menuList.stream().map(Menu::getId).collect(Collectors.toList());
        for (Menu menu : menus) {
            menu.setSelected(menuIds.contains(menu.getId()));
        }
        // 构建菜单树
        return MenuHelper.buildTree(menus);
    }

    /**
     * 根据角色id查询其所具有的菜单树
     *
     * @param id roleId
     * @return 菜单列表树
     */
    @Override
    public List<Menu> findMenuListByRoleId(Long id) {
        return menuMapper.findMenusByRoleId(id);
    }

    /**
     * 根据角色id删除菜单列表
     *
     * @param id 角色id
     * @return 受影响的行数
     */
    @Override
    public int deleteMenuListByRoleId(Long id) {
        return menuMapper.deleteMenusByRoleId(id);
    }

    /**
     * 批量添加角色具有的权限
     *
     * @param menus 角色-权限列表
     * @return 受影响的行数
     */
    @Override
    public int addMenuList(List<RoleMenu> menus) {
        return menuMapper.addMenuList(menus);
    }

    /**
     * 查询菜单树
     *
     * @return 菜单树
     */
    @Override
    public List<Menu> findMenuTree() {
        // 查询所有菜单
        List<Menu> menuList = menuMapper.selectMenuList();
        // 构建菜单树
        return MenuHelper.buildTree(menuList);
    }

    /**
     * 修改菜单
     *
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    @Override
    public int updateMenu(Menu menu) {
        menu.setUpdateTime(new Date());
        return menuMapper.updateMenu(menu);
    }

    /**
     * 添加菜单
     *
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    @Override
    public int addMenu(Menu menu) {
        menu.setCreateTime(new Date());
        menu.setUpdateTime(new Date());
        return menuMapper.insertMenu(menu);
    }

    /**
     * 删除菜单
     *
     * @param id 菜单id
     * @return 受影响的行数
     */
    @Override
    public int deleteMenu(Long id) {
        return menuMapper.removeMenu(id);
    }
}
