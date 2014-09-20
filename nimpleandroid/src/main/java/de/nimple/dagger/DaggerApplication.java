package de.nimple.dagger;

import android.app.Application;

import java.util.Arrays;

import dagger.ObjectGraph;

/**
 * Builds the dagger object graph for injection.
 */
public abstract class DaggerApplication extends Application {
	private ObjectGraph objectGraph;

	@Override
	public void onCreate() {
		super.onCreate();
		objectGraph = ObjectGraph.create(getModules());
	}

	public void inject(Object target) {
		objectGraph.inject(target);
	}

	public Object[] getModules() {
		return Arrays.asList(new NimpleModule(this)).toArray();
	}
}
