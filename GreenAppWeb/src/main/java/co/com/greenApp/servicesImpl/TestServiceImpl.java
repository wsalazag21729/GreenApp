package co.com.greenApp.servicesImpl;

import co.com.greenApp.configuracion.BaseService;
import co.com.greenApp.services.TestService;

/**
 *
 * @author AddaxT
 */

public class TestServiceImpl extends BaseService implements TestService{

    @Override
    public String getGreeting() {
        return "Hola";
    }
}