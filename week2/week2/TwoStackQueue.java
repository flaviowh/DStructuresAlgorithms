package week2;

public class TwoStackQueue {
    // Hint: If you push elements onto a stack and then pop them all, they appear in
    // reverse order. If you repeat this process, they're now back in order.
    private Stack<Integer> inbox = new Stack<Integer>();
    private Stack<Integer> outbox = new Stack<Integer>();

    public void queue(Integer item) {
        inbox.push(item);
    }

    public Integer dequeue() {
        if (outbox.isEmpty()) {
            while (!inbox.isEmpty()) {
                outbox.push(inbox.pop());
            }
        }
        return outbox.pop();
    }
}
