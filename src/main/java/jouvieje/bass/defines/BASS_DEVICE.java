/* DO NOT EDIT - AUTOGENERATED */
/**
 * 				NativeBass Project
 *
 * Want to use BASS (www.un4seen.com) in the Java language ? NativeBass is made for you.
 * Copyright @ 2007-2011 Jérôme Jouvie
 *
 * Created on 02 jul. 2007
 * @version file v1.1.1
 * @author Jérôme Jouvie (Jouvieje)
 * @site   http://jerome.jouvie.free.fr/
 * @mail   jerome.jouvie@gmail.com
 * 
 * 
 * INTRODUCTION
 * BASS is an audio library for use in Windows and Mac OSX software.
 * Its purpose is to provide developers with the most powerful and
 * efficient (yet easy to use), sample, stream (MP3, MP2, MP1, OGG, WAV, AIFF,
 * custom generated, and more via add-ons), MOD music (XM, IT, S3M, MOD, MTM, UMX),
 * MO3 music (MP3/OGG compressed MODs),
 * and recording functions. All in a tiny DLL, under 100KB* in size.
 * 
 * BASS official web site :
 * 		http://www.un4seen.com/
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

package jouvieje.bass.defines;

/**
 * BASS_Init flags [NAME] BASS_DEVICE
 */
public interface BASS_DEVICE {
	/** use 8 bit resolution, else 16 bit */
	public static final int BASS_DEVICE_8BITS = 1;
	/** use mono, else stereo */
	public static final int BASS_DEVICE_MONO = 2;
	/** enable 3D functionality */
	public static final int BASS_DEVICE_3D = 4;
	/** calculate device latency (BASS_INFO struct) */
	public static final int BASS_DEVICE_LATENCY = 256;
	/** detect speakers via Windows control panel */
	public static final int BASS_DEVICE_CPSPEAKERS = 1024;
	/** force enabling of speaker assignment */
	public static final int BASS_DEVICE_SPEAKERS = 2048;
	/** ignore speaker arrangement */
	public static final int BASS_DEVICE_NOSPEAKER = 4096;
	/** IDirectSound */
	public static final int BASS_OBJECT_DS = 1;
	/** IDirectSound3DListener */
	public static final int BASS_OBJECT_DS3DL = 2;
}