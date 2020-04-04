package com.sahajsoft;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

/**
 * Main class
 */
public class FlightTicketUpgraderApp {
	
	private static final String INPUT_FILE = "bookingRecords.txt";
	private static final String SUCCESSFUL_OUTPUT_FILE = "successfulBookingRecords.txt";
	private static final String FAILED_OUTPUT_FILE = "failedBookingRecords.txt";
	private static final boolean DEBUG_FLAG = true;
	private static final String ERROR_HEADER = "Error";
	private static final String DISCOUNT_CODE_HEADER = "Discount_code";
	private static final String COMMON_BOOKING_RECORD_HEADERS = "First_name,Last_name,Fare_class,Travel_date,Pax,Ticketing_date,Email,Mobile_phone,Booked_cabin";

	public static void main(String[] args) {
		try {
			String inputFileContent = Utilities.readResourceFile(INPUT_FILE);
			StringTokenizer bookingRecords = new StringTokenizer(inputFileContent, System.lineSeparator());
			ArrayList<String> successfulRecords = new ArrayList<>();
			successfulRecords.add(MessageConstants.SUCCESSFUL_RECORDS_HEADER_MSG);
			successfulRecords.add(COMMON_BOOKING_RECORD_HEADERS + BookingRecord.DELIMITER + DISCOUNT_CODE_HEADER);
			ArrayList<String> failedRecords = new ArrayList<>();
			failedRecords.add(COMMON_BOOKING_RECORD_HEADERS + BookingRecord.DELIMITER + ERROR_HEADER);
			bookingRecords.nextToken();// to by pass common booking record headers from processing
			while (bookingRecords.hasMoreTokens()) {
				String recordFields[] = bookingRecords.nextToken().split(BookingRecord.DELIMITER);
				BookingRecord record = new BookingRecord().withFirstName(recordFields[0]).withLastName(recordFields[1])
						.withPNR(recordFields[2]).withFareClass(recordFields[3].charAt(0))
						.withTravelDate(LocalDate.parse(recordFields[4])).withPax(Integer.valueOf(recordFields[5]))
						.withTicketingDate(LocalDate.parse(recordFields[6])).withEmailId(recordFields[7])
						.withMobileNumber(recordFields[8])
						.withBookedCabin(BookingRecord.valueOfCabinClass(recordFields[9]));
				Optional<List<String>> validationErrors = record.isValid();
				if (validationErrors.isPresent()) {
					if (validationErrors.get().isEmpty()) {
						successfulRecords.add(record.toString() + BookingRecord.DELIMITER + record.getDiscountCode());
					} else {
						failedRecords.add(record.toString() + BookingRecord.DELIMITER + validationErrors.get());
					}
				}
			}
			Utilities.writeToUserDirectory(successfulRecords, SUCCESSFUL_OUTPUT_FILE);
			Utilities.writeToUserDirectory(failedRecords, FAILED_OUTPUT_FILE);

		} catch (Exception e) {
			try {
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				System.out.println(DEBUG_FLAG ? sw.toString() : MessageConstants.EXCEPTION_OCCURRED);
			} catch (Exception fileNotFoundExp) {
				// Nothing
			}
		}
	}

}