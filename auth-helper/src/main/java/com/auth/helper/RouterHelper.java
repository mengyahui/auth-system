package com.auth.helper;

import com.auth.model.entity.Menu;
import com.auth.model.vo.MetaVo;
import com.auth.model.vo.RouterVo;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MYH
 * @time 2023/04/03 下午 09:23
 */
public class RouterHelper {

    public static List<RouterVo> buildRouters(List<Menu> menus) {
        List<RouterVo> routers = new LinkedList<>();
        for (Menu menu : menus) {
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
            List<Menu> children = menu.getChildren();
            //若当前是菜单，菜单中的按钮可能也对应一个路由，该路由并不需要在菜单下面显示，例如 ‘角色分配权限按钮’
            if(menu.getType() == 1) {
                List<Menu> hiddenMenuList = children.stream().filter(item -> !StringUtils.isEmpty(item.getComponent())).collect(Collectors.toList());
                for (Menu hiddenMenu : hiddenMenuList) {
                    RouterVo hiddenRouter = new RouterVo();
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(), hiddenMenu.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouters(children));
                }
            }
            routers.add(router);
        }
        return routers;
    }

    public static String getRouterPath(Menu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId() != 0) {
            routerPath = menu.getPath();
        }
        return routerPath;
    }
}
