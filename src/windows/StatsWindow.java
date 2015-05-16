package windows;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import defs.StatTypes;
import main.Stats;

public class StatsWindow extends Window
{
    private Stats mStats;
    private JTable mCurrentStatsTable = new JTable(StatTypes.values().length, 2);;
    private JTable mGlobalStatsTable = new JTable(StatTypes.values().length, 2);;
    private JTabbedPane mTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    
    public StatsWindow(String title, Stats stats)
    {
        super(title);
        mStats = stats;

        mCurrentStatsTable.setEnabled(false);
        mGlobalStatsTable.setEnabled(false);
        mTabbedPane.add("Current Stats", mCurrentStatsTable);
        mTabbedPane.add("Global Stats", mGlobalStatsTable);
        mFrame.getContentPane().add(mTabbedPane);
        mFrame.pack();
    }
    
    private void updateStats()
    {
        long currentStats[] = mStats.getCurrentStats();
        long globalStats[] = mStats.getGlobalStats();
        
        for (StatTypes stat : StatTypes.values())
        {
            mCurrentStatsTable.getModel().setValueAt(stat, stat.getIndex(), 0);
            mCurrentStatsTable.getModel().setValueAt(currentStats[stat.getIndex()], stat.getIndex(), 1);

            mGlobalStatsTable.getModel().setValueAt(stat, stat.getIndex(), 0);
            mGlobalStatsTable.getModel().setValueAt(globalStats[stat.getIndex()], stat.getIndex(), 1);
        }
    }
    
    @Override
    public void show()
    {
        updateStats();
        super.show();
    }
}
