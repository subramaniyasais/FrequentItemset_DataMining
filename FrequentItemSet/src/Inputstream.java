import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Iterator;

public class Inputstream extends P2 implements Runnable  {
	public void run() {
		try {
			while(true){
				int temp = (int) brin.readLine().charAt(0);
				Bucket bucket;
				int input_bit = Character.getNumericValue(temp);
				System.out.print(input_bit);
				if(data.size() >= window_size) {
					data.removeFirst();
				}
				time_stamp++;
				if(input_bit == 1) {
					bucket = new Bucket(time_stamp, 1);
					buckets.add(bucket);
					bucket_check();
				}
				data.add(input_bit);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bucket_check() throws InterruptedException {
		
		Iterator<Bucket> it = buckets.iterator();
		Bucket temp1 = null, temp2 = null, temp3 = null, temp4 = null;
		if(it.hasNext()) {
			temp1 = it.next();
		}
		
		while(it.hasNext()) {
			temp2 = it.next();
			if(temp1.get_bucket_size() != temp2.get_bucket_size()) {
				temp1 = temp2;
				continue;
			} else {
				if(it.hasNext()) {
					temp3 = it.next();
					if(temp2.get_bucket_size() != temp3.get_bucket_size()) {
						temp1 = temp3;
						continue;
					} else {
						if(it.hasNext()) {
							temp4 = it.next();
							if(temp3.get_bucket_size() != temp4.get_bucket_size()) {
								temp1 = temp4;
								continue;
							} else {
								temp2.set_bucket_size(temp2.get_bucket_size()*2);
								buckets.remove(temp1);
								bucket_check();
								break;
							}
						}
					}
				}
			}
				
		}
		return;
	}
}