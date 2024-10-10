package bookRideTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import domain.Booking;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;

public class BookRideBDBlackTest {
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
    private Traveler traveler;
    
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
			// Prepare parameters
			setInitValues("user1", ride, 1, 0);
									
			// Prepare existing user
			String fakePassword = "fakepassword";
			testDA.open();
			if (testDA.existTraveler(username)) {
				testDA.removeTraveler(username);
			}
						
			traveler = testDA.createTraveler(username, fakePassword);
			testDA.setMoneyToTraveler(10, traveler);
			testDA.close();
			
			// Prepare existing driver and existing ride
			String driverUsername="Driver Test";
			
			String rideFrom="Donostia";
			String rideTo="Zarautz";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date rideDate=null;
			

			try {
				rideDate = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				logger.info("Error encountered: "+e.getMessage());
			}	
			
			testDA.open();
			if (testDA.existDriver(driverUsername)) {
				testDA.removeDriver(driverUsername);
			}
			driver = testDA.createDriver(driverUsername, fakePassword);
			testDA.close();
			
			testDA.open();
			int nPlaces = 2;
			float price = 10;
			this.ride = testDA.addRideToDriver(driver, rideFrom, rideTo, rideDate, nPlaces, price);
			testDA.close();
			
			// Invoke System Under Test
			sut.open();
			
			boolean result = sut.bookRide(username, ride, seats, desk);
			sut.close();

			// Check results
			assertTrue(result);
			
			
		}
		catch(Exception e){
			logger.info("Error encountered: "+e.getMessage());
		}
		
	}
	
	@Test
	public void test2() {
		System.out.println("\n----- TEST 2 -----");
		try {
			// Prepare existing driver and existing ride
			String driverUsername="Driver Test";
			String fakePassword = "fakepassword";
			
			String rideFrom="Donostia";
			String rideTo="Zarautz";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date rideDate=null;
			

			try {
				rideDate = sdf.parse("05/10/2026");
			} catch (ParseException e) {
				logger.info("Error encountered: "+e.getMessage());
			}	
			
			testDA.open();
			if (testDA.existDriver(driverUsername)) {
				testDA.removeDriver(driverUsername);
			}
			driver = testDA.createDriver(driverUsername, fakePassword);
			testDA.close();
			
			//Check if this ride exists for this driver, and if it exists, remove it.
			testDA.open();
			driver = testDA.addDriverWithRide( driverUsername,  rideFrom,  rideTo,   rideDate,2,10);
			testDA.close();
			
			
			testDA.open();
			if (testDA.existRide(driverUsername, rideFrom, rideTo, rideDate)) {
				testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
			}
			
			ride = testDA.createRide(rideFrom, rideTo, rideDate, 2, 10, driver);
			testDA.close();
			
			// Prepare parameters
			
			setInitValues(null, ride, 1, 0);
			
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
		
		try{
			
			// Prepare parameters
						
			setInitValues("user1", null, 1, 0);
						
			// Prepare existing user
			String fakePassword = "fakepassword";
			testDA.open();
			if (testDA.existTraveler(username)) {
				testDA.removeTraveler(username);
			}
			
			traveler = testDA.createTraveler(username, fakePassword);
			testDA.close();
			
			// Invoke System Under Test
			sut.open();
						
			boolean result = sut.bookRide(username, null, seats, desk);
			sut.close();

			// Check results
			assertFalse(result);
						
						
		}
		catch(Exception e){
			logger.info("Error encountered: "+e.getMessage());
		}		
	}
	
	
}
