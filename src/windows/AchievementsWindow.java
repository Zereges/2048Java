package windows;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import main.Achievements;
import defs.AchievementTypes;
import defs.Definitions;

/**
 * Window used for showing {@link main.Achievements}.
 * @see Window
 */
public class AchievementsWindow extends Window
{
    /** {@code JTable} of achievements. */
    private JTable mAchievementsTable = new JTable(AchievementTypes.values().length, 1);
    
    /** Reference to {@link main.Achievements}. */
    private Achievements mAchievements;
    
    /**
     * Constructs {@code AchievementsWindow}.
     * @param achievements Reference to {@link main.Achievements} to show.
     */
    public AchievementsWindow(Achievements achievements)
    {
        super(Definitions.WINDOW_TITLE + " - Achievements");
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
    
    /** Updates contents of {@code mAchievementsTable} to reflect current achievements. */
    private void updateAchievements()
    {
        mAchievementsTable.removeAll();
        for (AchievementTypes achievement : AchievementTypes.values())
        {
            mAchievementsTable.getModel().setValueAt(achievement, achievement.getId(), 0);
        }
    }
    
    /**
     * Shows a window.
     * @see Window#show()
     */
    @Override
    public void show()
    {
        updateAchievements();
        super.show();
    }
}
