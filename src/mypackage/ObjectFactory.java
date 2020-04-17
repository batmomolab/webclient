
package mypackage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mypackage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RetourneDistance_QNAME = new QName("http://SOAP/", "retourneDistance");
    private final static QName _RetourneDistanceResponse_QNAME = new QName("http://SOAP/", "retourneDistanceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RetourneDistance }
     * 
     */
    public RetourneDistance createRetourneDistance() {
        return new RetourneDistance();
    }

    /**
     * Create an instance of {@link RetourneDistanceResponse }
     * 
     */
    public RetourneDistanceResponse createRetourneDistanceResponse() {
        return new RetourneDistanceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetourneDistance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOAP/", name = "retourneDistance")
    public JAXBElement<RetourneDistance> createRetourneDistance(RetourneDistance value) {
        return new JAXBElement<RetourneDistance>(_RetourneDistance_QNAME, RetourneDistance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetourneDistanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SOAP/", name = "retourneDistanceResponse")
    public JAXBElement<RetourneDistanceResponse> createRetourneDistanceResponse(RetourneDistanceResponse value) {
        return new JAXBElement<RetourneDistanceResponse>(_RetourneDistanceResponse_QNAME, RetourneDistanceResponse.class, null, value);
    }

}
