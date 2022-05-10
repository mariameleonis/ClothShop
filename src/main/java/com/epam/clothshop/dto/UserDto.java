package com.epam.clothshop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class UserDto {

    interface New {
    }

    interface Exist {
    }

    interface Update extends Exist {
    }

    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private Long id;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 7, max = 25, groups = {New.class, Update.class})
    private String username;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 2, max = 25, groups = {New.class, Update.class})
    private String firstName;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 2, max = 25, groups = {New.class, Update.class})
    private String lastName;

    @NotEmpty(groups = {New.class, Update.class})
    @Email(groups = {New.class, Update.class})
    private String email;

    @NotEmpty(groups = {New.class, Update.class})
    @Size(min = 8, max = 50, groups = {New.class, Update.class})
    private String password;

    @NotEmpty(groups = {New.class, Update.class})
    @Length(min = 16, max = 16, groups = {New.class, Update.class})
    private String phone;
}
