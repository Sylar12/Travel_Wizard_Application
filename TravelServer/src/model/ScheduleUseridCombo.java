package model;

import java.io.Serializable;

/*Load all the schedules ever be made by the user. The model is used when the user wants to view the history of the
 * schedules he've made*/
public class ScheduleUseridCombo implements Serializable{

    private static final long serialVersionUID = 6030273381198785526L;
	private Schedule schedule;
	private String userid;
	
	public ScheduleUseridCombo(Schedule schedule, String userid) {
		this.schedule = schedule;
		this.userid = userid;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public String getUserid() {
		return userid;
	}

    @Override
    public String toString() {
        return "ScheduleUseridCombo{" +
                "schedule=" + schedule +
                ", userid='" + userid + '\'' +
                '}';
    }
}
