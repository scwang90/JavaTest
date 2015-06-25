package com.test.print;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Random;

import org.junit.Test;

public class StringPrintTest implements Serializable{

	private static final long serialVersionUID = 257400698525953377L;
	
//	private String Name = "scwang90";

	@Test
	public void testPrintWriter(){
		StringWriter writer = new StringWriter();
		PrintWriter print = new PrintWriter(writer);
		print.println("holleword");
		print.printf("rand = %d", new Random().nextInt(9));
		System.out.println(writer.toString());
	}

	@Test
	public void testFileWriter() throws IOException{
		PrintWriter print = new PrintWriter(new FileWriter("print.txt"));
		print.println("holleword");
		print.printf("rand = %d", new Random().nextInt(9));
		print.close();
	}

	@Test
	public void testPrintStream() throws IOException{
		PrintStream print = new PrintStream(new FileOutputStream("PrintStream.txt"));
		print.println("holleword");
		print.printf("rand = %d", new Random().nextInt(9));
		print.close();
		
	}
	
	@Test
	public void testBufferedWriter() throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter("writer.txt"));
		writer.write("holleword");
		writer.newLine();
		writer.write("rand = %d"+new Random().nextInt(9));
		writer.close();
	}
	
	@Test
	public void testObjectStream() throws IOException{
		File file = new File("object.txt");
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(new StringPrintTest());
		outputStream.flush();
		outputStream.close();
		System.out.println(file.getAbsolutePath());
	}
	
}
