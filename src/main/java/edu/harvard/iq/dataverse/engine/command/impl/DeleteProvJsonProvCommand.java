package edu.harvard.iq.dataverse.engine.command.impl;

import edu.harvard.iq.dataverse.DataFile;
import edu.harvard.iq.dataverse.authorization.Permission;
import edu.harvard.iq.dataverse.dataaccess.StorageIO;
import edu.harvard.iq.dataverse.engine.command.AbstractCommand;
import edu.harvard.iq.dataverse.engine.command.CommandContext;
import edu.harvard.iq.dataverse.engine.command.DataverseRequest;
import edu.harvard.iq.dataverse.engine.command.RequiredPermissions;
import edu.harvard.iq.dataverse.engine.command.exception.CommandException;
import edu.harvard.iq.dataverse.engine.command.exception.IllegalCommandException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

@RequiredPermissions(Permission.EditDataset)
public class DeleteProvJsonProvCommand extends AbstractCommand<JsonObjectBuilder> {

    private static final Logger logger = Logger.getLogger(PersistProvJsonProvCommand.class.getCanonicalName());

    private final DataFile dataFile;

    public DeleteProvJsonProvCommand(DataverseRequest aRequest, DataFile dataFile) {
        super(aRequest, dataFile);
        this.dataFile = dataFile;
    }

    @Override
    public JsonObjectBuilder execute(CommandContext ctxt) throws CommandException {
        
        JsonObjectBuilder response = Json.createObjectBuilder();

        final String provJsonExtension = "prov-json.json"; //MAD: This is hardcoded and should be global

        try {
            StorageIO<DataFile> dataAccess = dataFile.getStorageIO();
            dataAccess.deleteAuxObject(provJsonExtension);             //MAD: do we need to do anything else other than catch things?

            response.add("message", "PROV-JSON provenance data deleted.");
            return response;
        } catch (IOException ex) {
            String error = "Exception caught deleting provenance aux object: " + ex;
            throw new IllegalCommandException(error, this);
        }
    }

}