package pl.mmorpg.prototype.client.quests;

import java.util.Collection;

import pl.mmorpg.prototype.client.exceptions.QuestFinishedException;

public class Progress
{
    private final Collection<QuestTask> questTasks;
    
    public Progress(Collection<QuestTask> questTasks)
    {
        this.questTasks = questTasks;
    }
    
    public boolean isEveryStepsFinished()
    {
        for(QuestTask step : questTasks)
            if(!step.isFinished())
                return false;
        return true;
    }
    
    public String getHumanReadableInfo()
    {
        int finishedSteps = getNumberOfFinishedSteps();
        return finishedSteps + " / " + questTasks.size();
    }
    
    private int getNumberOfFinishedSteps()
    {
        return (int)questTasks.stream()
                    .filter(QuestTask::isFinished)
                    .count();
    }
    
    public QuestTask getCurrentTask()
    {
        return questTasks.stream()
                .filter(q -> !q.isFinished())
                .findFirst()
                .orElseThrow(QuestFinishedException::new);
    }
}
