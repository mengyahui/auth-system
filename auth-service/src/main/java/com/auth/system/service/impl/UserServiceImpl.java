package com.auth.system.service.impl;


import com.auth.helper.ExcelHelper;
import com.auth.helper.ResponseHelper;
import com.auth.model.dto.ChangeStatusDto;
import com.auth.model.dto.user.AddUserDto;
import com.auth.model.dto.user.AssignUserDto;
import com.auth.model.dto.user.SearchUserDto;
import com.auth.model.dto.user.UpdateUserDto;
import com.auth.model.entity.User;
import com.auth.model.vo.PageVo;
import com.auth.model.vo.RouterVo;
import com.auth.model.vo.user.ExcelUserVo;
import com.auth.model.vo.user.UserVo;
import com.auth.system.mapper.UserMapper;
import com.auth.system.service.MenuService;
import com.auth.system.service.RoleService;
import com.auth.system.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:54
 */
@Repository
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    public UserMapper userMapper;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @Override
    public Map<String, Object> getUserInfo(String username) {
        // 根据username 查询用户信息
        User user = this.getByUsername(username);
        // 根据用户id获取菜单权限值
        List<RouterVo> routerVoList = menuService.findUserMenuList(user.getId());
        // 根据用户id获取用户按钮权限
        List<String> permsList = menuService.findUserPermsList(user.getId());

        Map<String, Object> result = new HashMap<>();
        result.put("name", user.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("menus", routerVoList);
        return result;
    }

    @Override
    public User getByUsername(String username) {
        List<User> userList = userMapper.selectByUsername(username);
        if (userList != null && !userList.isEmpty()) {
            return userList.get(0);
        } else {
            throw new RuntimeException("该用户不存在");
        }
    }

    @Override
    public PageVo getUserList(SearchUserDto searchUserDto) {
        PageHelper.startPage(searchUserDto.pageNum,searchUserDto.pageSize);
        List<UserVo> userVoList = userMapper.list(searchUserDto);
        PageInfo<UserVo> pageInfo = new PageInfo<>(userVoList);
        PageVo pageVo = new PageVo();
        BeanUtils.copyProperties(pageInfo,pageVo);
        return pageVo;
    }

    @Override
    public int removeUserByIds(List<Long> userIds) {
        return userMapper.deleteByUserIds(userIds);
    }

    @Override
    public int insertUser(AddUserDto addUserDto) {
        addUserDto.setCreateTime(new Date());
        addUserDto.setUpdateTime(new Date());
        // todo 处理密码和头像
        return userMapper.addUser(addUserDto);
    }

    @Override
    public int editUserById(UpdateUserDto updateUserDto) {
        updateUserDto.setUpdateTime(new Date());
        return userMapper.updateUserById(updateUserDto);
    }

    @Override
    public UserVo selectUserById(Long id) {
        UserVo userVo = userMapper.getUserById(id);
        if (userVo == null) {
            throw new RuntimeException("该用户不存在");
        }
        return userVo;
    }

    @Override
    public List<ExcelUserVo> getByUserIds(List<Long> userIds) {
        return userMapper.getUserListByIds(userIds);
    }

    @Override
    public void downLoad(HttpServletResponse response,List<Long> userIds){
        // 获取数据
        List<ExcelUserVo> excelUserVos = this.getByUserIds(userIds);
        try {
            ExcelHelper.writeToWeb(response, excelUserVos, ExcelUserVo.class, "用户信息");
        } catch (IOException e) {
            ResponseHelper.write(response);
        }
    }

    @Override
    public int changeStatusById(ChangeStatusDto statusDto) {
        return userMapper.changeUserStatus(statusDto);
    }

    /**
     * 给用户分配角色
     *
     * @param assignUserDto 用户id和角色列表
     */
    @Override
    public boolean toAssign(AssignUserDto assignUserDto) {
        // 删除用户之前的角色
        int result = roleService.deleteRoleByUserId(assignUserDto.userId);
        // 给用户添加新角色
        int res = roleService.addRoleListByUserId(assignUserDto);
        return result > 0 || res > 0;
    }
}
