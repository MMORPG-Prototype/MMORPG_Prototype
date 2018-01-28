package pl.mmorpg.prototype.server.states;

import com.badlogic.gdx.graphics.Camera;

public class PlayStateInputActions
{
	private final static float cameraMoveSpeed = 20.0f;
	private Camera camera;

	public PlayStateInputActions(Camera camera)
	{
		this.camera = camera;
	}

	public void moveCameraLeft()
	{
		camera.position.x -= cameraMoveSpeed * cameraArea();
	}

	private float cameraArea()
	{
		return (float) (Math.sqrt(camera.viewportHeight * camera.viewportHeight) / 1000.0f);
	}

	public void moveCameraRight()
	{
		camera.position.x += cameraMoveSpeed * cameraArea();
	}

	public void moveCameraUp()
	{
		camera.position.y += cameraMoveSpeed * cameraArea();
	}

	public void moveCameraDown()
	{
		camera.position.y -= cameraMoveSpeed * cameraArea();
	}

	public void zoomCameraOut()
	{
		float aspectRatio = camera.viewportWidth / camera.viewportHeight;
		camera.viewportWidth += cameraMoveSpeed * aspectRatio * cameraArea();
		camera.viewportHeight += cameraMoveSpeed * cameraArea();
		stayAtMinimumBound(aspectRatio);
	}

	private void stayAtMinimumBound(float aspectRatio)
	{
		if (camera.viewportWidth < 10 * aspectRatio)
			camera.viewportWidth = 10 * aspectRatio;
		if (camera.viewportHeight < 10)
			camera.viewportHeight = 10;
	}

	public void zoomCameraOut(float speedFactor)
	{
		float aspectRatio = camera.viewportWidth / camera.viewportHeight;
		camera.viewportWidth += speedFactor * cameraMoveSpeed * aspectRatio * cameraArea();
		camera.viewportHeight += speedFactor * cameraMoveSpeed * cameraArea();
		stayAtMinimumBound(aspectRatio);
	}

	public void zoomCameraIn()
	{
		float aspectRatio = camera.viewportWidth / camera.viewportHeight;
		camera.viewportWidth -= cameraMoveSpeed * aspectRatio * cameraArea();
		camera.viewportHeight -= cameraMoveSpeed * cameraArea();
		stayAtMinimumBound(aspectRatio);
	}

	public void zoomCameraIn(float speedFactor)
	{
		float aspectRatio = camera.viewportWidth / camera.viewportHeight;
		camera.viewportWidth -= speedFactor * cameraMoveSpeed * aspectRatio * cameraArea();
		camera.viewportHeight -= speedFactor * cameraMoveSpeed * cameraArea();
		stayAtMinimumBound(aspectRatio);
	}
}
