package org.example.sweater.config;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

/**
 Это класс, который при старте приложения конфигурирует WebSecurityConfig
 Система заходит в метод confi gure, передает на вход объект (http)
 и мы в нем включаем:
 - авторизацию,
 - указываем, что для этого пути ("/") -
 главная страничка на которую приходит пользователь - мы разрешаем полный доступ
 - для всех остальных запросов - мы требуем авторизацию
 - включаем formLogin
 - указываем, что loginPage находится на таком мэпинге ("/login")
 - разрешаем этим пользоваться всем
 - так же включаем logout()
 - и разрешаем этим пользоваться всем

 * dataSource - нужен для того, чтобы менеджер мог входить в базу данных и искать пользователя и их роли
 * passwordEncoder - будет шифровать пароли, чтобы они не хранились в явном виде
 * usersByUsernameQuery - запрос необходим для того, чтобы система могла найти пользователя по его имени
 (для этого нам нужны 3 поля именно в таком порядке: пользователь, пароль  и признак активности)
 * authoritiesByUsernameQuery - помогает Spring-у получиь список пользователей с их ролями
 (запрос - из таблицы user и присоединенной к ней таблицы user_role, соединенной через поля user_id и id,
 выбираем поля username и user_role (имя роли)
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/registration").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                .and()
                    .logout()
                    .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from usr where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
    }
}
