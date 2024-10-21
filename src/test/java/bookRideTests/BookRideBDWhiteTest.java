package bookRideTests;

import static org.junit.Assert.*;

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

public class BookRideBDWhiteTest {
	private static final Logger logger = Logger.getLogger(BLFacadeImplementation.class.getName());
	
	static DataAccess sut = new DataAccess();
    
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
	
	@SuppressWarnings("unused")
	private double initialUserMoney = 10;
	
	@SuppressWarnings("unused")
	private String driverUsername;
	
	@SuppressWarnings("unused")
	private String rideFrom;
	
	@SuppressWarnings("unused")
	private String rideTo;
	
	@SuppressWarnings("unused")
	private Date rideDate;
	
	private void setInitValues(String username, Ride ride, int seats, double desk) {
		this.username = username;
		this.ride = ride;
		this.seats = seats;
		this.desk = desk;
	}
	
	/*
	 * @Test public void test1() { // username equals null, enters in catch from
	 * bookRide() from the beginning, // should return false
	 * System.out.println("\n----- TEST 1 -----"); try {
	 * 
	 * 
	 * // Prepare parameters setInitValues(null, ride, 1, 0);
	 * 
	 * // Prepare existing driver and existing ride String fakePassword =
	 * "fakePassword"; driverUsername="Driver Test";
	 * 
	 * rideFrom="Donostia"; rideTo="Zarautz";
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * try { rideDate = sdf.parse("05/10/2026"); } catch (ParseException e) {
	 * logger.info("Error encountered: "+e.getMessage()); } testDA.open(); if
	 * (testDA.existDriver(driverUsername)) { testDA.removeDriver(driverUsername); }
	 * driver = testDA.createDriver(driverUsername, fakePassword);
	 * 
	 * int nPlaces = 2; float price = 10; ride =
	 * testDA.addRideToDriver(driverUsername, rideFrom, rideTo, rideDate, nPlaces,
	 * price); testDA.existRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.close();
	 * 
	 * // Invoke System Under Test sut.open();
	 * 
	 * boolean result = sut.bookRide(username, ride, seats, desk); sut.close();
	 * 
	 * // Check results assertFalse(result);
	 * 
	 * 
	 * } catch(Exception e){ logger.info("Error encountered: "+e.getMessage()); }
	 * finally { // Clean up: remove the created test data testDA.open();
	 * testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.removeDriver(driverUsername); testDA.close(); } }
	 * 
	 * @Test public void test2() { // user is not in the DB, should return false
	 * System.out.println("\n----- TEST 2 -----"); try { // Prepare parameters
	 * setInitValues("user2", ride, 1, 0);
	 * 
	 * // Prepare existing driver and existing ride String fakePassword =
	 * "fakepassword"; driverUsername="Driver Test";
	 * 
	 * rideFrom="Donostia"; rideTo="Zarautz";
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * try { rideDate = sdf.parse("05/10/2026"); } catch (ParseException e) {
	 * logger.info("Error encountered: "+e.getMessage()); } testDA.open(); if
	 * (testDA.existDriver(driverUsername)) { testDA.removeDriver(driverUsername); }
	 * driver = testDA.createDriver(driverUsername, fakePassword);
	 * 
	 * int nPlaces = 2; float price = 10; ride =
	 * testDA.addRideToDriver(driverUsername, rideFrom, rideTo, rideDate, nPlaces,
	 * price); testDA.existRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.close();
	 * 
	 * // Invoke System Under Test sut.open();
	 * 
	 * boolean result = sut.bookRide(username, ride, seats, desk); sut.close();
	 * 
	 * // Check results assertFalse(result);
	 * 
	 * } catch(Exception e){ logger.info("Error encountered: "+e.getMessage()); }
	 * finally { // Clean up: remove the created test data testDA.open();
	 * testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.removeDriver(driverUsername); testDA.close(); }
	 * 
	 * }
	 * 
	 * @Test public void test3() { // not enough seats available, should return
	 * false System.out.println("\n----- TEST 3 -----"); try { // Prepare parameters
	 * setInitValues("user1", ride, 12, 0); // Prepare existing user String
	 * fakePassword = "fakepassword"; testDA.open(); if
	 * (testDA.existTraveler(username)) { testDA.removeTraveler(username); }
	 * 
	 * traveler = testDA.createTraveler(username, fakePassword);
	 * testDA.setMoneyToTraveler(10, traveler);
	 * 
	 * // Prepare existing driver and existing ride driverUsername="Driver Test";
	 * 
	 * rideFrom="Donostia"; rideTo="Zarautz";
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * try { rideDate = sdf.parse("05/10/2026"); } catch (ParseException e) {
	 * logger.info("Error encountered: "+e.getMessage()); }
	 * 
	 * if (testDA.existDriver(driverUsername)) {
	 * testDA.removeDriver(driverUsername); } driver =
	 * testDA.createDriver(driverUsername, fakePassword);
	 * 
	 * int nPlaces = 2; float price = 10; ride =
	 * testDA.addRideToDriver(driverUsername, rideFrom, rideTo, rideDate, nPlaces,
	 * price); testDA.existRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.close();
	 * 
	 * // Invoke System Under Test sut.open();
	 * 
	 * boolean result = sut.bookRide(username, ride, seats, desk); sut.close();
	 * 
	 * // Check results assertFalse(result);
	 * 
	 * } catch(Exception e){ logger.info("Error encountered: "+e.getMessage()); }
	 * finally { // Clean up: remove the created test data testDA.open();
	 * testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.removeDriver(driverUsername); testDA.close(); }
	 * 
	 * }
	 * 
	 * @Test public void test4() { // traveler does not have enough money, should
	 * return false System.out.println("\n----- TEST 4 -----"); try { // Prepare
	 * parameters setInitValues("user3", ride, 1, 0); // Prepare existing user
	 * String fakePassword = "fakepassword"; testDA.open(); if
	 * (testDA.existTraveler(username)) { testDA.removeTraveler(username); }
	 * 
	 * traveler = testDA.createTraveler(username, fakePassword);
	 * testDA.setMoneyToTraveler(0, traveler);
	 * 
	 * // Prepare existing driver and existing ride driverUsername="Driver Test";
	 * 
	 * rideFrom="Donostia"; rideTo="Zarautz";
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * try { rideDate = sdf.parse("05/10/2026"); } catch (ParseException e) {
	 * logger.info("Error encountered: "+e.getMessage()); }
	 * 
	 * if (testDA.existDriver(driverUsername)) {
	 * testDA.removeDriver(driverUsername); } driver =
	 * testDA.createDriver(driverUsername, fakePassword);
	 * 
	 * int nPlaces = 2; float price = 10; ride =
	 * testDA.addRideToDriver(driverUsername, rideFrom, rideTo, rideDate, nPlaces,
	 * price); testDA.existRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.close();
	 * 
	 * // Invoke System Under Test sut.open();
	 * 
	 * boolean result = sut.bookRide(username, ride, seats, desk); sut.close();
	 * 
	 * // Check results assertFalse(result);
	 * 
	 * } catch(Exception e){ logger.info("Error encountered: "+e.getMessage()); }
	 * finally { // Clean up: remove the created test data testDA.open();
	 * testDA.removeRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.removeDriver(driverUsername); testDA.close(); }
	 * 
	 * }
	 * 
	 * @Test public void test5() { // everything correct, should return true
	 * System.out.println("\n----- TEST 1 -----"); try { // Prepare parameters
	 * setInitValues("user1", ride, 1, 0); // Prepare existing user String
	 * fakePassword = "fakepassword"; testDA.open(); if
	 * (testDA.existTraveler(username)) { testDA.removeTraveler(username); }
	 * 
	 * traveler = testDA.createTraveler(username, fakePassword);
	 * testDA.setMoneyToTraveler(10, traveler);
	 * 
	 * // Prepare existing driver and existing ride driverUsername="Driver Test";
	 * 
	 * rideFrom="Donostia"; rideTo="Zarautz";
	 * 
	 * SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * 
	 * 
	 * try { rideDate = sdf.parse("05/10/2026"); } catch (ParseException e) {
	 * logger.info("Error encountered: "+e.getMessage()); }
	 * 
	 * if (testDA.existDriver(driverUsername)) {
	 * testDA.removeDriver(driverUsername); } driver =
	 * testDA.createDriver(driverUsername, fakePassword);
	 * 
	 * int nPlaces = 2; float price = 10; ride =
	 * testDA.addRideToDriver(driverUsername, rideFrom, rideTo, rideDate, nPlaces,
	 * price); testDA.existRide(driverUsername, rideFrom, rideTo, rideDate);
	 * testDA.close();
	 * 
	 * // Invoke System Under Test sut.open();
	 * 
	 * boolean result = sut.bookRide(username, ride, seats, desk); sut.close();
	 * 
	 * // Check results assertTrue(result); testDA.open();
	 * testDA.existBooking(username, driverUsername, rideFrom, rideTo, rideDate,
	 * price); testDA.close();
	 * 
	 * } catch(Exception e){ logger.info("Error encountered: "+e.getMessage()); }
	 * finally { // Clean up: remove the created test data testDA.open();
	 * testDA.removeTraveler(username); testDA.removeRide(driverUsername, rideFrom,
	 * rideTo, rideDate); testDA.removeDriver(driverUsername); testDA.close(); }
	 * 
	 * }
	 */
}
