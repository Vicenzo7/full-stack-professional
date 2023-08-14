package com.vicenzo.customer.mapper;

import com.vicenzo.customer.model.Customer;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        // Given
        CustomerRowMapper customerRowMapper = new CustomerRowMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("name")).thenReturn("Vicenzo");
        when(resultSet.getString("email")).thenReturn("vicenzo@vicenzo.com");
        when(resultSet.getInt("age")).thenReturn(19);

        // When
        Customer actual = customerRowMapper.mapRow(resultSet, 1);

        // Then
        Customer expected = new Customer(1L, "Vicenzo", "vicenzo@vicenzo.com", 19);

        assertThat(actual).isEqualTo(expected);

    }
}