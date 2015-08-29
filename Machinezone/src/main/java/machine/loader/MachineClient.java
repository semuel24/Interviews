package machine.loader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

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

        CallResult rt = target
                .path("/haha").request()
//                .header("APIKey", appImportApiKey)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .get(CallResult.class);

        return rt;
    }
}
