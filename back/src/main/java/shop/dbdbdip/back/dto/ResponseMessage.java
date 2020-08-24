package shop.dbdbdip.back.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class ResponseMessage {

private static final String DEFAULT_KEY="result";
	
	private int code;
	private String message = null;
	private String status = null;
	private String timestamp;
	private Map<String, Object> data;
	
	public ResponseMessage(HttpStatus status) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datetime = new Date();
		
		this.code = status.value();
		this.message = status.getReasonPhrase();
		this.status = (status.isError()) ? "failure" : "success";
		this.data = new HashMap<> ();
		this.timestamp =  format.format(datetime);
	}
	
	public ResponseMessage(HttpStatus status, Object result) {
		this(status);
		this.data.put(DEFAULT_KEY, result);
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
	public void add(String key, Object obj) {
		this.data.put(key, obj);
	}
}
