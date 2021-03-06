/* Jalview - a java multiple alignment editor
 * Copyright (C) 1998  Michele Clamp
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package jalview.io;

import java.util.*;
import java.io.*;
import java.net.*;

public class FileParse {
  public File   inFile;
  public int    fileSize;
  public byte[] dataArray;
  public Vector lineArray;
  public int    noLines;

  int           bytes_read = 0;
  String        inType;
  URL           url;
  URLConnection urlconn;

  public FileParse() {}

  public FileParse(String fileStr, String type) throws MalformedURLException, IOException {

    this.inType = type;

    System.out.println("Input type = " + type);
    System.out.println("Input name = " + fileStr);

    if (type.equals("File")) {
      this.inFile = new File(fileStr);
      this.fileSize = (int)inFile.length();

      System.out.println("File: " + inFile);
      System.out.println("Bytes: " + fileSize);

    } else if (type.equals("URL")) {
      url = new URL(fileStr);
      this.fileSize = 0;
      urlconn = url.openConnection();
      //  printinfo(urlconn);

    }
    else {
      System.out.println("Unknwon FileParse inType " + inType);
    }
  }

  public void readLines(String inStr) {
    StringTokenizer str = new StringTokenizer(inStr,"\n");
    lineArray = new Vector();
    while (str.hasMoreTokens()) {
      lineArray.addElement(str.nextToken());
    }
    noLines = lineArray.size();
  }

  public void readLines() throws IOException {
    String line;
    this.lineArray = new Vector();
    DataInputStream dataIn;

    if (inType.equals("File")) {
      //Using a bis reduces the file reading time by about a factor of 3
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inFile));
      dataIn = new DataInputStream(bis);
    } else {
      dataIn = new DataInputStream(urlconn.getInputStream());
    }
    while ((line = dataIn.readLine()) != null) {
      lineArray.addElement(line);
    }
    noLines = lineArray.size();
  }

  public Vector splitLine(char splitChar, int element) {
    Vector letters = new Vector();
    Vector wordVector = new Vector();

    String line = (String)lineArray.elementAt(element);
    char[] charArray = line.toCharArray();

    int i = 0;
    int letter = 0;
    char[] word = new char[line.length()];

    char prev_char = '\n';

    //System.out.println("\nBefore loop");
    //    System.out.println("line " + line + "\nsplitChar :" + splitChar + ":");
    //System.out.println(line.length());

    for (i = 0; i < line.length() ; i++ ) {
      if (charArray[i] != splitChar) {
        word[letter] = charArray[i];
        prev_char = charArray[i];
        letter++;
      } else {
        if ((prev_char != splitChar) && (prev_char != '\n')) {
          wordVector.addElement(new String(word));
          letter = 0;
          word = null;
          word = new char[line.length()];
          prev_char = charArray[i];
          //	  System.out.println("word: " + wordVector.lastElement() + ":");
        }

      }
    }

    //Tack on the last word into the vector - unless we have an empty line
    //or if we have a splitchar at the end of the line
    if (line.length() != 0) {
      if (charArray[line.length() - 1] != splitChar) {
        wordVector.addElement(new String(word));
      }
    } else {
      //returns null vector if empty line
      return(null);
    }

    return(wordVector);
  }
}
