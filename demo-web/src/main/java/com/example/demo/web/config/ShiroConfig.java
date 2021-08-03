package com.example.demo.web.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author stone
 * @date 2021/07/27
 */
@Configuration
public class ShiroConfig {

    /**
     * shiro拦截器配置，必须在所有的自定义拦截器之前
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("shiroUrlFilter", new ShiroUrlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        // 注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        // 所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了
        // anon:所有url都都可以匿名访问；authc:需要认证才能进行访问；user:配置记住我或认证通过可以访问
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/demo/shiro/login", "anon");
        filterChainDefinitionMap.put("/demo/shiro/logout", "logout");
        filterChainDefinitionMap.put("/demo/shiro/current", "authc");
        filterChainDefinitionMap.put("/demo/**", "shiroUrlFilter");
        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 权限管理，配置自己的Realm管理认证
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm());
        return manager;
    }

    @Bean
    public ShiroRealm shiroRealm(){
        return new ShiroRealm();
    }

    /**
     * 开启 shiro aop 注解支持，使用代理方式，所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");
        mappings.setProperty("UnauthorizedException", "403");
        r.setExceptionMappings(mappings);
        r.setDefaultErrorView("error");
        r.setExceptionAttribute("ex");
        return r;
    }

    //@Bean
    //public HashedCredentialsMatcher hashedCredentialsMatcher() {
    //    HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
    //    // 散列算法:这里使用MD5算法
    //    hashedCredentialsMatcher.setHashAlgorithmName("md5");
    //    // 散列的次数，比如散列两次，相当于 md5(md5(""))
    //    hashedCredentialsMatcher.setHashIterations(2);
    //    return hashedCredentialsMatcher;
    //}

}
