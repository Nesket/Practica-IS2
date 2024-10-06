package bookRideTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class BookRideMockWhiteTest {
	private static final Logger logger = Logger.getLogger(BLFacadeImplementation.class.getName());
	
static DataAccess sut = new DataAccess();
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
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
	private Ride ride;
	
	@SuppressWarnings("unused")
	private int seats;
	
	@SuppressWarnings("unused")
	private double desk;
	
	private double initialUserMoney = 10;
	
	private void setInitValues(String username, Ride ride, int seats, double desk) {
		this.username = username;
		this.ride = ride;
		this.seats = seats;
		this.desk = desk;
	}

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
		System.out.println("\n----- TEST 1 -----");
		try {
			Ride mockRide = Mockito.mock(Ride.class);
			setInitValues("mockedUser", mockRide, 1, 0);
			
			// Enter catch in bookRide()
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(null);		
			//Mockito.when(typedQuery.getSingleResult()).thenThrow(NoResultException.class);
			
			// Invoke System Under Test
			sut.open();
			boolean result = sut.bookRide(username, ride, seats, desk);
			sut.close();

			// Check results
			assertFalse(result);
		}
		catch(Exception e){
			logger.info("Error encountered: "+e.getMessage());
		}
		
	}
	
	@Test
	public void test2() {
		System.out.println("\n----- TEST 2 -----");
		try {
			Ride mockRide = Mockito.mock(Ride.class);
			setInitValues("mockedUser", mockRide, 1, 0);
			
			// Prepare non-existing user
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);		
			Mockito.when(typedQuery.getSingleResult()).thenReturn(null);
			
			// Invoke System Under Test
			sut.open();
			boolean result = sut.bookRide(username, ride, seats, desk);
			sut.close();

			// Check results
			assertFalse(result);
		}
		catch(Exception e){
			logger.info("Error encountered: "+e.getMessage());
		}
		
	}
	
	@Test
	public void test3() {
		System.out.println("\n----- TEST 3 -----");
		try {
			Ride mockedRide = Mockito.mock(Ride.class);
			setInitValues("mockedUser", mockedRide, 1, 0);
			
			// Prepare existing user
			User mockedUser = Mockito.mock(Traveler.class);
			
			Mockito.when(db.createQuery(Mockito.anyString(), Mockito.any(Class.class))).thenReturn(typedQuery);
			Mockito.when(typedQuery.getSingleResult()).thenReturn(mockedUser);
			
			Mockito.doReturn(0).when(mockedRide).getnPlaces();
			
			// Invoke System Under Test
			sut.open();
			boolean result = sut.bookRide(username, ride, seats, desk);
			sut.close();

			// Check results
			assertFalse(result);
		}
		catch(Exception e){
			logger.info("Error encountered: "+e.getMessage());
		}
		
	}
	
}
