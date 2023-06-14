package com.project.schoolmanagment.security.service;

import com.project.schoolmanagment.entity.abstracts.User;
import com.project.schoolmanagment.entity.concrate.*;
import com.project.schoolmanagment.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final StudentRepository studentRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User  student= studentRepository.findByUsernameEquals(username);
        User user = null;
        if (student != null) {
            user = student;
        }
        User  teacher= teacherRepository.findByUsernameEquals(username);
        if (teacher != null) {
            user = teacher;
        }
        User    admin=   adminRepository.findByUsernameEquals(username);
        if (admin != null) {
            user = admin;
        }
        User     dean=    deanRepository.findByUsernameEquals(username);
        if (dean != null) {
            user = dean;
        }
        User viceDean=viceDeanRepository.findByUsernameEquals(username);
        if (viceDean != null) {
            user = viceDean;
        }
        if (user != null) {
            return new UserDetailsImpl(
                    user.getId(),
                    user.getUsername(),
                    user.getName(),
                    false,
                    user.getPassword(),
                    user.getUserRole().getRoleType().name());
        }
        throw new UsernameNotFoundException("User with user name '" + username + "' not found!");
    }
}
