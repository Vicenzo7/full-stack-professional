package com.vicenzo.customer.repository;


import com.vicenzo.AbstractTestContainers;
import com.vicenzo.customer.mapper.CustomerRowMapper;
import com.vicenzo.customer.model.Customer;
import com.vicenzo.util.QueryExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessRepositoryTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessRepository underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();


    @BeforeEach
    void setUp() {
        underTest = new CustomerJDBCDataAccessRepository(
                getJdbcTemplate(),
                getNamedParameterJdbcTemplate(),
                new QueryExecutor(getJdbcTemplate()),
                customerRowMapper
        );
    }

    @Test
    void selectAllCustomers() {
        // Given
        Customer customer = new Customer(
                FAKER.name().fullName(),
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                20
        );
        underTest.insertCustomer(customer);

        // When
        List<Customer> actual = underTest.selectAllCustomers();

        // Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();


        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        // Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById() {
        // Given
        long id = -1;

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isEmpty();
    }

    @Test
    void insertCustomer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );

        // When
        underTest.insertCustomer(customer);

        // Then
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        Optional<Customer> actual = underTest.selectCustomerById(id);
        // Then
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
        });
    }

    @Test
    void existsCustomerWithEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        // When
        boolean actual = underTest.existsCustomerWithEmail(email);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void willReturnFalseExistsCustomerWithEmail() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        // When
        boolean actual = underTest.existsCustomerWithEmail(email);

        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerWithId() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        boolean actual = underTest.existsCustomerWithId(id);

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void willReturnFalseExistsCustomerWithId() {
        // Given
        Long id = -1L;
        // When
        boolean actual = underTest.existsCustomerWithId(id);
        // Then
        assertThat(actual).isFalse();
    }

    @Test
    void deleteCustomerCustomerById() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        Long id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        // When
        underTest.deleteCustomerCustomerById(id);
        Optional<Customer> actual = underTest.selectCustomerById(id);

        // Then
        assertThat(actual).isNotPresent();
    }

    @Test
    void updateCustomer() {
        // Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(
                FAKER.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

        Customer fetchedCustomer = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst()
                .orElseThrow();
        // When
        fetchedCustomer.setName("Vicenzo");
        fetchedCustomer.setEmail("vicenzo@vicenzo.com");
        fetchedCustomer.setAge(23);
        underTest.updateCustomer(fetchedCustomer);

        Optional<Customer> actual = underTest.selectCustomerById(fetchedCustomer.getId());

        // Then
        assertThat(actual).isPresent().hasValue(fetchedCustomer);
    }

    @Test
    void selectCustomersByIdsAndNames() {
        // Given
        List<Customer> expectedCustomers = Arrays.asList(
                new Customer("Alice", "alice@example.com", 25),
                new Customer("Bob", "bob@example.com", 30),
                new Customer("Charlie", "charlie@example.com", 22)
        );

        expectedCustomers.forEach(c -> underTest.insertCustomer(c));
        List<Customer> customers = underTest.selectAllCustomers();
        List<Long> ids = customers.stream()
                .filter(customer -> expectedCustomers.stream().anyMatch(expected -> expected.getEmail().equals(customer.getEmail())))
                .map(Customer::getId)
                .collect(Collectors.toList());

        List<String> names = customers.stream()
                .filter(customer -> expectedCustomers.stream().anyMatch(expected -> expected.getEmail().equals(customer.getEmail())))
                .map(Customer::getName)
                .collect(Collectors.toList());

        // When
        List<Customer> actualCustomers = underTest.selectCustomersByIdsAndNames(ids, names);

        // Then
        assertThat(actualCustomers.size()).isEqualTo(expectedCustomers.size());

        for (int i = 0; i < actualCustomers.size(); i++) {
            Customer actual = actualCustomers.get(i);
            Customer expected = expectedCustomers.get(i);

            assertThat(actual.getName()).isEqualTo(expected.getName());
            assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
            assertThat(actual.getAge()).isEqualTo(expected.getAge());
        }
    }
}