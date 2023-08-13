package com.vicenzo.customer.repository;

import com.vicenzo.customer.CustomerRowMapper;
import com.vicenzo.customer.model.Customer;
import com.vicenzo.util.QueryExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("jdbc")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerJDBCDataAccessRepository implements CustomerDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final QueryExecutor queryExecutor;
    private final CustomerRowMapper customerRowMapper;

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name ,email, age
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Long customerId) {
        var sql = """
                Select id, name, email, age
                FROM customer
                WHERE  id = ?
                """;
        return jdbcTemplate.query(sql, customerRowMapper, customerId)
                .stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age)
                VALUES (?,?,?)
                """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println("jdbcTemplate.update =" + result);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE email = ?
                """;
        Long count = jdbcTemplate.queryForObject(sql, Long.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsCustomerWithId(Long id) {
        var sql = """
                SELECT count(id)
                FROM customer
                WHERE id = ?
                """;
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count != null && count > 0;
    }

    @Override
    public void deleteCustomerCustomerById(Long id) {
        var sql = """
                DELETE
                FROM customer
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateCustomer(Customer customer) {
        var sql = """
                UPDATE customer
                SET name = ?, email = ?, age = ?
                WHERE id = ?
                """;
        jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge(), customer.getId());
    }

    public List<Customer> selectCustomersByIdsAndNames(List<Long> ids, List<String> names) {
        var sql = """
                SELECT id, name, email, age
                FROM customer
                WHERE id IN (:ids)
                AND name IN (:names)
                """;

        // if namedParameterJDBCTemplate is not use we need to add the values of id in sql using comma separation
        Map<String, List<?>> paramMap = Map.of("ids", ids, "names", names);
        return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<>(Customer.class));
    }
}
