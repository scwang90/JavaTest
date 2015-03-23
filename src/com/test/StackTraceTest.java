package com.test;



public class StackTraceTest {

	private int count = 0;
	
	private void onBackKeyPressed() {
		// TODO Auto-generated method stub
		StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
		for (StackTraceElement element : stacks) {
			System.out.println(element+"\t\t\t"+element.getClassName());
		}
		System.out.println();
		
		StackTraceElement stack = stacks[1];
		for (int i = 2; i < stacks.length; i++) {
			StackTraceElement element = stacks[i];
			if (element.getClassName().equals(stack.getClassName())
					&& element.getMethodName().equals(stack.getMethodName())) {
				System.out.println("µÝ¹é¼ì²â");
			}
		}
		
		if (count++ < 1) {
			onBackPressed();
		}
	}
	
	private void onBackPressed() {
		// TODO Auto-generated method stub
		onBackKeyPressed();
	}

	public static void main(String[] args) throws Exception {
		new StackTraceTest().onBackPressed();
	}
}
