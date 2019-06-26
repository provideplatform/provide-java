package services.provide.microservices;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import services.provide.client.ApiClient;
import services.provide.dao.Application;
import services.provide.dao.Contract;
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


    public ArrayList<String> fetchBridges(MultiValueMap params)
    {
        return client.get("bridges", params);
    }

    public ArrayList<String> fetchBridgeDetails(String bridgeId) {
        return client.get("bridges/"+bridgeId, null);
    }

    public ArrayList<String> createBridge(MultiValueMap params) {
        return client.post("bridges", params);
    }

    public ArrayList<String> fetchConnectors(MultiValueMap params) {
        return client.get("connectors", params);
    }

    public ArrayList<String> fetchConnectorDetails(String connectorId) {
        return client.get("connectors/"+connectorId, null);
    }

    public ArrayList<String> createConnector(MultiValueMap params) {
        return client.post("connectors", params);
    }

    public ArrayList<String> deleteConnector(String connectorId) {
        return client.delete("connectors/"+connectorId);
    }

    public Contract[] fetchContracts() throws ProvideServicesException {
        Contract[] contracts = null;
        try {
            ArrayList<String> result = client.get("contracts", null);
            if (!result.get(0).equals("202")) throw new ProvideServicesException("ERROR: Failed to fetch contracts;");
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
            if (!result.get(0).equals("202") throw new ProvideServicesException("ERROR: Failed to fetch contract detail;");
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
            if (!result.get(0).equals("202")) throw new ProvideServicesException("ERROR: createContract; " + result.get(1));
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

    public ArrayList<String> fetchNetworks(MultiValueMap params) {
        return client.get("networks", params);
    }

    public ArrayList<String> createNetwork(MultiValueMap params) {
        return client.post("networks", params);
    }

    public ArrayList<String> updateNetwork(String networkId, MultiValueMap params) {
        return client.put("networks/"+networkId, params);
    }

    public ArrayList<String> fetchNetworkDetails(String networkId) {
        return client.get("networks/"+networkId, null);
    }

    public ArrayList<String> fetchNetworkAccounts(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/accounts", params);
    }

    public ArrayList<String> fetchNetworkBlocks(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/blocks", params);
    }

    public ArrayList<String> fetchNetworkBridges(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/bridges", params);
    }

    public ArrayList<String> fetchNetworkConnectors(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/connectors", params);
    }

    public ArrayList<String> fetchNetworkContracts(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/contracts", params);
    }

    public ArrayList<String> fetchNetworkContractDetails(String networkId, String contractId) {
        return client.get("networks/"+networkId+"/contracts/"+contractId, null);
    }

    public ArrayList<String> fetchNetworkOracles(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/oracles", params);
    }

    public ArrayList<String> fetchNetworkTokens(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/tokens", params);
    }

    public ArrayList<String> network_transactions(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/transactions", params);
    }

    public ArrayList<String> fetchNetworkTransactionDetails(String networkId, String transactionId) {
        return client.get("networks/"+networkId+"/transactions/"+transactionId, null);
    }

    public ArrayList<String> fetchNetworkStatus(String networkId) {
        return client.get("networks/"+networkId+"/status", null);
    }

    public ArrayList<String> fetchNetworkNodes(String networkId, MultiValueMap params) {
        return client.get("networks/"+networkId+"/nodes", params);
    }

    public ArrayList<String> createNetworkNode(String networkId, MultiValueMap params) {
        return client.post("networks/"+networkId+"/nodes", params);
    }

    public ArrayList<String> fetchNetworkNodeDetails(String networkId, String nodeId) {
        return client.get("networks/"+networkId+"/nodes/"+nodeId, null);
    }

    public ArrayList<String> fetchNetworkNodeLogs(String networkId, String nodeId) {
        return client.get("networks/"+networkId+"/nodes/"+nodeId+"/logs", null);
    }

    public ArrayList<String> deleteNetworkNode(String networkId, String nodeId) {
        return client.delete("networks/"+networkId+"/nodes/"+nodeId, null);
    }

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