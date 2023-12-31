/**
 * LibLoader
 * Copyright © 2007-2017 Jérôme Jouvie
 *
 * Created on 25 mar. 2007
 * @version file v1.0.0
 *
 * To contact me:
 * jerome.jouvie@gmail.com
 * http://jerome.jouvie.free.fr/
 *
 * INTRODUCTION
 * This project enhance the Java's System.loadLibrary and allows:
 * - library loading without loading dependencies
 * - search libraries from "java.library.path", "sun.jnlp.applet.launcher", "org.lwjgl.librarypath"
 * - library loading from applet
 *
 *
 * GNU LESSER GENERAL PUBLIC LICENSE
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1 of the License,
 * or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the
 * Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */

package jouvieje.libloader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * Utility to load library<BR>
 * <ul>
 * <li>System.load</li>
 * <li>System.loadLibrary</li>
 * <li>JNLPAppletLauncher</li>
 * <li>LWJGL applet launcher</li>
 * </ul>
 */
public final class LibLoader {
	/** Windows platform */
	public final static int PLATFORM_WINDOWS = 1;
	/** Linux platform */
	public final static int PLATFORM_LINUX = 2;
	/** Mac platform */
	public final static int PLATFORM_MAC = 3;
	/** SOLARIS platform */
	public final static int PLATFORM_SOLARIS = 4;

	private final static String NOT_RECOGNIZED = "Unrecognized platform";
	private final static String x64 = "64";
	private final static String lib = "lib";
	private final static String dll = ".dll";
	private final static String so = ".so";
	private final static String jnilib = ".jnilib";
	
	/** Prints debug output in System.out.<BR>
	 * Can be forced with JVM paramter <code>-Djouvieje.libloader.debug=true</code> */
	public static boolean DEBUG = false;
	/** Are libraries loaded ? */
	private static boolean librariesLoaded = false;
	/** Platform */
	private static int platform = -1;
	/** Platform bitness */
	private static boolean platform64 = false;

	/* Internal Utilities */
	
	/** @return true if LibLoader library is loaded
	 *  @see # */
	protected static boolean isLibLoaderLibsLoaded() {
		if(!librariesLoaded) {
			if(getPlatform() == PLATFORM_LINUX) {
				final String[] libLoaderName;
				if(isPlatform64Bits()) {
					libLoaderName = new String[]{"LibLoader64", "libLibLoader64.so"};
				}
				else {
					libLoaderName = new String[]{"LibLoader", "libLibLoader.so"};
				}
				if(loadWithSystem(libLoaderName[0], libLoaderName[1])) {
					librariesLoaded = true;
				}
			}
		}
		return librariesLoaded;
	}
	
	/**
	 * @see #PLATFORM_WINDOWS
	 * @see #PLATFORM_LINUX
	 * @see #PLATFORM_MAC
	 * @return the current platform type
	 */
	public static int getPlatform() {
		if(platform == -1) {
			final String osName = getProperty("os.name").toLowerCase();
			final String osArch = getProperty("os.arch");
			
			log("os.name: "+osName);
			log("os.arch: "+osArch);
			
			boolean bits64 = (osArch.indexOf("64") != -1);
			if (osName.startsWith("win")) {
				platform = PLATFORM_WINDOWS;
			}
			else if (osName.startsWith("linux") || osName.startsWith("freebsd") || osName.startsWith("sunos")) {
				platform = PLATFORM_LINUX;
			}
			else if (osName.startsWith("mac")) {
				platform = PLATFORM_MAC;
			}
			else if (osName.startsWith("Solaris") || osName.startsWith("SunOS")) {
				platform = PLATFORM_SOLARIS;
				if(osArch.indexOf("sparcv9") != -1) {
					bits64 = true;
				}
			}
			else {
				throw new RuntimeException(NOT_RECOGNIZED);
			}
			platform64 = bits64;
		}
		return platform;
	}
	
	public static boolean isPlatform64Bits() {
		getPlatform(); //Initialize platform64
		return platform64;
	}
	
	public static boolean isWindowsCE() {
		final String osName = getProperty("os.name").toLowerCase();
		return (osName.indexOf("ce") != -1);
	}
	
