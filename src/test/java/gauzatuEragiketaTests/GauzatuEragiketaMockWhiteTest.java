package gauzatuEragiketaTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;
import testOperations.TestDataAccess;

public class GauzatuEragiketaMockWhiteTest {

	// system under test
    @InjectMocks
    static DataAccess sut = new DataAccess();

    // Mocked EntityManager
    @Mock
    private EntityManager db;

    // Mocked TypedQuery
    @Mock
	TypedQuery<User> typedQuery;

    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    @SuppressWarnings("unused")
    private Driver driver;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
	public void test1() {
		System.out.println("\n----- TEST 1 -----");
		double initialUserMoney = 3;

		// Prepare parameters
		String username = "user1";
		double amount = 2;
		boolean deposit = true;

		// Prepare existing user
		User mockUser = new Driver(username, "fake_password");
        mockUser.setMoney(initialUserMoney);
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(driver);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);

		assertEquals(initialUserMoney + amount, mockUser.getMoney(), 0.0);
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