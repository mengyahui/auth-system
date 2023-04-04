package com.auth.system.mapper;


import com.auth.model.entity.Menu;
import com.auth.model.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {

    List<Menu> selectMenuByUserId(Long id);

    List<Menu> selectMenuList();


    /**
     * 根据角色id查询菜单
     * @param id roleId
     * @return 菜单列表
     */
    List<Menu> findMenusByRoleId(@Param("id") Long id);

    /**
     * 根据角色id删除菜单列表
     * @param id roleId
     * @return 受影响的行数
     */
    int deleteMenusByRoleId(@Param("id")Long id);

    /**
     * 批量添加角色具有的权限
     * @param menus 角色-权限列表
     * @return 受影响的行数
     */
    int addMenuList(@Param("menus") List<RoleMenu> menus);

    /**
     * 修改菜单
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    int updateMenu(@Param("menu") Menu menu);

    /**
     * 添加菜单
     * @param menu 菜单数据
     * @return 受影响的行数
     */
    int insertMenu(@Param("menu") Menu menu);

    /**
     * 删除菜单
     * @param id 菜单id
     * @return 受影响的行数
     */
    int removeMenu(@Param("id") Long id);
}