	/* Utility to access system properties */
	private static String getProperty(final String prop) {
		return (String)AccessController.doPrivileged(new PrivilegedAction() { public Object run() {
			return System.getProperty(prop);
		}});
	}
	private static boolean exists(final File file) {
		return ((Boolean)AccessController.doPrivileged(new PrivilegedAction() { public Object run() {
			return new Boolean(file.exists());
		}})).booleanValue();
	}

	/* Loading Interface */

	/** @return true if the library has been loaded */
	public static boolean loadLibrary(String libraryName, boolean libLoaderFirst) {
		return loadLibrary(defaultPlatformSpecificConfig(libraryName), libLoaderFirst);
	}
	/** @return true if the library has been loaded */
	private static LibraryConfig defaultPlatformSpecificConfig(String libraryName) {
		String libLibraryName = (libraryName.startsWith(lib)) ? libraryName : lib + libraryName;
		
		final LibraryConfig libConfig = new LibraryConfig();
		libConfig.windowsLibraries = new PlatformLibrary(libraryName, libraryName+dll);
		libConfig.windows64Libraries = new PlatformLibrary(libraryName+x64, libraryName+x64+dll);
		libConfig.linuxLibraries = new PlatformLibrary(libraryName, libLibraryName+so);
		libConfig.linux64Libraries = new PlatformLibrary(libraryName+x64, libLibraryName+x64+so);
		libConfig.macLibraries = new PlatformLibrary(libraryName, libLibraryName+jnilib);
		
		return libConfig;
	}
	
	/** @return true if the library has been loaded */
	public static boolean loadLibrary(LibraryConfig libConfig, boolean libLoaderFirst) {
		switch(getPlatform()) {
			case PLATFORM_WINDOWS:
				if(isPlatform64Bits()) {
					if(loadLibrary(libConfig.windows64Libraries, libLoaderFirst)) {
						return true;
					}
				}
				else {
					if(loadLibrary(libConfig.windowsLibraries, libLoaderFirst)) {
						return true;
					}
				}
				// In last ressort, try CE
				if(loadLibrary(libConfig.windowsCeLibraries, libLoaderFirst)) {
					return true;
				}
				break;
			case PLATFORM_LINUX:
				if(isPlatform64Bits()) {
					if(loadLibrary(libConfig.linux64Libraries, libLoaderFirst)) {
						return true;
					}
				}
				else {
					if(loadLibrary(libConfig.linuxLibraries, libLoaderFirst)) {
						return true;
					}
				}
				break;
			case PLATFORM_MAC:
				if(loadLibrary(libConfig.macLibraries, libLoaderFirst)) {
					return true;
				}
				break;
		}
		return false;
	}
	
	/* Loading Implementation */
	
	/** @return true if the library has been loaded */
	private static boolean loadLibrary(PlatformLibrary library, boolean libLoaderFirst) {
		return (library != null) && loadLibrary(library.libraryName, library.libraryFullName, libLoaderFirst);
	}
	/** @return true if the library has been loaded */
	public static boolean loadLibrary(final String libraryName, final String libraryFullName, boolean libLoaderFirst) {
		checkDebug();
		if(libLoaderFirst) {
			log("loadWithLibLoader");
			if(loadWithLibLoader(libraryName, libraryFullName)) {
				return true;
			}
			log("loadWithSystem");
			if(loadWithSystem(libraryName, libraryFullName)) {
				return true;
			}
		}
		else {
			log("loadWithSystem");
			if(loadWithSystem(libraryName, libraryFullName)) {
				return true;
			}
			log("loadWithLibLoader");
			if(loadWithLibLoader(libraryName, libraryFullName)) {
				return true;
			}
		}
		return false;
	}

