import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.regex.Pattern;

public class FrequentItemSet {
    public static LinkedList<Integer> data = new LinkedList<Integer>();
    public static LinkedList<Bucket> buckets = new LinkedList<Bucket>();
	public static int  window_size=20;
	public static int  time_stamp=0;
	public static String[] server_host_port;
    public static Semaphore lock = new Semaphore(1);
    public static Socket socketStream;
    public static BufferedReader brin;
	public static void main(String[] args) {
		Thread stream_thread = new Thread(new Inputstream());
		Thread query_thread = new Thread(new InputQuery());
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String userinput = new String("");

	    final Pattern IP_P = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
		try{
			userinput = reader.readLine();
            while (userinput.startsWith("#")) {
            	userinput = reader.readLine();
            }
            window_size = Integer.parseInt(userinput.trim());

            if(window_size <= 0) {
            	System.out.println("Please enter a valid window size!");
            	System.exit(0);
            }
            userinput = reader.readLine();
            while (userinput.startsWith("#")) {
            	userinput = reader.readLine();
            }
            server_host_port = userinput.trim().split(":");
            if((Integer.parseInt(server_host_port[1]) <1024) || (Integer.parseInt(server_host_port[1]) > 65535) ) {
                System.out.println("Please enter a valid port number!");
                System.exit(0);
            }

            String ip_addr = server_host_port[0];
            boolean check;
            try{
                if (ip_addr.matches(".*[a-z].*")) {
                    InetAddress IP_Address = InetAddress.getByName(ip_addr);
                    ip_addr = IP_Address.toString();
                    ip_addr = ip_addr.split("/")[1];
                }
                if (!IP_P.matcher(ip_addr).matches()) {
                    System.out.println("Please enter a valid host name!");
                    System.exit(0);
                }
            } catch(Exception e) {
                System.out.println("Please enter a valid host name!");
                System.exit(0);
            }

		}catch (IOException io) {
			System.out.println();
            System.exit(0);
		}
		try{
			socketStream = new Socket(server_host_port[0], Integer.parseInt(server_host_port[1]));
			brin = new BufferedReader(new InputStreamReader(socketStream.getInputStream()));
		}catch (Exception e) {
            System.exit(0);
		}
		stream_thread.start();
		query_thread.start();
		try{
		Thread.sleep(1);
		}catch (Exception e) {
		}
	}

}
