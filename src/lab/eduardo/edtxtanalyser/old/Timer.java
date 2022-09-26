package lab.eduardo.edtxtanalyser.old;

public class Timer {

    private int seconds;
    private int minutes;
    private int hours;
    private int carry;

    public Timer() {
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public Timer(String time) {
        try {
            parse(time);
        } catch (InvalidTimeStringException e) {
            System.out.println("Exception " + e);
        }
    }

    private void parse(String time) throws InvalidTimeStringException {
        String[] parts = time.split(":");
        this.hours = Integer.parseInt(parts[0]);
        this.minutes = Integer.parseInt(parts[1]);
        this.seconds = Integer.parseInt(parts[2].split(",")[0]);
        if (this.hours > 23 || this.minutes > 59 || this.seconds > 59) {
            throw new InvalidTimeStringException();
        }
    }

    private void plusSeconds(int secs) {
        int sum = this.seconds + secs;
        seconds = sum % 60;
        carry = sum > 59 ? 1 : 0;
    }

    private void plusMinutes(int mins) {
        int sum = this.minutes + mins;
        minutes = (sum % 60) + carry;
        carry = sum > 59 ? 1 : 0;
    }

    private void plusHours(int hrs) {
        hours = hours + hrs + carry;
        carry = 0;
    }

    private void minusSeconds(int secs) {
        int subtraction = this.seconds - secs;
        seconds = subtraction < 0 ? 60 + subtraction : subtraction;
        carry = subtraction < 0 ? 1 : 0;
    }

    private void minusMinutes(int mins) {
        int subtraction = this.minutes - mins;
        minutes = subtraction - carry < 0 ? 60 + (subtraction - carry) : subtraction - carry;
        carry = subtraction - carry < 0 ? 1 : 0;
    }

    private void minusHours(int hrs) {
        int subtraction = this.hours - hrs;
        hours = subtraction - carry < 0 ? 24 + (subtraction - carry) : subtraction - carry;
        carry = 0;
    }

    /**
     * @return the decimalSeconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * @return the seconds
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * @return the minutes
     */
    public int getHours() {
        return hours;
    }

    public Timer plusOther(Timer timer) {
        plusSeconds(timer.getSeconds());
        plusMinutes(timer.getMinutes());
        plusHours(timer.getHours());

        return new Timer(this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds());
    }

    public Timer minusOther(Timer timer) {
        minusSeconds(timer.getSeconds());
        minusMinutes(timer.getMinutes());
        minusHours(timer.getHours());

        return new Timer(this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds());
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.hours + " hours " + this.minutes + " minutes " + this.seconds + " seconds";
    }

}
