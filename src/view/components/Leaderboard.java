package view.components;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Leaderboard Interface.
 *
 * @author Mel Harvey
 * @version 1.0
 */
public interface Leaderboard {

    /**
     * Adds data to the leaderboard.
     *
     * @param theName of the player.
     * @param theScore of the player.
     * @param theTable table model of the leaderboard.
     */
    void addData(String theName, Integer theScore, DefaultTableModel theTable);

    /**
     * Returns table model.
     * @return myTableModel.
     */
    DefaultTableModel getMyTableModel();

    /**
     * Returns myTable.
     * @return myTable.
     */
    JTable getMyTable();
}
