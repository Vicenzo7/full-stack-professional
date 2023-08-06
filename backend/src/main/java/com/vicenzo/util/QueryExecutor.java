package com.vicenzo.util;

import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryExecutor {

    private final JdbcTemplate jdbcTemplate;

    public <T> List<T> executeQuery(String sql, Class<T> targetType, String[] columnNames, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rs -> {
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                T instance = rowMapper.mapRow(rs, 0);  // Maps the current row to an instance
                populateInstance(instance, rs, columnNames);
                results.add(instance);
            }
            return results;
        });
    }

    private <T> void populateInstance(T instance, ResultSet rs, String[] columnNames) throws SQLException {
        for (String columnName : columnNames) {
            if (columnName != null) {
                Object value = rs.getObject(columnName);
                // Map value to instance property using setters
                try {
                    String setterMethodName = "set" + capitalize(columnName);
                    Method setter = instance.getClass().getMethod(setterMethodName, value.getClass());
                    setter.invoke(instance, value);
                } catch (Exception e) {
                    throw new SQLException("Error mapping column " + columnName + " to property", e);
                }
            }
        }
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // second way

    /**
     If you need to perform custom column-to-property mapping, complex transformations, or work with non-standard Java classes,
     RowMapper might be more suitable.
     On the other hand, if you have a simple mapping scenario and your class follows JavaBean conventions,
     BeanPropertyRowMapper can simplify the mapping process.
    */
     public <T> List<T> executeQuery(String sql, Class<T> targetType) {
        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(targetType);
        return jdbcTemplate.query(sql, rowMapper);
    }


    // third way
    public <T> List<T> executeQuery(String sql, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, rowMapper);
    }



    /*

    Below are some example
    1. Normal Way
     var sql = """
                SELECT id, name ,email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        ));

    2. Using some reflection
        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        return queryExecutor.executeQuery(sql, Customer.class,
                new String[]{"id", "name", "email", "age"}, // Pass your column names
                (rs, rowNum) -> new Customer() // RowMapper
        );

    3. With BeanPropertyRowMapper
        String sql = """
                SELECT  name, email, age
                FROM customer
                """;
        return queryExecutor.executeQuery(sql, Customer.class);

    4. Using the first way but in clean style

        var sql = """
                SELECT id, name, email, age
                FROM customer
                """;

        return queryExecutor.executeQuery(sql, (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("age")
        ));
    */
}

