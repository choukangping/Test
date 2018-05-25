import java.util.concurrent.atomic.AtomicBoolean;

public class Worker extends Thread {
	protected AtomicBoolean toStart;

	public Worker(AtomicBoolean toStart){
		this.toStart = toStart;
	}
	
	@Override
	public void run(){
		while(true){
			try {
//				long diffMin = mlSec / (60 * 1000) % 60;
//				System.out.println("Sleep for "+ diffMin);
				Thread.sleep(60000);
//				toStart.set(true);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public void work(String fixing){
		if (toStart.get()){
			System.out.println("***********Work*********"+fixing+"50");				
		}
	}

}
