package pl.mmorpg.prototype.server.database.jsonconfig;

import org.hibernate.usertype.UserType;

import pl.mmorpg.prototype.server.quests.QuestTask;

public class QuestTaskJsonUserType extends JsonUserType implements UserType
{
    @Override
    public Class<QuestTask> returnedClass()
    {
        return QuestTask.class;
    }
}
