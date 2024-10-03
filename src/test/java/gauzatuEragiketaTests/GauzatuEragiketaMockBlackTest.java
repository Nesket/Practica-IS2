package gauzatuEragiketaTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.User;
import testOperations.TestDataAccess;

public class GauzatuEragiketaMockBlackTest {

	// system under test
    @InjectMocks
    static DataAccess sut = new DataAccess();
    
    protected MockedStatic<Persistence> persistenceMock;

    @Mock
	protected  EntityManager db;
    
    @Mock
	protected  EntityManagerFactory entityManagerFactory;
    
    @Mock
    protected  EntityTransaction  et;

    // Mocked TypedQuery
    @Mock
	TypedQuery<User> typedQuery;

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
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);
    }
	@After
    public  void tearDown() {
		persistenceMock.close();
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
		username = "mockedUser";
		amount = null;
		deposit = false;

		// Prepare existing user
		User mockUser = Mockito.mock(Driver.class);
        Mockito.doReturn(initialUserMoney).when(mockUser).getMoney();
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(mockUser);

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
		username = "mockedUser";
		amount = null;
		deposit = false;
		
		// Prepare non-existing user
		User mockUser = Mockito.mock(Driver.class);
        Mockito.doReturn(initialUserMoney).when(mockUser).getMoney();
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(mockUser);

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
		username = "mockedUser";
		amount = 10.0;
		deposit = true;

		// Prepare existing user
		User mockUser = Mockito.mock(Driver.class);
        Mockito.doReturn(initialUserMoney).when(mockUser).getMoney();
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(mockUser);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		Mockito.verify(mockUser).setMoney(initialUserMoney + amount);
	}

	@Test
	public void test5() {
		double initialUserMoney = 20;

		// Prepare parameters
		username = "mockedUser";
		amount = 10.0;
		deposit = false;

		// Prepare existing user
		User mockUser = Mockito.mock(Driver.class);
        Mockito.doReturn(initialUserMoney).when(mockUser).getMoney();
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(mockUser);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		Mockito.verify(mockUser).setMoney(initialUserMoney - amount);
	}
	
	@Test
	public void test6() {
		double initialUserMoney = 20;

		// Prepare parameters
		username = "mockedUser";
		amount = 23.0;
		deposit = false;

		// Prepare existing user
		User mockUser = Mockito.mock(Driver.class);
        Mockito.doReturn(initialUserMoney).when(mockUser).getMoney();
		
		Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
		Mockito.when(typedQuery.getSingleResult()).thenReturn(mockUser);

		// Invoke System Under Test
		sut.open();
		boolean result = sut.gauzatuEragiketa(username, amount, deposit);
		sut.close();

		// Check results
		assertTrue(result);
		
		Mockito.verify(mockUser).setMoney(0);
	}
}