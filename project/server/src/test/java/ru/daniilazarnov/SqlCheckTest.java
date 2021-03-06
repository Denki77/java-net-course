package ru.daniilazarnov;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class SqlCheckTest {

    @Test
    void sqlChecker() {
        assertEquals(Connection.class,Connection.class);
    }

}