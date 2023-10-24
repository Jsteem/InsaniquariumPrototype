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

package jouvieje.bass.enumerations;

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
import java.util.HashMap;

/**
 * translation options [NAME] BASS_FX_BPM_TRAN
 */
public class BASS_FX_BPM_TRAN implements Enumeration, Comparable {
	/**  */
	public final static BASS_FX_BPM_TRAN BASS_FX_BPM_TRAN_X2 = new BASS_FX_BPM_TRAN("BASS_FX_BPM_TRAN_X2", EnumerationJNI.get_BASS_FX_BPM_TRAN_X2());
	/**  */
	public final static BASS_FX_BPM_TRAN BASS_FX_BPM_TRAN_2FREQ = new BASS_FX_BPM_TRAN("BASS_FX_BPM_TRAN_2FREQ", EnumerationJNI.get_BASS_FX_BPM_TRAN_2FREQ());
	/**  */
	public final static BASS_FX_BPM_TRAN BASS_FX_BPM_TRAN_FREQ2 = new BASS_FX_BPM_TRAN("BASS_FX_BPM_TRAN_FREQ2", EnumerationJNI.get_BASS_FX_BPM_TRAN_FREQ2());
	/**  */
	public final static BASS_FX_BPM_TRAN BASS_FX_BPM_TRAN_2PERCENT = new BASS_FX_BPM_TRAN("BASS_FX_BPM_TRAN_2PERCENT", EnumerationJNI.get_BASS_FX_BPM_TRAN_2PERCENT());
	/**  */
	public final static BASS_FX_BPM_TRAN BASS_FX_BPM_TRAN_PERCENT2 = new BASS_FX_BPM_TRAN("BASS_FX_BPM_TRAN_PERCENT2", EnumerationJNI.get_BASS_FX_BPM_TRAN_PERCENT2());

	private final static HashMap VALUES = new HashMap(2*5);
	static {
		VALUES.put(new Integer(BASS_FX_BPM_TRAN_X2.asInt()), BASS_FX_BPM_TRAN_X2);
		VALUES.put(new Integer(BASS_FX_BPM_TRAN_2FREQ.asInt()), BASS_FX_BPM_TRAN_2FREQ);
		VALUES.put(new Integer(BASS_FX_BPM_TRAN_FREQ2.asInt()), BASS_FX_BPM_TRAN_FREQ2);
		VALUES.put(new Integer(BASS_FX_BPM_TRAN_2PERCENT.asInt()), BASS_FX_BPM_TRAN_2PERCENT);
		VALUES.put(new Integer(BASS_FX_BPM_TRAN_PERCENT2.asInt()), BASS_FX_BPM_TRAN_PERCENT2);
	}

	private final String name;
	private final int nativeValue;
	private BASS_FX_BPM_TRAN(String name, int nativeValue) {
		this.name = name;
		this.nativeValue = nativeValue;
	}

	public int asInt() {
		return nativeValue;
	}
	public String toString() {
		return name;
	}
	public boolean equals(Object object) {
		if(object instanceof BASS_FX_BPM_TRAN) {
			return asInt() == ((BASS_FX_BPM_TRAN)object).asInt();
		}
		return false;
	}
	public int compareTo(Object object) {
		return asInt() - ((BASS_FX_BPM_TRAN)object).asInt();
	}


	/**
	 * Retrieve a BASS_FX_BPM_TRAN enum field with his integer value
	 * @param nativeValue the integer value of the field to retrieve
	 * @return the BASS_FX_BPM_TRAN enum field that correspond to the integer value
	 */
	public static BASS_FX_BPM_TRAN get(int nativeValue) {
		return (BASS_FX_BPM_TRAN)VALUES.get(new Integer(nativeValue));
	}

	/**
	 * Retrieve a BASS_FX_BPM_TRAN enum field from a Pointer
	 * @param pointer a pointer holding an BASS_FX_BPM_TRAN enum field
	 * @return the BASS_FX_BPM_TRAN enum field that correspond to the enum field in the pointer
	 */
	public static BASS_FX_BPM_TRAN get(Pointer pointer) {
		return get(pointer.asInt());
	}

	/**
	 * @return an <code>Iterator</code> over the elements in this enumeration.<BR>
	 * Can be cast to <code>Iterator<BASS_FX_BPM_TRAN></code> in Java 1.5.
	 */
	public static java.util.Iterator iterator() {
		return new java.util.Iterator(){
			private java.util.Iterator i = VALUES.values().iterator();	//Wrapper of the HashMap iterator
			public boolean hasNext() { return i.hasNext(); }
			public Object next() { return i.next(); }
			public void remove() { throw new UnsupportedOperationException(); }
		};
	}
}