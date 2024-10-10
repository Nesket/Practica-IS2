package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

import java.util.logging.Logger;

public class TestDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();
	private static final Logger logger = Logger.getLogger(TestDataAccess.class.getName());

	public TestDataAccess() {

		logger.info("TestDataAccess created");

		// open();

	}

	public void open() {

		String fileName = c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
		logger.info("TestDataAccess opened");

	}

	public void close() {
		db.close();
		logger.info("TestDataAccess closed");
	}

	public boolean removeDriver(String name) {
		logger.info(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, name);
		if (d != null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else {
			logger.info("Driver with username = "+name+" was not removed.");
			return false;
		} 
			
    }

	public Driver createDriver(String name, String pass) {
		logger.info(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(name,pass);
				db.persist(driver);
				db.getTransaction().commit();
				logger.info("Driver with username = "+name+ " created.");
			}
			catch (Exception e){
				logger.info("Error occurred creating driver: "+ e.getMessage());
			}
			return driver;
    }


	public boolean existDriver(String email) {
		return db.find(Driver.class, email) != null;

	}
		
	public Driver addDriverWithRide(String name, String from, String to, Date date, int nPlaces, float price) {
		logger.info(">> TestDataAccess: addDriverWithRide");
		Driver driver = null;
		db.getTransaction().begin();
		try {
			driver = db.find(Driver.class, name);
			if (driver == null) {
				logger.info("Entra en null");
				driver = new Driver(name, null);
				db.persist(driver);
			}
			driver.addRide(from, to, date, nPlaces, price);
			db.getTransaction().commit();
			logger.info("Driver created " + driver);
	
			return driver;
	
		} catch (Exception e) {
			logger.info("Error occurred: " + e.getMessage());
		}
		return null;
	}		
		
		public boolean existRide(String name, String from, String to, Date date) {
			logger.info(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				logger.info("Ride from "+from+ " to "+ to+ " on "+date.toString()+" exists.");
				return d.doesRideExists(from, to, date);
			} else {
				logger.info("Ride from "+from+ " to "+ to+ " on "+date.toString()+" does not exist.");
				return false;
			} 
			
		}
		public Ride removeRide(String name, String from, String to, Date date ) {
			logger.info(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				logger.info("created rides" +d.getCreatedRides());
				return r;
			} else {
				logger.info("Ride from "+from+ " to "+ to+ " on "+date.toString()+" was not removed.");
				return null;
			}

		}

		public void setMoneyToDriver(double money, Driver driver) {
			logger.info(">> TestDataAccess: setMoneyToDriver");
			db.getTransaction().begin();

			try {
				driver.setMoney(money);
				db.getTransaction().commit();
				logger.info("Driver's money updated to " + money);
			} catch (Exception e) {
				logger.info("Error occurred setting money to driver: "+ e.getMessage());
			}
		}
		
		public Driver getDriver(String name) {
			logger.info(">> TestDataAccess: getDriver");
			Driver driver = null;
			db.getTransaction().begin();
			try {
				driver = db.find(Driver.class, name);
				return driver;

			} catch (Exception e) {
				logger.info("Error occurred getting driver: "+ e.getMessage());
			}
			return null;
	}

	
		
				
		public Ride createRide(String from, String to, Date date, int nPlaces, double price, Driver driver) {
			logger.info(">> TestDataAccess: createRide");
			Ride ride=null;
			db.getTransaction().begin();
			try {
			    ride=new Ride(from, to, date, nPlaces, price, driver);
				db.persist(ride);
				db.getTransaction().commit();
			}
			catch (Exception e){
				logger.info("Error occurred creating ride: "+ e.getMessage());
			}
			return ride;
		}
		
		public boolean existTraveler(String username) {
			logger.info(">> TestDataAccess: existTraveler");
			 return  db.find(Traveler.class, username)!=null;
		}
		
		
		public boolean removeTraveler(String username) {
			logger.info(">> TestDataAccess: removeTraveler");
			Traveler t = db.find(Traveler.class, username);
			if (t!=null) {
				db.getTransaction().begin();
				db.remove(t);
				db.getTransaction().commit();
				logger.info("Traveler with username = "+username+ " removed.");
				return true;
			} else {
				logger.info("Traveler with username = "+username+" was not removed.");
				return false;
			} 
				
	    }
		public Traveler createTraveler(String username, String passwd) {
			logger.info(">> TestDataAccess: createTraveler");
			Traveler traveler=null;
			db.getTransaction().begin();
			try {
				traveler=new Traveler(username, passwd);
				db.persist(traveler);
				db.getTransaction().commit();
				logger.info("Traveler with username = "+username+ " created.");
			}
			catch (Exception e){
				logger.info("Error occurred creating traveler: "+ e.getMessage());
			}
			return traveler;
		}
		
		public void setMoneyToTraveler(double money, Traveler traveler) {
			logger.info(">> TestDataAccess: setMoneyToTraveler");
			db.getTransaction().begin();
			try {
				traveler.setMoney(money);
				db.getTransaction().commit();
				logger.info("Traveler's money updated to " + money);
			} catch (Exception e) {
				logger.info("Error occurred setting money to traveler: "+ e.getMessage());
			}
		}
		
		public Ride addRideToDriver(Driver driver, String rideFrom, String rideTo, Date rideDate, int nPlaces, float price) {
			logger.info(">> TestDataAccess: addRideToDriver");
				db.getTransaction().begin();
				try {
				    Ride ride = driver.addRide(rideFrom, rideTo, rideDate, nPlaces, price);
				    db.persist(ride);
				    db.getTransaction().commit();
					logger.info("Ride added to driver with username = "+driver.getUsername());
					
					return ride;
					
				}
				catch (Exception e){
					logger.info("Error occurred adding ride to driver: "+ e.getMessage());
				}
				return null;
	    }
	}

