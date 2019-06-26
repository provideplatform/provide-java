package services.provide.client;

import io.ipfs.api.IPFS;
import io.ipfs.api.NamedStreamable;
import io.ipfs.api.NamedStreamable.ByteArrayWrapper;
import services.provide.helper.ProvideServicesException;

public class IPFSClient {

    private static final String DEFAULT_HOST = "pfs.provide.services";
    private static final Integer DEFAULT_PORT = 5001;
    private static final String DEFAULT_SCHEME = "http";
    private static final String DEFAULT_VERSION = "/api/v0/";

    private IPFS ipfs = null;

    public static IPFSClient init(String schema, String host, Integer port)
    {
        return new IPFSClient(schema, host, port);
    }

    private IPFSClient(String schema, String host, Integer port)
    {
        schema = (schema == null ? this.DEFAULT_SCHEME : schema);
        host = (host == null ? this.DEFAULT_HOST : host);
        port = (port == null ? this.DEFAULT_PORT : port);
        

        this.ipfs = new IPFS(host, port.intValue(), this.DEFAULT_VERSION);
    }

    public String add(String filename, byte[] bytes) throws ProvideServicesException
    {
        String hash = null;
        try {
            ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(filename, bytes);
            hash = ipfs.add(file).get(0).hash.toString();
        } catch(Exception e)
        {
            throw new ProvideServicesException("ERROR: Failed to add file to IPFS; " + e);
        }

        return hash;
    }
}