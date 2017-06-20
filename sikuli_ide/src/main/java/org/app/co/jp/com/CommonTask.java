package org.app.co.jp.com;

import java.util.TimerTask;

import org.app.co.jp.util.BasicLogger;
import org.app.co.jp.util.RunHelp;

public class CommonTask extends TimerTask {
	
	String strTempScriptPath;
	
	String strTempEvidencePath;
	
	String strTempEndMail;
	
	String strTempErrorMail;
	
	BasicLogger logger = BasicLogger.getLogger();
	
	RunHelp help;
	
	boolean testTempExec = false;
	
	String strTempJobId;
	
	CommonTask(RunHelp help, String strJobId, String strScriptPath, String strEvidencePath, boolean testExec, String strEndMail, String strErrorMail) {
		super();
		this.help = help;
		strTempJobId= strJobId;
		strTempScriptPath = strScriptPath;
		strTempEvidencePath = strEvidencePath;
		strTempEndMail = strEndMail;
		strTempErrorMail = strErrorMail;
		testTempExec = testExec;
	}

	@Override
	public void run() {
		try {
			help.runCurrentScript(strTempScriptPath, strTempEvidencePath, testTempExec, strTempEndMail, strTempErrorMail);
			ScheduleCommon.removeJobById(strTempJobId, "Z");
		} catch(Exception e) {
			try {
				ScheduleCommon.removeJobById(strTempJobId, "4");
			} catch (Exception e1) {
				logger.exception(e1);
			}
		}
	}
}