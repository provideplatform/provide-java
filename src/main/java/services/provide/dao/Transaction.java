package services.provide.dao;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Transaction {
    private String id;
    private String created_at;
    private String application_id;
    private String user_id;
    private String network_id;
    private String wallet_id;
    private String to;
    private int value;
    private String data;
    private String hash;
    private String status;
    private String params;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Trace traces;
    private String ref;
    private String description;
    private int block;
    private String block_timestamp;
    private String broadcast_at;
    private String finalized_at;
    private String published_at;
    private int published_latency;
    private int broadcast_latency;
    private int e2e_latency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNetwork_id() {
        return network_id;
    }

    public void setNetwork_id(String network_id) {
        this.network_id = network_id;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Trace getTraces() {
        return traces;
    }

    public void setTraces(Trace traces) {
        this.traces = traces;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getBlock_timestamp() {
        return block_timestamp;
    }

    public void setBlock_timestamp(String block_timestamp) {
        this.block_timestamp = block_timestamp;
    }

    public String getBroadcast_at() {
        return broadcast_at;
    }

    public void setBroadcast_at(String broadcast_at) {
        this.broadcast_at = broadcast_at;
    }

    public String getFinalized_at() {
        return finalized_at;
    }

    public void setFinalized_at(String finalized_at) {
        this.finalized_at = finalized_at;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public int getPublished_latency() {
        return published_latency;
    }

    public void setPublished_latency(int published_latency) {
        this.published_latency = published_latency;
    }

    public int getBroadcast_latency() {
        return broadcast_latency;
    }

    public void setBroadcast_latency(int broadcast_latency) {
        this.broadcast_latency = broadcast_latency;
    }

    public int getE2e_latency() {
        return e2e_latency;
    }

    public void setE2e_latency(int e2e_latency) {
        this.e2e_latency = e2e_latency;
    }

    @Override
    public String toString() {
        return "Transaction [application_id=" + application_id + ", block=" + block + ", block_timestamp="
                + block_timestamp + ", broadcast_at=" + broadcast_at + ", broadcast_latency=" + broadcast_latency
                + ", created_at=" + created_at + ", data=" + data + ", description=" + description + ", e2e_latency="
                + e2e_latency + ", finalized_at=" + finalized_at + ", hash=" + hash + ", id=" + id + ", network_id="
                + network_id + ", params=" + params + ", published_at=" + published_at + ", published_latency="
                + published_latency + ", ref=" + ref + ", status=" + status + ", to=" + to + ", traces=" + traces
                + ", user_id=" + user_id + ", value=" + value + ", wallet_id=" + wallet_id + "]";
    }

    

}

class Trace {
    private TraceResult[] result;

    public TraceResult[] getResult() {
        return result;
    }

    public void setResult(TraceResult[] result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Trace [result=" + Arrays.toString(result) + "]";
    }

    
}

class TraceResult {
    private String blockHash;
    private int blockNumber;
    private String error;
    private int subtraces;
    //private String traceAddress
    private String transactionHash;
    private int transactionPosition;
    private String type;
    private Action action;
    private Result result;

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getSubtraces() {
        return subtraces;
    }

    public void setSubtraces(int subtraces) {
        this.subtraces = subtraces;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public int getTransactionPosition() {
        return transactionPosition;
    }

    public void setTransactionPosition(int transactionPosition) {
        this.transactionPosition = transactionPosition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "TraceResult [action=" + action + ", blockHash=" + blockHash + ", blockNumber=" + blockNumber
                + ", error=" + error + ", result=" + result + ", subtraces=" + subtraces + ", transactionHash="
                + transactionHash + ", transactionPosition=" + transactionPosition + ", type=" + type + "]";
    }

    

}

class Action {
    private String callType;
    private String from;
    private String gas;
    private String init;
    private String input;
    private String to;
    private String value;

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Action [callType=" + callType + ", from=" + from + ", gas=" + gas + ", init=" + init + ", input="
                + input + ", to=" + to + ", value=" + value + "]";
    }

    
}

class Result {
    private String address;
    private String code;
    private String gasUsed;
    private String output;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGasUsed() {
        return gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Result [address=" + address + ", code=" + code + ", gasUsed=" + gasUsed + ", output=" + output + "]";
    }

    
}