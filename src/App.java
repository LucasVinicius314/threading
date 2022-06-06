import java.util.ArrayList;
import java.util.List;

public class App {

  static final boolean concurrentMode = false;

  static final List<String> obj = new ArrayList<String>();

  public static void main(String[] args) throws Exception {
    final var log = new ArrayList<String>();

    int x = -1;

    while (++x < 10) {
      final var j = x;

      new Thread(
        new Runnable() {
          public void run() {
            try {
              if (concurrentMode) {
                final var o = String.valueOf(j);

                log.add("started " + o);

                "".toCharArray();

                Thread.sleep(100);

                obj.clear();

                log.add("finished " + o);
              } else {
                synchronized (obj) {
                  while (!obj.isEmpty()) obj.wait();

                  final var o = String.valueOf(j);

                  log.add("started " + o);

                  "".toCharArray();

                  Thread.sleep(100);

                  obj.clear();

                  log.add("finished " + o);
                  obj.notifyAll();
                }
              }
            } catch (Exception e) {}
          }
        }
      )
        .start();
    }

    Thread.sleep(2000);

    log.toString();
  }
}
