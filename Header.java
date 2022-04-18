package com.pollosbucanero.wsclient;

import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.saaj.SaajSoapMessage;

public class Header implements WebServiceMessageCallback {

  @Override
  public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {

    try {
      SOAPMessage soapMessage = ((SaajSoapMessage)webServiceMessage).getSaajMessage();

      SOAPHeader header = soapMessage.getSOAPHeader();
      SOAPHeaderElement security = header.addHeaderElement(new QName("http://docs.oasis-open.org/wss/2004/01/secext", "Security", "wsse"));
      security.setMustUnderstand(false);
      SOAPElement usernameToken = security.addChildElement("UsernameToken", "wsse");
      SOAPElement username = usernameToken.addChildElement("Username", "wsse");
      SOAPElement password = usernameToken.addChildElement("Password", "wsse");

      username.setTextContent("NCRUZ");

      password.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
      password.setTextContent("123negro");
    }
    catch(Exception e) {
    }

  }
}