	/* Internal: Load library with System */
	private static boolean loadWithSystem(final String libraryName, final String fullLibraryName) {
		//Find library pathes
		final Vector libraryPaths = new Vector();
		findLibraryPathes(libraryPaths, fullLibraryName);
		extractLibrary(libraryPaths, fullLibraryName);
		findSystemPathes(libraryPaths);


		//Load with System.load
		for(Iterator i = libraryPaths.iterator(); i.hasNext(); ) {
			final String path = (String)i.next();
			final File file = new File(path, fullLibraryName);
			if(!exists(file)) {
				log("NOT_FOUND=" + path + " / " + fullLibraryName);
				continue;
			}
			
			log("TRY=" + file.getAbsolutePath());
			final Boolean res = (Boolean)AccessController.doPrivileged(new PrivilegedAction() { public Object run() {
				try {
					System.load(file.getAbsolutePath());
					log("LOADED=" + fullLibraryName + " | FROM=" + path);
					return Boolean.TRUE;
				} catch(Throwable t) {
					log("ERROR="+t.getMessage());
					return Boolean.FALSE;
				}
			}});
			if(res == Boolean.TRUE) {
				return true;
			}
		}
		
		//Load with System.loadLibrary
		Boolean res = (Boolean)AccessController.doPrivileged(new PrivilegedAction() { public Object run() {
			try {
				log("TRY=" + libraryName);
				System.loadLibrary(libraryName);
				log("LOADED=" + libraryName);
				return Boolean.TRUE;
			} catch(Throwable t) {
				log("ERROR="+t.getMessage());
				return Boolean.FALSE;
			}
		}});
		if(res == Boolean.TRUE) {
			return true;
		}
		
		//Use applet-launcher


		return false;
	}
	
	/** Internal: Load library with LibLoader
	 * Loads a library with <code>RTLD_NOW | RTLD_GLOBAL</code> mode<BR>
	 * <I>(<code>RTLD_NOW | RTLD_GLOBAL</code> mean external symbol will be available for other libraries)</I>.
	 * @param lib library <B><U>FULL</U></B> file name or absolute/relative path (including <B><U>FULL</U></B> library name)
	 */
	private static boolean loadWithLibLoader(final String libraryName, final String fullLibraryName) throws UnsatisfiedLinkError {
		if(!isLibLoaderLibsLoaded()) {
			return false;
		}

		//Find library pathes
		final Vector libraryPaths = new Vector();
		findLibraryPathes(libraryPaths, fullLibraryName);
		extractLibrary(libraryPaths, fullLibraryName);
		findSystemPathes(libraryPaths);
		
		//Load from known search path
		for(Iterator i = libraryPaths.iterator(); i.hasNext(); ) {
			final String path = (String)i.next();
			final File file = new File(path, fullLibraryName);
			if(!exists(file)) {
				log("NOT_FOUND: " + path + " / " + fullLibraryName);
				continue;
			}
			
			final int mode = RTLD.RTLD_NOW | RTLD.RTLD_GLOBAL;
			log("TRY: " + file.getAbsolutePath());
			final long handle = LibLoaderJNI.dlopen(file.getAbsolutePath().getBytes(), mode);
			if(handle == 0) {
				log("ERROR: " + LibLoaderJNI.dlerror());
			}
			else {
				log(fullLibraryName + " loaded from " + path + " [handle=0x" + Long.toHexString(handle) + "]");
				LibLoaderJNI.dlerror();	/* Clear any error */
				return true;
			}
		}
		
		//Use applet-launcher

		return false;
	}
	/** @return the handle of the library opened
	 *  @throws UnsatisfiedLinkError if failed. */
	private static long openLibrary(String fullPath) {
		if(!isLibLoaderLibsLoaded()) {
			return 0;
		}

		// Loading mode
		final int mode = RTLD.RTLD_NOW | RTLD.RTLD_GLOBAL;
		// Actually load the library
		final long handle = LibLoaderJNI.dlopen(fullPath.getBytes(), mode);
		if(handle == 0) {
			log("ERROR: "+LibLoaderJNI.dlerror());
		} else {
			log("Library successfully loaded from "+fullPath+" [handle=0x"+Long.toHexString(handle)+"]");
		}

		LibLoaderJNI.dlerror();	/* Clear any error */
		return handle;
	}
	/** @param handle handle of the librayr to close
	 *  @throws UnsatisfiedLinkError if failed. */
	private static void closeLibrary(long handle) {
		if(!isLibLoaderLibsLoaded() || handle == 0) {
			return;
		}

		final int error = LibLoaderJNI.dlclose(handle);
		if(error != 0) {
			log("Fail to close library.");
			String s = LibLoaderJNI.dlerror();
			log("ERROR: " + s);
			throw new UnsatisfiedLinkError(s);
		}
		else {
			log("Library sucessfully closed.");
		}
	}
	
