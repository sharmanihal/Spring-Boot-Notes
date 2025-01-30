-- Drop user first if they exist
DROP USER if exists 'test'@'%' ;

-- Now create user with prop privileges
CREATE USER 'test'@'%' IDENTIFIED BY 'test';

GRANT ALL PRIVILEGES ON * . * TO 'test'@'%';