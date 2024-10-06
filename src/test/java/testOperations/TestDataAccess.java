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

import java.util.logging.Logger;

public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();
	private static final Logger logger = Logger.getLogger(TestDataAccess.class.getName());

	public TestDataAccess()  {
		
		logger.info("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		logger.info("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		logger.info("TestDataAccess closed");
	}

	public boolean removeDriver(String name) {
		logger.info(">> TestDataAccess: removeDriver");
		Driver d = db.find(Driver.class, name);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
    }
	public Driver createDriver(String name, String pass) {
		logger.info(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(name,pass);
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				logger.info("Error occurred: "+ e.getMessage());
			}
			return driver;
    }
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Driver addDriverWithRide(String name, String from, String to,  Date date, int nPlaces, float price) {
			logger.info(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, name);
					if (driver==null) {
						logger.info("Entra en null");
						driver=new Driver(name,null);
				    	db.persist(driver);
					}
				    driver.addRide(from, to, date, nPlaces, price);
					db.getTransaction().commit();
					logger.info("Driver created "+driver);
					
					return driver;
					
				}
				catch (Exception e){
					logger.info("Error occurred: "+ e.getMessage());
				}
				return null;
	    }
		
		
		public boolean existRide(String name, String from, String to, Date date) {
			logger.info(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, name);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
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

			} else 
			return null;

		}

		public void setMoneyToDriver(double money, Driver driver) {
			System.out.println(">> TestDataAccess: setMoneyToDriver");
			db.getTransaction().begin();
			try {
				driver.setMoney(money);
				db.getTransaction().commit();
				System.out.println("Driver's money updated to " + money);
			} catch (Exception e) {
				logger.info("Error occurred: "+ e.getMessage());
			}
		}
		
}