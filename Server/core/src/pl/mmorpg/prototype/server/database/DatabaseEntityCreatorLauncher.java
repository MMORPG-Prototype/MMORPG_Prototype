package pl.mmorpg.prototype.server.database;

import pl.mmorpg.prototype.server.database.seeders.CharacterItemsTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.UserCharactersTableSeeder;
import pl.mmorpg.prototype.server.database.seeders.UsersTableSeeder;

public class DatabaseEntityCreatorLauncher
{
    public static void main(String[] args)
    {
        new UsersTableSeeder().seed();
        new UserCharactersTableSeeder().seed();
        new CharacterItemsTableSeeder().seed();
    }

}
