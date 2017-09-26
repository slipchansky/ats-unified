package com.genesys.ats.basics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Util {
	
	
	protected static String extractStackTrace(InputStream in) throws IOException {
		List <String> lines = new ArrayList<>(); 
		try (Scanner scanner = new Scanner (in)) {
			while (scanner.hasNext()) {
				String l = scanner.nextLine();
				lines.add(l);
			}
		}
		
		int j = 0;
		int indent = 1000;
		for (j = lines.size()-1; j>=0; j--) {
			String l = lines.get (j);
			int i = indent (l);
			if (i > 0) {
				indent=i;
				break;
			}
		}
		
		
		List<String> finalLines = new ArrayList<>();
		
		for (; j>=0; j--) {
			String l = lines.get (j);
			int i = indent (l);
			if (i <= indent && i > 0) {
				if (i < indent) {
				   indent = i;
				}
				finalLines.add(l);
			}
		}
		
		String stackTrace = "";
		
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream ()) {
			try (PrintStream out = new PrintStream (bos)) {
				for (j = finalLines.size()-1; j>=0; j--) {
					String s = finalLines.get (j);
					out.println(s);
				}
				
				out.println ("\n\n----------------------------------------------------------------------\ntail of execution:\n");
				int i = lines.size() - 30;
				if (i<0) i = 0;
				for (;i<lines.size(); i++) {
					out.println (lines.get(i));
				}
				
				
				out.flush ();
				
			}

			
			bos.flush();
			stackTrace = new String (bos.toByteArray());
		}
		return stackTrace;
	}

	private static int indent(String l) {
		char[] a = l.toCharArray();
		for (int i=0; i<a.length; i++) if (a[i]!=' ') return i;
		return a.length;
	}

	public static String extractStackTrace(byte[] byteArray) throws IOException {
		try (ByteArrayInputStream in = new ByteArrayInputStream (byteArray)) {
			return extractStackTrace (in); 
		}
	}

}
