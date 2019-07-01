package services.provide.microservices;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.MultiValueMap;

import services.provide.client.ApiClient;
import services.provide.dao.Application;
import services.provide.dao.Bridge;
import services.provide.dao.Connector;
import services.provide.dao.Contract;
import services.provide.dao.Network;
import services.provide.dao.Transaction;
import services.provide.dao.Wallet;
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
            if (!result.get(0).equals("204")) throw new ProvideServicesException("ERROR: deleteConnector for id; " + connectorId + "; " + result.get(1));
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

    
    
    public Network fetchNetworkDetails(String networkId) throws ProvideServicesException{
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkDetails networkId cannot be null; ");
        Network network = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchNetworkDetails; " + result.get(1));
            network = mapper.readValue(result.get(1), Network.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return network;
    }
    

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

    
    
    public Bridge[] fetchNetworkBridges(String networkId) throws ProvideServicesException
    {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkBridges networkId cannot be null; ");
        Bridge[] bridges = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId + "/bridges", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchNetworkBridges; " + result.get(1));
            bridges = mapper.readValue(result.get(1), Bridge[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return bridges;
        
    }
    

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

    public Contract fetchNetworkContract(String networkId, String contractId) throws ProvideServicesException {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkContrac networkId is null;");
        if (contractId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkContract contractId is null;");
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

    public Transaction[] fetchNetworkTransactions(String networkId) throws ProvideServicesException {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkTransactions networkId is null;");
        Transaction[] transactions = null;
        try {
            ArrayList<String> result = client.get("networks/" +networkId+ "/transactions", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetchNetworkTransactions;");
            transactions = mapper.readValue(result.get(1), Transaction[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return transactions;
    }

    public Transaction fetchNetworkTransaction(String networkId, String txId) throws ProvideServicesException {
        if (networkId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkTransaction networkId is null;");
        if (txId == null)
            throw new ProvideServicesException("ERROR: fetchNetworkTransaction txId is null;");
        Transaction transaction = null;
        try {
            ArrayList<String> result = client.get("networks/" + networkId + "/transactions/" + txId, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetchNetworkTransaction; " + result.get(1));
            transaction = mapper.readValue(result.get(1), Transaction.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return transaction;
    }

    // TODO: Network Nodes
 
    public Transaction[] fetchTransactions() throws ProvideServicesException {
        Transaction[] transactions = null;
        try {
            ArrayList<String> result = client.get("transactions", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetch transactions;");
            transactions = mapper.readValue(result.get(1), Transaction[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return transactions;
    }

    public Transaction fetchTransactionDetails(String txId) throws ProvideServicesException {
        if (txId == null) throw new ProvideServicesException("ERROR: fetchTransactionDetails txId cannot be null;");
        Transaction transaction = null;
        try {
            ArrayList<String> result = client.get("transactions/"+txId,null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: Failed to fetch transaction ; " + txId);
            transaction = mapper.readValue(result.get(1), Transaction.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }
        return transaction;
    }

    public Bridge[] fetchBridges() throws ProvideServicesException {
        
        Bridge[] bridges = null;
        try {
            ArrayList<String> result = client.get("bridges", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchBridges; " + result.get(1));
            bridges = mapper.readValue(result.get(1), Bridge[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return bridges;

    }

    public Bridge fetchBridge(String bridgeId) throws ProvideServicesException {
        if (bridgeId == null)
            throw new ProvideServicesException("ERROR: fetchBridge bridgeId cannot be null; ");
        Bridge bridge = null;
        try {
            ArrayList<String> result = client.get("bridges/"+bridgeId, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchBridge; " + result.get(1));
            bridge = mapper.readValue(result.get(1), Bridge.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return bridge;

    }

    public Bridge createBridges(Bridge bridge) throws ProvideServicesException {
        if (bridge == null)
            throw new ProvideServicesException("ERROR: createBridge bridge cannot be null; ");
        Bridge newBridge = null;
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, bridge);
            jsonWriter.flush();
            ArrayList<String> result = client.post("bridges", jsonWriter.toString());
            if (!result.get(0).equals("201"))
                throw new ProvideServicesException("ERROR: fetchBridges; " + result.get(1));
            newBridge = mapper.readValue(result.get(1), Bridge.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return newBridge;

    }


    public Wallet[] fetchWallets() throws ProvideServicesException
    {
        Wallet[] wallets = null;
        try {
            ArrayList<String> result = client.get("wallets", null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchWallets; " + result.get(1));
            wallets = mapper.readValue(result.get(1), Wallet[].class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return wallets;
    }

    public Wallet fetchWalletBalance(String walletId) throws ProvideServicesException
    {
        if (walletId == null)
            throw new ProvideServicesException("ERROR: fetchWalletDetailBalance walletId cannot be null; ");
        Wallet wallet = null;
        try {
            ArrayList<String> result = client.get("wallets/" + walletId, null);
            if (!result.get(0).equals("200"))
                throw new ProvideServicesException("ERROR: fetchWalletDetailBalance; " + result.get(1));
            wallet = mapper.readValue(result.get(1), Wallet.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return wallet;
    }

    public Wallet createWallet(Wallet wallet) throws ProvideServicesException {
        if (wallet == null)
            throw new ProvideServicesException("ERROR: createWallet wallet cannot be null; ");
        Wallet newWallet = null;
        try {
            Writer jsonWriter = new StringWriter();
            mapper.writeValue(jsonWriter, wallet);
            jsonWriter.flush();
            ArrayList<String> result = client.post("wallets", jsonWriter.toString());
            if (!result.get(0).equals("201"))
                throw new ProvideServicesException("ERROR: createWallet; " + result.get(1));
            newWallet = mapper.readValue(result.get(1), Wallet.class);
        } catch (Exception e) {
            throw new ProvideServicesException(e.getLocalizedMessage());
        }

        return newWallet;
    }
}