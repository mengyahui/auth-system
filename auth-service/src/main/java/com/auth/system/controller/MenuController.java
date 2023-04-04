package com.auth.system.controller;


import com.auth.model.entity.Menu;
import com.auth.model.result.Result;
import com.auth.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MYH
 * @time 2023/03/30 上午 08:55
 */

@RestController
@RequestMapping("system/menu")
@Api(tags = "菜单管理")
public class MenuController {

    @Resource
    private MenuService menuService;


    @ApiOperation("根据角色id查询菜单树")
    @GetMapping("all/{id}")
    public Result<?> getAll(@PathVariable @ApiParam("角色id") Long id) {
        List<Menu> allMenu = menuService.findAllMenu(id);
        return Result.ok(allMenu);
    }

    @ApiOperation("查询菜单树")
    @GetMapping("all")
    public Result<?> findMenuTree() {
        List<Menu> menuTree = menuService.findMenuTree();
        return Result.ok(menuTree);
    }

    @ApiOperation("修改菜单")
    @PutMapping("update")
    public Result<?> updateMenu(@RequestBody Menu menu) {
        int result = menuService.updateMenu(menu);
        return result > 0 ? Result.ok() : Result.fail();
    }

    @ApiOperation("添加菜单")
    @PostMapping("add")
    public Result<?> addMenu(@RequestBody Menu menu) {
        int result = menuService.addMenu(menu);
        return result > 0 ? Result.ok() : Result.fail();
    }

    @ApiOperation("删除菜单")
    @DeleteMapping("delete/{id}")
    public Result<?> deleteMenu(@PathVariable @ApiParam("菜单id") Long id) {
        int result = menuService.deleteMenu(id);
        return result > 0 ? Result.ok() : Result.fail();
    }


}
