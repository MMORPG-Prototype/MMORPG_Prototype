package pl.mmorpg.prototype.client.quests;

import java.util.ArrayList;
import java.util.Collection;

public class Progress
{
    private final Collection<ProgressStep> progressSteps = new ArrayList<>();
    
    public boolean isEveryStepsFinished()
    {
        for(ProgressStep step : progressSteps)
            if(!step.isFinished())
                return false;
        return true;
    }
    
    public String getHumanReadableInfo()
    {
        int finishedSteps = getNumberOfFinishedSteps();
        return finishedSteps + " / " + progressSteps.size();
    }
    
    private int getNumberOfFinishedSteps()
    {
        return (int)progressSteps.stream()
                    .filter(step -> step.isFinished())
                    .count();
    }
}
