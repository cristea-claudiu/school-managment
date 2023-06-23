package com.project.schoolmanagment.utils;

public abstract class Messages {


    public static final String CONTACT_MESSAGE_WITH_ID_NOT_FOUND = "Could not find contact message with id: ";
    public static final String CONTACT_MESSAGE_FAILED_TO_UPDATE = "Failed to update contact message";
    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
    public static final String NOT_FOUND_USER_MESSAGE = "ERROR: User not found with id %s";
    public static final String ADMIN_DELETED_SUCCESSFULLY = "Admin is deleted successfully";
    //DEAN
    public static final String DEAN_DELETED_SUCCESSFULLY = "Dean is deleted successfully";
    public static final String DEAN_SAVED_SUCCESSFULLY = "Dean is Saved successfully";
    public static final String DEAN_UPDATED_SUCCESSFULLY = "Dean is updated successfully";
    public static final String DEAN_FOUND_SUCCESSFULLY = "Dean Founded";
    //Teacher
    public static final String TEACHER_DELETED_SUCCESSFULLY = "Teacher is deleted successfully";
    public static final String TEACHER_SAVED_SUCCESSFULLY = "Teacher is Saved successfully";
    public static final String TEACHER_UPDATED_SUCCESSFULLY = "Teacher is updated successfully";
    public static final String TEACHER_FOUND_SUCCESSFULLY = "Teacher Found";
    public static final String TEACHER_NOT_FOUND = "Teacher not Found";
    //Teacher
    public static final String STUDENT_DELETED_SUCCESSFULLY = "Student is deleted successfully";
    public static final String STUDENT_SAVED_SUCCESSFULLY = "Student is Saved successfully";
    public static final String STUDENT_UPDATED_SUCCESSFULLY = "Student is updated successfully";
    public static final String STUDENT_FOUND_SUCCESSFULLY = "Student Founded";
    //ViceDean
    public static final String VICE_DEAN_DELETED_SUCCESSFULLY = "Vice Dean is deleted successfully";
    public static final String VICE_DEAN_SAVED_SUCCESSFULLY = "Vice Dean is Saved successfully";
    public static final String VICE_DEAN_UPDATED_SUCCESSFULLY = "Vice Dean is updated successfully";
    public static final String VICE_DEAN_FOUND_SUCCESSFULLY = "Vice Dean Founded";
    //role
    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String ROLE_ALREADY_EXIST = "Role already exist in DB";
    //Contact Message
    public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: you have already send a message with this email";
    public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: USER with username %s already registered";
    public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: USER with email %s already registered";
    public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: USER with ssn %s already registered";
    public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: USER with phone number %s already registered";
    //education term related messages
    public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date ";
    public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date ";
    public static final String EDUCATION_LAST_REGISTRATION_DATE_IS_AFTER_END_DATE = "Error: To late ";
    public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE = "Error: Education Term with Term And Year already exist ";
    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term with id %s not found";
    public static final String EDUCATION_TERM_SAVED_MESSAGE = "Education Term Saved successfully";
    public static final String EDUCATION_TERM_UPDATED_MESSAGE = "Education term Updated successfully";
    public static final String EDUCATION_TERM_DELETED_MESSAGE = "Education Term Deleted successfully";
    //Lesson
    public static final String ALREADY_REGISTER_LESSON_MESSAGE = "Error: Lesson with lesson name %s already registered";
    public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson with this field %s not found";
    public static final String NOT_FOUND_LESSONS_MESSAGE = "Error: Lesson not found";
    public static final String NOT_FOUND_LESSON_IN_LIST = "Error: Lesson not found in List";
    public static final String FOUND_LESSON_MESSAGE = "Lesson with this field %s found";
    public static final String LESSON_SAVED = "Lesson saved";
    public static final String LESSON_UPDATED = "Lesson Updated";
    public static final String LESSON_DELETED = "Lesson Deleted successfully";
    public static final String TIME_NOT_VALID_MESSAGE = "Error: incorrect time";
    //lesson program
    public static final String LESSON_PROGRAM_SAVED = "Lesson Program saved";
    public static final String LESSON_PROGRAM_DELETE = "Lesson Program deleted";
    public static final String LESSON_PROGRAM_NOT_FOUND = "Lesson Program NOT FOUND";

    public static final String ADVISORY_TEACHER_NOT_FOUND_SUCCESSFULLY = "Advisory Teacher Not Founded";

    public static final String LESSON_PROGRAM_EXIST_MESSAGE = "Error: Course schedule can not be selected fir the same hour and date" ;
    public static final String LESSON_PROGRAM_ADDED_TO_TEACHER = "Lesson program added to teacher" ;


    private Messages() {
    }


}
