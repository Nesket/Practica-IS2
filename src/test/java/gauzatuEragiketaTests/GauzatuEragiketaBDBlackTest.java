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
	
	private double initialUserMoney = 20;
	
	private void setInitValues(String username, Double amount, Boolean deposit) {
		this.username = username;
		this.amount = amount;
		this.deposit = deposit;
	}
	
	@Test
	public void test1() {
		// Prepare parameters
		setInitValues(null, 2.0, false);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();
		
		assertFalse(result);
	}
	
	@Test
	public void test2() {
		// Prepare parameters
		setInitValues("user1", null, false);

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		driver = testDA.createDriver(username, "fake_password");
		testDA.setMoneyToDriver(initialUserMoney, driver);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		try {			
			sut.gauzatuEragiketa(username, amount, deposit);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
		sut.close();
	}
	
	@Test
	public void test3() {
		// Prepare parameters
		setInitValues("user2", null, false);
		
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
		// Prepare parameters
		setInitValues("user1", 10.0, true);

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		driver = testDA.createDriver(username, "fake_password");
		testDA.setMoneyToDriver(initialUserMoney, driver);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		testDA.open();
		driver = testDA.getDriver(username);
		testDA.close();
		
		assertEquals(initialUserMoney + amount, driver.getMoney(), 0.0);
	}

	@Test
	public void test5() {
		// Prepare parameters
		setInitValues("user1", 10.0, false);

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		driver = testDA.createDriver(username, "fake_password");
		testDA.setMoneyToDriver(initialUserMoney, driver);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		testDA.open();
		driver = testDA.getDriver(username);
		testDA.close();
		
		assertEquals(initialUserMoney - amount, driver.getMoney(), 0.0);
	}
	
	@Test
	public void test6() {
		// Prepare parameters
		setInitValues("user1", 23.0, false);

		// Prepare existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		driver = testDA.createDriver(username, "fake_password");
		testDA.setMoneyToDriver(initialUserMoney, driver);
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		testDA.open();
		driver = testDA.getDriver(username);
		testDA.close();
		
		assertEquals(0, driver.getMoney(), 0.0);
	}

}
