package pl.mmorpg.prototype.client;

import java.util.function.Consumer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.packethandlers.PacketHandlerDispatcher;
import pl.mmorpg.prototype.client.packethandlers.PacketHandlerRegisterer;
import pl.mmorpg.prototype.client.packethandlers.SimplePacketHandlerRegisterer;
import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.ConnectionState;
import pl.mmorpg.prototype.client.states.DefaultConnectionInfo;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.State;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;

public class GameClient extends ApplicationAdapter
{
    private SpriteBatch batch;
    private Client client;
    private StateManager states;
    private ClientListener clientListener;

    private Texture mousePointer;
    private Texture background;
    
    @Override
    public void create()
    {
        mousePointer = Assets.get("cursor.gif"); 
        batch = Assets.getBatch();
        background = Assets.get("background.jpg");
        PacketHandlerDispatcher dispatcher = new PacketHandlerDispatcher();
        PacketHandlerRegisterer registerer = new SimplePacketHandlerRegisterer(dispatcher);
        Consumer<State> disposeState = state -> state.unregisterHandlers(registerer);
		states = new StateManager(disposeState);
        client = new Client();
        PlayState playState = new PlayState(states, client, registerer);
        client = initializeClient(dispatcher);
        states.push(playState);
        states.push(new ConnectionState(client, states, registerer, new DefaultConnectionInfo()));
        // Gdx.input.setCursorCatched(true); 
    } 

    private Client initializeClient(PacketHandlerDispatcher dispatcher)
    {
        PacketsRegisterer.registerPackets(client.getKryo());
        clientListener = new ClientListener(dispatcher);
        client.addListener(clientListener);
        client.start();
        return client;
    }

    @Override
    public void render()
    {
        update();
        clearScreen();
        Batch backupBatch = Assets.getBackupBatch();
        renderBackground(backupBatch);
        states.render(batch);
        renderCursor(backupBatch);
    }

	private void clearScreen()
	{
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private void renderBackground(Batch backupBatch)
	{
		backupBatch.begin();
        backupBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        backupBatch.end();
	}
	
	private void renderCursor(Batch backupBatch)
	{
		backupBatch.begin();
        backupBatch.draw(mousePointer, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - 24, 24, 24);
        backupBatch.end();
	}

    private void update()
    {
        states.update(Gdx.graphics.getDeltaTime());
        clientListener.tryHandlingUnhandledPackets();
        dontAllowMouseToGoOutOfWindow();
    }

    private void dontAllowMouseToGoOutOfWindow()
    {
        if (Gdx.input.getX() < 0) 
            Gdx.input.setCursorPosition(0, Gdx.input.getY());
        if (Gdx.input.getY() < 0)
            Gdx.input.setCursorPosition(Gdx.input.getX(), 0);
        if (Gdx.input.getX() > Gdx.graphics.getWidth())
            Gdx.input.setCursorPosition(Gdx.graphics.getWidth(), Gdx.input.getY());
        if (Gdx.input.getY() > Gdx.graphics.getHeight())
            Gdx.input.setCursorPosition(Gdx.input.getX(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose()
    {
        client.close();
        Assets.dispose();
    }
}
