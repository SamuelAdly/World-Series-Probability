//IMPORTANT: LOGO IS NOT MY ORIGINAL WORK, THIS WAS BORROWED FROM "https://en.wikipedia.org/wiki/1994_World_Series"
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
//GUI class where gui is created and calculations are made
public class GUI extends JFrame implements ActionListener {
    
    private JFrame frame;
    private JPanel team1Panel;
    private JPanel team2Panel;
    private JPanel winningProbabilityPanel;
    private JTextArea team1Text;
    private JTextArea team2Text;
    private JTextArea winningProbabilityText;
    private JLabel team1Label;
    private JLabel team2Label;
    private JLabel winnerLabel;
    private JButton calculate;
    private JComboBox <String> teamSelector;
    private JLabel teamSelectLabel;
    
    //creates the gui and sets the size, title, font, combobox, and layout of the frame
    public GUI() {
        //creates the frame and sets the size, title, logo and close operation
        frame = new JFrame("World Series Probability Calculator By Samuel Adly");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        ImageIcon icon = new ImageIcon("logo.png");
        frame.setIconImage(icon.getImage());
        
        //creates the labels and sets the font and bounds for team select
        teamSelectLabel = new JLabel("Select Team");
        teamSelectLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        teamSelectLabel.setBounds(190, 180, 100, 20);
        frame.add(teamSelectLabel);

        //creates the labels and sets the font and bounds for team 1
        team1Label = new JLabel("Enter Team 1 Probability");
        team1Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        team1Label.setBounds(10, 58, 170, 20);
        frame.add(team1Label);

        //creates the labels and sets the font and bounds for team 2
        team2Label = new JLabel("Enter Team 2 Probability");
        team2Label.setFont(new Font("Times New Roman", Font.BOLD, 15));
        team2Label.setBounds(300, 58, 170, 20);
        frame.add(team2Label);

        //creates the labels and sets the font and bounds for winner
        winnerLabel = new JLabel("Probabilty of Your Team Winning");
        winnerLabel.setFont(new Font("Times New Roman", Font.BOLD, 15));
        winnerLabel.setBounds(120, 328, 250, 20);
        frame.add(winnerLabel);

        //creates the panels and all its textareas and associated layout info for team 1, team 2, and winning probability
        team1Panel = new JPanel();
        team2Panel = new JPanel();
        winningProbabilityPanel = new JPanel();
        team1Panel.setBounds(60, 80, 50, 20);
        team2Panel.setBounds(350, 80, 50, 20);
        winningProbabilityPanel.setBounds(170, 350, 118, 20);
        team1Panel.setLayout(new BorderLayout());
        team1Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        team2Panel.setLayout(new BorderLayout());
        team2Panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        winningProbabilityPanel.setLayout(new BorderLayout());
        winningProbabilityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        team1Text = new JTextArea("");
        team2Text = new JTextArea("");
        winningProbabilityText = new JTextArea("Winning Probability");
        winningProbabilityText.setEditable(false);
        team1Text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        team2Text.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        winningProbabilityText.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        //creates the combobox and sets the font and bounds for team selector
        String[] teams = {"Team 1", "Team 2"};
        teamSelector = new JComboBox<>(teams);
        teamSelector.setBounds(190, 200, 80, 20);
        teamSelector.setFont(new Font("Times New Roman", Font.BOLD, 15));

        //creates the buttons and sets the font and bounds for team 1, team 2, and calculate
        calculate = new JButton("Calculate");
        calculate.setFont(new Font("Times New Roman", Font.BOLD, 15));
        calculate.addActionListener(this);
        calculate.setBounds(180, 375, 100, 20);
        frame.add(calculate);

        //adds the textareas and labels to the panels and the panels to the frame
        team1Panel.add(team1Text);
        team2Panel.add(team2Text);
        winningProbabilityPanel.add(winningProbabilityText);
        
        frame.add(teamSelector);
        frame.add(team1Panel);
        frame.add(team2Panel);
        frame.add(winningProbabilityPanel);
        
        //makes the frame open in the center of the screen, visible, and not resizable
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
    }

    //listens to the button and does the following then clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Calculate")) {
            double team1 = Double.parseDouble(team1Text.getText()); //parse the double value the user inputs for team 1 from the text area box
            double team2 = Double.parseDouble(team2Text.getText()); //parse the double value the user inputs for team 2 from the text area box
            double probTeam1WinsSeries = calculateWinningProbability(team1, team2); //calls calculateWinningProbability to calculate the probability of team 1 winning the series
            double probTeam2WinsSeries = 1 - probTeam1WinsSeries; //based off team 1's probability, calculates the probability of team 2 winning the series
            
            if (teamSelector.getSelectedItem().equals("Team 1")) { //if the user selected team 1, then set the text area to the probability of team 1 winning the series
                winningProbabilityText.setText(String.valueOf(probTeam1WinsSeries)); //sets the text area to the probability of team 1 winning the series
            } else {
                winningProbabilityText.setText(String.valueOf(probTeam2WinsSeries)); //since theres only two options then obviously if not team 1 then set it to team 2 and post that probability
            }
            
        }
    }

    //calculates the probability of team 1 winning the series using dynamic programming similar to pascals triangle
    public static double calculateWinningProbability(double team1, double team2) {
        double team1WinsSeries = 0; //team 1 wins the series
        double team2WinsSeries = 0; //team 2 wins the series
        double [][] dynamic = new double [8][8]; //creates a 2d array 
        //forms the values in the 2d array to help calculate binomial coefficients like pascals triangle method
        for (int i = 0; i <= 7; i++) { //row
            for (int j = 0; j <= i; j++) { //column
                if (j == 0 || j == i) { //if j is in the first column or at the last column of the current row
                    dynamic[i][j] = 1; //where that if statement is true, then the value is 1
                } else {
                    dynamic[i][j] = dynamic[i - 1][j - 1] + dynamic[i - 1][j];//sums up the 2 elements above it 
                }
            }
        }
        //System.out.println(Arrays.deepToString(dynamic)); //prints out the 2d array to make sure all values are correct
        //calculates the probability of team 1 winning the series based off the dynamic array values
        for (int i = 0; i<= 7; i++) {
            double team1Wins = dynamic[7][i] * Math.pow(team1, i) * Math.pow(team2, 7 - i);
            double team2Wins = dynamic[7][i] * Math.pow(team2, i) * Math.pow(team1, 7 - i);
    
            if (i >= 4) {
                team1WinsSeries += team1Wins;
            } else {
                team2WinsSeries += team2Wins;
            }
        }
        return team1WinsSeries;
    }



}
