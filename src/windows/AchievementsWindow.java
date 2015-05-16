package windows;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import main.Achievements;
import defs.AchievementTypes;

public class AchievementsWindow extends Window
{
    private JTable mAchievementsTable = new JTable(AchievementTypes.values().length, 1);
    private Achievements mAchievements;
    public AchievementsWindow(String title, Achievements achievements)
    {
        super(title);
        mAchievements = achievements;
        
        mAchievementsTable.setEnabled(false);
        mAchievementsTable.getColumnModel().getColumn(0).setHeaderValue("Achievement");
        mAchievementsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(mAchievements.isCompleted(AchievementTypes.values()[row]) ? Color.GREEN : Color.RED);
                return component;
            }
        });
        mFrame.getContentPane().add(new JScrollPane(mAchievementsTable));
        mFrame.pack();
    }
    
    private void updateAchievements()
    {
        mAchievementsTable.removeAll();
        for (AchievementTypes achievement : AchievementTypes.values())
        {
            mAchievementsTable.getModel().setValueAt(achievement, achievement.getIndex(), 0);
        }
    }
    
    @Override
    public void show()
    {
        updateAchievements();
        super.show();
    }
}
