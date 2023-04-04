package com.auth.system.service;



import com.auth.model.dto.role.AddRoleDto;
import com.auth.model.dto.role.AssignRoleDto;
import com.auth.model.dto.role.SearchRoleDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.entity.Role;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.role.ExcelRoleVo;
import com.auth.model.vo.role.RoleVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:53
 */
public interface RoleService {

    /**
     * 查询所有角色
     * @return 角色列表
     */
    List<RoleVo> findAllRole();

    /**
     * 根据用户id查询用户所具有的角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<RoleVo> selectRoleSByUserId(Long userId);

    /**
     * 根据用户id删除其所具有的角色信息
     * @param userid 用户id
     * @return 受影响的行数
     */
    int deleteRoleByUserId(Long userid);

    /**
     * 批量添加角色信息
     * @param assignUserDto 包含用户id和角色id列表
     * @return 受影响的行数
     */
    int addRoleListByUserId(AssignUserDto assignUserDto);

    /**
     * 根据条件查询角色列表
     * @param searchRoleDto 搜索条件
     * @return 包括了分页数据和角色列表
     */
    PageVo getRoleList(SearchRoleDto searchRoleDto);

    /**
     * 添加角色
     * @param addRoleDto 角色数据
     * @return 受影响的行数
     */
    int addRole(AddRoleDto addRoleDto);

    /**
     * 修改角色
     * @param role 角色数据
     * @return 受影响的行数
     */
    int updateRole(Role role);

    /**
     * 根据角色id查询角色
     * @param id 角色id
     * @return 角色对象
     */
    Role getRoleById(Long id);

    /**
     * 根据角色id删除角色
     * @param roleIds 角色id列表
     * @return 受影响的行数
     */
    int deleteRole(List<Long> roleIds);

    /**
     * 根据角色id列表查询角色列表
     * @param roleIds 角色id列表
     * @return 角色列表
     */
    List<ExcelRoleVo> getRoleListByIds(List<Long> roleIds);

    /**
     * 导出角色数据
     * @param response 响应对象
     * @param roleIds 要导出的角色id列表
     */
    void exportData(HttpServletResponse response, List<Long> roleIds) throws IOException;

    /**
     * 给角色分配权限
     * @param assignRoleDto 角色id和分配的菜单id列表
     * @return sf
     */
    boolean doAssign(AssignRoleDto assignRoleDto);
}
