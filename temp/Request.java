
public class Request {

	private int processNo;
	private int timeStamp;
	private boolean responded = false;
	
	public Request(int processNo,int timeStamp) {
		this.processNo = processNo;
		this.timeStamp = timeStamp;
	}
	
	public boolean isResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}

	public int getProcessNo() {
		return processNo;
	}
	public void setProcessNo(int processNo) {
		this.processNo = processNo;
	}
	public int getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public boolean equals(Request r){
		return (r.getProcessNo()==processNo);
	}
	
}
