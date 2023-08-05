package com.vicenzo.customer.dto;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
