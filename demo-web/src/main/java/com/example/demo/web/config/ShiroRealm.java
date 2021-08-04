package com.example.demo.web.config;

import javax.annotation.Resource;

import com.example.demo.api.response.PermissionVO;
import com.example.demo.api.response.RoleVO;
import com.example.demo.api.response.UserDetailVO;
import com.example.demo.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

/**
 * @author stone
 * @date 2021/07/26
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 登陆
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserDetailVO userDetailVO = userService.getUser(username);
        if (userDetailVO == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(
            userDetailVO,
            userDetailVO.getPassword(),
            //ByteSource.Util.bytes(userDetailVO.getSalt()),
            super.getName()
        );
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 能进入这里说明用户已经通过验证了
        UserDetailVO userDetailVO = (UserDetailVO)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (RoleVO roleVO : userDetailVO.getRoles()) {
            simpleAuthorizationInfo.addRole(roleVO.getRoleCode());
            for (PermissionVO permissionVO : roleVO.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissionVO.getPermissionCode());
            }
        }
        return simpleAuthorizationInfo;
    }

}
