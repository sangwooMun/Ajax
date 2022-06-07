package co.edu;

public class Schedule {
	private String title;
	private String start;
	private String end;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return start;
	}
	public void setStartDate(String startDate) {
		this.start = startDate;
	}
	public String getEndDate() {
		return end;
	}
	public void setEndDate(String endDate) {
		this.end = endDate;
	}
	@Override
	public String toString() {
		return "Schedule [title=" + title + ", startDate=" + start + ", endDate=" + end + "]";
	}
	
	
}
