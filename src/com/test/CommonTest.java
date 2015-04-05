package com.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CommonTest {

	public static <T> List<T> getList(){
		return null;
	}
	
	@Test
	public void test() {
		List<Integer> gg = getList();
		List<Thread> ss = getList();
		fail("Not yet implemented");
	}

	
}
