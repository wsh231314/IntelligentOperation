import threading

def wait_close_pop():
    captureNow()
    wait(1)
    type(Key.ENTER)

if Env.isLockOn(Key.CAPS_LOCK):
    type(Key.CAPS_LOCK)

try:
    initEvidence()
    re = keyDown(Key.F5)
    popup(re)
    timer = threading.Timer(3, wait_close_pop)
    timer.start()
    popup('the end! \n this information will disappear after 3 seconds.')
except Exception, ex:
    timer = threading.Timer(3, wait_close_pop)
    timer.start()
    popup('error! \n this information will disappear after 3 seconds.')
finally:
    endEvidence()
