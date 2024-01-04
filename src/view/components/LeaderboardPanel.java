package view.components;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Leaderboard object class used to create Leaderboard.
 *
 * @author Mel Harvey
 * @version Autumn 2023
 */
public class LeaderboardPanel extends JPanel implements Leaderboard {

    /**
     * Name of the leaderboard save file.
     */
    private static final String LEADERBOARD = "Leaderboard";

    /**
     * JTable for leaderbaord.
     */
    private final JTable myTable;

    /**
     * DefaultTableModel for JTable.
     */
    private final DefaultTableModel myTableModel;

    /**
     * Creates panel for Leaderbaoard.
     */
    public LeaderboardPanel() {
        super();
        myTableModel = new DefaultTableModel();

        myTable = new JTable(myTableModel);
        readInputFile();
    }

    //The unchecked cast is caught and handled within a try catch statement.
    @SuppressWarnings("unchecked")
    private void readInputFile() {

        final Vector<String> columnNames = new Vector<>();

        columnNames.add("Name");
        columnNames.add("Score");

        ObjectInputStream fileOutput = null;

        try {
            final FileInputStream fileInput = new FileInputStream(LEADERBOARD);
            fileOutput = new ObjectInputStream(fileInput);
        } catch (final IOException ignored) {

        }

        try {
            myTableModel.setDataVector((Vector<Vector<Object>>)
                    Objects.requireNonNull(fileOutput).readObject(), columnNames);
        } catch (final NullPointerException e) {
            myTableModel.setDataVector(null, columnNames);
        } catch (final IOException | ClassNotFoundException ignored) {

        }
    }

    @Override
    public void addData(final String theName, final Integer theScore,
                        final DefaultTableModel theTable) {
        theTable.addRow(new Object[] {theName, theScore});
        saveData();
    }

    private void saveData() {
        try {

            final FileOutputStream fileOutput = new FileOutputStream(LEADERBOARD);
            final ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            objectOutput.writeObject(myTableModel.getDataVector());

            fileOutput.close();
            objectOutput.close();

        } catch (final IOException ignored) {

        }
    }

    @Override
    public DefaultTableModel getMyTableModel() {
        return myTableModel;
    }

    @Override
    public JTable getMyTable() {
        return myTable;
    }

}