	/** @param libraryPathes destination for possible system pathes for the specified library */
	private static void findSystemPathes(Vector libraryPathes) {
		// From java.library.path
		addSystemPathes(libraryPathes, getProperty("java.library.path"));
		// From org.lwjgl.librarypath
		addSystemPathes(libraryPathes, getProperty("org.lwjgl.librarypath"));
	}
	private static void addSystemPathes(Vector libraryPathes, String pathes) {
		if(pathes != null) {
			final StringTokenizer tokens = new StringTokenizer(pathes, File.pathSeparator);
			while(tokens.hasMoreTokens()) {
				libraryPathes.add(tokens.nextToken());
			}
		}
	}
	/** @param libraryPathes destination for possible pathes for the specified library */
	private static void findLibraryPathes(Vector libraryPathes, String fullName) {
		//Full path ?
		final File file = new File(fullName);
		if(file.isAbsolute()) {
			final File folder;
			if(!file.isDirectory()) {
				folder = file.getParentFile();
			}
			else {
				folder = file;
			}
			libraryPathes.add(folder.getAbsoluteFile());
		}
	}
	private static void extractLibrary(Vector libraryPathes, String fullName) {	//FIXME Do in the last case
		//Extract library
		final String extractionDir = extractLibraryFromClasspath(fullName);
		if(extractionDir != null) {
			libraryPathes.add(extractionDir);
		}
	}
	/** @return the folder where the library has been extracted. Null if nothing has been extracted. */
	private static String extractLibraryFromClasspath(final String fullName) {
		return (String)AccessController.doPrivileged(new PrivilegedAction() { public Object run() {
			final String OPEN_FOR_READING = "Opening for reading: ";
			try {
				final String pathInJar = "/"+fullName;
				log(OPEN_FOR_READING+pathInJar);
				InputStream is = new LibLoader().getClass().getResourceAsStream(pathInJar);
				if(is == null) {
					log("Re-"+OPEN_FOR_READING+pathInJar);
					is = LibLoader.class.getResourceAsStream(pathInJar);
					if(is == null) {
						log("Re-Re-"+OPEN_FOR_READING+pathInJar);
						is = Class.class.getResourceAsStream(pathInJar);
					}
				}
				if(is != null) {
					log("Open succeed.");
					//Create output directory
					String destDir = getProperty("java.io.tmpdir") + File.separator + "LibLoader" + File.separator;	//TODO Add version
					File dir = new File(destDir);
					log("Creating destination : "+destDir);
					dir.mkdirs();
					File fileOut = new File(dir, fullName);
					try {
						//Write library to output directory
						BufferedInputStream in = new BufferedInputStream(is);
						log("Opening output");
						BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileOut));
						log("Writting to output");
						byte[] readBuffer = new byte[8 * 1024];
						int bytesRead = 0;
						while((bytesRead = is.read(readBuffer)) > 0) {
							out.write(readBuffer, 0, bytesRead);
						}
						log("Closing files");
						in.close();
						out.close();
						log("Extraction succeed");
						return destDir;
					} catch(Throwable t) {
						log(t);
						if(exists(fileOut)) {
							return destDir;
						}
					}
				}
			} catch(Throwable t) {
				log(t);
			}
			return null;
		}});
	}
	
	/* Debug */
	private static void checkDebug() {
		//Force debug
		if(!DEBUG) {
			final String property = getProperty("jouvieje.libloader.debug");
			if(property != null) {
				final boolean forceDebug = Boolean.valueOf(property).booleanValue();
				if(forceDebug) {
					DEBUG = true;
				}
			}
		}
	}
	private static void log(String s) {
		if(DEBUG) {
			java.lang.System.out.println(s);
		}
	}
	private static void log(Throwable t) {
		if(DEBUG) {
			t.printStackTrace();
		}
	}
}