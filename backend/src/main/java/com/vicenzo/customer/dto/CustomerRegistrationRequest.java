package com.vicenzo.customer.dto;

import lombok.Data;
import lombok.Getter;


public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age

) {
}
