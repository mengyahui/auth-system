package com.auth.system.service.impl;


import com.auth.helper.BeanCopyHelper;
import com.auth.helper.ExcelHelper;
import com.auth.helper.ResponseHelper;
import com.auth.model.dto.role.AddRoleDto;
import com.auth.model.dto.role.AssignRoleDto;
import com.auth.model.dto.role.SearchRoleDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.entity.Role;
import com.auth.model.entity.RoleMenu;
import com.auth.model.entity.UserRole;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.ExcelRoleVo;
import com.auth.model.vo.role.RoleVo;
import com.auth.system.mapper.RoleMapper;
import com.auth.system.service.MenuService;
import com.auth.system.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:54
 */

@Repository
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuService menuService;

    @Override
    public List<RoleVo> findAllRole() {
        return roleMapper.findAllRole();
    }

    @Override
    public List<RoleVo> selectRoleSByUserId(Long userId) {
        if (userId == 1L) {
            return this.findAllRole();
        }
        return roleMapper.getRolesByUserId(userId);
    }

    /**
     * 根据用户id删除其所具有的角色信息
     *
     * @param userId 用户id
     * @return 受影响的行数
     */
    @Override
    public int deleteRoleByUserId(Long userId) {
        if (userId == 1L) {
            throw new RuntimeException("管理员角色不可删除！");
        }
        return roleMapper.deleteRoleByUserId(userId);
    }

    /**
     * 批量添加角色信息
     *
     * @param assignUserDto 包含用户id和角色id列表
     * @return 受影响的行数
     */
    @Override
    public int addRoleListByUserId(AssignUserDto assignUserDto) {
        List<Long> roleIds = assignUserDto.getRoleIds();
        List<UserRole> userRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (Long roleId : assignUserDto.roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(assignUserDto.userId);
                userRole.setRoleId(roleId);
                userRole.setCreateTime(new Date());
                userRole.setUpdateTime(new Date());
                userRoles.add(userRole);
            }
            return roleMapper.addRolesByList(userRoles);
        }
        return -1;
    }

    /**
     * 根据条件查询角色列表
     *
     * @param searchRoleDto 搜索条件
     * @return 包括了分页数据和角色列表
     */
    @Override
    public PageVo getRoleList(SearchRoleDto searchRoleDto) {
        PageHelper.startPage(searchRoleDto.pageNum,searchRoleDto.pageSize);
        List<RoleVo> roleVos = roleMapper.list(searchRoleDto);
        PageInfo<RoleVo> pageInfo = new PageInfo<>(roleVos);
        return BeanCopyHelper.copyBean(pageInfo,PageVo.class);
    }

    /**
     * 添加角色
     *
     * @param addRoleDto 角色数据
     * @return 受影响的行数
     */
    @Override
    public int addRole(AddRoleDto addRoleDto) {
        addRoleDto.setCreateTime(new Date());
        addRoleDto.setUpdateTime(new Date());
        return roleMapper.insertRole(addRoleDto);
    }

    /**
     * 修改角色
     *
     * @param role 角色数据
     * @return 受影响的行数
     */
    @Override
    public int updateRole(Role role) {
        role.setUpdateTime(new Date());
        return roleMapper.editRole(role);
    }

    /**
     * 根据角色id查询角色
     *
     * @param id 角色id
     * @return 角色对象
     */
    @Override
    public Role getRoleById(Long id) {
        return roleMapper.selectRoleById(id);
    }

    /**
     * 根据角色id删除角色
     *
     * @param roleIds 角色id列表
     * @return 受影响的行数
     */
    @Override
    public int deleteRole(List<Long> roleIds) {
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 根据角色id列表查询角色列表
     *
     * @param roleIds 角色id列表
     * @return 角色列表
     */
    @Override
    public List<ExcelRoleVo> getRoleListByIds(List<Long> roleIds) {
        return roleMapper.selectRolesByIds(roleIds);
    }

    /**
     * 导出角色数据
     *
     * @param response 响应对象
     * @param roleIds  要导出的角色id列表
     */
    @Override
    public void exportData(HttpServletResponse response, List<Long> roleIds) throws IOException {
        // 根据角色id列表查询角色列表
        List<ExcelRoleVo> roleList = this.getRoleListByIds(roleIds);
        try {
            ExcelHelper.writeToWeb(response,roleList, ExcelRoleVo.class,"角色数据列表");
        } catch (IOException e) {
            ResponseHelper.write(response);
        }
    }

    /**
     * 给角色分配权限
     *
     * @param assignRoleDto 角色id和分配的菜单id列表
     * @return sf
     */
    @Override
    public boolean doAssign(AssignRoleDto assignRoleDto) {
        // 根据角色id删除菜单列表
        int delResult = menuService.deleteMenuListByRoleId(assignRoleDto.roleId);
        // 重新分配菜单
        int addResult = -1;
        List<Long> menuIds = assignRoleDto.getMenuIds();
        if (!menuIds.isEmpty()) {
            List<RoleMenu> menus = new ArrayList<>();
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(assignRoleDto.roleId);
                roleMenu.setMenuId(menuId);
                roleMenu.setCreateTime(new Date());
                roleMenu.setUpdateTime(new Date());
                menus.add(roleMenu);
            }
            addResult = menuService.addMenuList(menus);
        }
        return delResult > 0 || addResult > 0;
    }
}
