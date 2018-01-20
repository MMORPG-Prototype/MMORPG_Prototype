package pl.mmorpg.prototype.client.post.proccessing;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderBatch extends SpriteBatch {
	
	private final String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ "uniform mat4 u_projTrans;\n" //
		+ "varying vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "\n" //
		+ "void main()\n" //
		+ "{\n" //
		+ "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
		+ "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
		+ "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
		+ "}\n";
	
	private final String fragmentShader = "#ifdef GL_ES\n" //
		+ "#define LOWP lowp\n" //
		+ "precision mediump float;\n" //
		+ "#else\n" //
		+ "#define LOWP \n" //
		+ "#endif\n" //
		+ "varying LOWP vec4 v_color;\n" //
		+ "varying vec2 v_texCoords;\n" //
		+ "uniform sampler2D u_texture;\n" //
		+ "uniform float brightness;\n" //
		+ "uniform float contrast;\n" //
		+ "void main()\n"//
		+ "{\n" //
		+ "  vec4 color = v_color * texture2D(u_texture, v_texCoords);\n" 
		+ "  color.rgb /= color.a;\n" //ignore alpha
		+ "  color.rgb = ((color.rgb - 0.5) * max(contrast, 0.0)) + 0.5;\n" //apply contrast
		+ "  color.rgb += brightness;\n" //apply brightness
		+ "  color.rgb *= color.a;\n" //return alpha
		+ "  gl_FragColor = color;\n"
		+ "}";
	

	public ShaderBatch(float brightness, float contrast)
	{
		ShaderProgram.pedantic = false;
		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		setShader(shader);
		shader.begin();
		int brightnessLoc = shader.getUniformLocation("brightness");
		int contrastLoc = shader.getUniformLocation("contrast");
		shader.setUniformf(brightnessLoc, brightness);
		shader.setUniformf(contrastLoc, contrast);
		shader.end();
	}
}