package com.projects.tcpserver.webservice.mongodb.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.1.6
 * 2017-04-29T17:04:17.868+02:00
 * Generated source version: 3.1.6
 * 
 */
@WebService(targetNamespace = "http://mongodb.webservice.projects.com/", name = "BackendWS")
@XmlSeeAlso({ObjectFactory.class})
public interface BackendWS {

    @WebMethod
    @RequestWrapper(localName = "getMessages", targetNamespace = "http://mongodb.webservice.projects.com/", className = "com.projects.tcpserver.webservice.mongodb.client.GetMessages")
    @ResponseWrapper(localName = "getMessagesResponse", targetNamespace = "http://mongodb.webservice.projects.com/", className = "com.projects.tcpserver.webservice.mongodb.client.GetMessagesResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<com.projects.tcpserver.webservice.mongodb.client.MessageContainer> getMessages(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "storeMessages", targetNamespace = "http://mongodb.webservice.projects.com/", className = "com.projects.tcpserver.webservice.mongodb.client.StoreMessages")
    @ResponseWrapper(localName = "storeMessagesResponse", targetNamespace = "http://mongodb.webservice.projects.com/", className = "com.projects.tcpserver.webservice.mongodb.client.StoreMessagesResponse")
    public void storeMessages(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.projects.tcpserver.webservice.mongodb.client.MessageContainer> arg0
    );
}
