--------------------
SET NOCOUNT ON

DECLARE 
@userId bigint

PRINT '--------begin--------'

DECLARE myCur CURSOR FOR 
 select id from sUserInfos

OPEN myCur

FETCH NEXT FROM myCur 
INTO @userId
 
WHILE @@FETCH_STATUS = 0
BEGIN
  
 IF(select count(*) from sUserRole where user_id = @userId and ROLE_ID=3 )=0 
 
 BEGIN
   INSERT INTO sUserRole(user_id,ROLE_ID)
   VALUES (@userId, 3);
    INSERT INTO tUserMenu(userInfo,deptMenuTemplate)
   VALUES (@userId, 2);
   PRINT @userId
 END
 PRINT @userId
 
   -- Get the next author.
   FETCH NEXT FROM myCur 
   INTO @userId

END
PRINT '-------end --------'

CLOSE myCur
DEALLOCATE myCur