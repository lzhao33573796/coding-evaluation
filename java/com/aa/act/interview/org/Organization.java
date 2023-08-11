package com.aa.act.interview.org;

import java.util.*;

public abstract class Organization {

    private Position root;

    private static int id = 0;
    
    public Organization() {
        root = createOrganization();
    }
    
    protected abstract Position createOrganization();
    
    /**
     * hire the given person as an employee in the position that has that title
     * 
     * @param person
     * @param title
     * @return the newly filled position or empty if no position has that title
     */
    public Optional<Position> hire(Name person, String title) {
        // check if there is such title for all current positions
        Optional<Position> found = getTargetPosition(root, title);
        if (found.isPresent()) {
            found.get().setEmployee(Optional.of(new Employee(id++, person)));
            return found;
        }
        return Optional.empty();
    }

    /**
     * recursively find the position with the specific title
     *
     * @param cur current position
     * @param title target position title
     * @return Optional<Position>
     */
    private Optional<Position> getTargetPosition(Position cur, String title) {
        // if this position is the one we want, return it
        if (cur.getTitle().equals(title)) {
            return Optional.of(cur);
        }
        // if this position has no direct report
        // return empty
        if (cur.getDirectReports().isEmpty()) {
            return Optional.empty();
        }
        for (Position p : cur.getDirectReports()) {
            Optional<Position> found = getTargetPosition(p, title);
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return printOrganization(root, "");
    }
    
    private String printOrganization(Position pos, String prefix) {
        StringBuffer sb = new StringBuffer(prefix + "+-" + pos.toString() + "\n");
        for(Position p : pos.getDirectReports()) {
            sb.append(printOrganization(p, prefix + "  "));
        }
        return sb.toString();
    }
}
