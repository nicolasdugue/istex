package util;

public class Memory {
	private static Runtime runtime;
	public static void garbageCollector() {
		if (runtime == null)
			runtime= Runtime.getRuntime();
		runtime.gc();
	}
			
}
