package com.example.demo.config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.security.JwtAuthFilter;
import com.example.demo.security.UserDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
//@Configuration
@EnableWebSecurity // Spring Security를 활성화하기 위한 애노테이션
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	
	private final UserDao userDao;
	
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/**/auth/**").permitAll() // "/auth"로 시작하는 모든 경로에 대해 인증 없이 접근을 허용합니다.
			.anyRequest().authenticated() // 모든 요청에 대해 인증이 필요하다는 설정을 의미합니다. 모든 요청은 인증된 사용자만 접근할 수 있습니다.
			.and()
			.authenticationProvider(authenticationProvider()) // 사용자의 인증 요청에 대해 인증 정보를 확인하고, 인증이 성공한 경우 Authentication 객체를 반환
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성을 사용하지 않도록 설정합니다. RESTful API에서는 세션을 사용하지 않는 것이 일반적입니다.
			.and()
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // 토큰을 검증하여 인증을 처리합니다.
//			.httpBasic() // HTTP Basic 인증을 사용하도록 설정합니다.
			;
		return http.build();
	}

    @Bean
    public AuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
//		return new BCryptPasswordEncoder();
//    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

    @Bean
	public UserDetailsService userDetailsService() {
	return new UserDetailsService() {
		
		@Override
		public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userDao.findUserByEmail(email); 
			}
		};
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//	사용자를 인메모리 방식으로 관리
//	@Bean
//	public InMemoryUserDetailsManager user() {
//		return new InMemoryUserDetailsManager(
//					User.withUsername("jsd")
//						.password("{noop}1234")
//						.authorities("read")
//						.build()
//				);
//	}
	
//	앱 실행할때 http 빌드되면서 실행됨
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//        .csrf().disable() // cross site request forgery 비활성화 보안때문에 사용하지 않음.
//        .authorizeRequests()
//        	.antMatchers("/public/**").permitAll() // "/public/"로 시작하는 모든 URL에 대해 인증되지 않은 접근을 허용합니다.
//            .anyRequest()
//            .authenticated() // 모든 요청에 대해 인증이 필요함을 설정
//            .and()
//            .sessionManagement()
//            	.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션정책: STATELESS 세션을 생성하지 않음을 의미합니다.
//            	.maximumSessions(1) // 최대 세션 수를 1로 설정합니다
//            	.expiredUrl("/login?expired") // 세션이 만료된 경우, redirect url
//            	.and()
//            .and()
////            .oauth2ResourceServer() // OAuth 2.0 보호된 리소스 서버의 구성을 시작합니다.
////            	.jwt() // JWT 토큰 유형을 사용하여 인증을 처리하도록 구성합니다.
////            		.jwtAuthenticationConverter(jwtAuthenticationConverter()) // JWT 인증 객체로 변환하는 데 사용할 JwtAuthenticationConverter를 설정합니다.
////            		.and()
////            	.and()
//            .httpBasic(); // HTTP Basic 인증 사용
//    	return http.build(); // SecurityFilterChain 객체를 반환하여 필터 체인을 구성       
//    }
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable()) // (1)
//                .authorizeRequests( auth -> auth
//                        .anyRequest().authenticated() // (2)
//                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // (3)
//                .httpBasic(Customizer.withDefaults()) // (4)
//                .build();
//    }
    
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//    	JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        // JWT 내의 권한 클레임을 인증 객체의 권한으로 변환하는 데 사용할 수 있는 변환기를 추가합니다.
//    	converter.setJwtGrantedAuthoritiesConverter(new CustomJwtGrantedAuthoritiesConverter());
//    	return converter;
//    }


}
