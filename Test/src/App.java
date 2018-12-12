
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.core.*;
 


public class App {
//	private static final Logger logger = LogManager.getLogger(App.class);
	protected final String scheduleTm = "15:25:35:55";
	protected final String fixingTm = "9:11:14:16:17:18:20:21:22";
	protected AtomicBoolean toStart = new AtomicBoolean ();
	protected Worker worker;

	// protected static List<String> RunAt = Arrays.asList();

	public App() {
		
// add comment
	}

	public static void main(String[] args) {

		App app = new App();
		app.run();

	}

	protected void run() {
		TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
		toStart.set(false);
		worker = new Worker(toStart);
		
		worker.start();
		
		for (int idx=0; idx <10; idx++){
			
			toStart.set(true);
			worker.work("2018");
			
			
		}
		
		
		
		String[] fixingList = fixingTm.split(":");
		String[] scheduleList = scheduleTm.split(":");

		for (int id = 0; id < fixingList.length; id++) {
			long mlSec = 0;
			for (int idx = 0; idx < scheduleList.length; idx++) {
				Calendar runAt = Calendar.getInstance();
				Calendar now = Calendar.getInstance();
				toStart.set(false);


				now.set(2018, 2,22,22,50);
				int nowHour = now.get(Calendar.HOUR_OF_DAY);
				int lastHour = Integer.parseInt(fixingList[fixingList.length-1]);
				
				int yr = now.get(Calendar.YEAR);
				int mm = now.get(Calendar.MONTH);
				int dt = now.get(Calendar.DATE);
				
				if (nowHour >= lastHour){
					runAt.add(Calendar.HOUR, (24-lastHour)+1);
					yr = runAt.get(Calendar.YEAR);
					mm = runAt.get(Calendar.MONTH);
					dt = runAt.get(Calendar.DATE);
				}				
				
				runAt.set(yr,  
						mm, 
						dt, 
						Integer.parseInt(fixingList[id]),
						Integer.parseInt(scheduleList[idx]), 0);
				
				System.out.println("Next Schedule Time "+ runAt.getTime());
				
				if ((runAt.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))
					runAt.add(Calendar.HOUR, 48);
				else if ((runAt.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
					runAt.add(Calendar.HOUR, 24);
				
				//System.out.println("Next Schedule Time "+ runAt.getTime());
				//System.out.println(now.getTime());
				
				if (runAt.after(now)) {
					mlSec = runAt.getTime().getTime() - now.getTime().getTime();					
					System.out.println("Next Schedule Time "+ runAt.getTime()+"/"+mlSec);					
				
					try {
						//long diffMin = mlSec / (60 * 1000) % 60;
						System.out.println("Sleep for "+ mlSec);
						Thread.sleep(mlSec);
						toStart.set(true);
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
							
						worker.work(sdf.format(runAt.getTime()));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}

	}

}
