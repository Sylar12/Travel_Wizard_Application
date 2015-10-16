CREATE TABLE IF NOT EXISTS `city` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  cityName varchar(60),
  score double,
  location varchar(50),
  introduction varchar(1000)
);