package com.sahajsoft.test;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.sahajsoft.Utilities;

public class TestUtilities {

	@Test
	public void testEmailValidation() {

		Assert.assertTrue(Utilities.validateEmail("k.s.kankapure1@gmail.com"));
		Assert.assertTrue(Utilities.validateEmail("phanik@sahajsoft.com"));
		Assert.assertTrue(Utilities.validateEmail("rohitm@sahajsoft.com"));
		Assert.assertTrue(Utilities.validateEmail("org-team@sahajsoft.com"));
		Assert.assertFalse("Should not validate email with more than one @ symbol",
				Utilities.validateEmail("org-team@@sahajsoft.com"));
		Assert.assertFalse("Should not validate email with no TLD", Utilities.validateEmail("org-team@zzz"));
	}

	@Test
	public void testMobilePhoneValidation() {
		Assert.assertTrue(Utilities.validateMobileNum("8830187904"));
		Assert.assertTrue(Utilities.validateMobileNum("+91-8830187904"));
		Assert.assertFalse("Should not validate alphanumeric numbers", Utilities.validateMobileNum("883XYZ904"));
		Assert.assertFalse("Should not validate numbers with less than 10 digits",
				Utilities.validateMobileNum("123456789"));

	}

	@Test
	public void testPNRValidation() {
		Assert.assertTrue(Utilities.validatePNR("123456"));
		Assert.assertTrue(Utilities.validatePNR("ABCDFG"));
		Assert.assertTrue(Utilities.validatePNR("CFG45H"));
		Assert.assertFalse("Should not validate PNR with less than 6 characters", Utilities.validatePNR("12345"));
		Assert.assertFalse("Should not validate PNR with more than 6 characters", Utilities.validatePNR("1234567"));
		Assert.assertFalse("Should not validate PNR with special symbols", Utilities.validatePNR("12@345"));
	}
}
