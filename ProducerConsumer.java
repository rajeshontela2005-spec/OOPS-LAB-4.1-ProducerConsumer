import java.util.LinkedList;

class PC {
    LinkedList<Integer> buffer = new LinkedList<>();
    int capacity = 2;

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (this) {
                while (buffer.size() == capacity) {
                    wait();
                }
                System.out.println("Producer produced-" + value);
                buffer.add(value++);
                notify();
                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (buffer.size() == 0) {
                    wait();
                }
                int val = buffer.removeFirst();
                System.out.println("Consumer consumed-" + val);
                notify();
                Thread.sleep(1000);
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {
        final PC pc = new PC();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try { pc.produce(); } catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try { pc.consume(); } catch (InterruptedException e) {}
            }
        });

        t1.start();
        t2.start();
    }
}
