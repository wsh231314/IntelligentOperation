import smtplib
from email.mime.text import MIMEText
from email.header import Header

sender = 'hjpq0@163.com'
receiver = 'hjpq0@163.com'  
subject = 'python is ok'  
smtpserver = 'smtp.163.com'  
username = 'hjpq0@163.com'  
password = 'Wsh_19850213'  
  
msg = MIMEText('hello!','text','utf-8')
msg['Subject'] = Header(subject, 'utf-8')  
  
smtp = smtplib.SMTP()  
smtp.connect('smtp.163.com')  
smtp.login(username, password)  
smtp.sendmail(sender, receiver, msg.as_string())  
smtp.quit()  
