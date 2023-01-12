package org.eclipse.tractusx.ssi.extensions.did.web.controler;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.tractusx.ssi.extensions.core.setting.SsiSettings;

@Produces({MediaType.APPLICATION_JSON})
@Path("./well-known/did.json")
public class DidWebDocumentController {

    private final SsiSettings settings;
    private final Monitor monitor;

    public DidWebDocumentController(SsiSettings settings, Monitor monitor) {
        this.settings = settings;
        this.monitor = monitor;
    }
    @GET
    public String request() {
        String didDoc = getClass().getResource("webdid/did-document.json").getFile();
        return didDoc; // TODO
    }
}
