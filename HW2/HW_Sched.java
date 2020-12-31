import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// TODO Implement this
		return a1.weight <= a2.weight ? a1.weight >= a2.weight ? 0 : 1 : -1;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {
		//TODO Implement this
		
		//Sort assignments
		//Order will depend on how compare function is implemented
		ArrayList<Assignment> old = new ArrayList<>(Assignments);
		Collections.sort(Assignments, new Assignment());

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
		Assignment[] timeslots = new Assignment[lastDeadline];
		int totalAssign = 0;
		int count = 1;
		for (Assignment assignment : Assignments) {
			int deadline = assignment.deadline - 1;
			while (true) {
				if (timeslots[deadline] != null) {
					if (timeslots[deadline].weight < assignment.weight) {
						int oldDeadline = deadline;
						while (true) {
							deadline--;
							if (deadline == -1 || timeslots[deadline] == null) {
								deadline = 0;
								while (true) {
									if (deadline != oldDeadline) {
										timeslots[deadline] = timeslots[deadline + 1];
										deadline++;
									} else {
										timeslots[deadline] = assignment;
										totalAssign++;
										count = 0;
										break;
									}
								}
							}

							if (count == 0) break;
						}
					} else {
						deadline--;
					}
				} else {
					timeslots[deadline] = assignment;
					totalAssign++;
					break;
				}
				if (deadline == -1) break;
				if (totalAssign == lastDeadline) break;
			}
		}
		for (int i = 0; i < lastDeadline; i++) {
			for (int j = 0; j < Assignments.size(); j++) {
				if (old.get(j) == timeslots[i]) {
					homeworkPlan[i] = j;
				}
			}
		}
		return homeworkPlan;

	}
}
	



