package event.observability.zeebe.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@Configuration
@EnableWebSecurity
class SecurityConfig() : WebSecurityConfigurerAdapter(){
    @Value("\${zeebe.client.worker.adminUsername}")
    private val adminUsername: String? = null

    @Value("\${zeebe.client.worker.adminPassword}")
    private val adminPassword: String? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/", "/home")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .permitAll()
            .and()
            .logout()
            .permitAll()
    }

    @Bean
    override fun userDetailsService(): UserDetailsService? {
        val user: UserDetails = User.withDefaultPasswordEncoder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ADMIN")
                .build()
        return InMemoryUserDetailsManager(user)
    }

}