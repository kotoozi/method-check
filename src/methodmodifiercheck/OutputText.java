package methodmodifiercheck;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OutputText {
	/**
	 * 出力先のファイル名
	 */
	private String _outputFile;
	/**
	 * コンストラクタ
	 * @param outputFile メインクラスで指定される出力先のファイル
	 */
	public OutputText(String outputFile) {
		_outputFile = outputFile;
		Reset();
	}
	/**
	 * ファイルに出力する
	 * @param content 出力するテキスト情報
	 */
	public void WriteToFile(String content) {
		try {
			FileWriter fWriter = new FileWriter(_outputFile, true);
			PrintWriter pWriter = new PrintWriter(new BufferedWriter(fWriter));
			pWriter.print(content);
			pWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ファイルに改行コード込みで出力する
	 * @param content 出力するテキスト情報
	 */
	public void WriteToFileLn(String content) {
		try {
			FileWriter fWriter = new FileWriter(_outputFile, true);
			PrintWriter pWriter = new PrintWriter(new BufferedWriter(fWriter));
			pWriter.println(content);
			pWriter.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 出力先のファイルの情報を初期化する
	 */
	private void Reset() {
	    File file = new File(_outputFile);

	    if (file.exists()){
	      if (!file.delete()){
	        System.out.println("ファイルの削除に失敗しました");
	      }
	    }else{
	      System.out.println("ファイルが見つかりませんので新しく生成します");
	    }
	}
}
