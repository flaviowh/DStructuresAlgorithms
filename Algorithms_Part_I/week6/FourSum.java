package Algorithms_Part_I.week6;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Set;

public class FourSum {
    Hashtable<Integer, Integer[]> pairSums = new Hashtable<>();

    public int[][] solutions;
    public int size = 0;

    boolean alreadyAdded(int[] solution) {
        for (int[] existingSolution : solutions) {
            if (Arrays.equals(solution, existingSolution)) {
                return true;
            }
        }
        return false;
    }

    boolean isUnique(int[] solution) {
        Set<Integer> solutionSet = new HashSet<>();
        for (int n : solution) {
            solutionSet.add(n);
        }
        return solutionSet.size() == 4 ? true : false;
    }

    FourSum(int[] nums, int target) {
        solutions = new int[nums.length][4];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                pairSums.put(nums[i] + nums[j], new Integer[] { nums[i], nums[j] });
            }
        }

        for (Integer key : pairSums.keySet()) {
            int remainder = target - key;
            boolean found = false;
            for (Integer key2 : pairSums.keySet()) {
                if (key2 == remainder) {
                    found = true;
                    break;
                }
            }
            if (found) {
                int[] solution = new int[] {
                        pairSums.get(key)[0],
                        pairSums.get(key)[1],
                        pairSums.get(remainder)[0],
                        pairSums.get(remainder)[1] 
                    };
                Arrays.sort(solution);
                if (isUnique(solution) && !alreadyAdded(solution)) {
                    solutions[size++] = solution;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 20, 50, 60, 100, 200, 1000 };
        int tg = 32;
        FourSum fourSum = new FourSum(nums, tg);
        System.out.println("number of solutions : " + fourSum.size);
        for (int i = 0; i < fourSum.size; i++) {
            System.out.println(Arrays.toString(fourSum.solutions[i]));
        }
    }

}
