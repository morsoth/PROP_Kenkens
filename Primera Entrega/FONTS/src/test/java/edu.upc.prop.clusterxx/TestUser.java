package edu.upc.prop.clusterxx;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.upc.prop.clusterxx.domain.User;

public class TestUser {
    @Test
    public void createUserCorrectly() {
            User u = new User();
            assertNotNull(u);
    }

    @Test
    public void createUserWithNameCorrectly() {
            User u = new User("Alice");
            assertNotNull(u);
    }

    @Test
    public void createUserWithNameAndPasswordCorrectly() {
            User u = new User("Alice", "1234");
            assertNotNull(u);
    }

    @Test
    public void checkUserName() {
            User u = new User("Alice");
            String old_name = u.getName();
            u.setName("Bob");
            String new_name = u.getName();
            assertNotEquals(old_name, new_name);
    }

    @Test
    public void checkUserPassword() {
        User u = new User("Alice", "1234");
        boolean check = u.checkPassword("1234");
        assertTrue(check);
    }

    @Test
    public void checkUserPasswordWrong() {
        User u = new User("Alice", "1234");
        boolean check = u.checkPassword("4321");
        assertFalse(check);
    }

    @Test
    public void checkUserPasswordChange() {
        User u = new User("Alice", "1234");
        u.setPassword("4321");
        boolean check = u.checkPassword("4321");
        assertTrue(check);
    }

    @Test
    public void checkUserPoints() {
        User u = new User("Alice");
        u.setPoints(5);
        int old_points = u.getPoints();
        u.setPoints(10);
        int new_points = u.getPoints();
        assertNotEquals(old_points, new_points);
        assertEquals(new_points, 10);
    }

    @Test
    public void checkUserUpdatePoints() {
        User u = new User("Alice");
        u.setPoints(5);
        int old_points = u.getPoints();
        u.updatePoints(10);
        int new_points = u.getPoints();
        assertNotEquals(old_points, new_points);
        assertEquals(new_points, 15);
    }

    @Test
    public void checkUserRegistered() {
        User u = new User();
        boolean registered = u.isRegistered();
        assertFalse(registered);
    }

    @Test
    public void checkUserRegisteredWithName() {
        User u = new User("Alice");
        boolean registered = u.isRegistered();
        assertTrue(registered);
    }

    @Test
    public void checkUserRegisteredWithPassword() {
        User u = new User("Alice", "1234");
        boolean registered = u.isRegistered();
        assertTrue(registered);
    }
}
