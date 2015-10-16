CREATE TABLE IF NOT EXISTS `scene` (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  sceneName varchar(60),
  score double,
  cityID int,
  introduction varchar(1000),
  FOREIGN KEY(cityID) REFERENCES city(id) ON DELETE CASCADE ON UPDATE CASCADE
);
