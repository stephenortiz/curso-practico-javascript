package com.pollosbucanero.wsclient;

import com.pollosbucanero.wsclient.modelo.Articulo;
import com.pollosbucanero.wsclient.wsdl.ConfirmSalesOrderV4;
import com.pollosbucanero.wsclient.wsdl.Customer;
import com.pollosbucanero.wsclient.wsdl.Entity;
import com.pollosbucanero.wsclient.wsdl.GetCustomerItemPriceV2;
import com.pollosbucanero.wsclient.wsdl.ItemGroupCustomer;
import com.pollosbucanero.wsclient.wsdl.PricingV2;
import com.pollosbucanero.wsclient.wsdl.ProcessSOCustomerV2;
import com.pollosbucanero.wsclient.wsdl.ProcessSODetailBillingV2;
import com.pollosbucanero.wsclient.wsdl.ProcessSODetailProcessing;
import com.pollosbucanero.wsclient.wsdl.ProcessSODetailProductV2;
import com.pollosbucanero.wsclient.wsdl.ProcessSODetailV3;
import com.pollosbucanero.wsclient.wsdl.ProcessSOHeaderProcessing;
import com.pollosbucanero.wsclient.wsdl.ProcessSOHeaderV4;
import com.pollosbucanero.wsclient.wsdl.ProcessSalesOrderV4;
import com.pollosbucanero.wsclient.wsdl.Product;
import com.pollosbucanero.wsclient.wsdl.ShowCustomerItemPriceV2;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class ClienteOrdenesVenta extends WebServiceGatewaySupport {
  
  public ShowCustomerItemPriceV2 getPrecios(String businessUnit, Integer itemId, Integer customerId, String unitOfMeasureCodePricing) {
    GetCustomerItemPriceV2 request = new GetCustomerItemPriceV2();
    
    request.setBusinessUnit(businessUnit);
    
    Entity customerEntity = new Entity();
    customerEntity.setEntityId(customerId);
    
    ItemGroupCustomer item = new ItemGroupCustomer();
    item.setItemId(itemId);
    
    Product producto = new Product();
    producto.setBusinessUnit(businessUnit);
    producto.setItem(item);
    producto.setShipTo(customerEntity);
    
    request.setProduct(producto);
    
    Customer customer = new Customer();
    customer.setSoldTo(customerEntity);
    customer.setShipTo(customerEntity);
    
    request.setCustomer(customer);
    
    request.setUnitOfMeasureCodePricing(unitOfMeasureCodePricing);

		JAXBElement response = (JAXBElement)getWebServiceTemplate().marshalSendAndReceive(
				request,
				new Header());

    return (ShowCustomerItemPriceV2)response.getValue();
	}
  
  public ConfirmSalesOrderV4 procesarPedido(String businessUnit, Integer customerId, List<Articulo> articulos) throws DatatypeConfigurationException {
    
    ProcessSalesOrderV4 request = new ProcessSalesOrderV4();
    
    DatatypeFactory dtf = DatatypeFactory.newInstance();
    
    XMLGregorianCalendar dateRequested = dtf.newXMLGregorianCalendar(new GregorianCalendar());
    
    ProcessSODetailProcessing detailProcessing = new ProcessSODetailProcessing();
    detailProcessing.setActionType("A");
    
    ProcessSOHeaderProcessing headerProcessing = new ProcessSOHeaderProcessing();
    headerProcessing.setActionType("A");
    headerProcessing.setProcessingVersion("JDE0098");
    
    Entity customerEntity = new Entity();
    customerEntity.setEntityId(customerId);
    
    ProcessSOCustomerV2 customer = new ProcessSOCustomerV2();
    customer.setCustomer(customerEntity);
    
    ProcessSOHeaderV4 header = new ProcessSOHeaderV4();
    header.setBusinessUnit(businessUnit);
    
    for(Articulo articulo : articulos) {
      ItemGroupCustomer item = new ItemGroupCustomer();
      item.setItemId(articulo.getCodigo());
      
      ProcessSODetailProductV2 product = new ProcessSODetailProductV2();
      product.setItem(item);
      
      PricingV2 pricing = new PricingV2();
      pricing.setUnitOfMeasureCodePricing(articulo.getUnidadMedidaPrecio());
      
      ProcessSODetailBillingV2 billing = new ProcessSODetailBillingV2();
      billing.setPricing(pricing);
      
      ProcessSODetailV3 detail = new ProcessSODetailV3();
      detail.setBusinessUnit(businessUnit);
      detail.setDateRequested(dateRequested);
      detail.setProcessing(detailProcessing);
      detail.setProduct(product);
      detail.setQuantityOrdered(BigDecimal.ONE);
      detail.setBilling(billing);
      
      header.getDetail().add(detail);
    }
    
    header.setProcessing(headerProcessing);
    header.setShipTo(customer);
    header.setSoldTo(customer);
    
    request.setHeader(header);
    
    JAXBElement response = (JAXBElement)getWebServiceTemplate().marshalSendAndReceive(
				request,
				new Header());

    return (ConfirmSalesOrderV4)response.getValue();
    
  }

}
