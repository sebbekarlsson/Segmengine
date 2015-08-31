package rt.main.physics;

public class AABB {
	private final float epsilon = 0.0F;
	public float x0;
	public float y0;
	public float z0;
	public float x1;
	public float y1;
	public float z1;

	public AABB(final float x0, final float y0, final float z0, final float x1, final float y1, final float z1) {
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
	}

	public AABB expand(final float xa, final float ya, final float za) {
		float _x0 = x0;
		float _y0 = y0;
		float _z0 = z0;
		float _x1 = x1;
		float _y1 = y1;
		float _z1 = z1;
		if (xa < 0.0F) {
			_x0 += xa;
		}
		if (xa > 0.0F) {
			_x1 += xa;
		}
		if (ya < 0.0F) {
			_y0 += ya;
		}
		if (ya > 0.0F) {
			_y1 += ya;
		}
		if (za < 0.0F) {
			_z0 += za;
		}
		if (za > 0.0F) {
			_z1 += za;
		}
		return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
	}

	public AABB grow(final float xa, final float ya, final float za) {
		final float _x0 = x0 - xa;
		final float _y0 = y0 - ya;
		final float _z0 = z0 - za;
		final float _x1 = x1 + xa;
		final float _y1 = y1 + ya;
		final float _z1 = z1 + za;

		return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
	}

	public float clipXCollide(final AABB c, float xa) {
		if (c.y1 <= y0 || c.y0 >= y1) {
			return xa;
		}
		if (c.z1 <= z0 || c.z0 >= z1) {
			return xa;
		}
		if (xa > 0.0F && c.x1 <= x0) {
			final float max = x0 - c.x1 - epsilon;
			if (max < xa) {
				xa = max;
			}
		}
		if (xa < 0.0F && c.x0 >= x1) {
			final float max = x1 - c.x0 + epsilon;
			if (max > xa) {
				xa = max;
			}
		}
		return xa;
	}

	public float clipYCollide(final AABB c, float ya) {
		if (c.x1 <= x0 || c.x0 >= x1) {
			return ya;
		}
		if (c.z1 <= z0 || c.z0 >= z1) {
			return ya;
		}
		if (ya > 0.0F && c.y1 <= y0) {
			final float max = y0 - c.y1 - epsilon;
			if (max < ya) {
				ya = max;
			}
		}
		if (ya < 0.0F && c.y0 >= y1) {
			final float max = y1 - c.y0 + epsilon;
			if (max > ya) {
				ya = max;
			}
		}
		return ya;
	}

	public float clipZCollide(final AABB c, float za) {
		if (c.x1 <= x0 || c.x0 >= x1) {
			return za;
		}
		if (c.y1 <= y0 || c.y0 >= y1) {
			return za;
		}
		if (za > 0.0F && c.z1 <= z0) {
			final float max = z0 - c.z1 - epsilon;
			if (max < za) {
				za = max;
			}
		}
		if (za < 0.0F && c.z0 >= z1) {
			final float max = z1 - c.z0 + epsilon;
			if (max > za) {
				za = max;
			}
		}
		return za;
	}

	public boolean intersects(final AABB c) {
		if (c.x1 <= x0 || c.x0 >= x1) {
			return false;
		}
		if (c.y1 <= y0 || c.y0 >= y1) {
			return false;
		}
		if (c.z1 <= z0 || c.z0 >= z1) {
			return false;
		}
		return true;
	}

	public void move(final float xa, final float ya, final float za) {
		x0 += xa;
		y0 += ya;
		z0 += za;
		x1 += xa;
		y1 += ya;
		z1 += za;
	}
}