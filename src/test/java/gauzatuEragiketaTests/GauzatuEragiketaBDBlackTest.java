package gauzatuEragiketaTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.NoResultException;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class GauzatuEragiketaBDBlackTest {

	// system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@SuppressWarnings("unused")
	private Driver driver;
	
	@SuppressWarnings("unused")
	private String username;
	
	@SuppressWarnings("unused")
	private Double amount;
	
	@SuppressWarnings("unused")
	private Boolean deposit;

	@Test
	public void test1() {
		// Prepare parameters
		username = null;
		amount = 2.0;
		deposit = false;

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();
		
		assertFalse(result);
	}
	
	@Test
	public void test2() {
		double initialUserMoney = 20;

		// Prepare parameters
		username = "user1";
		amount = null;
		deposit = false;

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		User u = testDA.createDriver(username, "fake_password");
		u.setMoney(initialUserMoney);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		try {			
			boolean result = sut.gauzatuEragiketa(username, amount, deposit);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		sut.close();
	}
	
	@Test
	public void test3() {
		double initialUserMoney = 20;

		// Prepare parameters
		username = "user2";
		amount = null;
		deposit = false;
		
		// Prepare non-existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}

		// Invoke System Under Test
		sut.open();
		try {			
			boolean result = sut.gauzatuEragiketa(username, amount, deposit);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		sut.close();
	}

	@Test
	public void test4() {
		double initialUserMoney = 20;

		// Prepare parameters
		String username = "user1";
		double amount = 10;
		boolean deposit = true;

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		User u = testDA.createDriver(username, "fake_password");
		u.setMoney(initialUserMoney);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
	}

	@Test
	public void test5() {
		double initialUserMoney = 20;

		// Prepare parameters
		String username = "user1";
		double amount = 10;
		boolean deposit = false;

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		User u = testDA.createDriver(username, "fake_password");
		u.setMoney(initialUserMoney);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
	}
	
	@Test
	public void test6() {
		double initialUserMoney = 20;

		// Prepare parameters
		String username = "user1";
		double amount = 23;
		boolean deposit = false;

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		User u = testDA.createDriver(username, "fake_password");
		u.setMoney(initialUserMoney);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
	}

}