import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;
import testOperations.TestDataAccess;
import domain.Driver;

public class CreateRideBDWhiteTest {

	 //sut:system under test
	 static DataAccess sut=new DataAccess();
	 
	 //additional operations needed to execute the test 
	 static TestDataAccess testDA=new TestDataAccess();

	@SuppressWarnings("unused")
	private Driver driver; 

	@Test
	//sut.createRide:  The Driver("Urtzi") HAS one ride "from" "to" in that "date". 
	public void test1() {
		
		String driverUsername="Urtzi";
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
		boolean existDriver=false;
		try {
			
			//define parameters
			
			
			//configure the state of the system (create object in the database)
			testDA.open();
			 existDriver=testDA.existDriver(driverUsername);
			testDA.addDriverWithRide(driverUsername, rideFrom, rideTo, rideDate, 0, 0);
			testDA.close();			
			
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
		} finally {
				  //Remove the created objects in the database (cascade removing)   
				testDA.open();
				  if (existDriver) 
					  testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
				  else 
					  testDA.removeDriver(driverUsername);
		          testDA.close();
		        }
		   } 

	@Test
	//sut.createRide:  The Driver("Driver Test") HAS NOT one ride "from" "to" in that "date". 
	public void test2() {
		//define paramaters
		String driverUsername="Driver Test";
		String driverPassword="123";

		String rideFrom="Donostia";
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		
		boolean driverCreated=false;

		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			//Check if exist this ride for this driver, and if exist, remove it.
			
			testDA.open();
			if (!testDA.existDriver(driverUsername)) {
				testDA.createDriver(driverUsername,null);
			    driverCreated=true;
			}
			
			boolean b=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
			if (b) {testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
				System.out.println("Se lo borro");
			}
			testDA.close();
			
			
			//invoke System Under Test (sut)  
			sut.open();
			Ride ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);
			

			sut.close();
			//verify the results
			assertNotNull(ride);
			assertEquals(ride.getFrom(),rideFrom);
			assertEquals(ride.getTo(),rideTo);
			assertEquals(ride.getDate(),rideDate);
			
			//ride is in DB
			testDA.open();
			boolean existRide=testDA.existRide(driverUsername,ride.getFrom(), ride.getTo(), ride.getDate());
				
			assertTrue(existRide);
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// if the program goes to this point fail  
			fail();
			//reestablish the state of the system (create object in the database)
			
			
			} catch (RideMustBeLaterThanTodayException e) {
				// if the program goes to this point fail  

			fail();
			
		} finally {

			testDA.open();
			//reestablish the state of the system (create object in the database)

			if (driverCreated) 
				testDA.removeDriver(driverUsername);

			testDA.close();	      
		        }
		   } 
	
	
	@Test
	//sut.createRide:  The Driver is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.
		public void test3() {
		Ride ride=null;
			try {
				
				//define parameters
				driver=null;

				String rideFrom="Donostia";
				String rideTo="Zarautz";
				
				String driverUsername=null;

				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date rideDate=null;;
				try {
					rideDate = sdf.parse("05/10/2026");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
				
				//invoke System Under Test (sut)  
				sut.open();
			    ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);

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
				// TODO Auto-generated catch block
					fail();
					
				} finally {
					sut.close();
				}
					   }
	@Test
	//sut.createRide:  The ride "from" is null. The test must return null. If  an Exception is returned the createRide method is not well implemented.

	//This method detects a fail in createRide method because the method does not check if the parameters are null, and the ride is created.

	public void test4() {
		boolean driverCreated=false;
		String driverUsername="Test driver";
		String rideFrom=null;
		String rideTo="Zarautz";
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/10/2026");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		Ride ride=null;
		
		testDA.open();
		if (!testDA.existDriver(driverUsername)) {
			testDA.createDriver(driverUsername,null);
		    driverCreated=true;
		}
		
		testDA.close();
		try {
			//invoke System Under Test (sut)  
			sut.open();
			 ride=sut.createRide(rideFrom, rideTo, rideDate, 0, 0, driverUsername);
			sut.close();			
			
			//verify the results
			System.out.println(ride);
			assertNull(ride);
			
			//q is in DB
			testDA.open();
			boolean exist=testDA.existRide(driverUsername,rideFrom, rideTo, rideDate);
				
			assertTrue(!exist);
			testDA.close();
			
		   } catch (RideAlreadyExistException e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (RideMustBeLaterThanTodayException e) {

			// TODO Auto-generated catch block
			fail();
			}  catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
			}
		
		
		finally {   

			testDA.open();
			if (driverCreated) 
				testDA.removeDriver(driverUsername);
			
			testDA.close();
			
		        }
		   }
		   
}
