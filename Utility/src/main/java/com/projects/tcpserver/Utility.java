package com.projects.tcpserver;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.projects.tcpserver.mongodb.MongoDBConnection;
import com.projects.tcpserver.mongodb.MongoDBManager;

public final class Utility {

	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	private static final Random Random = new Random();
	public static final String MongoDBManagerServletContextAttributeName = "MongoDBManager";
	public static final String BackendWSUsernameHeader = "backend_ws_username";
	public static final String BackendWSPasswordHeader = "backend_ws_password";
	public static final String SoapHeaderSequenceName = "sequence";
	public static final String SoapHeaderNamespaceURI = "http://github.com/nicolanardino/tcpmessaging";
	private static final GregorianCalendar GregorianCalendar = new GregorianCalendar();
	
	public static void shutdownExecutorService(final ExecutorService es, long timeout, TimeUnit timeUnit) throws InterruptedException {
		es.shutdown();
		if (!es.awaitTermination(timeout, timeUnit))
			es.shutdownNow();
		logger.info("Terminated ExecutorService "+es.toString());
	}
	
	public static Properties getApplicationProperties(final String propertiesFileName) throws FileNotFoundException, IOException {
		final Properties p = new Properties();
		try(final InputStream inputStream = ClassLoader.getSystemResourceAsStream(propertiesFileName)) {
			p.load(inputStream);
			return p;
		}
	}
	
	public static int getRandom(final int upperBound) {
		return Random.nextInt(upperBound);
	}
	
	public static String getSubString(final String sourceString, final MessageType messageType) {
		return sourceString.substring(sourceString.indexOf(messageType.getMessageDescription()) + messageType.getMessageDescription().length());
	}
	
	public static XMLGregorianCalendar getXMLGregorianCalendar(final Date date) throws DatatypeConfigurationException {
		GregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar);
	}
	
	public static String buildStringFromSoapMessage(final SOAPMessage soapMessage) {
		try(final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			soapMessage.writeTo(baos);
			return baos.toString();
		} catch (final IOException | SOAPException e) {
			logger.warn("Error while converting SOAP message to string.");
			return "";
		}
	}
	
	public static void setServletContextAttributes(final ServletContext sc) {
		sc.setAttribute(Utility.MongoDBManagerServletContextAttributeName, new MongoDBManager(new MongoDBConnection(sc.getInitParameter("mongodb_host"), 
				Integer.valueOf(sc.getInitParameter("mongodb_port")), sc.getInitParameter("mongodb_database_name")), 
				sc.getInitParameter("mongodb_collection_name")));
		sc.setAttribute(Utility.BackendWSUsernameHeader, sc.getInitParameter(Utility.BackendWSUsernameHeader));
		sc.setAttribute(Utility.BackendWSPasswordHeader, sc.getInitParameter(Utility.BackendWSPasswordHeader));
	}
	
	public static void setContextDestroyed(final ServletContext sc) {
		try {
			((MongoDBManager)sc.getAttribute(Utility.MongoDBManagerServletContextAttributeName)).close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
