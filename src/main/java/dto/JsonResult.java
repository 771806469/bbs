package dto;


public class JsonResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String message;
    private Object data;
    private String state;

    public JsonResult() {}

    public JsonResult(String message) {
        this.state = SUCCESS;
        this.message = message;
    }

    public JsonResult(Object data) {
        this.state = SUCCESS;
        this.data = data;
    }



    public JsonResult(Object data, String state) {
        this.data = data;
        this.state = SUCCESS;
    }

    public JsonResult(String message, String state) {
        this.message = message;
        this.state = ERROR;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
