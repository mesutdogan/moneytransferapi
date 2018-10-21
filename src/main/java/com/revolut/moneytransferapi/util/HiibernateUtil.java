package com.revolut.moneytransferapi.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HiibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			configureSessionFactory();
		}
		return sessionFactory;

	}

	private static void configureSessionFactory() {
		sessionFactory = new Configuration()
				.configure()
				.buildSessionFactory();
	}
}
