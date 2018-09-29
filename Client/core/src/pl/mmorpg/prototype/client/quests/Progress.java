package pl.mmorpg.prototype.client.quests;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Progress
{
	private final QuestTask rootTask;
	private final int[] finishedQuestTasksPath;

	public Progress(QuestTask rootTask, int[] finishedQuestTasksPath)
	{
		this.rootTask = rootTask;
		this.finishedQuestTasksPath = finishedQuestTasksPath;
	}

	public boolean isEveryStepsFinished()
	{
		return getCurrentTasks().length == 0;
	}

	private QuestTask getLastFinishedTask()
	{
		AtomicReference<QuestTask> currentTask = new AtomicReference<>(rootTask);
		Arrays.stream(finishedQuestTasksPath)
				.skip(1)
				.forEach(index -> currentTask.set(currentTask.get().getNextTasks()[index]));
		return currentTask.get();
	}

	public String getHumanReadableInfo()
	{
		int finishedSteps = getNumberOfFinishedSteps();
		return finishedSteps + " / " + getQuestLength();
	}

	private int getQuestLength()
	{
		int length = 1;
		QuestTask currentTask = rootTask;
		while (currentTask.getNextTasks().length != 0)
		{
			currentTask = currentTask.getNextTasks()[0];
			length++;
		}
		return length;
	}

	private int getNumberOfFinishedSteps()
	{
		return finishedQuestTasksPath.length;
	}

	public QuestTask[] getCurrentTasks()
	{
		return getLastFinishedTask().getNextTasks();
	}
}
