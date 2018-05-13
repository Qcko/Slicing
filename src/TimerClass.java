import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;

public class TimerClass {
    private boolean isTimerRunning;
    private Instant endTime;


    public TimerClass(SystemClass startingTime) {
        Instant startTime = startingTime.getActualTimer();
        long duration = startingTime.getDuration();

        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex) {
            } catch (InstantiationException ex) {
            } catch (IllegalAccessException ex) {
            } catch (UnsupportedLookAndFeelException ex) {
            }
            endTime = startTime.plusSeconds(duration);
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(new ClockPane());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public class ClockPane extends JPanel {
        protected SimpleDateFormat timeFormat = new SimpleDateFormat("  mm:ss  ");

        private JLabel clock;

        public ClockPane() {

            isTimerRunning = true;

            setLayout(new BorderLayout());
            clock = new JLabel();
            clock.setHorizontalAlignment(JLabel.CENTER);
            clock.setFont(UIManager.getFont("Label.font").deriveFont(Font.BOLD, 48f));
            tickTock();
            add(clock);

            Timer timer = new  Timer(500, e -> {
                tickTock();
                while (!isTimerRunning) {
                    System.out.println("Time is up!!!");
                    System.exit(0);
                }
            });

            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            timer.start();
        }

        public void tickTock() {
            long interval = Duration.between(endTime, Instant.now()).toMillis();
            if ( interval < 0 ) {
                clock.setText(timeFormat.format(-interval));
            } else {
                clock.setText("END");
                isTimerRunning = false;
            }
        }
    }

    public void punish() {
        System.out.println("Wrong command");
        Instant endTimeNew = endTime.plusSeconds(-60);
        endTime = endTimeNew;
    }
}