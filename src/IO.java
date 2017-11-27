import java.util.Random;
public class IO {

        private Random randomIO = new Random();

        public int generateIOBurst() {
            return Clock.getClock() + ((randomIO.nextInt(50)));
        }

}
