package com.example.app.service.impl;

import java.security.SecureRandom;

public class PasswordGenerator {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_-+=<>?";
	private static final int PASSWORD_LENGTH = 12;

	public static void main(String[] args) {
		String password = generateRandomPassword(PASSWORD_LENGTH);
		System.out.println("Generated Password: " + password);
	}

	public static String generateRandomPassword(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}

		return password.toString();
	}
}
