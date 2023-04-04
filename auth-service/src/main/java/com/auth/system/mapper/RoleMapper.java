package com.auth.system.mapper;


import com.auth.model.dto.role.AddRoleDto;
import com.auth.model.dto.role.SearchRoleDto;
import com.auth.model.entity.Role;
import com.auth.model.entity.UserRole;
import com.auth.model.vo.role.ExcelRoleVo;
import com.auth.model.vo.role.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_role】的数据库操作Mapper
* @createDate 2023-03-30 08:50:24
* @Entity com.auth.domain.entity.Role
*/
public interface RoleMapper {

    /**
     * 查询所有角色信息
     * @return 角色列表
     */
    List<RoleVo> findAllRole();

    /**
     * 根据用户id查询用户角色列表
     * @param userId 用户id
     * @return 角色列表
     */
    List<RoleVo> getRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据用户id删除用户所具有角色列表
     * @param userId 用户id
     * @return 受影响的行数
     */
    int deleteRoleByUserId(@Param("userId")Long userId);

    /**
     * 批量添加角色信息
     * @param userRoles 用户角色信息列表
     * @return 受影响的行数
     */
    int addRolesByList(@Param("userRoles") List<UserRole> userRoles);

    /**
     * 条件查询角色列表
     * @param searchRoleDto 搜索条件
     * @return 角色列表
     */
    List<RoleVo> list(@Param("searchRoleDto") SearchRoleDto searchRoleDto);

    /**
     * 添加角色
     * @param addRoleDto 角色数据
     * @return 受影响的行数
     */
    int insertRole(@Param("addRoleDto") AddRoleDto addRoleDto);

    /**
     * 修改角色
     * @param role 角色数据
     * @return 受影响的行数
     */
    int editRole(@Param("role") Role role);

    /**
     * 根据角色id查询角色
     * @param id 角色id
     * @return 角色对象
     */
    Role selectRoleById(@Param("id") Long id);

    /**
     * 根据角色id数组删除角色信息
     * @param roleIds 角色列表
     * @return 受影响的行数
     */
    int deleteRoleByIds(@Param("roleIds") List<Long> roleIds);

    List<ExcelRoleVo> selectRolesByIds(@Param("roleIds") List<Long> roleIds);
}
