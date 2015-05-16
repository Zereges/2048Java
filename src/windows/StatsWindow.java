package windows;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import defs.StatTypes;
import main.Stats;

public class StatsWindow extends Window
{
    private Stats mStats;
    private JTable mCurrentStatsTable = new JTable(StatTypes.values().length, 2);
    private JTable mGlobalStatsTable = new JTable(StatTypes.values().length, 2);
    private JTabbedPane mTabbedPane = new JTabbedPane(JTabbedPane.TOP);
    
    public StatsWindow(String title, Stats stats)
    {
        super(title);
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
