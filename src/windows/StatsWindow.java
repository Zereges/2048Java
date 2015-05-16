package windows;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import defs.Definitions;
import defs.StatTypes;
import main.Stats;

/**
 * Window used for showing {@link main.Stats Statistics}.
 * @see Window
 */
public class StatsWindow extends Window
{
    /** {@code JTable} of current session statistics. */
    private JTable mCurrentStatsTable = new JTable(StatTypes.values().length, 2);

    /** {@code JTable} of global statistics. */
    private JTable mGlobalStatsTable = new JTable(StatTypes.values().length, 2);
    
    /** {@code JTabbedPane} for switching views between current and global statistics */
    private JTabbedPane mTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    
    /** Reference to {@link main.Stats}. */
    private Stats mStats;

    /**
     * Constructs {@code StatsWindow}.
     * @param stats Reference to {@link main.Stats} to show.
     */
    public StatsWindow(Stats stats)
    {
        super(Definitions.WINDOW_TITLE + " - Statistics");
        mStats = stats;
        
        mCurrentStatsTable.setEnabled(false);
        mCurrentStatsTable.getColumnModel().getColumn(0).setHeaderValue("Statistics");
        mCurrentStatsTable.getColumnModel().getColumn(1).setHeaderValue("Value");
        mGlobalStatsTable.setEnabled(false);
        mGlobalStatsTable.getColumnModel().getColumn(0).setHeaderValue("Statistics");
        mGlobalStatsTable.getColumnModel().getColumn(1).setHeaderValue("Value");
        mTabbedPane.add("Current Stats", new JScrollPane(mCurrentStatsTable));
        mTabbedPane.add("Global Stats", new JScrollPane(mGlobalStatsTable));
        mFrame.getContentPane().add(mTabbedPane);
        mFrame.pack();
    }
    
    /** Updates contents of {@code mAchievementsTable} to reflect new statistics. */
    private void updateStats()
    {
        long currentStats[] = mStats.getCurrentStats();
        long globalStats[] = mStats.getGlobalStats();
        
        for (StatTypes stat : StatTypes.values())
        {
            mCurrentStatsTable.getModel().setValueAt(stat, stat.getId(), 0);
            mCurrentStatsTable.getModel().setValueAt(currentStats[stat.getId()], stat.getId(), 1);

            mGlobalStatsTable.getModel().setValueAt(stat, stat.getId(), 0);
            mGlobalStatsTable.getModel().setValueAt(globalStats[stat.getId()], stat.getId(), 1);
        }
    }

    /**
     * Shows a window.
     * @see Window#show()
     */
    @Override
    public void show()
    {
        updateStats();
        super.show();
    }
}
