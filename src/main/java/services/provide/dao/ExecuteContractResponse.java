package services.provide.dao;

public class ExecuteContractResponse {
    private String confidence;
    private String ref;

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "ExecuteContractResponse [confidence=" + confidence + ", ref=" + ref + "]";
    }

    
}