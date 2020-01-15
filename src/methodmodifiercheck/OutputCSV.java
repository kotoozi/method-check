package methodmodifiercheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OutputCSV {
	private String _outputFile;
	public OutputCSV(String outputFile) {
		_outputFile = outputFile;
	}
	public void WriteToFile(List<Integer> input) {
		System.out.println("サイズは"+input.size());
		try {
			FileWriter fWriter = new FileWriter(_outputFile, true);
			PrintWriter pWriter = new PrintWriter(new BufferedWriter(fWriter));
			for(int i = 0;i<input.size();++i) {
				pWriter.print(input.get(i));
				if(i == input.size() - 1) break;
				pWriter.print(", ");
			}
			pWriter.println();
			pWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void Reset() {
	    File file = new File(_outputFile);

	    if (file.exists()){
	      if (!file.delete()){
	        System.out.println("ファイルの削除に失敗しました");
	      }
	    }else{
	      System.out.println("ファイルが見つかりません");
	    }
	}
}
