package com.sahajsoft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class with utility methods which can be used across application
 */
public class Utilities {

	private final static String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	private final static Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private final static String MOBILE_NUMBER_REGEX = "^((\\+)?(\\d{2}[-]))?(\\d{10}){1}?$";
	private final static Pattern MOBILE_NUMBER_PATTERN = Pattern.compile(MOBILE_NUMBER_REGEX);
	private final static String PNR_REGEX = "\\b[a-zA-Z0-9]{6}\\b";
	private final static Pattern PNR_PATTERN = Pattern.compile(PNR_REGEX);

	public static boolean isBetween(char x, char lower, char upper) {
		return lower <= x && x <= upper;
	}

	public static boolean validateEmail(String emailId) {
		return EMAIL_PATTERN.matcher(emailId).matches();
	}

	public static boolean validateMobileNum(String mobileNumber) {
		return MOBILE_NUMBER_PATTERN.matcher(mobileNumber).matches();
	}

	public static boolean validatePNR(String PNR) {
		return PNR_PATTERN.matcher(PNR).matches();
	}

	public static String readResourceFile(String fileNameWithExtension) {
		return new BufferedReader(
				new InputStreamReader(Utilities.class.getClassLoader().getResourceAsStream(fileNameWithExtension)))
						.lines().collect(Collectors.joining(System.lineSeparator()));
	}

	public static void writeToUserDirectory(ArrayList<String> contentList, String fileNameWithExtension) {
		try {
			FileWriter writer = new FileWriter(fileNameWithExtension);
			for (String str : contentList) {
				writer.write(str + System.lineSeparator());
			}
			writer.close();
			System.out.println("Check " + System.getProperty("user.dir") + File.separator + fileNameWithExtension);
		} catch (IOException e) {
			// Nothing
		}
	}

}
