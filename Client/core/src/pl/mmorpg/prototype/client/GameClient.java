package pl.mmorpg.prototype.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import pl.mmorpg.prototype.client.resources.Assets;
import pl.mmorpg.prototype.client.states.PlayState;
import pl.mmorpg.prototype.client.states.SettingsChoosingState;
import pl.mmorpg.prototype.client.states.StateManager;
import pl.mmorpg.prototype.clientservercommon.registering.PacketsRegisterer;

public class GameClient extends ApplicationAdapter
{
    private SpriteBatch batch;
    private Client client;
    private StateManager states;
    private PlayState playState;
    private ClientListener clientListener;

    private Texture mousePointer;
    private Texture background;

    @Override
    public void create()
    {
        mousePointer = Assets.get("cursor.gif");
        batch = Assets.getBatch();
        background = Assets.get("background.jpg");
        states = new StateManager();
        client = new Client();
        playState = new PlayState(states, client);
        client = initizlizeClient();
        states.push(playState);
        states.push(new SettingsChoosingState(client, states));
        // Gdx.input.setCursorCatched(true);
    }

    private Client initizlizeClient()
    {
        Kryo kryo = client.getKryo();
        kryo = PacketsRegisterer.registerAllAnnotated(kryo);
        clientListener = new ClientListener(playState, states);
        client.addListener(clientListener);
        client.start();
        return client;
    }

    @Override
    public void render()
    {
        update();
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(Color.WHITE);
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        states.render(batch);
        batch.begin();
        batch.draw(mousePointer, Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY() - 24, 24, 24);
        batch.end();
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
