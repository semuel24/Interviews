package machine.loader;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

public class MachineClient
{
    private WebTarget target;

    public WebTarget getTarget()
    {
        return target;
    }

    public void setTarget(WebTarget target)
    {
        this.target = target;
    }
 
    public CallResult call(String _mediaType) {
        if (target == null) {
            return null;
        }

        String rt = target
                .path("/api/").request()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get(String.class);

        
        ObjectMapper mapper = new ObjectMapper();
        CallResult result = null;
        try {
        	result = mapper.readValue(rt, CallResult.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
    }
    
    
    
}
