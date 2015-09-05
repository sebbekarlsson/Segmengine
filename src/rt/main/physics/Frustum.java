package rt.main.physics;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * This class will remain undocumented because the author is unknown and the code is stolen.
 * 
 * @author Unknown
 */
public class Frustum {
	public float[][] m_Frustum = new float[6][4];
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	public static final int BOTTOM = 2;
	public static final int TOP = 3;
	public static final int BACK = 4;
	public static final int FRONT = 5;
	public static final int A = 0;
	public static final int B = 1;
	public static final int C = 2;
	public static final int D = 3;
	private static Frustum frustum = new Frustum();

	public static Frustum getFrustum() {
		frustum.calculateFrustum();
		return frustum;
	}

	private void normalizePlane(final float[][] frustum, final int side) {
		final float magnitude = (float) Math.sqrt(frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]);

		frustum[side][0] /= magnitude;
		frustum[side][1] /= magnitude;
		frustum[side][2] /= magnitude;
		frustum[side][3] /= magnitude;
	}

	private final FloatBuffer _proj = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer _modl = BufferUtils.createFloatBuffer(16);
	private final FloatBuffer _clip = BufferUtils.createFloatBuffer(16);
	float[] proj = new float[16];
	float[] modl = new float[16];
	float[] clip = new float[16];

	private void calculateFrustum() {
		_proj.clear();
		_modl.clear();
		_clip.clear();

		GL11.glGetFloat(2983, _proj);

		GL11.glGetFloat(2982, _modl);

		_proj.flip().limit(16);
		_proj.get(proj);
		_modl.flip().limit(16);
		_modl.get(modl);

		clip[0] = modl[0] * proj[0] + modl[1] * proj[4] + modl[2] * proj[8] + modl[3] * proj[12];
		clip[1] = modl[0] * proj[1] + modl[1] * proj[5] + modl[2] * proj[9] + modl[3] * proj[13];
		clip[2] = modl[0] * proj[2] + modl[1] * proj[6] + modl[2] * proj[10] + modl[3] * proj[14];
		clip[3] = modl[0] * proj[3] + modl[1] * proj[7] + modl[2] * proj[11] + modl[3] * proj[15];

		clip[4] = modl[4] * proj[0] + modl[5] * proj[4] + modl[6] * proj[8] + modl[7] * proj[12];
		clip[5] = modl[4] * proj[1] + modl[5] * proj[5] + modl[6] * proj[9] + modl[7] * proj[13];
		clip[6] = modl[4] * proj[2] + modl[5] * proj[6] + modl[6] * proj[10] + modl[7] * proj[14];
		clip[7] = modl[4] * proj[3] + modl[5] * proj[7] + modl[6] * proj[11] + modl[7] * proj[15];

		clip[8] = modl[8] * proj[0] + modl[9] * proj[4] + modl[10] * proj[8] + modl[11] * proj[12];
		clip[9] = modl[8] * proj[1] + modl[9] * proj[5] + modl[10] * proj[9] + modl[11] * proj[13];
		clip[10] = modl[8] * proj[2] + modl[9] * proj[6] + modl[10] * proj[10] + modl[11] * proj[14];
		clip[11] = modl[8] * proj[3] + modl[9] * proj[7] + modl[10] * proj[11] + modl[11] * proj[15];

		clip[12] = modl[12] * proj[0] + modl[13] * proj[4] + modl[14] * proj[8] + modl[15] * proj[12];
		clip[13] = modl[12] * proj[1] + modl[13] * proj[5] + modl[14] * proj[9] + modl[15] * proj[13];
		clip[14] = modl[12] * proj[2] + modl[13] * proj[6] + modl[14] * proj[10] + modl[15] * proj[14];
		clip[15] = modl[12] * proj[3] + modl[13] * proj[7] + modl[14] * proj[11] + modl[15] * proj[15];

		m_Frustum[0][0] = clip[3] - clip[0];
		m_Frustum[0][1] = clip[7] - clip[4];
		m_Frustum[0][2] = clip[11] - clip[8];
		m_Frustum[0][3] = clip[15] - clip[12];

		normalizePlane(m_Frustum, 0);

		m_Frustum[1][0] = clip[3] + clip[0];
		m_Frustum[1][1] = clip[7] + clip[4];
		m_Frustum[1][2] = clip[11] + clip[8];
		m_Frustum[1][3] = clip[15] + clip[12];

		normalizePlane(m_Frustum, 1);

		m_Frustum[2][0] = clip[3] + clip[1];
		m_Frustum[2][1] = clip[7] + clip[5];
		m_Frustum[2][2] = clip[11] + clip[9];
		m_Frustum[2][3] = clip[15] + clip[13];

		normalizePlane(m_Frustum, 2);

		m_Frustum[3][0] = clip[3] - clip[1];
		m_Frustum[3][1] = clip[7] - clip[5];
		m_Frustum[3][2] = clip[11] - clip[9];
		m_Frustum[3][3] = clip[15] - clip[13];

		normalizePlane(m_Frustum, 3);

		m_Frustum[4][0] = clip[3] - clip[2];
		m_Frustum[4][1] = clip[7] - clip[6];
		m_Frustum[4][2] = clip[11] - clip[10];
		m_Frustum[4][3] = clip[15] - clip[14];

		normalizePlane(m_Frustum, 4);

		m_Frustum[5][0] = clip[3] + clip[2];
		m_Frustum[5][1] = clip[7] + clip[6];
		m_Frustum[5][2] = clip[11] + clip[10];
		m_Frustum[5][3] = clip[15] + clip[14];

		normalizePlane(m_Frustum, 5);
	}

	public boolean pointInFrustum(final float x, final float y, final float z) {
		for (int i = 0; i < 6; i++) {
			if (m_Frustum[i][0] * x + m_Frustum[i][1] * y + m_Frustum[i][2] * z + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
		}
		return true;
	}

	public boolean sphereInFrustum(final float x, final float y, final float z, final float radius) {
		for (int i = 0; i < 6; i++) {
			if (m_Frustum[i][0] * x + m_Frustum[i][1] * y + m_Frustum[i][2] * z + m_Frustum[i][3] <= -radius) {
				return false;
			}
		}
		return true;
	}

	public boolean cubeFullyInFrustum(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
		for (int i = 0; i < 6; i++) {
			if (m_Frustum[i][0] * x1 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x2 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x1 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x2 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x1 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x2 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x1 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
			if (m_Frustum[i][0] * x2 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
		}
		return true;
	}

	public boolean cubeInFrustum(final float x1, final float y1, final float z1, final float x2, final float y2, final float z2) {
		for (int i = 0; i < 6; i++) {
			if (m_Frustum[i][0] * x1 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x2 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x1 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x2 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z1 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x1 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x2 + m_Frustum[i][1] * y1 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x1 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F
					&& m_Frustum[i][0] * x2 + m_Frustum[i][1] * y2 + m_Frustum[i][2] * z2 + m_Frustum[i][3] <= 0.0F) {
				return false;
			}
		}
		return true;
	}

	public boolean isVisible(final AABB aabb) {
		return cubeInFrustum(aabb.x0, aabb.y0, aabb.z0, aabb.x1, aabb.y1, aabb.z1);
	}
}