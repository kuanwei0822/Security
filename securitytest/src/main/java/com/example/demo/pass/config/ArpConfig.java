package com.example.demo.pass.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.pass.filter.JWTAuthenticationFilter;
import com.example.demo.pass.security.ArpUserDetailsService;


@Configuration
@EnableWebSecurity
public class ArpConfig extends WebSecurityConfigurerAdapter{

	
	// for .addFilterBefore(...) 方法中使用。之前使用建構函數來帶入。  
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		
			// 用來規定哪些請求可以放行
			.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/test1").permitAll() // 這行不需要驗證
			
			// 頁面權限設定，hasRole 符合特定角色(Role)才能來
			.antMatchers(HttpMethod.GET,"/test2").hasRole("USER") // 這行需要驗證+角色正確
			
			// 頁面權限設定，hasRole 符合特定角色(Role)才能來
			.antMatchers(HttpMethod.GET,"/test3").hasRole("ADMIN") // 這行需要驗證+角色正確
			
			// 頁面權限設定，hasAnyRole 符合其中一個角色(Role)就能來
			.antMatchers(HttpMethod.GET,"/test4").hasAnyRole("USER","ADMIN") // 這行需要驗證+角色正確
			
			// 這是登入頁面，必須要開啟
			.antMatchers(HttpMethod.GET,"/loginPage.html").permitAll() // 這行不需要驗證
			// 登入成功頁面，要回傳 Token，必須要開啟
			.antMatchers(HttpMethod.POST,"/getToken").permitAll() // 這行不需要驗證
		
			
			.antMatchers(HttpMethod.GET,"/def").permitAll() // 這行不需要驗證
			.anyRequest().authenticated() // 代表剩下的網頁一律需要驗證
			// .permitAll()		不需要驗證
			// .authenticated()	要驗證
	        
			// 取消 Session 的使用
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			
			// 遇到需要權限的頁面，轉跳的登入畫面
			// .formLogin()為預設登入畫面
			// .formLogin().loginPage("/XXX"); 可以自行設定登入畫面(默認會用GET方法進入登入頁面)
			// 若都沒有設定登入畫面，則轉跳後會收到 403
			
			// 重要: 記得登入頁面要設定"不需要要驗證"，否則怎樣都無法登入。
			.and()
			.formLogin().loginPage("/loginPage.html")
			
			
			// 設定登入成功畫面統一導到此畫面
			// 登入頁面按下送出按鈕之後，就會接到此登入頁面(/loginSuccessPage)
			// 所以表單 action 一定要設定與這邊相同，然後使用 POST 方法
			// 可以寫一個 Controller 接收資料( @RequestMapping(path="/loginSuccessPage" method=RequestMethod.POST)... )
			
			// 重要: 成功頁面也一定要設定"不需要要驗證"，不然會一直卡在登入畫面。
			.defaultSuccessUrl("/getToken")
			
			// 安全性設定，固定要放
			.and()
			.csrf().disable()
			
			
			// 設定自訂義 Security 的 Filter，必須要加，否則 JWTAuthenticationFilter 會只是普通 Filter，跑不過。
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
			
		
		
	}
	   
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {

	}
	
	
	 // 註冊認證管理器，當你帳密輸入正確之後，他會發一個已授權的 Authentication 認證給你。
	 // 驗證必須要加，否則會找不到 Bean
	 @Override
	 @Bean
	 public AuthenticationManager authenticationManagerBean() throws Exception {
	     return super.authenticationManagerBean();
	 }
	
	
//	// 密碼驗證。適用於資料庫中密碼已經是加密過的。
//	@Autowired
//	private UserDetailsService userDetailsService;
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		// 用來驗證帳號、密碼(只能驗證在資料庫就是加密狀態的密碼)
//		auth.userDetailsService(userDetailsService)
//		.passwordEncoder(new BCryptPasswordEncoder());
//		
//	}
	
	// 密碼驗證。適用於資料庫密碼不加密。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); //不推薦使用，但測試不管它
    }
    
    
    
    
}
