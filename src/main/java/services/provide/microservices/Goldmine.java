package services.provide.microservices;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import services.provide.client.ApiClient;
import services.provide.dao.Application;
import services.provide.dao.Connector;
import services.provide.dao.Contract;
import services.provide.dao.Network;
import services.provide.helper.ProvideServicesException;

public class Goldmine {
    private static final String DEFAULT_HOST = "goldmine.provide.services";
    private ApiClient client = null;
    private ObjectMapper mapper = null;

    public Goldmine init(String token)
    {
        return new Goldmine(token);
    }

    private Goldmine(String token)
    {
        String schema = System.getenv("GOLDMINE_API_SCEME");
        String host = System.getenv("GOLDMINE_API_HOST");

        if (host == null) host = this.DEFAULT_HOST;

        this.client = ApiClient.init(schema, host, token);

        // Set ObjectMapper
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public boolean validateToken(String token)
    {
        if (token.split("\\.").length == 3)
           return true;
        else
            return false;
    }

    public Application getApplication(String json) {
        return Application.init(json);
    }


    public ArrayList<String> fetchBridges() throws ProvideServicesException
    {
        return client.get("bridges", null);
    }

    public ArrayList<String> fetchBridgeDetails(String bridgeId) throws ProvideServicesException {
        return client.get("bridges/"+bridgeId, null);
    }

    public ArrayList<String> createBridge(String params) throws ProvideServicesException {
        return client.post("bridges", params);
    }

    public Connector[] fetchConnectors() throws ProvideServicesException{
        Connector[] connectors = null;
        try {
            ArrayList<String> result = client.get("connectors", null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: Failed to fetch connectors;");
            connectors = mapper.readValue(result.get(1), Connector[].class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return connectors;
    }

    /**
     * TODO: Need to follow up on why this does not exist?
     * public ArrayList<String> fetchConnectorDetails(String connectorId) { return
     * client.get("connectors/"+connectorId, null); }
     */


    public Connector createConnector(Connector connector) throws ProvideServicesException{
        if (connector == null) throw new ProvideServicesException("ERROR: create connector value cannot be null;");
        Connector createdConnector = null;
        try {
            Writer jsonWrite = new StringWriter();
            mapper.writeValue(jsonWrite, connector);
            jsonWrite.flush();
            ArrayList<String> result = client.post("connectors", jsonWrite.toString());
            if (!result.get(0).equals("201")) throw new ProvideServicesException("ERROR: failed to create connector; " + result.get(1));
            createdConnector = mapper.readValue(result.get(1), Connector.class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return createdConnector;
    }

    public void deleteConnector(String connectorId) throws ProvideServicesException {
        if (connectorId == null) throw new ProvideServicesException("ERROR: delete connector value cannot be null;");
        try {
            ArrayList<String> result = client.delete("connectors/"+connectorId, null);
            if (!result.get(0).equals("202")) throw new ProvideServicesException("ERROR: deleteConnector for id; " + connectorId + "; " + result.get(1));
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
    }

    public Contract[] fetchContracts() throws ProvideServicesException {
        Contract[] contracts = null;
        try {
            ArrayList<String> result = client.get("contracts", null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: Failed to fetch contracts;");
            contracts = mapper.readValue(result.get(1), Contract[].class);
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return contracts;
    }

    public Contract fetchContractDetails(String contractId) throws ProvideServicesException{
        Contract contract = null;
        try {
            ArrayList<String> result = client.get("contracts/"+contractId, null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: Failed to fetch contract detail;");
            contract = mapper.readValue(result.get(1), Contract.class);
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return contract;
    }

    public Contract createContract(Contract contract) throws ProvideServicesException {
        if (contract == null) throw new ProvideServicesException("ERROR: Contract cannot be null; ");
        Contract createdContract = null;
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, contract);
            jsonWriter.flush();
            ArrayList<String> result = client.post("contracts", jsonWriter.toString());
            if (!result.get(0).equals("201")) throw new ProvideServicesException("ERROR: createContract; " + result.get(1));
            createdContract = mapper.readValue(result.get(1), Contract.class);
        } catch(Exception e)
        {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return createdContract;
    }

    public ArrayList<String> executeContract(String contractId, MultiValueMap params) {
        return client.post("contracts/"+contractId+"/execute", params);
    }

    public Network[] fetchNetworks() throws ProvideServicesException {
        Network[] networks = null;
        try {
            ArrayList<String> result = client.get("networks", null);
            if (!result.get(0).equals("200")) throw new ProvideServicesException("ERROR: fetchNetworks; " + result.get(1));
            networks = mapper.readValue(result.get(1), Network[].class);
        } catch(Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return networks;
    }

    /*
    * Need to discuss with Kyle if we want to allow this
    public ArrayList<String> createNetwork(MultiValueMap params) {
        return client.post("networks", params);
    }
    */

    /*
    * Need to discuss with Kyle if we want to allow this. Very limited as of this writing
    public ArrayList<String> updateNetwork(String networkId, MultiValueMap params) {
        return client.put("networks/"+networkId, params);
    }
    */

    /*
    * Need to discuss with Kyle what level of detail we would want to expose
    public ArrayList<String> fetchNetworkDetails(String networkId) {
        return client.get("networks/"+networkId, null);
    }
    */

    /*
    * Returns a Page Not Found
    public ArrayList<String> fetchNetworkAccounts(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/accounts", params);
    }
    */

    /*
    * Returns a Page Not Found
    public ArrayList<String> fetchNetworkBlocks(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/blocks", params);
    }
    */

    /*
    * Need to discuss with Kyle just returns a blank page with HTML not a blank Json Array
    public ArrayList<String> fetchNetworkBridges(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/bridges", params);
    }
    */

    public Connector[] fetchNetworkConnectors(String networkId)  throws ProvideServicesException {
        if (networkId == null) throw new ProvideServicesException("ERROR: fetchNetworkConnectors networkId is null;");
        Connector[] connectors = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId + "/connectors", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetchNetworkConnectors;");
            connectors = mapper.readValue(result.get(1), Connector[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return connectors;
    }

    public Contract[] fetchNetworkContracts(String networkId) throws ProvideServicesException {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkContracts networkId is null;");
        Contract[] contracts = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId + "/contracts", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetch network contracts;");
            contracts = mapper.readValue(result.get(1), Contract[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return contracts;
    }

    public Contract fetchNetworkContractDetails(String networkId, String contractId) throws ProvideServicesException {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkContractDetail networkId is null;");
        if (contractId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkContractDetail contractId is null;");
        Contract contract = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId + "/contracts/" + contractId, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetch network contract detail;");
            contract = mapper.readValue(result.get(1), Contract.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return contract;
    }

    /*
    * TODO: Discuss with Kyle
    public ArrayList<String> fetchNetworkOracles(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/oracles", params);
    }
    */

    // TODO: Have kyle send example of Token Contract?
    public ArrayList<String> fetchNetworkTokens(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/tokens", params);
    }

    /*
    * TODO: Does not exist
    public ArrayList<String> network_transactions(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/transactions", params);
    }
    */

    /*
    * TODO: Does not exist
    public ArrayList<String> fetchNetworkTransactionDetails(String networkId, String transactionId) {
        return client.get("networks/"+networkId+"/transactions/"+transactionId, null);
    }
    */

    /*
    * TODO: Does not work
    public ArrayList<String> fetchNetworkStatus(String networkId) {
        return client.get("networks/"+networkId+"/status", null);
    }
    */

    // TODO: Get an example of this from Kyle
    /*
    public ArrayList<String> fetchNetworkNodes(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/nodes", params);
    }
    */

    // TODO: Get an example of this from Kyle
    /*
    public ArrayList<String> createNetworkNode(String networkId, MultiValueMap params) {
        return client.post("networks/"+networkId+"/nodes", params);
    }
    */

    /*
     * TODO: Get an example of this from Kyle public ArrayList<String>
     * fetchNetworkNodeDetails(String networkId, String nodeId) { return
     * client.get("networks/"+networkId+"/nodes/"+nodeId, null); }
     */

    /*
     * TODO: Get an example of this from Kyle public ArrayList<String>
     * fetchNetworkNodeLogs(String networkId, String nodeId) { return
     * client.get("networks/"+networkId+"/nodes/"+nodeId+"/logs", null); }
     */

    /*
     * TODO: Get an example of this from Kyle public ArrayList<String>
     * deleteNetworkNode(String networkId, String nodeId) { return
     * client.delete("networks/"+networkId+"/nodes/"+nodeId, null); }
     */

    /*
     * TODO: Need to discuss with Kyle
    public ArrayList<String> fetchOracles(MultiValueMap params) {
        return client.get("oracles", params);
    }

    public ArrayList<String> fetchOracleDetails(String oracleId) {
        return client.get("oracles/"+oracleId,null);
    }

    public ArrayList<String> createOracle(MultiValueMap params) {
        return client.post("oracles",params);
    }

    public ArrayList<String> fetchTokens(MultiValueMap params) {
        return client.get("tokens",params);
    }

    public ArrayList<String> fetchTokenDetails(String tokenId) {
        return client.get("tokens/"+tokenId,null);
    }

    public ArrayList<String> createToken(MultiValueMap params) {
        return client.post("tokens",params);
    }
    */


    public ArrayList<String> createTransaction(MultiValueMap params) {
        return client.post("transactions",params);
    }

    public ArrayList<String> fetchTransactions(MultiValueMap params) {
        return client.get("transactions",params);
    }

    public ArrayList<String> fetchTransactionDetails(String txId) {
        return client.get("transactions/"+txId,null);
    }

    public ArrayList<String> fetchWalletBalance(String walletId,String tokenId) {
        return client.get("wallets/"+walletId+"/balances/"+tokenId,null);
    }

    public ArrayList<String> fetchWallets(MultiValueMap params) {
        return client.get("wallets",null);
    }

    public ArrayList<String> fetchWalletDetails(String walletId) {
        return client.get("wallets/"+walletId,null);
    }

    public ArrayList<String> createWallet(MultiValueMap params) {
        return client.post("wallets",params);
    }
}