package com.project.schoolmanagment.utils;

import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.repository.*;
import com.project.schoolmanagment.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FieldControl {
    private final AdminRepository adminRepository;
    private final DeanRepository deanRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final GuestUserRepository guestUserRepository;

    //As a requirement all Admin, ViceAdmin, Dean, Student, Teacher, guestUser
    // should have unique userName, ssn and phone number

    public void checkDuplicate(String userName, String ssn, String phone) {
        if (adminRepository.existsByUsername(userName) ||
                deanRepository.existsByUsername(userName) ||
                studentRepository.existsByUsername(userName) ||
                teacherRepository.existsByUsername(userName) ||
                viceDeanRepository.existsByUsername(userName) ||
                guestUserRepository.existsByUsername(userName)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME, userName));
        } else if (adminRepository.existsBySsn(ssn) ||
                deanRepository.existsBySsn(ssn) ||
                studentRepository.existsBySsn(ssn) ||
                teacherRepository.existsBySsn(ssn) ||
                viceDeanRepository.existsBySsn(ssn) ||
                guestUserRepository.existsBySsn(ssn)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
        } else if (adminRepository.existsByPhoneNumber(phone) ||
                deanRepository.existsByPhoneNumber(phone) ||
                studentRepository.existsByPhoneNumber(phone) ||
                teacherRepository.existsByPhoneNumber(phone) ||
                viceDeanRepository.existsByPhoneNumber(phone) ||
                guestUserRepository.existsByPhoneNumber(phone)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
        }
    }

    public void checkDuplicate(String userName, String ssn, String phone, String email) {
        checkDuplicate(userName,ssn,phone);
        if (studentRepository.existsByEmail(email) || teacherRepository.existsByEmail(email)) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
        }
    }







}
