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

public class BASS_BFX_MIX extends Pointer {
	/**
	 * Create a view of the <code>Pointer</code> object as a <code>BASS_BFX_MIX</code> object.<br>
	 * This view is valid only if the memory holded by the <code>Pointer</code> holds a BASS_BFX_MIX object.
	 */
	public static BASS_BFX_MIX asBASS_BFX_MIX(Pointer pointer) {
		long address = Pointer.getPointer(pointer);
		if(address == 0) return null;
		return new BASS_BFX_MIX(address);
	}
	/**
	 * Allocate a new <code>BASS_BFX_MIX</code>.<br>
	 * The call <code>isNull()</code> on the object created will return false.<br>
	 * <pre><code>  BASS_BFX_MIX obj = BASS_BFX_MIX.allocate();
	 *  (obj == null) <=> obj.isNull() <=> false
	 * </code></pre>
	 */
	public static BASS_BFX_MIX allocate() {
		final long pointer = StructureJNI.BASS_BFX_MIX_new();
		if(pointer == 0) throw new OutOfMemoryError();
		return new BASS_BFX_MIX(pointer);
	}

	protected BASS_BFX_MIX(long pointer) {
		super(pointer);
	}

	/**
	 * Create an object that holds a null <code>BASS_BFX_MIX</code>.<br>
	 * The call <code>isNull()</code> on the object created will returns true.<br>
	 * <pre><code>  BASS_BFX_MIX obj = new BASS_BFX_MIX();
	 *  (obj == null) <=> false
	 *  obj.isNull() <=> true
	 * </code></pre>
	 * To creates a new <code>BASS_BFX_MIX</code>, use the static "constructor" :
	 * <pre><code>  BASS_BFX_MIX obj = BASS_BFX_MIX.allocate();</code></pre>
	 * @see BASS_BFX_MIX#allocate()
	 */
	public BASS_BFX_MIX() {
		super();
	}

	public void release() {
		if(pointer != 0) {
			StructureJNI.BASS_BFX_MIX_delete(pointer);
		}
		pointer = 0;
	}

	/**
	 * an array of channels to mix using BASS_BFX_CHANxxx flag/s (lChannel[0] is left channel...)
	 */
	public void setChannel(IntBuffer channel) {
		if(pointer == 0) throw new NullPointerException();
		StructureJNI.BASS_BFX_MIX_set_lChannel(pointer, channel, BufferUtils.getPositionInBytes(channel));
	}

}