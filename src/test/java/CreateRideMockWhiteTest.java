import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Driver;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideMockWhiteTest {
	
	static DataAccess sut;
	
	protected MockedStatic<Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	

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
	
	
	Driver driver;
	@Test
	//sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test1() {
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverUserName=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;
				try {
					rideDate = sdf.parse("05/10/2026");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				Mockito.when(db.find(Driver.class, null)).thenReturn(null);

				
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUserName);

				//verify the results
				assertNull(ride);
				
			   } catch (RideAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();

				} catch (RideMustBeLaterThanTodayException e) {
				// TODO Auto-generated catch block
					fail();

				} catch (Exception e) {
					e.toString();
				// TODO Auto-generated catch block
					fail();

				} finally {
					sut.close();
				}
			
			   } 
	
	@Test
	//sut.createRide:  The Driver("Driver Test") does not exist in the DB. The test must return null 
	//The test supposes that the "Driver Test" does not exist in the DB
	public void test2() {


        
		String driverUsername="Driver Test";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
					
			 
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driverUsername)).thenReturn(null);
		
			
	      //invoke System Under Test (sut)  
			sut.open();
		    Ride r=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);
			sut.close();
			
			assertNull(r);
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
		} 
	} 
	
	@Test
	//sut.createRide:  the date of the ride must be later than today. The RideMustBeLaterThanTodayException 
		// exception must be thrown. 		
	public void test3() {
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverUserName=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;;
				try {
					rideDate = sdf.parse("05/10/2018");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				driver=new Driver(driverUserName,"123");
		        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);

				
				//invoke System Under Test (sut)  
				sut.open();
				Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUserName);

				//verify the results
				assertNull(ride);
				
			   } catch (RideAlreadyExistException e) {
				// TODO Auto-generated catch block
				// if the program goes to this point fail  
				fail();

				} catch (RideMustBeLaterThanTodayException e) {
					assertTrue(true);


				} catch (Exception e) {
					e.toString();
				// TODO Auto-generated catch block
					fail();

				} finally {
					sut.close();
				}
			
			   } 
	@Test
	//sut.createRide:  The Driver("Driver Test") HAS one ride "from" "to" in that "date". 
	public void test4() {


        
		String driverUsername="Driver Test";
		String driverPassword="123";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		try {
					
			 driver=new Driver(driverUsername,driverPassword);
			 driver.addRide(rideFrom, rideTo, rideDate, 2, 10);
			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver.getUsername())).thenReturn(driver);
		
			
			//invoke System Under Test (sut)  
			sut.open();
		    sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);
			sut.close();
			
			fail();
			
		   } catch (RideAlreadyExistException e) {
			 //verify the results
				sut.close();
				assertTrue(true);
			} catch (RideMustBeLaterThanTodayException e) {
			// TODO Auto-generated catch block
			fail();
		} 
	} 
	@Test
	
	//sut.createRide:  The Driver("Driver Test") HAS  NOT one ride "from" "to" in that "date". 
	// and the Ride must be created in DB
	//The test supposes that the "Driver Test" does not exist in the DB before the test
	public void test5() {
		//define parameters
		String driverUsername="Driver Test";
		String driverPassword="123";
		
		
		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			Driver driver1=new Driver(driverUsername,driverPassword);

			//configure the state through mocks 
	        Mockito.when(db.find(Driver.class, driver1.getUsername())).thenReturn(driver1);
					
			//invoke System Under Test (sut)  
			sut.open();
			Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);
			sut.close();
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

			fail();
			//redone state of the system (create object in the database)
			
		} 
	} 
	

}