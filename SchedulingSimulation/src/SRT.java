import java.util.ArrayList;

public class SRT{

    private static ArrayList<Process> processes;

    public SRT(ArrayList<Process> processList){
        //processes = processList;
        processes = (ArrayList<Process>) processList.clone();
    }

    public static void simulatesSRT(int numProcesses)
    {
        int ATT = 0;
        int completedProcesses = 0;
        int currentTime = 0;
        int shortestTime = Integer.MAX_VALUE;
        int shortestProcess = 0;
        int currentRemainingTime = 0;
        boolean checkProcess = false;
        int[] activeTime = new int[numProcesses];
        // ArrayList<Process> processes_copy = new ArrayList<>(processes);

        // Traverses through loop until all processes are completed
        while(completedProcesses != numProcesses)
        {
            System.out.println("The current time is: " + currentTime + " ms");
            for(int i=0; i<numProcesses; i++)
            {
                if((processes.get(i).getArrivalTime() <= currentTime) && (processes.get(i).getRemCPUTime() < shortestTime) && (processes.get(i).getRemCPUTime() > 0))
                {
                    if(processes.get(i).getActive()==0)
                    {
                        processes.get(i).setActive(1);
                        activeTime[i] = currentTime;
                    }
                    shortestTime = processes.get(i).getRemCPUTime();
                    shortestProcess = i;
                    checkProcess = true;
                }
            }

            if(checkProcess==false)
            {
                currentTime++;
                continue;
            }

            System.out.println("Currently running process " + shortestProcess);

            currentRemainingTime = processes.get(shortestProcess).getRemCPUTime();

            // Decrements CPU time of the shortest process
            currentRemainingTime--;
            processes.get(shortestProcess).setRemCPUTime(currentRemainingTime);
            System.out.println("the current remaining time is: " + processes.get(shortestProcess).getRemCPUTime() + "\n");

            shortestTime = processes.get(shortestProcess).getRemCPUTime();

            // Once the shortest process is completed, update all values
            if(shortestTime == 0)
            {
                shortestTime = Integer.MAX_VALUE;
            }

            if(processes.get(shortestProcess).getRemCPUTime()==0)
            {
                completedProcesses++;
                checkProcess = false;
                processes.get(shortestProcess).setTurnAround(1+currentTime-activeTime[shortestProcess]);
                processes.get(shortestProcess).setActive(0);
            }
            currentTime++;
        }
    	
    	/*System.out.println("TURNAROUND TIMES:");
    	for(int z=0; z<numProcesses; z++)
    	{
    		System.out.println("Process " + z + " has a turnaround time of " + processes_copy.get(z).getTurnAround());
    	}*/

        System.out.println("\nSRT AVERAGE TURNAROUND TIME:");
        for(int y=0; y<numProcesses; y++)
        {
            ATT += processes.get(y).getTurnAround();
        }
        ATT = ATT/numProcesses;
        System.out.println("The average turnaround time is: " + ATT);
    }
}