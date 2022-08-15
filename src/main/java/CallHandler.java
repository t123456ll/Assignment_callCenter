import enums.StafferRank;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CallHandler {

    public Staffer findCallHandler ( @NotNull Collection<Staffer> staffersList) {
        List<Staffer> availableStaffers = staffersList.stream().filter(s -> s.getStatus()).collect(Collectors.toList());
        System.out.println("Available operators: " + availableStaffers.size());
        Optional<Staffer> staffer = availableStaffers.stream().filter(s -> s.getRank() == StafferRank.EMPLOYEE).findAny();
        if (staffer.isEmpty()) {
            staffer = availableStaffers.stream().filter(s -> s.getRank() == StafferRank.SUPERVISOR).findAny();
            if (staffer.isEmpty()) {
                staffer = availableStaffers.stream().filter(s -> s.getRank() == StafferRank.MANAGER).findAny();
                if (staffer.isEmpty()) {
                    System.out.println("everyone is busy, call will go to queue");
                    return null;
                }
            }
        }

        System.out.println("ID: " + staffer.get().getId() + " " + staffer.get().getRank() + " will help with this call");
        return staffer.get();

    }
}
