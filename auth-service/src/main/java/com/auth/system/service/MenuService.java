package com.auth.system.service;



import com.auth.model.entity.Menu;
import com.auth.model.entity.RoleMenu;
import com.auth.model.vo.RouterVo;

import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:52
 */
public interface MenuService {

    /**
     * 根据用户id查询菜单树
     * @param id userId
     * @return 菜单树
     */
    List<RouterVo> findUserMenuList(Long id);

    /**
     * 根据用户id查询权限
     * @param id userId
     * @return 权限列表
     */
    List<String> findUserPermsList(Long id);

    /**
     * 查询所有的菜单
     * @return 菜单列表树
     */
    List<Menu> findAllMenu(Long id);

    /**
     * 根据角色id查询其所具有的菜单
     * @param id roleId
     * @return 菜单列表
     */
    List<Menu> findMenuListByRoleId(Long id);

    /**
     * 根据角色id删除菜单列表
     * @param id 角色id
     * @return 受影响的行数
     */
    int deleteMenuListByRoleId(Long id);

    /**
     * 批量添加角色具有的权限
     * @param menus 角色-权限列表
     * @return 受影响的行数
     */
    int addMenuList(List<RoleMenu> menus);

    /**
     * 查询菜单树
     * @return 菜单树
     */
    List<Menu> findMenuTree();

    /**
     * 修改菜单
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    int updateMenu(Menu menu);

    /**
     * 添加菜单
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    int addMenu(Menu menu);

    /**
     * 删除菜单
     * @param id 菜单id
     * @return 受影响的行数
     */
    int deleteMenu(Long id);
}
