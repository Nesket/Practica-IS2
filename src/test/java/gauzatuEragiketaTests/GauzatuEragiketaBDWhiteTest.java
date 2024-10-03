package gauzatuEragiketaTests;

import static org.junit.Assert.*;

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

public class GauzatuEragiketaBDWhiteTest {

	// system under test
	static DataAccess sut = new DataAccess();

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	@SuppressWarnings("unused")
	private Driver driver;

	@Test
	public void test1() {
		System.out.println("\n----- TEST 1 -----");
		double initialUserMoney = 3;

		// Prepare parameters
		String username = "user1";
		double amount = 2;
		boolean deposit = true;

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
	public void test2() {
		System.out.println("\n----- TEST 2 -----");
		double initialUserMoney = 3;

		// Prepare parameters
		String username = "user1";
		double amount = 4;
		boolean deposit = false;

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

	@Test
	public void test3() {
		System.out.println("\n----- TEST 3 -----");
		double initialUserMoney = 3;

		// Prepare parameters
		String username = "user1";
		double amount = 2;
		boolean deposit = false;

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
	public void test5() {
		System.out.println("\n----- TEST 5 -----");
		// Prepare parameters
		String username = "user2";
		double amount = 2;
		boolean deposit = false;

		// Prepare non-existing user
		testDA.open();
		if (testDA.existDriver(username)) {
			testDA.removeDriver(username);
		}
		testDA.close();

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertFalse(result);
	}

}
