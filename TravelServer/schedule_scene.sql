CREATE TABLE IF NOT EXISTS `schedule_scene` (
  scheduleID int,
  sceneID int,
  date int,
  time int,
  PRIMARY KEY(scheduleID, sceneID),
  FOREIGN KEY(scheduleID) REFERENCES schedule(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(sceneID) REFERENCES scene(id) ON UPDATE CASCADE ON DELETE CASCADE
);
