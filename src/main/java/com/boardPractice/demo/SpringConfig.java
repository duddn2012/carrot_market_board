package com.boardPractice.demo;

import com.boardPractice.demo.repository.JpaUserRepository;
import com.boardPractice.demo.repository.MemoryUserRepository;
import com.boardPractice.demo.repository.UserRepository;
import com.boardPractice.demo.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public UserRepository userRepository(){
        //return new MemoryUserRepository();
        //return new JdbcMemberRepository(DataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        return new JpaUserRepository(em);
    }
}
