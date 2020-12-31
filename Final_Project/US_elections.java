import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {


    public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided) {
        int answer = 0;
        int[] gap = new int[num_states];
        int[] swingStates = new int [num_states];
        int[] electoralVotes = new int [num_states];
        int vBToWin = 0;
        int dBToWin = 0;
        int dSum = 0;
        int[] vToGet = new int[num_states];
        int[] votestotal_state = new int[num_states];

        for (int i = num_states - 1; i >= 0; i--) {
            votestotal_state[i]=votes_Trump[i] + votes_Biden[i]+ votes_Undecided[i];
            if((double)votes_Biden[i]>(double)votestotal_state[i]/2){
                vToGet[i]=0;
            }else if((double)votes_Trump[i]>=(double)votestotal_state[i]/2) {
                vToGet[i]=-1;
            } else {
                vToGet[i] = votestotal_state[i]/2+1-votes_Biden[i];
            }
        }
        for (int i = num_states - 1; i >= 0; i--) {
            dSum += delegates[i];
        }
        answer = findMinSolution(dSum/2 +1, dSum,vToGet, delegates, num_states);
        return answer;
    }

    private static int findMinSolution(int dBToWin, int dSum,  int[] vToGet, int[] delegates, int num_states) {
        List<Integer> peopleToWin = new ArrayList<>();
        List<Integer> electoralVotes = new ArrayList<>();
        int solution = 999999999;
        int states = 0;
        for (int i = num_states - 1; i >= 0; i--) {
            if (!(vToGet[i] == -1)) {
                states++;
                electoralVotes.add(delegates[i]);
                peopleToWin.add(-vToGet[i]);
            }
        }
        int bound = dBToWin + Collections.max(electoralVotes)/2;
        while (bound >= dBToWin) {
            int[][] maxValue = new int[states+1][dSum+1];
            knapsack(maxValue,states,dBToWin,peopleToWin,electoralVotes);
            solution = Math.min(-maxValue[states][dBToWin],solution);
            dBToWin++;
        }
        if (solution == 999999999) return -1;
        return solution;
    }

    private static void knapsack (int[][] dp, int N, int V, List<Integer> peopleToWin, List<Integer> electoralVotes){
        //value is peopleToWin, weight is electoralVotes
        for (int n = 0; n <= N; n++) {
            int v = 0;
            while (v <= V) {
                if (v != 0) {
                    if (v > 0 &&n == 0 ) {
                        dp[n][v] = -999999999;
                    } else if (electoralVotes.get(n-1) > v){
                        dp[n][v] = dp[n-1][v];
                    } else{
                        dp[n][v] = Math.max(dp[n-1][v], peopleToWin.get(n-1)+dp[n-1][v-electoralVotes.get(n-1)] );
                    }
                } else {
                    dp[n][v] = 0;
                }
                v++;
            }
        }
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();    //measure time consuming
        try {
            String path = args[0];
            File myFile = new File(path);
            Scanner sc = new Scanner(myFile);
            int num_states = sc.nextInt();
            int[] delegates = new int[num_states];
            int[] votes_Biden = new int[num_states];
            int[] votes_Trump = new int[num_states];
            int[] votes_Undecided = new int[num_states];
            for (int state = 0; state < num_states; state++) {
                delegates[state] = sc.nextInt();
                votes_Biden[state] = sc.nextInt();
                votes_Trump[state] = sc.nextInt();
                votes_Undecided[state] = sc.nextInt();
            }
            sc.close();
            int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
            long elapsedTime = System.nanoTime() - startTime; //measure time consuming
            System.out.println("Time execute "                   //measure time consuming
                    + elapsedTime/1000000);
            System.out.println(answer);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}



