package pl.mmorpg.prototype.server;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

import pl.mmorpg.prototype.clientservercommon.Settings;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;
import pl.mmorpg.prototype.server.commandUtils.CommandHandler;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.exceptions.CannotBindServerException;
import pl.mmorpg.prototype.server.resources.Assets;
import pl.mmorpg.prototype.server.states.PlayState;
import pl.mmorpg.prototype.server.states.StateManager;

public class GameServer extends ApplicationAdapter
{
    private Batch batch;
    private StateManager states;
    private Server server;
    private PlayState playState;

    private final Map<Integer, UserInfo> loggedUsersKeyUserId = new ConcurrentHashMap<>();
    private final Map<Integer, User> authenticatedClientsKeyClientId = new ConcurrentHashMap<>();

    @Override
    public void create()
    {
        startCommandHandlerOnNewThread();
        Assets.loadAssets();
        states = new StateManager();
        batch = Assets.getBatch();
        server = initializeServer();

        playState = new PlayState(server, states);
        server.addListener(
                new ServerListener(loggedUsersKeyUserId, authenticatedClientsKeyClientId, server, playState));
        bindServer();
        states.push(playState);
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
        Kryo serverKryo = server.getKryo();
        serverKryo = PacketsRegisterer.registerPackets(serverKryo);
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
        states.render(batch);
        batch.end();
    }

    private void update()
    {
        states.update(Gdx.graphics.getDeltaTime());
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
    }

}
