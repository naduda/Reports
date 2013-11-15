package ua.pr.model;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.internal.SessionImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import ua.pr.model.orm.Account;
import ua.pr.model.orm.ClassV;
import ua.pr.model.orm.Connection;
import ua.pr.model.orm.Device;
import ua.pr.model.orm.LinkAccount;
import ua.pr.model.orm.LstConnection;
import ua.pr.model.orm.Meter;
import ua.pr.model.orm.Soket;
import ua.pr.model.orm.Substation;
import ua.pr.model.orm.TypeMeter;

public class ModelDB {
	
	private static SessionFactory sFactory;
	public static Session session;
	public static java.sql.Connection conn;
	
	public ModelDB() {
		try {
			Properties prop= new Properties();
			prop.setProperty("hibernate.connection.url", "jdbc:sqlserver://46.201.240.87:1433;databaseName=KPVP");
			prop.setProperty("hibernate.connection.username", "ukreni");
			prop.setProperty("hibernate.connection.password", "pfdpbgdq");
			prop.setProperty("dialect", "org.hibernate.dialect.SQLServerDialect");
			prop.setProperty("hibernate.show_sql", "true");
			
			Configuration cfg = new Configuration();
			cfg.setProperties(prop);
			cfg.addAnnotatedClass(Substation.class);
			cfg.addAnnotatedClass(Account.class);
			cfg.addAnnotatedClass(LinkAccount.class);
			cfg.addAnnotatedClass(Meter.class);
			cfg.addAnnotatedClass(LstConnection.class);
			cfg.addAnnotatedClass(TypeMeter.class);
			cfg.addAnnotatedClass(Connection.class);
			cfg.addAnnotatedClass(Device.class);
			cfg.addAnnotatedClass(Soket.class);
			cfg.addAnnotatedClass(ClassV.class);
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(prop).buildServiceRegistry();
			
			sFactory = cfg.buildSessionFactory(serviceRegistry);
			session = sFactory.openSession();
			conn = (java.sql.Connection) ((SessionImpl)session).connection();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Substation> allSubstations() {
		List<Substation> result = null;
		
		result = session.createQuery("select s from Substation s where s.idSubstation > 0").list();
		return result;
	}
	
	public Substation getSubstationById(int idSubstation) {
		Substation result = null;
		
		result = (Substation) session.get(Substation.class, new Integer(idSubstation));
		return result;
	}
	
//	@SuppressWarnings("unchecked")
//	public List<Account> getAccountByIdSubstation(int idSubstation) {
//		List<Account> result = null;
//		result = session.createQuery("select a from Account a where a.IdSubstation = 1").list();
//		return result;
//	}
}
