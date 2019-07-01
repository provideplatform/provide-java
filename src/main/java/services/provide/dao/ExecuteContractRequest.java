package services.provide.dao;

import java.util.LinkedList;
import java.util.List;

public class ExecuteContractRequest {
    private String network_id;
    private String app_id;
    private String wallet_id;
    private String to;
    private int value;
    private String data;
    private List<String> params;
    private String nonce;
    private String method;


    public ExecuteContractRequest() {
        this.params = new LinkedList<String>();
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public void addParam(String param)
    {
        this.params.add(param);
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ExecuteContractRequest [app_id=" + app_id + ", data=" + data + ", method=" + method + ", network_id="
                + network_id + ", nonce=" + nonce + ", params=" + params + ", to=" + to + ", value=" + value
                + ", wallet_id=" + wallet_id + "]";
    }

    
}