package com.example.demo.web.config;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author stone
 * @date 2021/07/27
 */
@Configuration
public class ShiroConfig {

    // https://www.w3cschool.cn/shiro/andAccessControlFilterc1if0.html
    // https://zhuanlan.zhihu.com/p/54176956
    // https://blog.csdn.net/sinat_40553837/article/details/88371533

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 注意此处使用的是LinkedHashMap，是有顺序的，shiro会按从上到下的顺序匹配验证，匹配了就不再继续验证
        // 所以上面的url要苛刻，宽松的url要放在下面，尤其是"/**"要放到最下面，如果放前面的话其后的验证规则就没作用了
        // anon:所有url都都可以匿名访问；authc:需要认证才能进行访问；user:配置记住我或认证通过可以访问
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/demo/shiro/login", "anon");
        filterChainDefinitionMap.put("/demo/shiro/logout", "logout");
        filterChainDefinitionMap.put("/demo/**", "authc");
        filterChainDefinitionMap.put("/**", "anon");
        //filterChainDefinitionMap.put("/index.html", "anon");
        //filterChainDefinitionMap.put("/#/**", "anon");
        //filterChainDefinitionMap.put("/static/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //Map<String, javax.servlet.Filter> filtersMap = new LinkedHashMap<>();
        //filtersMap.put("authc",  loginFilter());
        //filtersMap.put("authc",  crossFilter());
        //shiroFilterFactoryBean.setFilters(filtersMap);

        //shiroFilterFactoryBean.setLoginUrl("/#/user/login");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/demo/shiro/logout");
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        return shiroFilterFactoryBean;
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

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public Subject getSubject() {
        SecurityUtils.setSecurityManager(securityManager());
        return SecurityUtils.getSubject();
    }

    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver
    createSimpleMappingExceptionResolver() {
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
    //public LoginFilter loginFilter() {
    //    LoginFilter loginFilter = new LoginFilter();
    //    return loginFilter;
    //}

    //@Bean
    //public CrossFilter crossFilter() {
    //    CrossFilter crossFilter = new CrossFilter();
    //    return crossFilter;
    //}

    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        //shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
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
