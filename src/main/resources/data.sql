--BEGIN
   INSERT INTO user_role (role_name)
   VALUES ("USER_ROLE");
--   WHERE NOT EXISTS ( SELECT * FROM user_role
--                   WHERE role_name = "USER_ROLE");
--END
--BEGIN
   INSERT INTO user_role (role_name)
   VALUES ("ADMIN_ROLE");
--   WHERE NOT EXISTS ( SELECT * FROM user_role
--                   WHERE role_name = "ADMIN_ROLE");
--END