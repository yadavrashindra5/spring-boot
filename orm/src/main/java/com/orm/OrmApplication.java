package com.orm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrmApplication implements Runnable{

	public static void main(String[] args) {
		SpringApplication.run(OrmApplication.class, args);
	}

	@Override
	public void run() {
		System.out.println("Hello world");
	}
}
