package com.github.wenslo.forger.security.config

import com.github.wenslo.forger.security.filter.ApiAuthenticationFilter
import com.github.wenslo.forger.security.filter.ValidateCodeFilter
import com.github.wenslo.forger.security.provider.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.springframework.security.web.session.HttpSessionEventPublisher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.cors.CorsUtils
import javax.servlet.http.HttpServletRequest


/**
 * @author wenhailin
 */
@Configuration
@EnableWebSecurity
class ApiSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var apiAuthenticationProvider: ApiAuthenticationProvider

    @Autowired
    lateinit var apiAuthenticationEntryPoint: ApiAuthenticationEntryPoint

    @Autowired
    lateinit var apiAuthenticationSuccessHandler: ApiAuthenticationSuccessHandler

    @Autowired
    lateinit var apiAuthenticationFailureHandler: ApiAuthenticationFailureHandler

    @Autowired
    lateinit var apiLogoutSuccessHandler: ApiLogoutSuccessHandler

    @Autowired
    lateinit var apiAccessDeniedHandler: ApiAccessDeniedHandler

    @Autowired
    lateinit var validateCodeFilter: ValidateCodeFilter

    @Autowired
    lateinit var apiSessionInformationExpiredStrategy: ApiSessionInformationExpiredStrategy

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .cors()
            .and()
            .antMatcher("/**").authorizeRequests()
            .antMatchers("/login**", "/code/image", "/actuator**", "/actuator/**").permitAll()
            .antMatchers("/questionnaireResponse/callback").permitAll()
            .requestMatchers(RequestMatcher { request: HttpServletRequest? ->
                CorsUtils.isPreFlightRequest(
                    request!!
                )
            }).permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login_page")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessHandler(apiLogoutSuccessHandler)
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .and()
            .csrf()
            .disable()
            .cors()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(apiAuthenticationEntryPoint)
            .accessDeniedHandler(apiAccessDeniedHandler)
            .and()
            .sessionManagement()
            .maximumSessions(3)
            .sessionRegistry(sessionRegistry())
            .expiredSessionStrategy(apiSessionInformationExpiredStrategy)
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter::class.java)
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http.csrf().disable()
    }

    @Bean
    fun customAuthenticationFilter(): ApiAuthenticationFilter {
        val filter = ApiAuthenticationFilter()
        filter.setAuthenticationSuccessHandler(apiAuthenticationSuccessHandler)
        filter.setAuthenticationFailureHandler(apiAuthenticationFailureHandler)
        filter.setFilterProcessesUrl("/login")
        filter.setAuthenticationManager(authenticationManagerBean())
        filter.setSessionAuthenticationStrategy(apiSessionAuthenticationStrategy())
        return filter
    }

    @Bean
    fun apiSessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        return RegisterSessionAuthenticationStrategy(sessionRegistry())
    }

    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun servletListenerRegistrationBean(): ServletListenerRegistrationBean<HttpSessionEventPublisher> {
        return ServletListenerRegistrationBean(HttpSessionEventPublisher())
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(apiAuthenticationProvider)
    }

}