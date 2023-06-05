package com.project.schoolmanagment.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class ContactMessageRequest implements Serializable{
    @NotNull(message = "Please Enter Name")
    @Size(min = 4, max =16,message = "Your name should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+",message = "Your name must consist of the characters.")
    private String name;
    @Email(message = "Your email address need to be valid!")
    @Size(min = 5,max = 20,message = "Your email must be at least 5 characters")
    @NotNull(message = "Please enter your email address")
    //column annotation should be in entity class for db validation
    @Column(nullable = false,unique = true,length = 20)
    private String email;
    @NotNull(message = "Please enter subject")
    @Size(min = 4, max = 50, message = "Your subject should be at least 4 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters .")
    private String subject;
    @NotNull(message = "Please enter message ")
    @Size(min = 4, max = 50, message = "Your subject should be at least 16 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Your message must consist of the characters .")
    private String message;
}
