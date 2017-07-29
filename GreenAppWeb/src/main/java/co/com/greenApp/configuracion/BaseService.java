package co.com.greenApp.configuracion;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

/**
 *
 * @author wsalazar - wsalazar@ias.com.co
 */
public class BaseService {

    @Context
    private ServletContext servletContext;

    protected <T> T getContextAttribute(String attributeName) {
        Object attributeValue = servletContext.getAttribute(attributeName);
        if (attributeValue == null) {
            return null;
        }
        return (T) attributeValue;
    }
}
