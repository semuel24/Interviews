package machine.loader;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.FactoryBean;

/**
 * @version $Id$
 */
public class WebTargetFactory implements FactoryBean<WebTarget>
{
    private String serverURL;

    public String getServerURL()
    {
        return serverURL;
    }

    public void setServerURL(String serverURL)
    {
        this.serverURL = serverURL;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    @Override
    public WebTarget getObject() throws Exception
    {
        WebTarget target = ClientBuilder.newClient().register(JacksonFeature.class).target(serverURL);
        return target;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class< ? > getObjectType()
    {

        return WebTarget.class;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    @Override
    public boolean isSingleton()
    {
        return true;
    }

}
