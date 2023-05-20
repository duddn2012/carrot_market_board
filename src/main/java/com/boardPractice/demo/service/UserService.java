package com.boardPractice.demo.service;

import com.boardPractice.demo.domain.User;
import com.boardPractice.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Transactional
public class UserService {

    private final UserRepository userRepository;
    //@Autowired private PasswordEncoder passwordEncoder;

    //private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입
     */

    public int join(User user){
        validateDuplicateMember(user);
        //비밀번호 암호화
        //user.hashPassword(passwordEncoder);
        userRepository.save(user);
        return user.getUserId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(User user) {
        userRepository.findById(user.getUserId())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findOne(int userId){
        return userRepository.findById(userId);
    }

    public void deleteUser(int userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> updateUser(int userId, User user){

        return userRepository.updateById(userId, user);
    }

}
