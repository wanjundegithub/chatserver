drop database if exists `chatDB`;
create database `chatDB`;
use `chatDB`;
drop table if exists `users`;
create table `users`
(
`userID` int(11) primary key not null auto_increment comment "用户ID",
`userName` varchar(20) not null comment "用户名",
`passWord` varchar(20) not null comment "密码",
`sex` varchar(10) not null comment "性别",
`age` int(4) not null comment "年龄",
`signature` varchar(30) not null comment "签名",
unique index `userNameIndex`(`userName`)
)engine=InnoDB,charset=utf8;
drop table if exists `friends`;
create table `friends`
(
`friendID` int(11) primary key not null auto_increment comment "好友ID",
`userID` int(11) not null comment "用户ID",
`userFriendID` int(11) not null comment "用户好友ID",
`remark` varchar(20) not null comment "好友备注",
index `userFriendIndex`(`userID`,`userFriendID`)
) engine=InnoDB,charset=utf8;
insert into `users` values(null,"Smith",1000,"male",18,"Java"),(null,"Bob",1001,"male",18,"C#"),
(null,"Jack",1002,"male",19,"C"),(null,"Kate",1003,"female",18,"C++"),(null,"Alice",1004,"female",19,"Python"),
(null,"Even",1005,"male",18,"Matlab");
insert into `friends` values(null,1,2,"Bob"),(null,1,3,"Jack"),(null,1,4,"Kate"),(null,1,6,"Even"),
(null,2,1,"Smith"),(null,2,3,"Jack"),(null,2,5,"Alice"),
(null,3,1,"Smith"),(null,3,2,"Bob"),(null,3,4,"Kate"),(null,3,6,"Even");
