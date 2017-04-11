package pl.mmorpg.prototype.server.database.managers;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;

import pl.mmorpg.prototype.server.database.HibernateUtil;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.entities.UserCharacter;

public class UserCharacterTableManager
{
    public static List<UserCharacter> getUserCharacters(User user)
    {
        List<UserCharacter> users = TableManagerHelper.getListOfDataElements("UserCharacter",
                "UserCharacter.user.id = " + user.getId(), UserCharacter.class);
        return users;
    }

    public static List<UserCharacter> getUserCharacters(String username)
    {
        User user = UserTableManager.getUser(username);
        return getUserCharacters(user);
    }

    public static UserCharacter getUserCharacter(int id)
    {
        Session session = HibernateUtil.openSession();
        Query<UserCharacter> getUserCharacterQuery = session
                .createQuery("from " + "UserCharacter" + " as character where character.id = :id", UserCharacter.class)
                .setParameter("id", id);
        UserCharacter character = getUserCharacterQuery.getSingleResult();
        session.close();
        return character;
    }

    public static UserCharacter getUserCharacter(String nickname)
    {
        Session session = HibernateUtil.openSession();
        Query<UserCharacter> getUserCharacterQuery = session
                .createQuery("from UserCharacter as character where character.nickname = :nickname",
                        UserCharacter.class)
                .setParameter("nickname", nickname);
        UserCharacter character = getUserCharacterQuery.getSingleResult();
        session.close();
        return character;
    }

    public static boolean hasUserCharacter(String nickname)
    {
        try
        {
            getUserCharacter(nickname);
            return true;
        } catch (NoResultException e)
        {
            return false;
        }
    }

    public static UserCharacter createCharacter(String nickname, int userId)
    {
        User user = UserTableManager.getUser(userId);
        UserCharacter character = new UserCharacter();
        character.setUser(user);
        character.setNickname(nickname);
        HibernateUtil.makeOperation((session) -> session.save(character));
        return character;
    }
}
