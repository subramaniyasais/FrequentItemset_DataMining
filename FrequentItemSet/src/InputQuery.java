import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

import javax.sound.midi.Soundbank;

public class InputQuery  extends P2 implements Runnable  {
	public void run() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String input_query = new String("");
		try {
			while(true) {
				try {
					input_query = input.readLine().trim();
					if(input_query != null && !input_query.equals("")) {
					    LinkedList<Integer> current_data = new LinkedList<Integer>();
					    LinkedList<Bucket> current_buckets = new LinkedList<Bucket>();
					    int current_time_stamp = 0;
						System.out.println(input_query);
						String num = input_query.substring(36, input_query.length()-2);
						String var[] = num.split(" ");
						int k = Integer.parseInt(var[0]);
						String tempstr = "What is the number of ones for last "+ k + " data?";
						if(!tempstr.equals(input_query)) {
							System.out.println("Please enter a valid Query!");
							continue;
						}

						lock.acquire();
						current_data = data;
						current_time_stamp = time_stamp;
						current_buckets = buckets;
						lock.release();
						int count = 0;
						
						if(k < current_data.size() || current_data.size() < window_size) {
							if((current_time_stamp - k) > 0) {
								for(int i=current_data.size()-1; i >= (current_data.size()-k); i--) {
									if(current_data.get(i) == 1) {
										count++;
									}
								}
							} else {
								for(int i=1; i<=current_time_stamp; i++) {
									if(current_data.get(current_data.size()-i) == 1) {
										count++;
									}
								}
							}
							System.out.println("The number of ones of last "+k+" data is exact "+count+".");
						}
						else /*if((current_time_stamp - k) > 0)*/ {
							current_time_stamp = current_time_stamp - k;
							int i;
							for(i=current_buckets.size()-1; i>0; i--) {
								Bucket bucket = current_buckets.get(i);
								if(current_time_stamp < bucket.get_time_stamp()) {
									if(current_time_stamp > current_buckets.get(i-1).get_time_stamp()) {
										count = count + (bucket.get_bucket_size() / 2);
										break;
									} else {
										count = count + bucket.get_bucket_size();
									}
								} else if(current_time_stamp == bucket.get_time_stamp()) {
									count++;
									break;
								}
							}
							if(i == 0) {
								count = count + (current_buckets.get(i).get_bucket_size() / 2);
							}
							System.out.println("The number of ones of last "+k+" data is estimated "+count+".");
						}						
					} else {
						continue;
					}
					
				} catch (Exception io) {
					System.out.println("Please enter a valid Query!");
					continue;
				}
			}
			
		}catch (Exception e) {
			System.out.println("Please enter a valid Query!");
			System.exit(0);
		}
	}

}
