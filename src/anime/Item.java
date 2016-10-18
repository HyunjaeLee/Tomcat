package anime;

public class Item implements Comparable<Item>{
	
	private String title;
	private String url;
	private String src;
	
	public Item() {
		super();
	}
	
	public Item(String title, String url, String src) {
		super();
		this.title = title;
		this.url = url;
		this.src = src;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
	@Override
	public int compareTo(Item o) {
		return this.title.compareTo(o.getTitle());
	}
	
}
