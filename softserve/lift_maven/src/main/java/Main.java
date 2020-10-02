import classes.Human;
import classes.Lift;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter amount of elevators: ");
        Scanner reader = new Scanner(System.in);
        int n=reader.nextInt();
        Lift[] lifts = new Lift[n];
        System.out.print("Enter for every elevator from which floor  to which floor it go to: ");
        for(int i=0;i<n;i++){
            lifts[i]=new Lift(reader.nextInt(),reader.nextInt(),reader.nextInt());
        }
        System.out.print("Enter direction and the current floor: ");
        String d=reader.next();
        Human human;
        if(d.equals("down"))human=new Human(reader.nextInt(), Human.Direction.DOWN);
        else human=new Human(reader.nextInt(), Human.Direction.UP);
        int id= bestLift(lifts,human);
        for(int i=0;i<n;i++)
        {
            if( lifts[i].getId()==id){
                System.out.println("\n"+lifts[i].toString());
                break;
            }

        }


    }

    public static int bestLift(Lift[] lifts, Human human){
        int iBest=pickUp(lifts,human);
        if (iBest == -1) {
            int minPath = Integer.MAX_VALUE;
            for (int i = 0; i < lifts.length; i++) {
                if(lifts[i].getToFloor()==human.getCurrentFloor()){ iBest = i; break;}
                else {
                    int path = Math.abs(lifts[i].getFromFloor()  - human.getCurrentFloor());
                    if (path < minPath) {
                        minPath = path;
                        iBest = i;
                    }
                }
            }
        }
        return lifts[iBest].getId();
    }

    public static int pickUp(Lift[] lifts,Human human){
        int mod=Integer.MAX_VALUE;
        int iBest=-1;
        int subtract;
        for(int i=0;i<lifts.length;i++){
            subtract=Math.abs(human.getCurrentFloor()-lifts[i].getFromFloor());
            if(((human.getDirection()== Human.Direction.UP &&
                            lifts[i].getFromFloor()<=human.getCurrentFloor() &&
                            lifts[i].getToFloor()>human.getCurrentFloor())  ||
                    (human.getDirection()== Human.Direction.DOWN &&
                            lifts[i].getFromFloor()>=human.getCurrentFloor() &&
                            lifts[i].getToFloor()<human.getCurrentFloor()
                    )) &&
                    mod>=subtract  ){
                mod=subtract;
                iBest=i;
                if(subtract==0)break;
            }
        }
        return iBest;
    }

}
