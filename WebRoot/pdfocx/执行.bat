echo 安装文件
copy "ShowData.ocx" "c:\windows\system32\
copy "libmupdf.dll" "c:\windows\system32\
regsvr32.exe c:\windows\system32\ShowData.ocx
echo 退出
exit;