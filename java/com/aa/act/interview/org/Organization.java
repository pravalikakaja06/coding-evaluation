package com.aa.act.interview.org;

import java.util.Optional;

public abstract class Organization {

	private Position root;

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
		Optional<Position> optPosition = searchPosition(root, title);
		if (!optPosition.isPresent()) {
			return optPosition;
		}
		optPosition.get().setEmployee(Optional.of(new Employee(EmployeeCounter.getNextEmployeeId(),person)));
		return optPosition;
	}

	private Optional<Position> searchPosition(Position root, String title) {

		if (root.getTitle().equalsIgnoreCase(title)) {
			return Optional.of(root);
		}
		for (Position curr : root.getDirectReports()) {
			Optional<Position> matchedPosition = searchPosition(curr, title);
			if (matchedPosition.isPresent() && !matchedPosition.get().isFilled()) {
				return matchedPosition;
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
		for (Position p : pos.getDirectReports()) {
			sb.append(printOrganization(p, prefix + "\t"));
		}
		return sb.toString();
	}
}
