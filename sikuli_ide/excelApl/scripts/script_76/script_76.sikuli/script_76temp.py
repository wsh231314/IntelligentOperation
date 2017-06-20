import threading

def wait_close_pop():
    captureNow()
    wait(1)
    type(Key.ENTER)

if Env.isLockOn(Key.CAPS_LOCK):
    type(Key.CAPS_LOCK)

try:
    initEvidence()
    import xlrd
    data = xlrd.open_workbook('C:/Users/shawn.shaohua.wang/git/autoScreen/sikuli_ide/excelApl/evidences/scripts/script_76/20170529/20170529172238/data_20170529172253.xls')
    sheet = data.sheets()[0]
    intDataStartRow = 0
    for i in range(1):
        iColumn = 1
        openApp('C:/work/worksoft/WinSCP/WinSCP.exe')
        closeApp('C:/work/worksoft/WinSCP/WinSCP.exe')
    timer = threading.Timer(3, wait_close_pop)
    timer.start()
    mail_by_id('MAIL_1')
    popup('the end! \n this information will disappear after 3 seconds.')
except Exception, ex:
    timer = threading.Timer(3, wait_close_pop)
    timer.start()
    mail_by_id('MAIL_1')
    popup('error! \n this information will disappear after 3 seconds.')
finally:
    endEvidence()
