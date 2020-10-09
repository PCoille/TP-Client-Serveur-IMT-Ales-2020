rem syntax closeport [portnumber]  example closeport 23
set "port=0.0.0.0:%1"
for /f "tokens=5" %%a in ('netstat -aon ^| findstr "%port%" ^| findstr "LISTENING"') do taskkill /f /pid %%a