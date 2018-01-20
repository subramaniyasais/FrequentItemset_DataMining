
public class Bucket {
	private int time_stamp;
	private int bucket_size;
	
	protected Bucket(int time_stamp, int bucket_size) {
		this.time_stamp = time_stamp;
		this.bucket_size = bucket_size;
	}
	
	protected int get_time_stamp() {
		return this.time_stamp;
	}
	
	protected void set_time_stamp(int time_stamp) {
		this.time_stamp = time_stamp;
	}
	
	protected int get_bucket_size() {
		return this.bucket_size;
	}
	
	protected void set_bucket_size(int bucket_size) {
		this.bucket_size = bucket_size;
	}
	
}
