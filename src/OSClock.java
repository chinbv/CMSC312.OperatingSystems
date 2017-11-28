public class OSClock
{
    private static int time = 0;

    public static int getClock()
    {
        return time;
    }

    public static void setClock(int x)
    {
        time = x;
    }

    public static void incrementClock()
    {
        time++;
    }

    public static void resetClock()
    {
        time = 0;
    }

}
