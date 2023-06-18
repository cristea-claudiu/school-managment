package com.project.schoolmanagment.utils;

public abstract class Messages {

private Messages(){
}
public static final String NOT_PERMITED_METHOD_MESSAGE="You do not have any permission to do this operation";
public static final String NOT_FOUND_USER_MESSAGE="ERROR: User not found with id %s";
public static final String ADMIN_DELETED_SUCCESSFULLY="Admin is deleted successfully";


public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
public static final String ROLE_ALREADY_EXIST = "Role already exist in DB";


public static final String ALREADY_SEND_A_MESSAGE_TODAY="Error: you have already send a message with this email";
public static final String ALREADY_REGISTER_MESSAGE_USERNAME="Error: USER with username %s already registered";
public static final String ALREADY_REGISTER_MESSAGE_EMAIL="Error: USER with email %s already registered";
public static final String ALREADY_REGISTER_MESSAGE_SSN="Error: USER with ssn %s already registered";
public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER="Error: USER with phone number %s already registered";


    //education term related messages
    public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date " ;
    public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date " ;
    public static final String EDUCATION_LAST_REGISTRATION_DATE_IS_AFTER_END_DATE = "Error: To late " ;
    public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE = "Error: Education Term with Term And Year already exist " ;
    public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term with id %s not found" ;



    public static final String ALREADY_REGISTER_LESSON_MESSAGE = "Error: Lesson with lesson name %s already registered";
    public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson with this field %s not found";
    public static final String FOUND_LESSON_MESSAGE = "Lesson with this field %s found";
    public static final String LESSON_SAVED = "Lesson saved";
    public static final String LESSON_UPDATED = "Lesson Updated";
    public static final String LESSON_DELETED = "Lesson Deleted successfully";




















}
