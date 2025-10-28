package com.javatech.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Lab3Application implements CommandLineRunner {
	private final ServiceA serviceA;

    public Lab3Application(ServiceA serviceA) {
        this.serviceA = serviceA;
		System.out.println("1. Application Constructor Injection Performed");
    }

    public static void main(String[] args) {
		SpringApplication.run(Lab3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		serviceA.performAction();
	}
}

@Component
class ServiceA {

	private final DependencyA dependencyA;

	@Autowired
	private DependencyB dependencyB;

	private DependencyC dependencyC;

	@Autowired
	public ServiceA(DependencyA dependencyA) {
		System.out.println("2. Constructor Injection performed");
		this.dependencyA = dependencyA;
	}

	@Autowired
	public void setDependencyC(DependencyC dependencyC) {
		this.dependencyC = dependencyC;
		System.out.println("3. Setter Injection performed");
	}

	public void performAction() {
		System.out.println("All dependencies injected successfully!");
	}
}

@Component
class DependencyA {
	public DependencyA() {
		System.out.println("DependencyA created");
	}
}

@Component
class DependencyB {
	public DependencyB() {
		System.out.println("DependencyB created");
	}
}

@Component
class DependencyC {
	public DependencyC() {
		System.out.println("DependencyC created");
	}
}
