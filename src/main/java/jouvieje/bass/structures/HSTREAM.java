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

/**
 * sample stream handle
 */
public class HSTREAM extends Pointer {
	/**
	 * Create a view of the <code>Pointer</code> object as a <code>HSTREAM</code> object.<br>
	 * This view is valid only if the memory holded by the <code>Pointer</code> holds a HSTREAM object.
	 */
	public static HSTREAM asHSTREAM(Pointer pointer) {
		long address = Pointer.getPointer(pointer);
		if(address == 0) return null;
		return new HSTREAM(address);
	}
	protected HSTREAM(long pointer) {
		super(pointer);
	}

	/**
	 * Create an object that holds a null <code>HSTREAM</code>.<br>
	 * The call <code>isNull()</code> on the object created will returns true.<br>
	 * <pre><code>  HSTREAM obj = new HSTREAM();
	 *  (obj == null) <=> false
	 *  obj.isNull() <=> true
	 * </code></pre>
	 * To creates a new <code>HSTREAM</code>, use the static "constructor" :
	 * <pre><code>  HSTREAM obj = HSTREAM.allocate();</code></pre>
	 */
	public HSTREAM() {
		super();
	}

	public void release() {
		pointer = 0;
	}

}