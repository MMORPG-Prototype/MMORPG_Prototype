package pl.mmorpg.prototype.server;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;
import pl.mmorpg.prototype.data.entities.repositories.UserRepository;
import pl.mmorpg.prototype.server.commandUtils.CommandHandler;
import pl.mmorpg.prototype.data.entities.User;
import pl.mmorpg.prototype.server.database.seeders.DatabaseSeeder;
import pl.mmorpg.prototype.server.exceptions.CannotBindServerException;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;

public class GameServer extends ApplicationAdapter
{
    private Batch batch;
    private Server server;
    private PlayState playState;

    private final Map<Integer, UserInfo> loggedUsersKeyUserId = new ConcurrentHashMap<>();
    private final Map<Integer, User> authenticatedClientsKeyClientId = new ConcurrentHashMap<>();

    @Override
    public void create()
    {
        startCommandHandlerOnNewThread();
        Assets.loadAssets();
        batch = Assets.getBatch();
        server = initializeServer();
        seedDatabaseIfEmpty();

        playState = new PlayState(server);
        server.addListener(
                new ServerListener(loggedUsersKeyUserId, authenticatedClientsKeyClientId, server, playState));
        bindServer();
    }

    private void seedDatabaseIfEmpty()
    {
        boolean emptyDatabase = !SpringContext.getBean(UserRepository.class).findAll().iterator().hasNext();
        if (emptyDatabase)
            new DatabaseSeeder().seed();
    }

    private void startCommandHandlerOnNewThread()
    {
        Log.info("NOTE: you can now type commands here.");
        CommandHandler commandHandler = new CommandHandler();
        Runnable commandHandlingTask = () ->
        {
            try (Scanner scanner = new Scanner(System.in))
            {
                while (true)
                {
                    String command = scanner.nextLine();
                    commandHandler.handle(command);
                }
            }
        };
        new Thread(commandHandlingTask).start();
    }

    private Server initializeServer()
    {
        server = new Server();
        PacketsRegisterer.registerPackets(server.getKryo());
        return server;
    }

    private void bindServer()
    {
        server.start();
        try
        {
            server.bind(Settings.TCP_PORT, Settings.UDP_PORT);
        } catch (IOException e)
        {
            throw new CannotBindServerException(e.getMessage());
        }
    }

    @Override
    public void render()
    {
        update();
        clearScreen();
        batch.begin();
        playState.render(batch);
        batch.end();
    }

    private void update()
    {
    	playState.update(Gdx.graphics.getDeltaTime());
    }

    private void clearScreen()
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void dispose()
    {
        Assets.dispose();
        batch.dispose();
        System.exit(0);
    }

}
