package com.epam.clothshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class UserDto {

    public interface New {
    }

    interface Exist {
    }

    public interface Update extends Exist {
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
    @Size(min = 5, max = 50, groups = {New.class, Update.class})
    private String password;

    @NotEmpty(groups = {New.class, Update.class})
    @Length(min = 11, max = 11, groups = {New.class, Update.class})
    private String phone;

    @Tolerate
    public UserDto(String username, String firstName, String lastName, String email, String password, String phone) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @Tolerate
    public UserDto(Long id, String username, String firstName, String lastName, String email, String password, String phone) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}
