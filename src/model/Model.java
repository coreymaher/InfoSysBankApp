package model;

public abstract class Model {
	
	private boolean loaded = false;
	
	public void save() {
		if (isLoaded()) {
			update();
		} else {
			insert();
		}
	}

	public void setIsLoaded() {
		loaded = true;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public abstract boolean isChanged();
	protected abstract void loadData();
	protected abstract void update();
	protected abstract void insert();

}
