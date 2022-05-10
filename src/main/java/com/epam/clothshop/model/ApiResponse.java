package com.epam.clothshop.model;

import lombok.Getter;

@Getter
public class ApiResponse {

    private int code;
    private String type;
    private String message;
}
