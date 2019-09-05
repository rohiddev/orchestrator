


create table orchestration(
id bigint auto_increment, 
processname varchar(255),
status varchar(255),
jobid integer,
refid integer,
result varchar(10000),
error varchar(512)


);
insert into orchestration(processname, status, jobid,refid, result, error) 
values('hello', '1', 1, 2, 'test', 'error');

