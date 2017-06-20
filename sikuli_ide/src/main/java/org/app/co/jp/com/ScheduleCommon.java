package org.app.co.jp.com;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import org.app.co.jp.dao.JobListDao;
import org.app.co.jp.util.RunHelp;

public class ScheduleCommon {
	
	private static Timer timer;
	
	public static Map<String, CommonTask> allJobMap = new HashMap<String, CommonTask> ();
	
	private static RunHelp help;
	
	public static void init() {
		if (help == null) {
			help = new RunHelp();
		}
		if (timer == null) {
			timer = new Timer();
		}
	}
	
	public static void addToSchedule(Date date, String strJobId, String strScriptPath, String strEvidencePath, boolean testExec, String strEndMail, String strErrorMail) {
		
		CommonTask task = new CommonTask(help, strJobId, strScriptPath, strEvidencePath, testExec, strEndMail, strErrorMail);
		
		// add task to schedule
		timer.schedule(task, date);
		
		// add task to list
		allJobMap.put(strJobId, task);
		
	}
	
	public static void removeJobById(String strJobId, String strStatus) throws Exception {
		if (allJobMap.containsKey(strJobId)) {
			// stop task
			CommonTask task = allJobMap.get(strJobId);
			task.cancel();
			
			// delete from map
			allJobMap.remove(strJobId);
			
		}
		// 
		JobListDao dao = new JobListDao();
		dao.updateStatus(strJobId, strStatus);
	}
}
