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

package jouvieje.bass.structures;

import jouvieje.bass.*;
import jouvieje.bass.exceptions.*;
import jouvieje.bass.callbacks.*;
import jouvieje.bass.*;
import jouvieje.bass.defines.*;
import jouvieje.bass.enumerations.*;
import jouvieje.bass.structures.*;
import java.nio.*;
import jouvieje.bass.*;
import jouvieje.bass.enumerations.*;
import jouvieje.bass.structures.*;
import jouvieje.bass.utils.*;

public class TAG_FLAC_CUE extends Pointer {
	/**
	 * Create a view of the <code>Pointer</code> object as a <code>TAG_FLAC_CUE</code> object.<br>
	 * This view is valid only if the memory holded by the <code>Pointer</code> holds a TAG_FLAC_CUE object.
	 */
	public static TAG_FLAC_CUE asTAG_FLAC_CUE(Pointer pointer) {
		long address = Pointer.getPointer(pointer);
		if(address == 0) return null;
		return new TAG_FLAC_CUE(address);
	}
	/**
	 * Allocate a new <code>TAG_FLAC_CUE</code>.<br>
	 * The call <code>isNull()</code> on the object created will return false.<br>
	 * <pre><code>  TAG_FLAC_CUE obj = TAG_FLAC_CUE.allocate();
	 *  (obj == null) <=> obj.isNull() <=> false
	 * </code></pre>
	 */
	public static TAG_FLAC_CUE allocate() {
		final long pointer = StructureJNI.TAG_FLAC_CUE_new();
		if(pointer == 0) throw new OutOfMemoryError();
		return new TAG_FLAC_CUE(pointer);
	}

	protected TAG_FLAC_CUE(long pointer) {
		super(pointer);
	}

	/**
	 * Create an object that holds a null <code>TAG_FLAC_CUE</code>.<br>
	 * The call <code>isNull()</code> on the object created will returns true.<br>
	 * <pre><code>  TAG_FLAC_CUE obj = new TAG_FLAC_CUE();
	 *  (obj == null) <=> false
	 *  obj.isNull() <=> true
	 * </code></pre>
	 * To creates a new <code>TAG_FLAC_CUE</code>, use the static "constructor" :
	 * <pre><code>  TAG_FLAC_CUE obj = TAG_FLAC_CUE.allocate();</code></pre>
	 * @see TAG_FLAC_CUE#allocate()
	 */
	public TAG_FLAC_CUE() {
		super();
	}

	public void release() {
		if(pointer != 0) {
			StructureJNI.TAG_FLAC_CUE_delete(pointer);
		}
		pointer = 0;
	}

	/**
	 * media catalog number
	 */
	public String getCatalog() {
		if(pointer == 0) throw new NullPointerException();
		String javaResult = StructureJNI.TAG_FLAC_CUE_get_catalog(pointer);
		return javaResult;
	}
	/**
	 * media catalog number
	 */
	public void setCatalog(String catalog) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.TAG_FLAC_CUE_set_catalog(pointer, catalog == null ? null : catalog.getBytes());
	}

	/**
	 * lead-in (samples)
	 */
	public int getLeadIn() {
		if(pointer == 0) throw new NullPointerException();
		int javaResult = StructureJNI.TAG_FLAC_CUE_get_leadin(pointer);
		return javaResult;
	}
	/**
	 * lead-in (samples)
	 */
	public void setLeadIn(int leadIn) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.TAG_FLAC_CUE_set_leadin(pointer, leadIn);
	}

	/**
	 * a CD?
	 */
	public boolean getIsCd() {
		if(pointer == 0) throw new NullPointerException();
		boolean javaResult = StructureJNI.TAG_FLAC_CUE_get_iscd(pointer);
		return javaResult;
	}
	/**
	 * a CD?
	 */
	public void setIsCd(boolean isCd) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.TAG_FLAC_CUE_set_iscd(pointer, isCd);
	}

	/**
	 * number of tracks
	 */
	public int getNumTracks() {
		if(pointer == 0) throw new NullPointerException();
		int javaResult = StructureJNI.TAG_FLAC_CUE_get_ntracks(pointer);
		return javaResult;
	}
	/**
	 * number of tracks
	 */
	public void setNumTracks(int numTracks) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.TAG_FLAC_CUE_set_ntracks(pointer, numTracks);
	}

	/**
	 * the tracks
	 */
	public TAG_FLAC_CUE_TRACK getTracks() {
		if(pointer == 0) throw new NullPointerException();
		long javaResult = StructureJNI.TAG_FLAC_CUE_get_tracks(pointer);
		return javaResult == 0 ? null : TAG_FLAC_CUE_TRACK.asTAG_FLAC_CUE_TRACK(Pointer.newPointer(javaResult));
	}
	/**
	 * the tracks
	 */
	public void setTracks(TAG_FLAC_CUE_TRACK tracks) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.TAG_FLAC_CUE_set_tracks(pointer, Pointer.getPointer(tracks));
	}

}