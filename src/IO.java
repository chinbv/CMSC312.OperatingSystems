//max waiting time for IO = 50
import java.util.Random;
public class IO {

        private Random randomIO = new Random();

        public int generateIOBurst() {
            return OSClock.getClock() + ((randomIO.nextInt(50)));
        }

}
