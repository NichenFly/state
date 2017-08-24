#!/bin/bash
#第一个参数是ip地址,第二个是用户,第三个是密码
mysql -h"$1" -u"$2" -p"$3" -e"show slave status\G" | grep -E "Slave_IO_State|Running|Last_SQL_Error|Last_IO_Error"
