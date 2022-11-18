package com.aa.act.interview.org;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Organization {

	private Position root;

	int maxId = 0;

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
		Optional<Position> optTarget = searchPosition(root, title);
		if (optTarget.isPresent() == false) {
			return optTarget;
		}
		int nextId = EmployeeCounter.getCounter();
		Employee emp = new Employee(nextId, person);
		optTarget.get().setEmployee(Optional.of(emp));
		return optTarget;
	}

	private Optional<Position> searchPosition(Position root, String title) {

		if (root.getTitle().equalsIgnoreCase(title)) {
			if (root.getEmployee().isPresent())
				maxId = Math.max(maxId, root.getEmployee().get().getIdentifier());
			return Optional.of(root);
		}
		for (Position curr : root.getDirectReports()) {
			Optional<Position> matchedPosition = searchPosition(curr, title);
			if (matchedPosition.isPresent() && matchedPosition.get().isFilled() == false) {
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
