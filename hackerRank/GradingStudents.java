package hackerRank;

import java.util.List;

public class GradingStudents {
    public static List<Integer> gradingStudents(List<Integer> grades) {
        for(int i=0; i<grades.size(); i++){
            if(grades.get(i)>=38){
                if(grades.get(i)%5==3){
                    grades.set(i, grades.get(i)+2);
                }else if(grades.get(i)%5==4){
                    grades.set(i, grades.get(i)+1);
                }
            }
        }
        return grades;
    }
}
