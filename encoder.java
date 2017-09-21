
package ADSHuffman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class encoder {
	
	static final String encoded = "encoded.bin";
	
	public static void main(String args[]) {

		Huffman huffman = new Huffman();

		/* HashMap for Frequency Table */
		HashMap<Integer, Integer> freq_table = new HashMap<Integer, Integer>();
		try {

			String input_file = args[0];
			long startTime1 = System.currentTimeMillis();
			freq_table = huffman.build_freq_table(input_file);

			// huffman.build_tree_using_pairing_heap(freq_table);

			// huffman.build_tree_using_FourAry_heap(freq_table);

			huffman.build_tree_using_binary_heap(freq_table);

			make_fullCode_string(input_file);
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime1;
			System.out.println("Time using binary heap (millisecond) till encoder : " + totalTime);

		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception : " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
			
	/* Make a string of fullCode using codeTableHashmap after reading from input file */
	public static void make_fullCode_string(String input_file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(input_file));
			StringBuilder fullCode = new StringBuilder();
			String line = "";
			while ((line = br.readLine()) != null && !line.equals("")) {

				line = line.trim();
				int val = Integer.valueOf(line);
				fullCode.append(Huffman.codeTableHashMap.get(val));

			}
			br.close();

			/* Write this fullCode string to encoded.bin file */
			write_to_bin_file(fullCode.toString());
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found Exception makeString encoder: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception makeString encode: " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	/* Write the full Huffman code of input_file to encoded.bin file */
	public static void write_to_bin_file(String code) throws IOException {
		FileOutputStream file_out = new FileOutputStream(encoded);
		int i = 0; 
		byte[] bytearray = new byte[code.length()/8];
		int count = 0;
		while(i < code.length() - 7){ 
			byte nextbyte = 0x00;
			for(int j = 0 ; j < 8; j++){
				nextbyte  = (byte) (nextbyte << 1);
				nextbyte += code.charAt(i+j) == '0'?0x0:0x1;
			}
			bytearray[count] = nextbyte;
			count++;
			i += 8;
		}
		file_out.write(bytearray);
	}
}
