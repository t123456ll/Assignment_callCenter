import enums.StafferRank;

import java.util.ArrayList;

public class BaseTest {
    public static ArrayList<Staffer> setupStaffers(int employeeNum, int supervisorNum, int managerNum){
        ArrayList<Staffer> staffers = new ArrayList<>();
        for (int i=0; i<employeeNum; i++){  // add employees
            Staffer employee = Staffer.buildEmployee();
            staffers.add(employee);
        }
        for (int j=0; j<supervisorNum; j++){
            Staffer manger = Staffer.buildManager(); // add supervisors
            staffers.add(manger);
        }
        for (int k=0; k<managerNum; k++){
            Staffer director = Staffer.buildDirector(); // add managers
            staffers.add(director);
        }
        return staffers;
    }

    public static ArrayList<Staffer> setupBusyStaffers(ArrayList<Staffer> staffers, boolean isEmployeeBusy,
                                                       boolean isSupervisorBusy, boolean isManagerBusy){
        for(Staffer staffer: staffers){
            if (isEmployeeBusy && staffer.getRank() == StafferRank.EMPLOYEE) {
                staffer.setStafferStatus(false);
            } if (isSupervisorBusy && staffer.getRank() == StafferRank.SUPERVISOR) {
                staffer.setStafferStatus(false);
            } if (isManagerBusy && staffer.getRank() == StafferRank.MANAGER) {
                staffer.setStafferStatus(false);
            }

        }
        return staffers;
    }


}
