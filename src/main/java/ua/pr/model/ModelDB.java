package ua.pr.model;

import java.io.Serializable;
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
import ua.pr.model.orm.PgdiFileStorage;
import ua.pr.model.orm.PgdiLink;
import ua.pr.model.orm.Soket;
import ua.pr.model.orm.Substation;
import ua.pr.model.orm.TypeMeter;
import ua.pr.model.orm.UserSetting;
import ua.pr.reports.xml.objects.Login;

public class ModelDB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Properties prop;
	transient private SessionFactory sFactory;
	transient private Session session;
	transient private java.sql.Connection conn;
	
	private String query;
	
	public ModelDB(Login login) {
		prop = new Properties();
		prop.setProperty("hibernate.connection.url", "jdbc:sqlserver://" + login.getServer() + ":1433;databaseName=" + login.getDB());
		prop.setProperty("hibernate.connection.username", login.getUser());
		prop.setProperty("hibernate.connection.password", login.getPassword());
		prop.setProperty("dialect", "org.hibernate.dialect.SQLServerDialect");
		prop.setProperty("hibernate.show_sql", "true");
	}
	
	public SessionFactory getsFactory() {
		try {
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
			cfg.addAnnotatedClass(UserSetting.class);
			cfg.addAnnotatedClass(PgdiLink.class);
			cfg.addAnnotatedClass(PgdiFileStorage.class);
			
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(prop).buildServiceRegistry();
			
			sFactory = cfg.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
		return sFactory;
	}

	public Session getSession() {
		session = getsFactory().openSession();
		return session;
	}

	public java.sql.Connection getConn() {
		if (session == null) {
			session = getSession();
		}
		conn = (java.sql.Connection) ((SessionImpl)session).connection();
		return conn;
	}

	@SuppressWarnings("unchecked")
	public List<Substation> allSubstations() {
		List<Substation> result = null;
		
		query = "select s from Substation s where s.idSubstation > 0 order by s.name";
		result = getSession().createQuery(query).list();
		return result;
	}
	
	public Substation getSubstationById(int idSubstation) {
		Substation result = null;
		
		result = (Substation) session.get(Substation.class, new Integer(idSubstation));
		return result;
	}
	
	public UserSetting getUser(String name) {
		UserSetting result = null;
		
		query = String.format("select s from UserSetting s where s.nameUser = '%s'", name);
		result = (UserSetting) getSession().createQuery(query).uniqueResult();
		return result;
	}
}
