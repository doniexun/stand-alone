@Echo Off

Title 正在检测网络连通性 ……
Echo 正在检测网络连通性 ……

ping www.baidu.com | find "TTL=" || Exit /B 999


Title 网络连通性检测完毕
Echo 网络连通性检测完毕
Echo.
Echo.
