import java.util.*;
public class Demo {
	public static void main(String args[]) {
		Lamport lm = new Lamport();
		lm.calc();
	}
}

class Lamport{
	static int np, ne[] = new int[100];
	static Event ev[][] = new Event[100][100];
	static int et[][] = new int[100][100];
	public void calc(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of processes : ");
		np = scan.nextInt();
		System.out.println("Enter the number of events per process : ");
		for(int i = 0; i < np; i++)
			ne[i] = scan.nextInt();
		ev = new Event[100][];
		System.out.println("For each process and every event in process, enter the id of process that sends the message and time which it sends, if the event is a receving one. If event is not receiving one, press 0");
		for(int i = 0; i < np; i++){
			int k = 0;
			ev[i] = new Event[100];
			System.out.println("For process " + (i+1));
			for(int j = 0; j < ne[i]; j++){
				ev[i][j] = new Event();
				System.out.println("For event " + (j+1));
				int x = scan.nextInt();
				if(x != 0){
					int y = scan.nextInt();
					ev[i][j].set(x, y);
				}
			}
		}
		for(int i = 0; i < np; i++)
			et[i][0] = 1;
		
		Node []n = new Node[10];
		for(int i = 0; i < np; i++) {
			n[i] = new Node("Process " + (i+1), ne[i], ev[i]);
		}
	}
}
class Node implements Runnable{
	Event e[];
	int ne;
	String name;
	public Node(String name, int num, Event e[]) {
		Thread t = new Thread(this, name);
		this.name = name;
		this.e = new Event[100];
		this.e = e;
		this.ne = num;
		t.start();
	}
	public void run() {
		int timestep = 1;
		for(int i = 0; i < ne; i++) {
			Event x = e[i];
			if(x.getSendingProcess() == 0)
				System.out.println(name + ", event " + (i+1) + " at " + timestep);
			else {
				if(x.getSendingEventNo() >= timestep) {
					while(x.getSendingEventNo() >= timestep) {
						timestep ++;
						try {
							Thread.sleep(1000);
						}catch(InterruptedException e) {
							System.out.println("Interrupted Exception");
						}
					}
				}
				System.out.println(name + ", event " + (i+1) + " at " + timestep);
			}
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				System.out.println("InterruptedException");
			}
			timestep++;
		}
	}
}
class Event {
		private int sp, n;
		public Event(){		
			this.sp = 0;
			this.n = 0;
		}
		public  void set(int sp, int n){
			this.sp = sp;
			this.n = n;
		}
		public int getSendingProcess(){
			return this.sp;
		}
		public int getSendingEventNo(){
			return this.n;
		}
}

