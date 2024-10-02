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
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;
import testOperations.TestDataAccess;

public class GauzatuEragiketaMockBlackTest {

	// system under test
    @InjectMocks
    static DataAccess sut = new DataAccess();

    // Mocked EntityManager
    @Mock
    private EntityManager db;

    // Mocked TypedQuery
    @Mock
    private TypedQuery<User> query;

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

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

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
        when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);

        User mockUser = new Driver(username, "fake_password");
        mockUser.setMoney(initialUserMoney);
        when(query.getSingleResult()).thenReturn(mockUser);

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
        when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);

        when(query.getSingleResult()).thenReturn(null);

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
        when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);

        User mockUser = new Driver(username, "fake_password");
        mockUser.setMoney(initialUserMoney);
        when(query.getSingleResult()).thenReturn(mockUser);

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
        when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);

        User mockUser = new Driver(username, "fake_password");
        mockUser.setMoney(initialUserMoney);
        when(query.getSingleResult()).thenReturn(mockUser);

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
        when(db.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)).thenReturn(query);
        when(query.setParameter("username", username)).thenReturn(query);

        User mockUser = new Driver(username, "fake_password");
        mockUser.setMoney(initialUserMoney);
        when(query.getSingleResult()).thenReturn(mockUser);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
	}
}