package services.provide.dao;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContractParams {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ABI[] abi;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String[] argv;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String constructor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lang = "solidity";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String raw_source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String wallet_id;

    public ContractParams() {
    }

        public ABI[] getAbi() {
        return abi;
    }

    public void setAbi(ABI[] abi) {
        this.abi = abi;
    }

    public String[] getArgv() {
        return argv;
    }

    public void setArgv(String[] argv) {
        this.argv = argv;
    }

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaw_source() {
        return raw_source;
    }

    public void setRaw_source(String raw_source) {
        this.raw_source = raw_source;
    }

    public String getWallet_id() {
        return wallet_id;
    }

    public void setWallet_id(String wallet_id) {
        this.wallet_id = wallet_id;
    }

    @Override
    public String toString() {
        return "ContractParams [abi=" + Arrays.toString(abi) + ", argv=" + Arrays.toString(argv) + ", constructor="
                + constructor + ", lang=" + lang + ", name=" + name + ", raw_source=" + raw_source + ", wallet_id="
                + wallet_id + "]";
    }

    

    
}