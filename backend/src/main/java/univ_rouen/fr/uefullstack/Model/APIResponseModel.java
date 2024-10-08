package univ_rouen.fr.uefullstack.Model;

public class APIResponseModel<T> {
    private String message;
    private boolean result;
    private T data;

    public APIResponseModel(String message, boolean result, T data) {
        this.message = message;
        this.result = result;
        this.data = data;
    }

    // Getters et setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}