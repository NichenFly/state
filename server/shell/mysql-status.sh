#!/bin/bash
# shell -h 主机 -P 端口 -u 用户 -p 密码 -e 执行语句
while getopts "h:P:u:p:e:" opt;
do
	case $opt in 
	h)
		h=$OPTARG
		;;
	P)
		P=$OPTARG
		;;
	u)
		u=$OPTARG
		;;
	p)
		p=$OPTARG
		;;
	e)
		e=$OPTARG
		;;
	esac
done

mysql -h"$h" -P"$P" -u"$u" -p"$p" -e"$e"