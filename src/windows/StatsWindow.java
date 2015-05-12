package windows;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import defs.StatTypes;
import main.Stats;

public class StatsWindow extends Window
{
    private Stats mStats;
    private JTable mGlobalStatsTable;
    private JTable mCurrentStatsTable;
    private JTabbedPane mTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    
    public StatsWindow(String title, Stats stats)
    {
        super(title);
        mStats = stats;
        mCurrentStatsTable = new JTable(StatTypes.values().length, 2);
        mCurrentStatsTable.setEnabled(false);
        mGlobalStatsTable = new JTable(StatTypes.values().length, 2);
        mGlobalStatsTable.setEnabled(false);
        
        mTabbedPane.add("Current Stats", mCurrentStatsTable);
        mTabbedPane.add("Global Stats", mGlobalStatsTable);
        mFrame.getContentPane().add(mTabbedPane);
    }
    
    private void updateStats()
    {
        long currentStats[] = mStats.getCurrentStats();
        long globalStats[] = mStats.getGlobalStats();
        for (StatTypes i : StatTypes.values())
        {
            mCurrentStatsTable.getModel().setValueAt(i, i.getIndex(), 0);
            mCurrentStatsTable.getModel().setValueAt(currentStats[i.getIndex()], i.getIndex(), 1);

            mGlobalStatsTable.getModel().setValueAt(i, i.getIndex(), 0);
            mGlobalStatsTable.getModel().setValueAt(globalStats[i.getIndex()], i.getIndex(), 1);
        }
    }
    
    @Override
    public void show()
    {
        updateStats();
        super.show();
    }
}
