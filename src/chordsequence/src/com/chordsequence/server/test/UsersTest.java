package com.chordsequence.server.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chordsequence.server.Users;

public class UsersTest {

	@Test
	public void test_basic() {
		Users users = new Users();
		
		assertTrue(users.login("jim", "apples"));
		assertTrue(!users.login("jim", "bananas"));
		assertTrue(users.login("share", ""));
		
	}

}
