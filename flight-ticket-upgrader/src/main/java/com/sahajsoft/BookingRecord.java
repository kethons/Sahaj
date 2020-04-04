package com.sahajsoft;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * POJO class to store booking records
 */
public class BookingRecord {

	/**
	 * First name
	 */
	private String firstName;

	/**
	 * Last name
	 */
	private String lastName;

	/**
	 * PNR Number
	 */
	private String PNR;

	/**
	 * Fare class
	 */
	private char fareClass;

	/**
	 * Travel date
	 */
	private LocalDate travelDate;

	/**
	 * Number of people travelling together
	 */
	private int pax;

	/**
	 * Ticketing date
	 */
	private LocalDate ticketingDate;

	/**
	 * Email Id
	 */
	private String emailId;

	/**
	 * Mobile Number
	 */
	private String mobileNumber;

	/**
	 * Booked cabin
	 */
	private CabinClass bookedCabin;

	/**
	 * Validation Error list
	 */
	private List<String> validationErrors;

	/**
	 * Booking record field delimiter
	 */
	public final static String DELIMITER = ",";

	/**
	 * Default constructor
	 */
	public BookingRecord() {
		validationErrors = new ArrayList<>();
	}

	/**
	 * Builds {@link BookingRecord} with {@link #firstName}
	 *
	 * @param firstName <code>firstName</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #lastName}
	 *
	 * @param lastName <code>lastName</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #PNR}
	 *
	 * @param PNR <code>PNR</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withPNR(String PNR) {
		this.PNR = PNR;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #fareClass}
	 *
	 * @param fareClass <code>fareClass</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withFareClass(char fareClass) {
		this.fareClass = fareClass;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #travelDate}
	 *
	 * @param travelDate <code>travelDate</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #pax}
	 *
	 * @param pax <code>pax</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withPax(int pax) {
		this.pax = pax;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #ticketingDate}
	 *
	 * @param ticketingDate <code>ticketingDate</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withTicketingDate(LocalDate ticketingDate) {
		this.ticketingDate = ticketingDate;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #emailId}
	 *
	 * @param emailId <code>emailId</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withEmailId(String emailId) {
		this.emailId = emailId;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #mobileNumber}
	 *
	 * @param mobileNumber <code>mobileNumber</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
		return this;
	}

	/**
	 * Builds {@link BookingRecord} with {@link #bookedCabin}
	 *
	 * @param bookedCabin <code>bookedCabin</code> to be set
	 * @return {@link BookingRecord}
	 */
	public BookingRecord withBookedCabin(CabinClass bookedCabin) {
		this.bookedCabin = bookedCabin;
		return this;
	}

	/**
	 * Validates the booking records
	 *
	 * @return <em>true<em/> if validation is successful, <em>false<em/> otherwise
	 */
	public Optional<List<String>> isValid() {
		if (!Utilities.validatePNR(PNR))
			validationErrors.add(MessageConstants.INVALID_PNR);
		if (ticketingDate.isAfter(travelDate))
			validationErrors.add(MessageConstants.TRAVEL_DATE_BEFORE_TICKET_DATE);
		if (!Utilities.validateEmail(emailId))
			validationErrors.add(MessageConstants.INVALID_EMAIL);
		if (!Utilities.validateMobileNum(mobileNumber))
			validationErrors.add(MessageConstants.INVALID_MOBILE_NUMBER);
		if (!CabinClass.contains(bookedCabin.name()))
			validationErrors.add(MessageConstants.INVALID_BOOKED_CABIN);
		return Optional.ofNullable(validationErrors);
	}

	@Override
	public String toString() {

		return firstName + DELIMITER + lastName + DELIMITER + PNR + DELIMITER + fareClass + DELIMITER + travelDate
				+ DELIMITER + pax + DELIMITER + ticketingDate + DELIMITER + emailId + DELIMITER + mobileNumber
				+ DELIMITER + bookedCabin;

	}

	/**
	 * Gets {@link CabinClass} value
	 *
	 * @param cabinClass cabinClass
	 * @return {@link CabinClass} value
	 */
	public static CabinClass valueOfCabinClass(String cabinClass) {
		return CabinClass.valueOf(cabinClass.toUpperCase());
	}

	/**
	 * Gets discount code for {@link #fareClass}
	 *
	 * @return
	 */
	public String getDiscountCode() {
		if (Utilities.isBetween(fareClass, 'A', 'E'))
			return DiscountCode.OFFER_20.name();
		else if (Utilities.isBetween(fareClass, 'F', 'K'))
			return DiscountCode.OFFER_30.name();
		else if (Utilities.isBetween(fareClass, 'L', 'R'))
			return DiscountCode.OFFER_25.name();
		else
			return "";

	}

	/**
	 * Enum for various booking discount codes
	 */
	public enum DiscountCode {

		OFFER_20, OFFER_25, OFFER_30;

	}

	/**
	 * Enum for various booking cabin classes
	 */
	private enum CabinClass {

		ECONOMY, PREMIUM_ECONOMY, BUSINESS, FIRST;

		/**
		 * Checks if <code>cabinClass</code> is a valid {@link BookingRecord.CabinClass}
		 *
		 * @param cabinClass <code>cabinClass</code>
		 * @return <em>true</em> is <code>cabinClass</code> is valid, <em>false</em>
		 *         otherwise
		 */
		public static boolean contains(String cabinClass) {
			for (CabinClass c : values()) {
				if (c.name().equalsIgnoreCase(cabinClass)) {
					return true;
				}
			}
			return false;
		}
	}
}