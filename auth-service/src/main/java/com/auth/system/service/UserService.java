package com.auth.system.service;



import com.auth.model.dto.ChangeStatusDto;
import com.auth.model.dto.user.AddUserDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.dto.user.SearchUserDto;
import com.auth.model.dto.user.UpdateUserDto;
import com.auth.model.entity.User;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.user.ExcelUserVo;
import com.auth.model.vo.user.UserVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:52
 */
public interface UserService {

    // 根据用户名获取用户权限信息和菜单信息
    Map<String, Object> getUserInfo(String token);

    // 根据用户名获取用户对象
    User getByUsername(String username);

    // 获取用户列表
    PageVo getUserList(SearchUserDto searchUserDto);

    // 根据 ids 删除用户
    int removeUserByIds(List<Long> userIds);

    // 添加用户
    int insertUser(AddUserDto addUserDto);

    // 修改用户
    int editUserById(UpdateUserDto updateUserDto);

    // 根据id查询用户
    UserVo selectUserById(Long id);

    // 根据用户id集合获取用户集合
    List<ExcelUserVo> getByUserIds(List<Long> userIds);

    // 导出用户
    void downLoad(HttpServletResponse response,List<Long> userIds) throws IOException;

    // 根据用户id修改用户状态
    int changeStatusById(ChangeStatusDto statusDto);

    /**
     * 给用户分配角色
     * @param assignUserDto
     */
    boolean toAssign(AssignUserDto assignUserDto);
}

