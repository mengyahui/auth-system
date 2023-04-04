package com.auth.system.mapper;


import com.auth.model.dto.ChangeStatusDto;
import com.auth.model.dto.user.AddUserDto;
import com.auth.model.dto.user.SearchUserDto;
import com.auth.model.dto.user.UpdateUserDto;
import com.auth.model.entity.User;
import com.auth.model.vo.user.ExcelUserVo;
import com.auth.model.vo.user.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author MYH
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2023-03-30 08:45:49
* @Entity com.auth.domain.entity.User
*/
public interface UserMapper {

    List<User> selectByUsername(@Param("username") String username);

    // 查询用户列表
    List<UserVo> list(@Param("searchUserDto") SearchUserDto searchUserDto);

    int deleteByUserIds(@Param("userIds") List<Long> userIds);

    int addUser(@Param("addUserDto") AddUserDto addUserDto);

    int updateUserById(@Param("updateUserDto") UpdateUserDto updateUserDto);

    UserVo getUserById(@Param("id") Long id);

    List<ExcelUserVo> getUserListByIds(@Param("userIds") List<Long> userIds);

    int changeUserStatus(@Param("statusDto") ChangeStatusDto statusDto);
}
