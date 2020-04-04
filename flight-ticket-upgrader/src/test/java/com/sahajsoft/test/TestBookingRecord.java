package com.sahajsoft.test;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sahajsoft.BookingRecord;
import com.sahajsoft.FlightTicketUpgraderApp;
import com.sahajsoft.MessageConstants;

public class TestBookingRecord {

	private static BookingRecord record;

	private static void initializeRecord() {
		record = new BookingRecord().withFirstName("Ketan").withLastName("Kankapure").withPNR("ABC123")
				.withFareClass('A').withTravelDate(LocalDate.parse("2020-03-30")).withPax(1)
				.withTicketingDate(LocalDate.parse("2020-03-15")).withEmailId("k.s.kankapure1@gmail.com")
				.withMobileNumber("8830187904").withBookedCabin(BookingRecord.valueOfCabinClass("Economy"));
	}

	@Before
	public void runBeforeTestMethod() {
		initializeRecord();
	}

	@Test
	public void testValidBookingRecord() {
		Assert.assertTrue("Should validate booking record with no validation errors",
				record.isValid().get().size() == 0);

	}

	@Test
	public void testBookingRecordWithInvalidPNR() {
		Assert.assertTrue("Should validate booking record with invalid PNR",
				record.withPNR("12345").isValid().get().get(0).contentEquals(MessageConstants.INVALID_PNR));

	}

	@Test
	public void testBookingRecordWithInvalidEmail() {
		Assert.assertTrue("Should validate booking record with invalid Email",
				record.withEmailId("k.s.kankapure@@gmail.com").isValid().get().get(0)
						.contentEquals(MessageConstants.INVALID_EMAIL));

	}

	@Test
	public void testBookingRecordWithInvalidPhoneNumber() {
		Assert.assertTrue("Should validate booking record with invalid Mobile Phone No.",
				record.withMobileNumber("123456789").isValid().get().get(0)
						.contentEquals(MessageConstants.INVALID_MOBILE_NUMBER));
	}

	@Test
	public void testBookingRecordWithInvalidDates() {
		Assert.assertTrue("Should validate booking record with travel date which is before ticketing date",
				record.withTicketingDate(LocalDate.parse("2020-03-27")).withTravelDate(LocalDate.parse("2020-03-25"))
						.isValid().get().get(0).contentEquals(MessageConstants.TRAVEL_DATE_BEFORE_TICKET_DATE));
	}

	@Test
	public void testDiscountCode() {
		Assert.assertTrue(record.getDiscountCode().contentEquals(BookingRecord.DiscountCode.OFFER_20.name()));

	}

	@Test
	public void testAppRun() {
		//No exceptions should be thrown
		FlightTicketUpgraderApp.main(null);
	}
}
